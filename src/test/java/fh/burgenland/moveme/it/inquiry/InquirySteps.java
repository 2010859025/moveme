package fh.burgenland.moveme.it.inquiry;

import fh.burgenland.moveme.inquiry.api.InquiryContact;
import fh.burgenland.moveme.inquiry.api.InquiryContactAnswer;
import fh.burgenland.moveme.inquiry.api.InquiryForLocalMove;
import fh.burgenland.moveme.inquiry.api.InquiryLocation;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class InquirySteps {

    @Autowired
    private transient WebTestClient webClient;

    @Autowired
    private transient InquiryContext inquiryContext;

    @Given("{client} wants to move from {location} to {location}")
    public void clientWantsToMoveFromTo(TestClient client, TestLocation from, TestLocation to) {
        this.inquiryContext.client = client;
        this.inquiryContext.fromLocation = from;
        this.inquiryContext.toLocation = to;
    }

    @Given("wants to be contacted via telephone number {contactNumber}")
    public void wantsToBeContactedViaTelephoneNumber(TestContactNumber contactNumber) {
        this.inquiryContext.contactInformation = contactNumber;
    }

    @When("she inquires support for the movement")
    public void sheInquiresSupportForTheMovement() {
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

    @When("she gets a reference number back")
    public void sheGetsAReferenceNumberBack() {
        assertThat(this.inquiryContext.inquiryContactAnswer.getReferenceNumber())
                .isEqualTo("IFLM_" + this.inquiryContext.client.getName().toUpperCase(Locale.ROOT).replace(" ", "_") + "_WIEN");
    }

    @When("the information she will get contacted within the next {int} hours")
    public void theInformationSheWillGetContactedWithinTheNext24Hours(int hours) {
        assertThat(this.inquiryContext.inquiryContactAnswer.getAnswerHour()).isEqualTo(hours);
    }
}
