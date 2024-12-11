package se.inera.intyg.certificateprintservice.application.print;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateRequest;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateResponse;
import se.inera.intyg.certificateprintservice.print.PrintCertificateGenerator;

@Service
@RequiredArgsConstructor
public class GetPrintService {

  private final PrintCertificateGenerator printCertificateGenerator;

  public PrintCertificateResponse get(PrintCertificateRequest request) {
    //validate request
    return PrintCertificateResponse.builder()
        .pdfData(printCertificateGenerator.generate())
        .build();
  }
}