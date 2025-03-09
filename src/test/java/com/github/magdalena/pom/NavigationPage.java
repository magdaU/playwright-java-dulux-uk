package com.github.magdalena.pom;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class NavigationPage {

    private final Page page;

    public NavigationPage(Page page) {
        this.page = page;
    }

    public void dropdownFindColour() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Find a colour")).click();
    }

    public void openFindColour() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Find a colour")).click();
    }

    public void openShoppingCart() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Shopping Cart")).click();
    }
}
