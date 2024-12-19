package se.inera.intyg.certificateprintservice.print.api;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Metadata {

  String name;
  String fileName;
  String version;
  String typeId;
  String certificateId;
  String signingDate;
  boolean isSent;
  byte[] recipientLogo;
  String recipientName;
  String applicationOrigin;
  String personId;
  String description;

  public boolean isDraft() {
    return signingDate == null;
  }
}