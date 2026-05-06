package com.github.magdalena.page.component;

import com.github.magdalena.page.BasePage;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class AlertComponent extends BasePage {

    public AlertComponent(Page page) {
        super(page);
    }

    public void closeAlert() {
        page.getByRole(AriaRole.ALERT).getByRole(AriaRole.BUTTON).click();
    }
}
