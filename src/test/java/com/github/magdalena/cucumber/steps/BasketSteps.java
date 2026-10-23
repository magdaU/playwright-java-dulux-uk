package com.github.magdalena.cucumber.steps;

import com.github.magdalena.cucumber.CucumberContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BasketSteps {

    private static final Pattern PRODUCT_LISTING_URL = Pattern.compile(".*/en/products.*");

    private final CucumberContext ctx;

    public BasketSteps(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @When("the customer removes the tester from the basket")
    public void theCustomerRemovesTheTesterFromTheBasket() {
        ctx.cartPage().removeItem();
    }

    @When("the customer increases the quantity")
    public void theCustomerIncreasesTheQuantity() {
        ctx.cartPage().increaseQuantity();
    }

    @When("the customer decreases the quantity")
    public void theCustomerDecreasesTheQuantity() {
        ctx.cartPage().decreaseQuantity();
    }

    @When("the customer continues shopping")
    public void theCustomerContinuesShopping() {
        ctx.cartPage().continueShopping();
    }

    @Then("the product listing page is shown")
    public void theProductListingPageIsShown() {
        assertThat(ctx.page()).hasURL(PRODUCT_LISTING_URL);
    }

    @Then("the basket is empty")
    public void theBasketIsEmpty() {
        assertThat(ctx.cartPage().getBasketEmptyText()).isVisible();
        assertThat(ctx.cartPage().getQuantity()).hasCount(0);
    }

    @Then("the quantity is {int}")
    public void theQuantityIs(int quantity) {
        assertThat(ctx.cartPage().getQuantity()).hasValue(String.valueOf(quantity));
    }

    @And("the quantity cannot be decreased")
    public void theQuantityCannotBeDecreased() {
        assertThat(ctx.cartPage().getDecreaseQuantityButton()).isDisabled();
    }
}
