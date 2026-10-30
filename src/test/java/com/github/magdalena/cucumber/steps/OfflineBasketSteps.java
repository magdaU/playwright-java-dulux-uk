package com.github.magdalena.cucumber.steps;

import com.github.magdalena.cucumber.CucumberContext;
import io.cucumber.java.en.Given;

public class OfflineBasketSteps {

    private static final String CART_PATH = "/en/store/cart";

    private final CucumberContext ctx;

    public OfflineBasketSteps(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @Given("a {word} customer has one tester in the basket")
    public void aCustomerHasOneTesterInTheBasket(String viewport) {
        ctx.useViewport(viewport);
        ctx.offlineSite().serve(CART_PATH, "cart-with-tester");
        ctx.cartPage().openCartPage();
    }
}
