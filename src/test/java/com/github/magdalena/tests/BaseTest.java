package com.github.magdalena.tests;

import com.github.magdalena.page.component.AlertComponent;
import com.github.magdalena.page.component.NavigationComponent;
import com.github.magdalena.page.pom.CartPage;
import com.github.magdalena.page.pom.ColorSelectionPage;
import com.github.magdalena.page.pom.HomePage;
import com.github.magdalena.support.BrowserSession;
import com.github.magdalena.support.Viewport;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    private static BrowserSession session;

    protected BrowserContext context;
    protected Page page;

    protected HomePage homePage;
    protected NavigationComponent navigationPage;
    protected ColorSelectionPage colorSelectionPage;
    protected CartPage cartPage;
    protected AlertComponent alertComponent;

    @BeforeAll
    static void setUpAll() {
        session = new BrowserSession();
    }

    protected void setUpDesktop() {
        setUp(Viewport.DESKTOP);
    }

    protected void setUpMobile() {
        setUp(Viewport.MOBILE);
    }

    private void setUp(Viewport viewport) {
        page = session.open(viewport);
        context = session.context();
        homePage = session.homePage();
        navigationPage = session.navigationPage();
        colorSelectionPage = session.colorSelectionPage();
        cartPage = session.cartPage();
        alertComponent = session.alertComponent();
    }

    @AfterEach
    void tearDown() {
        session.closeContext();
    }

    @AfterAll
    static void tearDownAll() {
        session.close();
    }
}
