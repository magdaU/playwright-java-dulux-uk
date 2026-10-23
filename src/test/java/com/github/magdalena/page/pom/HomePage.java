package com.github.magdalena.page.pom;

import com.github.magdalena.page.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class HomePage extends BasePage {

    private static final String DUlUX_PAGE = "https://www.dulux.co.uk";
    private static final String REJECT_ALL = "#onetrust-reject-all-handler";
    private static final String COOKIE_BANNER = "#onetrust-banner-sdk";

    public HomePage(Page page) {
        super(page);
    }

    public void openHomePage() {
        page.navigate(DUlUX_PAGE);
        page.waitForLoadState();
    }

    public Locator getCookieBanner() {
        return page.locator(COOKIE_BANNER);
    }

    public void rejectAllCookies() {
        page.locator(REJECT_ALL).click();
    }
}