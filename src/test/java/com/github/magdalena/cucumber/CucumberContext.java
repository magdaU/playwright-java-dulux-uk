package com.github.magdalena.cucumber;

import com.github.magdalena.page.component.AlertComponent;
import com.github.magdalena.page.component.NavigationComponent;
import com.github.magdalena.page.pom.CartPage;
import com.github.magdalena.page.pom.ColorSelectionPage;
import com.github.magdalena.page.pom.HomePage;
import com.github.magdalena.page.pom.SearchResultsPage;
import com.github.magdalena.support.OfflineSite;
import com.github.magdalena.support.PlaywrightConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CucumberContext {

    private static final int DESKTOP_WIDTH = 1920;
    private static final int DESKTOP_HEIGHT = 1080;
    private static final int MOBILE_WIDTH = 375;
    private static final int MOBILE_HEIGHT = 667;

    private static final int MAX_DIAGNOSTIC_ENTRIES = 20;

    private final List<String> consoleErrors = Collections.synchronizedList(new ArrayList<>());
    private final List<String> failedResponses = Collections.synchronizedList(new ArrayList<>());

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private boolean desktop;

    private HomePage homePage;
    private NavigationComponent navigationPage;
    private ColorSelectionPage colorSelectionPage;
    private CartPage cartPage;
    private SearchResultsPage searchResultsPage;
    private AlertComponent alertComponent;
    private Page visualizerTab;
    private boolean offline;
    private OfflineSite offlineSite;

    public void initBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(PlaywrightConfig.isHeadless())
        );
    }

    /** Serves the site from local stubs for the rest of the scenario; call before choosing a viewport. */
    public void goOffline() {
        offline = true;
    }

    public OfflineSite offlineSite() {
        return offlineSite;
    }

    public void useViewport(String viewport) {
        if ("mobile".equalsIgnoreCase(viewport)) {
            initContext(MOBILE_WIDTH, MOBILE_HEIGHT);
            desktop = false;
            return;
        }

        initContext(DESKTOP_WIDTH, DESKTOP_HEIGHT);
        desktop = true;
    }

    public void openEmptyCart() {
        cartPage.openCartPage();
        homePage.rejectAllCookies();
    }

    public void openHomePage() {
        homePage.openHomePage();
    }

    public void openHomePageAndRejectCookies() {
        homePage.openHomePage();
        homePage.rejectAllCookies();
    }

    public void browseToShade(String colourFamily, String shade, boolean mobileNavigation) {
        openHomePage();
        openColourFinder(mobileNavigation);
        colorSelectionPage.chooseColour(colourFamily);
        colorSelectionPage.chooseShade(shade);
    }

    public void openColourFinder(boolean mobileNavigation) {
        if (mobileNavigation) {
            navigationPage.clickDropdownHamburgerMenu();
        }

        navigationPage.clickDropdownFindColour();
        navigationPage.clickFindColour();
    }

    public ColorSelectionPage colorSelectionPage() {
        return colorSelectionPage;
    }

    public void searchForShade(String shade) {
        navigationPage.searchClickOnPage();
        navigationPage.inputColorOnSearchBoxAndEnter(shade);
    }

    public void addTesterToBasket() {
        colorSelectionPage.buyATesterColour();
        alertComponent.closeAlert();
    }

    public void openShoppingCart() {
        navigationPage.openShoppingCart();
    }

    public void openVisualizerExperience() {
        if (desktop) {
            visualizerTab = context.waitForPage(colorSelectionPage::openVisualizerApp);
            return;
        }

        colorSelectionPage.openVisualizerApp();
    }

    /**
     * Plain-text snapshot of where the browser was when a scenario failed, attached to the
     * Allure report so the failure reason is visible without re-running the test.
     */
    public String describeFailureContext() {
        if (page == null || page.isClosed()) {
            return "Browser page was not available.";
        }

        StringBuilder sb = new StringBuilder()
                .append("URL: ").append(page.url()).append('\n')
                .append("Title: ").append(page.title()).append('\n')
                .append("Viewport: ").append(desktop ? "desktop" : "mobile").append('\n');
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
        if (page == null || page.isClosed()) {
            return new byte[0];
        }

        return page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
    }

    public CartPage cartPage() {
        return cartPage;
    }

    public HomePage homePage() {
        return homePage;
    }

    public SearchResultsPage searchResultsPage() {
        return searchResultsPage;
    }

    public Page page() {
        return page;
    }

    public Page visualizerTab() {
        return visualizerTab;
    }

    public boolean isDesktop() {
        return desktop;
    }

    public void tearDown() {
        if (context != null) {
            context.close();
            context = null;
        }
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    private void initContext(int width, int height) {
        if (context != null) {
            context.close();
        }

        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        // Routes must exist before the page's first navigation.
        offlineSite = offline ? new OfflineSite(context) : null;
        page = context.newPage();
        recordBrowserDiagnostics();
        homePage = new HomePage(page);
        navigationPage = new NavigationComponent(page);
        colorSelectionPage = new ColorSelectionPage(page);
        cartPage = new CartPage(page);
        searchResultsPage = new SearchResultsPage(page);
        alertComponent = new AlertComponent(page);
        visualizerTab = null;
    }
}
