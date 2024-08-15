package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateConfirmationModalDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateModalActionTypeDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateModalActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateConfirmationModal;

class CertificateConfirmationModalConverterTest {

  @Test
  void shouldConvertConfirmationModal() {
    final var expected = CertificateConfirmationModalDTO.builder()
        .text("TEXT")
        .title("TITLE")
        .alert("ALERT")
        .checkboxText("CHECKBOX")
        .primaryAction(CertificateModalActionTypeDTO.READ)
        .secondaryAction(CertificateModalActionTypeDTO.CANCEL)
        .build();

    final var result = new CertificateConfirmationModalConverter().convert(
        CertificateConfirmationModal.builder()
            .text("TEXT")
            .title("TITLE")
            .alert("ALERT")
            .checkboxText("CHECKBOX")
            .primaryAction(CertificateModalActionType.READ)
            .secondaryAction(CertificateModalActionType.CANCEL)
            .build()
    );

    assertEquals(expected, result);
  }
}