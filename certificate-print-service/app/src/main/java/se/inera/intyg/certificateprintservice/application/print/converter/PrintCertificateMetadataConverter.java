package se.inera.intyg.certificateprintservice.application.print.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateMetadataDTO;
import se.inera.intyg.certificateprintservice.print.api.Metadata;

@Component
public class PrintCertificateMetadataConverter {

  public Metadata convert(PrintCertificateMetadataDTO metadata) {
    return Metadata.builder()
        .name(metadata.getName())
        .fileName(metadata.getFileName())
        .description(metadata.getDescription())
        .version(metadata.getVersion())
        .typeId(metadata.getTypeId())
        .certificateId(metadata.getCertificateId())
        .applicationOrigin(metadata.getApplicationOrigin())
        .personId(metadata.getPersonId())
        .recipientLogo(metadata.getRecipientLogo())
        .recipientName(metadata.getRecipientName())
        .signingDate(metadata.getSigningDate())
        .sentDate(metadata.getSentDate())
        .build();
  }
}