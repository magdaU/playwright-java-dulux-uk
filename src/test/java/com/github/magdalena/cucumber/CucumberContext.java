package com.github.magdalena.cucumber;

import com.github.magdalena.page.component.AlertComponent;
import com.github.magdalena.page.component.NavigationComponent;
import com.github.magdalena.page.pom.CartPage;
import com.github.magdalena.page.pom.ColorSelectionPage;
import com.github.magdalena.page.pom.HomePage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class CucumberContext {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    public HomePage homePage;
    public NavigationComponent navigationPage;
    public ColorSelectionPage colorSelectionPage;
    public CartPage cartPage;
    public AlertComponent alertComponent;
    public Page newTab;

    public void initBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    public void initContext(int width, int height) {
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        homePage = new HomePage(page);
        navigationPage = new NavigationComponent(page);
        colorSelectionPage = new ColorSelectionPage(page);
        cartPage = new CartPage(page);
        alertComponent = new AlertComponent(page);
    }

    public BrowserContext getContext() {
        return context;
    }

    public Page getPage() {
        return page;
    }

    public void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}

