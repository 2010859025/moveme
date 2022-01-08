package fh.burgenland.moveme.inquiry.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InquiryContactAnswer {
    private final String referenceNumber;
    private final int answerHour;
    private boolean error;
}
