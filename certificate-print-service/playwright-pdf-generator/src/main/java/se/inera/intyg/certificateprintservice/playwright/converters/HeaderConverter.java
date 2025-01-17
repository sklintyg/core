package se.inera.intyg.certificateprintservice.playwright.converters;

import lombok.extern.slf4j.Slf4j;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.document.Header;

@Slf4j
public class HeaderConverter {

  private HeaderConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static Header convert(Metadata metadata) {
    return Header.builder()
        .certificateName(metadata.getName())
        .certificateType(metadata.getTypeId())
        .certificateVersion(metadata.getVersion())
        .personId(metadata.getPersonId())
        .recipientName(metadata.getRecipientName())
        .recipientLogo(metadata.getRecipientLogo())
        .isDraft(metadata.isDraft())
        .isSent(metadata.isSent())
        .build();
  }

}
