package com.github.magdalena.page.pom;

import com.github.magdalena.page.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class HomePage extends BasePage {

    private static final String REJECT_ALL = "#onetrust-reject-all-handler";
    private static final String COOKIE_BANNER = "#onetrust-banner-sdk";

    public HomePage(Page page) {
        super(page);
    }

    public void openHomePage() {
        page.navigate(BASE_URL);
        page.waitForLoadState();
    }

    public Locator getCookieBanner() {
        return page.locator(COOKIE_BANNER);
    }

    public void rejectAllCookies() {
        page.locator(REJECT_ALL).click();
    }
}