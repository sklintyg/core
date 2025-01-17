package se.inera.intyg.certificateprintservice.playwright.element;

import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;

public class LeftMarginInfoConverter {

  private LeftMarginInfoConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static LeftMarginInfo convert(Metadata metadata) {
    return LeftMarginInfo.builder()
        .certificateType(metadata.getTypeId())
        .recipientName(metadata.getRecipientName())
        .build();
  }

}
