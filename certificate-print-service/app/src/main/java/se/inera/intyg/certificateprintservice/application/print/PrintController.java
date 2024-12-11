package se.inera.intyg.certificateprintservice.application.print;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateRequest;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateResponse;

@RestController
@RequestMapping("api/print")
@RequiredArgsConstructor
public class PrintController {

  private final GetPrintService printService;

  @PostMapping
  PrintCertificateResponse get(@RequestBody PrintCertificateRequest request) {
    return printService.get(request);
  }
}