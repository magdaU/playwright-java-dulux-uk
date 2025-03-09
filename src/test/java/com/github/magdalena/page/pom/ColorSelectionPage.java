package com.github.magdalena.page.pom;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ColorSelectionPage {

    private static final String BUY_A_TESTER_TEXT = "Buy a Tester in this colour";
    private static final String VISUALIZER_APP_TEXT = "Try our Visualizer App";

    private final Page page;

    public ColorSelectionPage(Page page) {
        this.page = page;
    }

    public void chooseColour(String colour) {
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(colour))).isVisible();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(colour)).click();
    }

    public void choseSpecificTypeColor(String colour) {
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(colour))).isVisible();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(colour)).click();
    }

    public void buyATesterColour() {
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(BUY_A_TESTER_TEXT))).isVisible();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(BUY_A_TESTER_TEXT)).click();
    }

    public void openVisualizerApp() {
        page.getByRole(AriaRole.LISTITEM).
                filter(new Locator.FilterOptions().setHasText(VISUALIZER_APP_TEXT)).getByRole(AriaRole.LINK).click();
    }
}