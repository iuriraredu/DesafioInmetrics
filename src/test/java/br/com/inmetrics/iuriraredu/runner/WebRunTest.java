package br.com.inmetrics.iuriraredu.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.UNDERSCORE;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/web", // caminho para suas features de Web
        glue = {
                "br.com.inmetrics.iuriraredu.web.steps",
                "br.com.inmetrics.iuriraredu.hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/web/cucumber-reports.html",
                "json:target/cucumber-reports/web/cucumber.json"
        },
        monochrome = true,
        snippets = UNDERSCORE,
        tags = "@web"
)
public class WebRunTest {
}
