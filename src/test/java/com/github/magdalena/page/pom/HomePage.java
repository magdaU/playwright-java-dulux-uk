package com.github.magdalena.page.pom;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class HomePage {

    private final Page page;
    private static final String DUlUX_PAGE = "https://www.dulux.co.uk/en/store/cart";

    public HomePage(Page page) {
        this.page = page;
    }

    // PAGE_URL wyniesie do zmienne
    public void openHomePage() {
        page.navigate("https://www.dulux.co.uk");
        page.waitForLoadState();
    }

    public void rejectAllCookies() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reject All")).isVisible();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reject All")).click();
    }
}