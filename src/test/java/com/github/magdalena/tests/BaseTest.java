package com.github.magdalena.tests;

import com.github.magdalena.page.pom.ColorSelectionPage;
import com.github.magdalena.page.pom.HomePage;
import com.github.magdalena.page.component.NavigationComponent;
import com.github.magdalena.support.PlaywrightConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected Page page;

    protected HomePage homePage;
    protected NavigationComponent navigationPage;
    protected ColorSelectionPage colorSelectionPage;

    @BeforeAll
    static void setUpAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(PlaywrightConfig.isHeadless()));
    }

    protected void setUpDesktop() {
        createSetup(1920, 1080);
    }

    protected void setUpMobile() {
        createSetup(375, 667);
    }

    protected void createSetup(int width, int height) {
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(width, height));
        page = context.newPage();
        navigationPage = new NavigationComponent(page);
        colorSelectionPage = new ColorSelectionPage(page);
        homePage = new HomePage(page);
    }

    @AfterEach
    void tearDown() {
        context.close();
    }

    @AfterAll
    static void tearDownAll() {
        browser.close();
        playwright.close();
    }
}

