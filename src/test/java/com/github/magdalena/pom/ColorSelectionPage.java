package com.github.magdalena.pom;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Gentle Lavender"))).isVisible();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Gentle Lavender")).isVisible();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Gentle Lavender")).click();
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Buy a Tester in this colour"))).isVisible();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Buy a Tester in this colour")).isVisible();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Buy a Tester in this colour")).click();
    }
    public void openVisualizerApp() {
        page.getByRole(AriaRole.LISTITEM).
                filter(new Locator.FilterOptions().setHasText("Try our Visualizer App")).getByRole(AriaRole.LINK).click();
    }
}