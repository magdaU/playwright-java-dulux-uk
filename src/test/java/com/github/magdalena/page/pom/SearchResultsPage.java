package com.github.magdalena.page.pom;

import com.github.magdalena.page.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class SearchResultsPage extends BasePage {

    public SearchResultsPage(Page page) {
        super(page);
    }

    public Locator getHeadingFor(String term) {
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("search for '" + term + "'"));
    }

    public Locator getNoResultsMessageFor(String term) {
        return page.getByText("Sorry, we couldn't find any results for '" + term + "'");
    }
}
