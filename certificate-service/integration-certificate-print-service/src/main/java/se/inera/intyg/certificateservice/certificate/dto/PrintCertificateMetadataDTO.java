package se.inera.intyg.certificateservice.certificate.dto;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintCertificateMetadataDTO {

  String name;
  String version;
  String typeId;
  String certificateId;
  String sentDate;
  String signingDate;
  byte[] recipientLogo;
  String recipientName;
  String recipientId;
  boolean canSendElectronically;
  String applicationOrigin;
  String personId;
  String description;
  String fileName;
  String issuerName;
  String issuingUnit;
  List<String> unitInformation;
  GeneralPrintTextDTO generalPrintText;
}
