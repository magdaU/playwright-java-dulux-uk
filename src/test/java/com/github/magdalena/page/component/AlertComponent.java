package com.github.magdalena.page.component;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AlertComponent {

    private final Page page;

    public AlertComponent(Page page) {
        this.page = page;
    }

    public void closeAlert() {
        page.getByRole(AriaRole.ALERT).getByRole(AriaRole.BUTTON).click();
    }
}
