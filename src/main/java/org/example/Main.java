package org.example;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Main class to start the application.
 */
public class Main {

    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://www.dulux.co.uk/");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Reject All")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("search-field")).fill("Gentle Lavender");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("search-field")).press("Enter");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("image Gentle Lavender")).click();
            Page page1 = page.waitForPopup(() -> {
                page.getByRole(AriaRole.LISTITEM).filter(new Locator.FilterOptions().setHasText("Try our Visualizer App")).getByRole(AriaRole.LINK).click();
            });
            assertThat(page1.getByRole(AriaRole.HEADING)).containsText("Dulux Visualiser");
        }
    }
}