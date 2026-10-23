package com.github.magdalena.cucumber.steps;

import com.github.magdalena.cucumber.CucumberContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CookieSteps {

    private final CucumberContext ctx;

    public CookieSteps(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @Given("a {word} customer opens the home page for the first time")
    public void aCustomerOpensTheHomePageForTheFirstTime(String viewport) {
        ctx.useViewport(viewport);
        ctx.openHomePage();
    }

    @When("the customer rejects all cookies")
    public void theCustomerRejectsAllCookies() {
        ctx.homePage().rejectAllCookies();
    }

    @Then("the cookie banner is shown")
    public void theCookieBannerIsShown() {
        assertThat(ctx.homePage().getCookieBanner()).isVisible();
    }

    @Then("the cookie banner is hidden")
    public void theCookieBannerIsHidden() {
        assertThat(ctx.homePage().getCookieBanner()).isHidden();
    }
}
