package se.inera.intyg.certificateprintservice.application.print;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateRequestDTO;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateResponseDTO;
import se.inera.intyg.certificateprintservice.print.PrintCertificateGenerator;

@Service
@RequiredArgsConstructor
public class GeneratePrintService {

  private final PrintCertificateGenerator printCertificateGenerator;

  public PrintCertificateResponseDTO get(PrintCertificateRequestDTO request) {

    return PrintCertificateResponseDTO.builder()
        .pdfData(printCertificateGenerator.generate())
        .build();
  }
}