package com.github.magdalena.cucumber.steps;

import com.github.magdalena.cucumber.CucumberContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddTesterToCartSteps {

    private final CucumberContext ctx;

    public AddTesterToCartSteps(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @Given("the cart page is open")
    public void theCartPageIsOpen() {
        ctx.initContext(1920, 1080);
        ctx.cartPage.openCartPage();
    }

    @And("cookies are rejected")
    public void cookiesAreRejected() {
        ctx.homePage.rejectAllCookies();
    }

    @And("the basket is empty")
    public void theBasketIsEmpty() {
        assertThat(ctx.cartPage.getBasketEmptyText()).isVisible();
    }

    @When("the customer navigates to the home page on desktop")
    public void navigateToHomePageDesktop() {
        ctx.initContext(1920, 1080);
        ctx.setDesktop(true);
        ctx.cartPage.openCartPage();
        ctx.homePage.rejectAllCookies();
        assertThat(ctx.cartPage.getBasketEmptyText()).isVisible();
        ctx.homePage.openHomePage();
    }

    @And("opens {string} from the top navigation")
    public void opensDropdownFromTopNav(String menuItem) {
        ctx.navigationPage.clickDropdownFindColour();
    }

    @And("opens {string} from the mobile navigation")
    public void opensLinkFromMobileNav(String menuItem) {
        ctx.navigationPage.clickDropdownFindColour();
    }

    @When("the customer navigates to the home page on mobile")
    public void navigateToHomePageMobile() {
        ctx.initContext(375, 667);
        ctx.setDesktop(false);
        ctx.cartPage.openCartPage();
        ctx.homePage.rejectAllCookies();
        assertThat(ctx.cartPage.getBasketEmptyText()).isVisible();
        ctx.homePage.openHomePage();
    }

    @And("opens the hamburger menu")
    public void opensHamburgerMenu() {
        ctx.navigationPage.clickDropdownHamburgerMenu();
    }

    @And("selects colour group {string}")
    public void selectsColourGroup(String colour) {
        ctx.navigationPage.clickFindColour();
        ctx.colorSelectionPage.chooseColour(colour);
    }

    @And("selects shade {string}")
    public void selectsShade(String shade) {
        ctx.colorSelectionPage.choseSpecificTypeColor(shade);
    }

    @And("clicks {string}")
    public void clicksButton(String buttonText) {
        ctx.colorSelectionPage.buyATesterColour();
    }

    @And("closes the confirmation alert")
    public void closesConfirmationAlert() {
        ctx.alertComponent.closeAlert();
    }

    @And("opens the shopping cart")
    public void opensShoppingCart() {
        ctx.navigationPage.openShoppingCart();
    }

    @Then("the cart contains {int} item")
    public void theCartContainsItem(int quantity) {
        Assertions.assertThat(ctx.cartPage.getQuantity().inputValue())
                .isEqualTo(String.valueOf(quantity));
    }

    @And("the cart contains product {string}")
    public void theCartContainsProduct(String productName) {
        assertThat(ctx.cartPage.findText(productName)).isVisible();
    }

    @And("the cart contains shade {string}")
    public void theCartContainsShade(String shade) {
        assertThat(ctx.cartPage.findText(shade)).isVisible();
    }
}
