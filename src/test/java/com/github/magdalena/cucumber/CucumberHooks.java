package com.github.magdalena.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;

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
    public void afterScenario() {
        ctx.tearDown();
    }
}


