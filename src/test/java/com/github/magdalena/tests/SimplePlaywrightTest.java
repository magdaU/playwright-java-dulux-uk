package com.github.magdalena.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.magdalena.pom.AlertComponent;
import com.github.magdalena.pom.CartPage;
import com.github.magdalena.pom.ColorSelectionPage;
import com.github.magdalena.pom.HomePage;
import com.github.magdalena.pom.NavigationPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SimplePlaywrightTest {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;

    private HomePage homePage;
    private NavigationPage navigationPage;
    private ColorSelectionPage colorSelectionPage;
    private CartPage cartPage;
    private AlertComponent alertComponent;


    @BeforeAll
    static void setUpAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    void setUp() {
        context = browser.newContext();
        page = context.newPage();
        navigationPage = new NavigationPage(page);
        colorSelectionPage = new ColorSelectionPage(page);
        cartPage = new CartPage(page);
        homePage = new HomePage(page);
        alertComponent = new AlertComponent(page);
    }

    @Test
    void loop() {
        for (int i = 0; i < 100; i++) {
            setUpAll();
            setUp();
            Instant now = Instant.now();
            colourTesterShouldBeVisibleInCart();
            Instant then = Instant.now();
            System.out.println("Test " + i + " passed : " + Duration.between(now, then).toMillis() + " ms");
            tearDown();
            tearDownAll();
            for (int ii = 1; ii <= 14; ii++) {
                Path path = Paths.get(i + ".png");
                try {
                    Files.deleteIfExists(path);

                } catch (Exception e) {
                    System.err.println("Failed to delete: " + path + " due to " + e.getMessage());
                }
            }
        }
    }

    @Test
    void colourTesterShouldBeVisibleInCart() {
        // Sprawdzic czy koszyk jest pusty
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("1.png"))
                .setFullPage(true));
        cartPage.openCartPage();
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("2.png"))
                .setFullPage(true));
        homePage.rejectAllCookies();
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("3.png"))
                .setFullPage(true));

        cartPage.checkBasketIsEmpty();
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("4.png"))
                .setFullPage(true));
        homePage.openHomePage();

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("5.png"))
                .setFullPage(true));
        navigationPage.dropdownFindColour();
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("6.png"))
                .setFullPage(true));
        navigationPage.openFindColour();

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("7.png"))
                .setFullPage(true));
        colorSelectionPage.chooseColour();
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("8.png"))
                .setFullPage(true));
        colorSelectionPage.chooseSpecificTypeColorAndBuyTester();

        // AlertPage
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("9.png"))
                .setFullPage(true));
        alertComponent.closeAlert();

        // NavigationPage
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("10.png"))
                .setFullPage(true));
        navigationPage.openShoppingCart();

        // Assert
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("11.png"))
                .setFullPage(true));
        assertThat(cartPage.getQuantity()).hasValue("1");
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("12.png"))
                .setFullPage(true));
        assertThat(cartPage.findText("Dulux Colour Tester")).isVisible();
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("13.png"))
                .setFullPage(true));
        assertThat(cartPage.findText("Gentle Lavender")).isVisible();
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("14.png"))
                .setFullPage(true));
    }

    @Test
    void searchColorAndTryOurVisualizerApp() {
        homePage.openHomePage();
        homePage.rejectAllCookies();

        navigationPage.searchClickonPage();
        navigationPage.inputColorOnSearchBoxAndEnter();

        Page newPage = context.waitForPage(() -> {
            colorSelectionPage.openVisualizerApp();
        });
        Assertions.assertThat(newPage.url()).isEqualTo("https://www.dulux.co.uk/en/articles/dulux-visualizer-app");

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