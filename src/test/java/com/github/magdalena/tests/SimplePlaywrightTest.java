package com.github.magdalena.tests;

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
        browser = playwright.chromium().launch();
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
            colourTesterShouldBeVisibleInCart();
            System.out.println("Test " + i + " passed");
        }
    }

    @Test
    void colourTesterShouldBeVisibleInCart() {
        // Sprawdzic czy koszyk jest pusty
        cartPage.openCartPage();
        homePage.rejectAllCookies();

        cartPage.checkBasketIsEmpty();
        homePage.openHomePage();

        navigationPage.dropdownFindColour();
        navigationPage.openFindColour();

        colorSelectionPage.chooseColour();
        colorSelectionPage.chooseSpecificTypeColorAndBuyTester();

        // AlertPage
        alertComponent.closeAlert();

        // NavigationPage
        navigationPage.openShoppingCart();

        // Assert
        assertThat(cartPage.getQuantity()).hasValue("1");
        assertThat(cartPage.findText("Dulux Colour Tester")).isVisible();
        assertThat(cartPage.findText("Gentle Lavender")).isVisible();
    }

    @Test
    void addVioletColorProductToTheCart() {
        page.navigate("https://www.dulux.co.uk/");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reject All")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Find a colour")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Find a colour")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Violet")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Gentle Lavender")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Buy a Tester in this colour")).click();
        page.getByRole(AriaRole.ALERT).getByRole(AriaRole.BUTTON).click();


        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Shopping Cart")).click();
        page.getByText("Shopping Basket").isVisible();
        page.getByText("Gentle Lavender").isVisible();
        page.getByText("Dulux Colour Tester").isVisible();
        page.getByText("Quantity").isVisible();
        page.getByRole(AriaRole.LISTITEM, new Page.GetByRoleOptions().setName("1")).isVisible();
        //String quantity = page.locator("#order_line_item_attribute_0_quantity").evaluate("element => element.value");
        //Assertions.assertThat(quantity).isEqualTo("1");


        //page.locator("#order_line_item_attribute_0_quantity").isVisible();
        //String quantity = (String) page.evaluate
        //("document.querySelector('#order_line_item_attribute_0_quantity').value");
        //Assertions.assertThat(quantity).isEqualTo("1");
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