package com.github.magdalena.cucumber.steps;

import com.github.magdalena.cucumber.CucumberContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddTesterToCartSteps {

    private final CucumberContext ctx;

    public AddTesterToCartSteps(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @Then("the basket contains {int} item")
    public void theBasketContainsItem(int quantity) {
        assertThat(ctx.cartPage().getQuantity()).hasValue(String.valueOf(quantity));
    }

    @And("the basket includes tester {string} for shade {string}")
    public void theBasketIncludesTesterForShade(String productName, String shade) {
        assertThat(ctx.cartPage().findText(productName)).isVisible();
        assertThat(ctx.cartPage().findText(shade)).isVisible();
    }
}
