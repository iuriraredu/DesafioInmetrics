package br.com.inmetrics.iuriraredu.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {
            "br.com.inmetrics.iuriraredu.steps",
            "br.com.inmetrics.iuriraredu.hooks"
    },
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber-reports.html",
        "json:target/cucumber-reports/cucumber.json"
    },
    tags = "@web"
)
public class RunTest {
}
