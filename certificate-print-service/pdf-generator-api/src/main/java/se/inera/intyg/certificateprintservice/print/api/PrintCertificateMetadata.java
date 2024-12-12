package se.inera.intyg.certificateprintservice.print.api;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateprintservice.print.api.PrintCertificateMetadata.PrintCertificateMetadataBuilder;

@Value
@Builder
@JsonDeserialize(builder = PrintCertificateMetadataBuilder.class)
public class PrintCertificateMetadata {

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
  public static class PrintCertificateMetadataBuilder {

  }
}