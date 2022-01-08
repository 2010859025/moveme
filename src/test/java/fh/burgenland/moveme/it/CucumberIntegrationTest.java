package fh.burgenland.moveme.it;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

@Suite //JUnit 5
@SelectClasspathResource("features") //wo sind die feature files
@ConfigurationParameter( //Properties für Cucumber
        key = GLUE_PROPERTY_NAME, //wo ist glue code zu finden?
        value = "fh.burgenland.moveme.it"
)
@ConfigurationParameter(
        key = PLUGIN_PROPERTY_NAME,
        value = "pretty,json:build/cucumber.json,html:build/cucumber.html" //pretty = schön anzeigen, als JSON, als HTML
)
public class CucumberIntegrationTest {
}
