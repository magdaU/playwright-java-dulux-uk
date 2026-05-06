package com.github.magdalena.page.pom;

import com.github.magdalena.page.BasePage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CartPage extends BasePage {

    private static final String CART_PAGE_URL = "https://www.dulux.co.uk/en/store/cart";
    private static final String QUANTITY_LABEL = "Quantity";
    private static final String YOUR_BASKET_IS_EMPTY_TEXT = "Your basket is empty";

    public CartPage(Page page) {
        super(page);
    }

    public void openCartPage () {
        page.navigate(CART_PAGE_URL);
    }

    public Locator getQuantity() {
        assertThat(page.getByLabel(QUANTITY_LABEL)).isVisible();
        return page.getByLabel(QUANTITY_LABEL);
    }

    public Locator findText(String colourName) {
        return page.getByText(colourName);
    }

    public void checkBasketIsEmpty() {
        assertThat(page.getByText(YOUR_BASKET_IS_EMPTY_TEXT)).isVisible();
    }
}