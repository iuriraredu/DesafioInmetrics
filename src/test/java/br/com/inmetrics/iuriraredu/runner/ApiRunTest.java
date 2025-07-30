package br.com.inmetrics.iuriraredu.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.UNDERSCORE;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/api", // caminho para suas features de API
        glue = {
                "br.com.inmetrics.iuriraredu.steps",
                "br.com.inmetrics.iuriraredu.hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api/cucumber-report.html",
                "json:target/cucumber-reports/api/json-report.json",
                "junit:target/cucumber-reports/api/junit-report.xml"
        },
        monochrome = true,
        snippets = UNDERSCORE,
        tags = "@api"
)
public class ApiRunTest {
}
