package fh.burgenland.moveme.it.inquiry;

import fh.burgenland.moveme.inquiry.api.InquiryContact;
import fh.burgenland.moveme.inquiry.api.InquiryContactAnswer;
import fh.burgenland.moveme.inquiry.api.InquiryForLocalMove;
import fh.burgenland.moveme.inquiry.api.InquiryLocation;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class InquirySteps {

    @Autowired
    private transient WebTestClient webClient;

    @Autowired
    private transient InquiryContext inquiryContext;

    @Given("{client} wants to move from {location} to {location}") // "location" z.B. stammt von LocationParameterType
    public void clientWantsToMoveFromTo(TestClient client, TestLocation from, TestLocation to) {
        this.inquiryContext.client = client;
        this.inquiryContext.fromLocation = from;
        this.inquiryContext.toLocation = to;
    }

    @Given("wants to be contacted via telephone number {contactNumber}")
    public void wantsToBeContactedViaTelephoneNumber(TestContactNumber contactNumber) {
        this.inquiryContext.contactInformation = contactNumber;
    }

    @When("{word} inquires support for the movement")
    public void sheInquiresSupportForTheMovement(String gender) {
        var contact = InquiryContact.builder().name(this.inquiryContext.client.getName()).telephoneNumber(this.inquiryContext.contactInformation.getTelephoneNumber()).build();
        var fromLocation = InquiryLocation.builder().street(this.inquiryContext.fromLocation.getStreet()).zip(this.inquiryContext.fromLocation.getZip()).city(this.inquiryContext.fromLocation.getCity()).build();
        var toLocation = InquiryLocation.builder().street(this.inquiryContext.toLocation.getStreet()).zip(this.inquiryContext.toLocation.getZip()).city(this.inquiryContext.toLocation.getCity()).build();
        var request = InquiryForLocalMove
                .builder()
                .inquiryContact(contact)
                .fromInquiryLocation(fromLocation)
                .toInquiryLocation(toLocation).build();
        this.inquiryContext.inquiryContactAnswer = this.webClient.post().uri("inquireForLocalMove")
                .bodyValue(request)
                .exchange()
                .expectStatus()
                .isCreated()
                .returnResult(InquiryContactAnswer.class)
                .getResponseBody()
                .blockFirst();
    }

    @Then("{word} gets a reference number back")
    public void sheGetsAReferenceNumberBack(String gender) {
        assertThat(this.inquiryContext.inquiryContactAnswer.getReferenceNumber())
                .isEqualTo("IFLM_" + this.inquiryContext.client.getName().toUpperCase(Locale.ROOT).replace(" ", "_") + "_WIEN");
    }

    @Then("the information {word} will get contacted within the next {int} hours")
    public void theInformationSheWillGetContactedWithinTheNext24Hours(String gender, int hours) {
        assertThat(this.inquiryContext.inquiryContactAnswer.getAnswerHour()).isEqualTo(hours);
        Assertions.assertFalse(this.inquiryContext.inquiryContactAnswer.isError());
    }

    @Then("{word} gets an error back because the cities are not the same!")
    public void sheGetsAnErrorBack(String gender) {
        Assertions.assertTrue(this.inquiryContext.inquiryContactAnswer.isError());
    }
}
