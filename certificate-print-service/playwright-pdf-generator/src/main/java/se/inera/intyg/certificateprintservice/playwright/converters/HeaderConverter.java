package se.inera.intyg.certificateprintservice.playwright.converters;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.document.Header;

@Component
public class HeaderConverter {

  public Header convert(Metadata metadata) {
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
