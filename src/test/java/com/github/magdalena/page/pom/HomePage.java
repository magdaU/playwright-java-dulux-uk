package com.github.magdalena.page.pom;

import com.github.magdalena.page.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage extends BasePage {

    private static final String DUlUX_PAGE = "https://www.dulux.co.uk";
    private static final String REJECT_ALL = "#onetrust-reject-all-handler";

    public HomePage(Page page) {
        super(page);
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