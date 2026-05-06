package com.github.magdalena.cucumber.steps;

import com.github.magdalena.cucumber.CucumberContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.assertj.core.api.Assertions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class VisualizerAppSteps {

    private final CucumberContext ctx;

    public VisualizerAppSteps(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @Then("the Visualizer opens in a new tab")
    public void theVisualizerOpensInANewTab() {
        Assertions.assertThat(ctx.isDesktop()).isTrue();
        Assertions.assertThat(ctx.visualizerTab()).isNotNull();
    }

    @And("the Visualizer page URL is {string}")
    public void theVisualizerPageUrlIs(String expectedUrl) {
        Assertions.assertThat(ctx.visualizerTab().url()).isEqualTo(expectedUrl);
    }

    @Then("the page shows message {string}")
    public void thePageShowsMessage(String errorMessage) {
        assertThat(ctx.page().locator("pre")).isVisible();
        Assertions.assertThat(ctx.page().locator("pre").textContent()).contains(errorMessage);
    }
}
