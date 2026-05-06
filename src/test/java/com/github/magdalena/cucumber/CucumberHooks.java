package com.github.magdalena.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

public class CucumberHooks {

    private final CucumberContext ctx;

    public CucumberHooks(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @Before
    public void beforeScenario() {
        ctx.initBrowser();
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ctx.takeScreenshot();
            if (screenshot.length > 0) {
                scenario.attach(screenshot, "image/png", "failure-screenshot");
                Allure.addAttachment("failure-screenshot", new ByteArrayInputStream(screenshot));
            }
        }

        ctx.tearDown();
    }
}
