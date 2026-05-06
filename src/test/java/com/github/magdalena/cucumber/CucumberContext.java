package com.github.magdalena.cucumber;

import com.github.magdalena.page.component.AlertComponent;
import com.github.magdalena.page.component.NavigationComponent;
import com.github.magdalena.page.pom.CartPage;
import com.github.magdalena.page.pom.ColorSelectionPage;
import com.github.magdalena.page.pom.HomePage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class CucumberContext {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;
    private boolean desktop;

    public void initBrowser() {
        playwright = Playwright.create();
    }

        if (context != null) {
            context.close();
        }

        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(width, height));
        page = context.newPage();
        homePage = new HomePage(page);
        navigationPage = new NavigationComponent(page);
        colorSelectionPage = new ColorSelectionPage(page);
        cartPage = new CartPage(page);
        alertComponent = new AlertComponent(page);
    }
}

