package com.github.magdalena.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CucumberHooks {

    private static final Path ALLURE_RESULTS = Paths.get("target", "allure-results");

    private final CucumberContext ctx;

    public CucumberHooks(CucumberContext ctx) {
        this.ctx = ctx;
    }

    /**
     * Copies the static Allure metadata (Environment and Categories widgets) into the
     * results directory before any scenario runs, so they appear in local, Docker and CI
     * reports alike. The Executors and Trend widgets are populated by the CI pipeline.
     */
    @BeforeAll
    public static void writeAllureMetadata() {
        copyResource("allure/environment.properties", "environment.properties");
        copyResource("allure/categories.json", "categories.json");
    }

    private static void copyResource(String classpathResource, String targetFileName) {
        try (InputStream in = CucumberHooks.class.getClassLoader().getResourceAsStream(classpathResource)) {
            if (in == null) {
                return;
            }
            Files.createDirectories(ALLURE_RESULTS);
            Files.copy(in, ALLURE_RESULTS.resolve(targetFileName),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.err.println("Could not write Allure metadata " + targetFileName + ": " + e.getMessage());
        }
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