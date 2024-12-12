package se.inera.intyg.certificateprintservice.application.print.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateMetadataDTO.PrintCertificateMetadataDTOBuilder;

@Value
@Builder
@JsonDeserialize(builder = PrintCertificateMetadataDTOBuilder.class)
public class PrintCertificateMetadataDTO {

  String name;
  String fileName;
  String version;
  String typeId;
  String certificateId;
  String signingDate;
  byte[] recipientLogo;
  String recipientName;
  String applicationOrigin;
  String personId;
  String description;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PrintCertificateMetadataDTOBuilder {

  }
}