package fh.burgenland.moveme.inquiry.api;

import fh.burgenland.moveme.inquiry.domain.InquiryForLocalMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InquiryController {

    private final transient InquiryForLocalMoveService service;

    @Autowired
    public InquiryController(InquiryForLocalMoveService service) {
        this.service = service;
    }

    @PostMapping("inquireForLocalMove")
    public ResponseEntity<InquiryContactAnswer> inquireForLocalMove(@RequestBody InquiryForLocalMove inquiry) {
        var domainResult = this.service.inquiry(inquiry);

        return ResponseEntity.status(HttpStatus.CREATED).body(domainResult.getSuccess());
    }

}
