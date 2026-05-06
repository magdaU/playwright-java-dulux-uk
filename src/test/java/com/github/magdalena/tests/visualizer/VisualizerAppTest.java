package com.github.magdalena.tests.visualizer;

import java.nio.file.Paths;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.magdalena.tests.BaseTest;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class VisualizerAppTest extends BaseTest {

    @Test
    void whenDesktop_thenOpenVisualizerAppNewTab() {
        // GIVEN
        setUpDesktop();
        String colourType = "Gentle Lavender";

        // WHEN
        homePage.openHomePage();
        homePage.rejectAllCookies();
        navigationPage.searchClickOnPage();
        navigationPage.inputColorOnSearchBoxAndEnter(colourType);

        Page newPage = context.waitForPage(() -> {
            colorSelectionPage.openVisualizerApp();
        });

        // THEN
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("Screenshots/VisualizerAppTest/LastScreenShoot.png")));
        Assertions.assertThat(newPage.url()).isEqualTo("https://www.dulux.co.uk/en/articles/dulux-visualizer-app");
    }

    @Test
    void whenMobile_thenShowContactSupport() {
        // GIVEN
        setUpMobile();
        String colourType = "Gentle Lavender";

        // WHEN
        homePage.openHomePage();
        homePage.rejectAllCookies();
        navigationPage.searchClickOnPage();
        navigationPage.inputColorOnSearchBoxAndEnter(colourType);
        colorSelectionPage.openVisualizerApp();

        // THEN
        page.waitForLoadState(LoadState.NETWORKIDLE);
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("Screenshots/VisualizerAppTest/LastScreenShoot.png")));
        Assertions.assertThat(page.locator("pre").textContent())
                .contains("Inconsistent store data, contact support@adjust.com");
    }
}