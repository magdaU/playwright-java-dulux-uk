package com.github.magdalena.page.component;

import com.github.magdalena.page.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class NavigationComponent extends BasePage {

    private static final String FIND_A_COLOUR_MENU_ITEM = "Find a colour";
    private static final String MENU_HAMBURGER = "Menu";
    private static final String SHOPPING_CART = "Shopping Cart";
    private static final String SEARCH_FIELD = "search-field";
    private static final String SEARCH_BUTTON = "Search";

    public NavigationComponent(Page page) {
        super(page);
    }

    public void clickDropdownFindColour() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(FIND_A_COLOUR_MENU_ITEM)).click();
        // The button triggers a page navigation (not a dropdown). Wait for the new
        // page to load before proceeding — without this, the next click resolves
        // against the outgoing page and hits a stale element.
        page.waitForLoadState();
    }

    public void clickDropdownHamburgerMenu() {
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(MENU_HAMBURGER)).click();
    }

    public void clickFindColour() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(FIND_A_COLOUR_MENU_ITEM)).click();
    }

    public void openShoppingCart() {
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(SHOPPING_CART)).click();
    }

    public void searchClickOnPage(){
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(SEARCH_BUTTON)).click();
    }

    public void inputColorOnSearchBoxAndEnter(String color){
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName(SEARCH_FIELD)).fill(color);
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName(SEARCH_FIELD)).press("Enter");
    }
}
