package br.com.inmetrics.iuriraredu.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static io.cucumber.junit.CucumberOptions.SnippetType.CAMELCASE;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/features/api",
                "src/test/resources/features/web"
        },
        glue = {
                "br.com.inmetrics.iuriraredu.steps.api",
                "br.com.inmetrics.iuriraredu.steps.web",
                "br.com.inmetrics.iuriraredu.hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-reports.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/junit-report.xml"
        },
        monochrome = true,
        snippets = CAMELCASE,
        tags = "@AtualizarImagem"
)
public class RunTest {
}
