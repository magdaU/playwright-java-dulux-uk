package com.github.magdalena.tests.visualizer;

import java.nio.file.Paths;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.magdalena.page.pom.ColorSelectionPage;
import com.github.magdalena.page.pom.HomePage;
import com.github.magdalena.page.component.NavigationComponent;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class VisualizerAppTest {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;

    private HomePage homePage;
    private NavigationComponent navigationPage;
    private ColorSelectionPage colorSelectionPage;


    @BeforeAll
    static void setUpAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

    @BeforeEach
    void setUp() {
        context = browser.newContext();
        page = context.newPage();
        navigationPage = new NavigationComponent(page);
        colorSelectionPage = new ColorSelectionPage(page);
        homePage = new HomePage(page);
    }

    @Test
    void searchColorAndTryOurVisualizerApp() {
        // GIVEN
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
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("Screenshots/VisualizerAppTest/LastScreenShoot.png")));
        Assertions.assertThat(newPage.url()).isEqualTo("https://www.dulux.co.uk/en/articles/dulux-visualizer-app");
    }

    @AfterEach
    void tearDown() {
        context.close();
    }

    @AfterAll
    static void tearDownAll() {
        browser.close();
        playwright.close();
    }
}