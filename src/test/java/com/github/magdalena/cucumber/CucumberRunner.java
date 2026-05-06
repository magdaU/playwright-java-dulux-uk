package com.github.magdalena.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.github.magdalena.cucumber")
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty, summary, html:target/cucumber-reports/report.html, json:target/cucumber-reports/report.json, junit:target/cucumber-reports/report.xml, message:target/cucumber-reports/messages.ndjson, io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
)
public class CucumberRunner {
}
