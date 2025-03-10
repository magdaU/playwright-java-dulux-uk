package com.github.magdalena.tests.purchase;

import java.nio.file.Paths;
import java.util.Arrays;
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
import com.microsoft.playwright.options.LoadState;

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
        context = browser.newContext(new Browser.NewContextOptions()

                .setLocale("pl-PL") // Set the locale to English (United Kingdom)

                .setGeolocation(52.2296756, 21.0122287) // Set the geolocation to London, UK

               .setPermissions(Arrays.asList("geolocation"))); // Grant geolocation permissions
        page = context.newPage();
        navigationPage = new NavigationComponent(page);
        colorSelectionPage = new ColorSelectionPage(page);
        cartPage = new CartPage(page);
        homePage = new HomePage(page);
        alertComponent = new AlertComponent(page);
    }

    @Test
    void shouldAddTesterToCart() {
        // GIVEN
        String colour = "Violet";
        String colourType = "Gentle Lavender";

        cartPage.openCartPage();
        homePage.rejectAllCookies();
        cartPage.checkBasketIsEmpty();

        // WHEN
        homePage.openHomePage();
        navigationPage.dropdownFindColour();
        navigationPage.openFindColour();
        colorSelectionPage.chooseColour(colour);
        colorSelectionPage.choseSpecificTypeColor(colourType);
        colorSelectionPage.buyATesterColour();
        alertComponent.closeAlert();
        navigationPage.openShoppingCart();

        // THEN
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("Screenshots/TesterProductTest/LastScreenShoot.png")));

        assertThat(cartPage.getQuantity()).hasValue("1");
        assertThat(cartPage.findText("Dulux Colour Tester")).isVisible();
        assertThat(cartPage.findText(colourType)).isVisible();

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