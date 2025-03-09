package com.github.magdalena.pom;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ColorSelectionPage {

    private final Page page;

    public ColorSelectionPage(Page page) {
        this.page = page;
    }

    public void chooseColour() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Violet")).isVisible();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Violet")).click();
    }

    public void chooseSpecificTypeColorAndBuyTester() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Gentle Lavender")).isVisible();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Gentle Lavender")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Buy a Tester in this colour")).isVisible();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Buy a Tester in this colour")).click();
    }
}
