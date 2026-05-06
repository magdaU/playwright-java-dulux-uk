package com.github.magdalena.cucumber.steps;

import com.github.magdalena.cucumber.CucumberContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class VisualizerAppSteps {

    private final CucumberContext ctx;

    public VisualizerAppSteps(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @Before
    public void beforeScenario() {
        ctx.initBrowser();
    }

    @After
    public void afterScenario() {
        ctx.tearDown();
    }

    // ── Background ────────────────────────────────────────────────────────────

    @Given("the viewport is desktop")
    public void theViewportIsDesktop() {
        ctx.initContext(1920, 1080);
    }

    @Given("the viewport is mobile")
    public void theViewportIsMobile() {
        ctx.initContext(375, 667);
    }

    @Given("the customer is on the home page")
    public void theCustomerIsOnHomePage() {
        // context is initialised in the viewport steps above; navigate handled in search step
    }

    @And("the customer searches for {string}")
    public void theCustomerSearchesFor(String query) {
        ctx.homePage.openHomePage();
        ctx.homePage.rejectAllCookies();
        ctx.navigationPage.searchClickOnPage();
        ctx.navigationPage.inputColorOnSearchBoxAndEnter(query);
    }

    // ── Actions ───────────────────────────────────────────────────────────────

    @When("the customer clicks {string}")
    public void theCustomerClicksLink(String linkText) {
        ctx.colorSelectionPage.openVisualizerApp();
    }

    // ── Assertions ────────────────────────────────────────────────────────────

    @Then("the Visualizer App opens in a new tab")
    public void theVisualizerAppOpensInNewTab() {
        ctx.newTab = ctx.getContext().waitForPage(ctx.colorSelectionPage::openVisualizerApp);
    }

    @And("the new tab URL is {string}")
    public void theNewTabUrlIs(String expectedUrl) {
        Assertions.assertThat(ctx.newTab.url()).isEqualTo(expectedUrl);
    }

    @Then("an error message is displayed")
    public void anErrorMessageIsDisplayed() {
        assertThat(ctx.getPage().locator("pre")).isVisible();
    }

    @And("the error contains {string}")
    public void theErrorContains(String errorMessage) {
        Assertions.assertThat(ctx.getPage().locator("pre").textContent())
                .contains(errorMessage);
    }
}

