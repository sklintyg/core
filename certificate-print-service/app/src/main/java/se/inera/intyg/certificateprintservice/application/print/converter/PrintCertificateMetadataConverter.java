package se.inera.intyg.certificateprintservice.application.print.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateMetadataDTO;
import se.inera.intyg.certificateprintservice.print.api.PrintCertificateMetadata;

@Component
public class PrintCertificateMetadataConverter {

  public PrintCertificateMetadata convert(PrintCertificateMetadataDTO metadata) {
    return PrintCertificateMetadata.builder()
        .build();
  }
}