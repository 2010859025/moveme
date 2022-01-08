package fh.burgenland.moveme.inquiry.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fh.burgenland.moveme.infrastructure.domain.DomainResult;
import fh.burgenland.moveme.inquiry.domain.InquiryForLocalMoveService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static fh.burgenland.moveme.data.InquiryContactAnswerTestData.getAnswerForAlexiPaoli;
import static fh.burgenland.moveme.data.InquiryForLocalMoveTestData.getInquiryForAlexiPaoli;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(value = InquiryController.class)
public class InquiryControllerTest {

    @Autowired
    protected transient MockMvc mvc;

    @MockBean
    transient InquiryForLocalMoveService service;

    @Test
    public void should_return_http_201_for_successfully_creation_of_inquiry_for_local_move() throws Exception {
        var inquiry = getInquiryForAlexiPaoli();
        var successAnswer = getAnswerForAlexiPaoli();
        var domainResult = DomainResult.<InquiryContactAnswer>builder().success(successAnswer).build();

        Mockito.when(service.inquiry(inquiry)).thenReturn(domainResult);

        this.mvc.perform(
                post("/inquireForLocalMove")
                        .content(toJson(inquiry))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());

        Mockito.verify(this.service, times(1)).inquiry(inquiry);
    }

    public String toJson(InquiryForLocalMove inquiry) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(inquiry);
    }
}
