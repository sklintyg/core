package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateModalActionType;

@Value
@Builder
public class CertificateConfirmationModal {

  String title;
  String text;
  Alert alert;
  String checkboxText;
  CertificateModalActionType primaryAction;
  CertificateModalActionType secondaryAction;
}
