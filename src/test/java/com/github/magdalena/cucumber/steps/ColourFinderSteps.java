package com.github.magdalena.cucumber.steps;

import com.github.magdalena.cucumber.CucumberContext;
import io.cucumber.java.en.Then;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ColourFinderSteps {

    private final CucumberContext ctx;

    public ColourFinderSteps(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @Then("the colour family {string} is available")
    public void theColourFamilyIsAvailable(String colourFamily) {
        assertThat(ctx.colorSelectionPage().getColourFamilyButton(colourFamily)).isVisible();
    }
}
