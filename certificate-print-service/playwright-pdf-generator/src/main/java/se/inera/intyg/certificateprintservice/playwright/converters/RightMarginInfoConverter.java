package se.inera.intyg.certificateprintservice.playwright.converters;

import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.document.RightMarginInfo;

public class RightMarginInfoConverter {

  private RightMarginInfoConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static RightMarginInfo convert(Metadata metadata) {
    return RightMarginInfo.builder()
        .certificateId(metadata.getCertificateId())
        .build();
  }

}
