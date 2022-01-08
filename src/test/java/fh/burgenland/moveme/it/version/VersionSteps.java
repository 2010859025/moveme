package fh.burgenland.moveme.it.version;

import fh.burgenland.moveme.it.webresponse.WebResponseContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

public class VersionSteps {

    // https://github.com/eugenp/tutorials/blob/master/spring-cucumber/src/test/java/com/baeldung/SpringIntegrationTest.java
    @Autowired
    private transient WebTestClient webClient;

    @Autowired
    private transient WebResponseContext context;

    @When("the application is asked for the version")
    public void the_version_is_retrieved() {
        this.context.lastResponse = webClient.get().uri("/actuator/info")
                .exchange();
    }

    @Then("the build version is shown as version")
    public void theVersionIsTheBuildVersion() {
        this.context.lastResponse.expectStatus().is2xxSuccessful().expectBody().jsonPath("$.build.version").exists();
    }
}
