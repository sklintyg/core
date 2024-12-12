package se.inera.intyg.certificateprintservice.application.print.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateCategoryDTO;
import se.inera.intyg.certificateprintservice.print.api.PrintCertificateCategory;

@Component
public class PrintCertificateCategoryConverter {

  public PrintCertificateCategory convert(PrintCertificateCategoryDTO category) {
    return PrintCertificateCategory.builder()
        .build();
  }
}