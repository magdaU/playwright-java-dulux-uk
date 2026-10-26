package com.github.magdalena.tests.unit;

import com.github.magdalena.page.pom.CartPage;
import com.github.magdalena.page.pom.HomePage;
import com.microsoft.playwright.Page;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the exact URLs the page objects navigate to, using a JDK dynamic proxy as a fake
 * {@link Page}. No browser or network involved.
 */
@Epic("Quality")
@Feature("Page objects")
@Story("Page URLs")
@Owner("Magdalena")
@Tag("unit")
class PageUrlTest {

    private static final String HOME_URL = "https://www.dulux.co.uk";
    private static final String CART_URL = "https://www.dulux.co.uk/en/store/cart";

    @Test
    void whenOpenHomePage_thenNavigatesToBaseUrl() {
        // arrange
        List<String> navigated = new ArrayList<>();
        HomePage homePage = new HomePage(fakePage(navigated));

        // act
        homePage.openHomePage();

        // assert
        assertThat(navigated).containsExactly(HOME_URL);
    }

    @Test
    void whenOpenCartPage_thenNavigatesToCartUrl() {
        // arrange
        List<String> navigated = new ArrayList<>();
        CartPage cartPage = new CartPage(fakePage(navigated));

        // act
        cartPage.openCartPage();

        // assert
        assertThat(navigated).containsExactly(CART_URL);
    }

    private static Page fakePage(List<String> navigated) {
        return (Page) Proxy.newProxyInstance(
                Page.class.getClassLoader(),
                new Class<?>[]{Page.class},
                (proxy, method, args) -> {
                    if ("navigate".equals(method.getName())) {
                        navigated.add((String) args[0]);
                    }
                    return null;
                });
    }
}
