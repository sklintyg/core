package se.inera.intyg.certificateprintservice.application.print;

import org.springframework.stereotype.Service;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateRequest;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateResponse;

@Service
public class GetPrintService {

  public PrintCertificateResponse get(PrintCertificateRequest request) {

    return PrintCertificateResponse.builder().build();
  }
}