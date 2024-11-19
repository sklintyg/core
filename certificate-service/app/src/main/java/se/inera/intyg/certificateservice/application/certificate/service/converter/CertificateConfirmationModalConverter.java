package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.AlertDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateConfirmationModalDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateModalActionTypeDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateModalActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateConfirmationModal;

@Component
public class CertificateConfirmationModalConverter {

  public CertificateConfirmationModalDTO convert(CertificateConfirmationModal modal) {
    if (modal == null) {
      return null;
    }

    return CertificateConfirmationModalDTO.builder()
        .title(modal.title())
        .alert(
            AlertDTO.builder()
                .type(modal.alert().type())
                .text(modal.alert().text())
                .build()
        )
        .checkboxText(modal.checkboxText())
        .primaryAction(convertAction(modal.primaryAction()))
        .secondaryAction(convertAction(modal.secondaryAction()))
        .text(modal.text())
        .build();
  }

  private CertificateModalActionTypeDTO convertAction(CertificateModalActionType type) {
    switch (type) {
      case DELETE -> {
        return CertificateModalActionTypeDTO.DELETE;
      }
      case READ -> {
        return CertificateModalActionTypeDTO.READ;
      }
      case CANCEL -> {
        return CertificateModalActionTypeDTO.CANCEL;
      }
    }

    throw new IllegalStateException(String.format("Not allowed modal action type '%s'", type));
  }

}
