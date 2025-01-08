package se.inera.intyg.certificateprintservice.pdfgenerator.api;


import java.util.List;
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
  String sentDate;
  byte[] recipientLogo;
  String recipientName;
  String applicationOrigin;
  String personId;
  String description;
  String issuerName;
  String issuingUnit;
  List<String> issuingUnitInfo;

  public boolean isDraft() {
    return signingDate == null;
  }

  public boolean isSent() {
    return sentDate != null;
  }

  public boolean isSigned() {
    return signingDate != null;
  }
}