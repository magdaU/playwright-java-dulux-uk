package com.github.magdalena.cucumber;

import com.github.magdalena.page.pom.CartPage;
import com.github.magdalena.page.pom.ColorSelectionPage;
import com.github.magdalena.page.pom.HomePage;
import com.github.magdalena.page.pom.SearchResultsPage;
import com.github.magdalena.support.BrowserSession;
import com.github.magdalena.support.OfflineSite;
import com.github.magdalena.support.Viewport;
import com.microsoft.playwright.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CucumberContext {

    private static final int MAX_DIAGNOSTIC_ENTRIES = 20;

    private final List<String> consoleErrors = Collections.synchronizedList(new ArrayList<>());
    private final List<String> failedResponses = Collections.synchronizedList(new ArrayList<>());

    private BrowserSession session;
    private Page visualizerTab;
    private boolean offline;
    private OfflineSite offlineSite;

    public void initBrowser() {
        session = new BrowserSession();
    }

    /** Serves the site from local stubs for the rest of the scenario; call before choosing a viewport. */
    public void goOffline() {
        offline = true;
    }

    public OfflineSite offlineSite() {
        return offlineSite;
    }

    public void useViewport(String viewport) {
        // Routes must exist before the page's first navigation.
        session.open(Viewport.fromName(viewport),
                context -> offlineSite = offline ? new OfflineSite(context) : null);
        recordBrowserDiagnostics();
        visualizerTab = null;
    }

    public void openEmptyCart() {
        session.cartPage().openCartPage();
        session.homePage().rejectAllCookies();
    }

    public void openHomePage() {
        session.homePage().openHomePage();
    }

    public void openHomePageAndRejectCookies() {
        session.homePage().openHomePage();
        session.homePage().rejectAllCookies();
    }

    public void browseToShade(String colourFamily, String shade, boolean mobileNavigation) {
        openHomePage();
        openColourFinder(mobileNavigation);
        session.colorSelectionPage().chooseColour(colourFamily);
        session.colorSelectionPage().chooseShade(shade);
    }

    public void openColourFinder(boolean mobileNavigation) {
        if (mobileNavigation) {
            session.navigationPage().clickDropdownHamburgerMenu();
        }

        session.navigationPage().clickDropdownFindColour();
        session.navigationPage().clickFindColour();
    }

    public ColorSelectionPage colorSelectionPage() {
        return session.colorSelectionPage();
    }

    public void searchForShade(String shade) {
        session.navigationPage().searchClickOnPage();
        session.navigationPage().inputColorOnSearchBoxAndEnter(shade);
    }

    public void addTesterToBasket() {
        session.colorSelectionPage().buyATesterColour();
        session.alertComponent().closeAlert();
    }

    public void openShoppingCart() {
        session.navigationPage().openShoppingCart();
    }

    public void openVisualizerExperience() {
        if (isDesktop()) {
            visualizerTab = session.context().waitForPage(session.colorSelectionPage()::openVisualizerApp);
            return;
        }

        session.colorSelectionPage().openVisualizerApp();
    }

    /**
     * Plain-text snapshot of where the browser was when a scenario failed, attached to the
     * Allure report so the failure reason is visible without re-running the test.
     */
    public String describeFailureContext() {
        if (!hasOpenPage()) {
            return "Browser page was not available.";
        }

        Page page = session.page();
        StringBuilder sb = new StringBuilder()
                .append("URL: ").append(page.url()).append('\n')
                .append("Title: ").append(page.title()).append('\n')
                .append("Viewport: ").append(isDesktop() ? "desktop" : "mobile").append('\n');
        appendSection(sb, "Console errors", consoleErrors);
        appendSection(sb, "Failed or error HTTP responses", failedResponses);
        return sb.toString();
    }

    private static void appendSection(StringBuilder sb, String title, List<String> entries) {
        sb.append('\n').append(title).append(": ");
        synchronized (entries) {
            if (entries.isEmpty()) {
                sb.append("none\n");
                return;
            }
            sb.append('\n');
            entries.forEach(entry -> sb.append("  - ").append(entry).append('\n'));
        }
    }

    private void recordBrowserDiagnostics() {
        Page page = session.page();
        consoleErrors.clear();
        failedResponses.clear();
        page.onConsoleMessage(message -> {
            if ("error".equals(message.type())) {
                record(consoleErrors, message.text());
            }
        });
        page.onResponse(response -> {
            if (response.status() >= 400 && isFirstParty(response.url())) {
                record(failedResponses, response.status() + " " + response.request().method() + " " + withoutQuery(response.url()));
            }
        });
        page.onRequestFailed(request -> {
            if (isFirstParty(request.url())) {
                record(failedResponses, "FAILED " + request.method() + " " + withoutQuery(request.url()) + " - " + request.failure());
            }
        });
    }

    // Third-party failures (e.g. social media thumbnails returning 403) are noise in a failure report.
    private static boolean isFirstParty(String url) {
        return url.contains("dulux.co.uk");
    }

    private static String withoutQuery(String url) {
        int queryStart = url.indexOf('?');
        return queryStart < 0 ? url : url.substring(0, queryStart);
    }

    private static void record(List<String> target, String entry) {
        synchronized (target) {
            if (target.size() < MAX_DIAGNOSTIC_ENTRIES) {
                target.add(entry);
            }
        }
    }

    public byte[] takeScreenshot() {
        if (!hasOpenPage()) {
            return new byte[0];
        }

        return session.page().screenshot(new Page.ScreenshotOptions().setFullPage(true));
    }

    public CartPage cartPage() {
        return session.cartPage();
    }

    public HomePage homePage() {
        return session.homePage();
    }

    public SearchResultsPage searchResultsPage() {
        return session.searchResultsPage();
    }

    public Page page() {
        return session.page();
    }

    public Page visualizerTab() {
        return visualizerTab;
    }

    public boolean isDesktop() {
        return session != null && session.viewport() == Viewport.DESKTOP;
    }

    public void tearDown() {
        if (session != null) {
            session.close();
            session = null;
        }
    }

    private boolean hasOpenPage() {
        return session != null && session.hasOpenPage();
    }
}
