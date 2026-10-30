package com.github.magdalena.cucumber.steps;

import com.github.magdalena.cucumber.CucumberContext;
import io.cucumber.java.Before;

public class OfflineHooks {

    private final CucumberContext ctx;

    public OfflineHooks(CucumberContext ctx) {
        this.ctx = ctx;
    }

    @Before("@offline")
    public void serveSiteFromStubs() {
        ctx.goOffline();
    }
}
