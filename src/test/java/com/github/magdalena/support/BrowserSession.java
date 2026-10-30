package com.github.magdalena.support;

import com.github.magdalena.page.component.AlertComponent;
import com.github.magdalena.page.component.NavigationComponent;
import com.github.magdalena.page.pom.CartPage;
import com.github.magdalena.page.pom.ColorSelectionPage;
import com.github.magdalena.page.pom.HomePage;
import com.github.magdalena.page.pom.SearchResultsPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import java.util.function.Consumer;

/**
 * Owns the Playwright lifecycle (Playwright, browser, one isolated browser context per
 * {@link #open}) and builds the page objects for the current page. Shared by the JUnit
 * base class and the Cucumber context.
 */
public final class BrowserSession implements AutoCloseable {

    private final Playwright playwright;
    private final Browser browser;

    private BrowserContext context;
    private Page page;
    private Viewport viewport;

    private HomePage homePage;
    private NavigationComponent navigationPage;
    private ColorSelectionPage colorSelectionPage;
    private CartPage cartPage;
    private SearchResultsPage searchResultsPage;
    private AlertComponent alertComponent;

    public BrowserSession() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(PlaywrightConfig.isHeadless())
        );
    }

    public Page open(Viewport newViewport) {
        return open(newViewport, newContext -> { });
    }

    /**
     * Starts a fresh, isolated context with the given viewport, closing any previous one.
     * {@code beforePage} runs after the context exists but before its page is created, so
     * routes registered there are in place before the page's first navigation.
     */
    public Page open(Viewport newViewport, Consumer<BrowserContext> beforePage) {
        closeContext();
        viewport = newViewport;
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(newViewport.width(), newViewport.height()));
        beforePage.accept(context);
        page = context.newPage();
        homePage = new HomePage(page);
        navigationPage = new NavigationComponent(page);
        colorSelectionPage = new ColorSelectionPage(page);
        cartPage = new CartPage(page);
        searchResultsPage = new SearchResultsPage(page);
        alertComponent = new AlertComponent(page);
        return page;
    }

    public boolean hasOpenPage() {
        return page != null && !page.isClosed();
    }

    public BrowserContext context() {
        return context;
    }

    public Page page() {
        return page;
    }

    public Viewport viewport() {
        return viewport;
    }

    public HomePage homePage() {
        return homePage;
    }

    public NavigationComponent navigationPage() {
        return navigationPage;
    }

    public ColorSelectionPage colorSelectionPage() {
        return colorSelectionPage;
    }

    public CartPage cartPage() {
        return cartPage;
    }

    public SearchResultsPage searchResultsPage() {
        return searchResultsPage;
    }

    public AlertComponent alertComponent() {
        return alertComponent;
    }

    @Override
    public void close() {
        closeContext();
        browser.close();
        playwright.close();
    }

    public void closeContext() {
        if (context != null) {
            context.close();
            context = null;
        }
    }
}
