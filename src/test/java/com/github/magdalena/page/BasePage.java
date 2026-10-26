package com.github.magdalena.page;

import com.microsoft.playwright.Page;

public abstract class BasePage {

    protected static final String BASE_URL = "https://www.dulux.co.uk";

    protected final Page page;

    public BasePage(Page page) {
        this.page = page;
    }
}

