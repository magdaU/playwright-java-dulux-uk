package com.github.magdalena.tests.purchase;

import java.nio.file.Paths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.magdalena.page.component.AlertComponent;
import com.github.magdalena.page.pom.CartPage;
import com.github.magdalena.page.pom.ColorSelectionPage;
import com.github.magdalena.page.pom.HomePage;
import com.github.magdalena.page.component.NavigationComponent;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TesterProductTest {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;

    private HomePage homePage;
    private NavigationComponent navigationPage;
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
        navigationPage = new NavigationComponent(page);
        colorSelectionPage = new ColorSelectionPage(page);
        cartPage = new CartPage(page);
        homePage = new HomePage(page);
        alertComponent = new AlertComponent(page);
    }

    // TODO Zmiana nazwy testu
    @Test
    void colourTesterShouldBeVisibleInCart() {
        // GIVEN
        String colour = "Violet";
        String colourType = "Gentle Lavender";

        // wydzielenie UTIL class dla screenshotow - parametr in: katalog oraz nazwa pliku
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

        // WHEN
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
        colorSelectionPage.chooseColour(colour);
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("8.png"))
                .setFullPage(true));
        colorSelectionPage.choseSpecificTypeColor(colourType);
        colorSelectionPage.buyATesterColour();

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

        // Then
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