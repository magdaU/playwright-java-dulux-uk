package com.github.magdalena.pom;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class HomePage {

    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    public void openHomePage() {
        page.navigate("https://www.dulux.co.uk");
        page.waitForLoadState();
    }

    public void rejectAllCookies() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reject All")).click();
    }
}