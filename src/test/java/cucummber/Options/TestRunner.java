package cucummber.Options;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/org/example/features",plugin = "json:target/jsonReports/cucumber-report.json",glue = {"org.example.stepDefinitions"})
public class TestRunner {
//    , tags = "@DeletePlace"
}