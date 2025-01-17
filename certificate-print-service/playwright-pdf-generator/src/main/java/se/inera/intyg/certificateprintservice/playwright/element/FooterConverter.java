package se.inera.intyg.certificateprintservice.playwright.element;

import lombok.extern.slf4j.Slf4j;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;

@Slf4j
public class FooterConverter {

  private FooterConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static Footer convert(Metadata metadata) {
    return Footer.builder()
        .applicationOrigin(metadata.getApplicationOrigin())
        .build();
  }

}
