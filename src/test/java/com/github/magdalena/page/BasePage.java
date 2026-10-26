package com.github.magdalena.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public abstract class BasePage {

    protected static final String BASE_URL = "https://www.dulux.co.uk";

    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }

    protected Locator buttonByName(String name) {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(name));
    }
}

