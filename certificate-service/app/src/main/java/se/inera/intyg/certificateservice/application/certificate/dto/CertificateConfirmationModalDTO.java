package se.inera.intyg.certificateservice.application.certificate.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CertificateConfirmationModalDTO {

  String title;
  String text;
  String alert;
  String checkboxText;
  CertificateModalActionTypeDTO primaryAction;
  CertificateModalActionTypeDTO secondaryAction;
}
