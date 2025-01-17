package se.inera.intyg.certificateprintservice.playwright.converters;

import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.document.LeftMarginInfo;

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
