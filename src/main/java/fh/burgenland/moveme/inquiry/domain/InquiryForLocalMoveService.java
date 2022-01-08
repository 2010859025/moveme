package fh.burgenland.moveme.inquiry.domain;

import fh.burgenland.moveme.infrastructure.domain.DomainResult;
import fh.burgenland.moveme.inquiry.api.InquiryContactAnswer;
import fh.burgenland.moveme.inquiry.api.InquiryForLocalMove;
import fh.burgenland.moveme.inquiry.api.InquiryLocation;
import fh.burgenland.moveme.inquiry.persistence.InquiryForLocalMoveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InquiryForLocalMoveService {

    private final transient InquiryForLocalMoveRepository repository;

    @Autowired()
    public InquiryForLocalMoveService(InquiryForLocalMoveRepository repository) {
        this.repository = repository;
    }

    public DomainResult<InquiryContactAnswer> inquiry(InquiryForLocalMove inquiry) {
        this.repository.save(toDomain(inquiry));
        boolean checkCity = fromToEquals(inquiry.getFromInquiryLocation(), inquiry.getToInquiryLocation());
        return DomainResult
                .<InquiryContactAnswer>builder()
                .success(createInquiryContactAnswer(inquiry, !checkCity))
                .build();
    }

    private boolean fromToEquals(InquiryLocation inquiryFrom, InquiryLocation inquiryTo) {
        return inquiryFrom.getCity().equals(inquiryTo.getCity());
    }

    private InquiryContactAnswer createInquiryContactAnswer(InquiryForLocalMove inquiry, boolean err) {
            return InquiryContactAnswer.builder().error(err).referenceNumber(createReferenceNumber(inquiry)).answerHour(24).build();
    }

    private String createReferenceNumber(InquiryForLocalMove inquiry) {
        return ReferenceNumber.localReferenceNumberOf(inquiry.getInquiryContact().getName(), inquiry.getFromInquiryLocation().getCity()).getValue();
    }

    private InquiryForLocalMoveDomain toDomain(InquiryForLocalMove inquiry) {
        var domain = new InquiryForLocalMoveDomain();
        domain.setName(inquiry.getInquiryContact().getName());
        domain.setTelephoneNumber(inquiry.getInquiryContact().getTelephoneNumber());
        domain.setToStreet(inquiry.getToInquiryLocation().getStreet());
        domain.setToZip(inquiry.getToInquiryLocation().getZip());
        domain.setFromStreet(inquiry.getFromInquiryLocation().getStreet());
        domain.setFromZip(inquiry.getFromInquiryLocation().getZip());
        domain.setCity(inquiry.getFromInquiryLocation().getCity());
        return domain;
    }
}
