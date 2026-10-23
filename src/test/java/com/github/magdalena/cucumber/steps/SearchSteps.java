package com.github.magdalena.cucumber.steps;

import com.github.magdalena.cucumber.CucumberContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SearchSteps {

    private static final Pattern SEARCH_RESULTS_URL = Pattern.compile(".*/en/search-results\\?search=.*");

    private final CucumberContext ctx;

    public SearchSteps(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @When("the customer searches for {string}")
    public void theCustomerSearchesFor(String term) {
        ctx.searchForShade(term);
    }

    @Then("the search results page is shown for {string}")
    public void theSearchResultsPageIsShownFor(String term) {
        assertThat(ctx.page()).hasURL(SEARCH_RESULTS_URL);
        assertThat(ctx.searchResultsPage().getHeadingFor(term)).isVisible();
    }

    @And("the customer is told no results were found for {string}")
    public void theCustomerIsToldNoResultsWereFoundFor(String term) {
        assertThat(ctx.searchResultsPage().getNoResultsMessageFor(term)).isVisible();
    }
}
