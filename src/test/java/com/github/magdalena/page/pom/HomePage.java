package com.github.magdalena.page.pom;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage {

    private static final String DUlUX_PAGE = "https://www.dulux.co.uk";
    private static final String REJECT_ALL = "#onetrust-reject-all-handler";

    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public void openHomePage() {
        page.navigate(DUlUX_PAGE);
        page.waitForLoadState();
    }

    public void rejectAllCookies() {
        assertThat(page.locator(REJECT_ALL)).isVisible();
        page.locator(REJECT_ALL).click();
    }
}