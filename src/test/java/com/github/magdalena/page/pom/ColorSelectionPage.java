package com.github.magdalena.page.pom;

import com.github.magdalena.page.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class ColorSelectionPage extends BasePage {

    private static final String BUY_A_TESTER_TEXT = "Buy a Tester in this colour";
    private static final String VISUALIZER_APP_TEXT = "Try our Visualizer App";

    public ColorSelectionPage(Page page) {
        super(page);
    }

    public void chooseColour(String colourFamily) {
        clickButtonByName(colourFamily);
    }

    public void chooseShade(String shade) {
        clickButtonByName(shade);
    }

    public Locator getColourFamilyButton(String colourFamily) {
        return buttonByName(colourFamily);
    }

    private void clickButtonByName(String name) {
        buttonByName(name).click();
    }

    public void buyATesterColour() {
        buttonByName(BUY_A_TESTER_TEXT).click();
    }

    public void openVisualizerApp() {
        page.getByRole(AriaRole.LISTITEM)
                .filter(new Locator.FilterOptions().setHasText(VISUALIZER_APP_TEXT))
                .getByRole(AriaRole.LINK).click();
    }
}