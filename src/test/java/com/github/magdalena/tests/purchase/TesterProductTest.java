package com.github.magdalena.tests.purchase;

import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

import com.github.magdalena.page.component.AlertComponent;
import com.github.magdalena.page.pom.CartPage;
import com.github.magdalena.tests.BaseTest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TesterProductTest extends BaseTest {

    private CartPage cartPage;
    private AlertComponent alertComponent;

    @Override
    protected void createSetup(int width, int height) {
        super.createSetup(width, height);
        cartPage = new CartPage(page);
        alertComponent = new AlertComponent(page);
    }

    @Test
    void whenDesktop_thenShouldAddTesterToCart() {
        // GIVEN
        setUpDesktop();
        String colour = "Violet";
        String colourType = "Gentle Lavender";

        cartPage.openCartPage();
        homePage.rejectAllCookies();
        assertThat(cartPage.getBasketEmptyText()).isVisible();

        // WHEN
        homePage.openHomePage();
        navigationPage.clickDropdownFindColour();
        navigationPage.clickFindColour();
        colorSelectionPage.chooseColour(colour);
        colorSelectionPage.choseSpecificTypeColor(colourType);
        colorSelectionPage.buyATesterColour();
        alertComponent.closeAlert();
        navigationPage.openShoppingCart();

        // THEN
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("Screenshots/TesterProductTest/LastScreenShootDesktop.png")));

        assertThat(cartPage.getQuantity()).hasValue("1");
        assertThat(cartPage.findText("Dulux Colour Tester")).isVisible();
        assertThat(cartPage.findText(colourType)).isVisible();
    }

    @Test
    void whenMobile_thenShouldAddTesterToCart() {
        // GIVEN
        setUpMobile();
        String colour = "Violet";
        String colourType = "Gentle Lavender";

        cartPage.openCartPage();
        homePage.rejectAllCookies();
        assertThat(cartPage.getBasketEmptyText()).isVisible();

        // WHEN
        homePage.openHomePage();
        navigationPage.clickDropdownHamburgerMenu();
        navigationPage.clickDropdownFindColour();
        navigationPage.clickFindColour();
        colorSelectionPage.chooseColour(colour);
        colorSelectionPage.choseSpecificTypeColor(colourType);
        colorSelectionPage.buyATesterColour();
        alertComponent.closeAlert();
        navigationPage.openShoppingCart();

        // THEN
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("Screenshots/TesterProductTest/LastScreenShootMobile.png")));

        assertThat(cartPage.getQuantity()).hasValue("1");
        assertThat(cartPage.findText("Dulux Colour Tester")).isVisible();
        assertThat(cartPage.findText(colourType)).isVisible();
    }
}