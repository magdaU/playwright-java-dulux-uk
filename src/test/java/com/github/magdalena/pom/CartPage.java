package com.github.magdalena.pom;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CartPage {

    private final Page page;

    public CartPage(Page page) {
        this.page = page;
    }

    public Locator getQuantity() {
        page.getByLabel("Quantity").isVisible();
        return page.getByLabel("Quantity");
    }

    public Locator findText(String colourName) {
        return page.locator("text=" + colourName);
    }

    public CartPage openCartPage () {
        page.navigate("https://www.dulux.co.uk/en/store/cart");
        page.waitForLoadState();
        return this;
    }
    public Locator checkBasketIsEmpty() {
        page.locator("text=Your basket is empty").isVisible();
        return page.locator("text=Your basket is empty");
    }
}