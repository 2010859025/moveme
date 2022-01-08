package fh.burgenland.moveme.inquiry.persistence;

import fh.burgenland.moveme.inquiry.domain.InquiryForLocalMoveDomain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static fh.burgenland.moveme.data.InquiryForLocalMoveDomainTestData.getInquiryDomainForAlexiPaoli;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class InquiryForLocalMoveRepositoryTest {

    @Autowired
    transient InquiryForLocalMoveRepository repository;

    @Test
    public void canSaveAnInquiryForLocalMove() {
        var inquiryDomain = getInquiryDomainForAlexiPaoli();

        var result = repository.save(inquiryDomain);
        assertThat(result).isEqualTo(inquiryDomain);
    }
}
