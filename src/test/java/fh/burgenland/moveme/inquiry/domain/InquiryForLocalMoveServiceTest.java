package fh.burgenland.moveme.inquiry.domain;

import fh.burgenland.moveme.inquiry.persistence.InquiryForLocalMoveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static fh.burgenland.moveme.data.InquiryForLocalMoveDomainTestData.getInquiryDomainForAlexiPaoli;
import static fh.burgenland.moveme.data.InquiryForLocalMoveTestData.getInquiryForAlexiPaoli;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class InquiryForLocalMoveServiceTest {

    @Mock
    private transient InquiryForLocalMoveRepository repository;

    @Test
    void should_inquire_the_movement_successfully() {
        var inquiryDomain = getInquiryDomainForAlexiPaoli();
        Mockito.when(repository.save(inquiryDomain)).thenReturn(inquiryDomain);

        var inquiry = getInquiryForAlexiPaoli();

        var service = new InquiryForLocalMoveService(repository);
        var domainResult = service.inquiry(inquiry);

        Mockito.verify(this.repository, times(1)).save(inquiryDomain);

        assertThat(domainResult.getSuccess()).isNotNull();
    }
}
