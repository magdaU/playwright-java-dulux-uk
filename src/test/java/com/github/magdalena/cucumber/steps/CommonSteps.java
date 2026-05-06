package com.github.magdalena.cucumber.steps;

import com.github.magdalena.cucumber.CucumberContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CommonSteps {

    private final CucumberContext ctx;

    public CommonSteps(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @Given("a {word} customer starts with an empty basket")
    public void aCustomerStartsWithAnEmptyBasket(String viewport) {
        ctx.useViewport(viewport);
        ctx.openEmptyCart();
        assertThat(ctx.cartPage().getBasketEmptyText()).isVisible();
    }

    @Given("a {word} customer is viewing shade {string}")
    public void aCustomerIsViewingShade(String viewport, String shade) {
        ctx.useViewport(viewport);
        ctx.openHomePageAndRejectCookies();
        ctx.searchForShade(shade);
    }

    @When("the customer browses to shade {string} from colour family {string}")
    public void theCustomerBrowsesToShadeFromColourFamily(String shade, String colourFamily) {
        ctx.browseToShade(colourFamily, shade, false);
    }

    @When("the customer browses to shade {string} from colour family {string} using mobile navigation")
    public void theCustomerBrowsesToShadeFromColourFamilyUsingMobileNavigation(String shade, String colourFamily) {
        ctx.browseToShade(colourFamily, shade, true);
    }

    @When("the customer adds a tester to the basket")
    public void theCustomerAddsATesterToTheBasket() {
        ctx.addTesterToBasket();
        ctx.openShoppingCart();
    }

    @When("the customer opens the Visualizer experience")
    public void theCustomerOpensTheVisualizerExperience() {
        ctx.openVisualizerExperience();
    }
}

