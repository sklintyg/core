package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.AlertDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateConfirmationModalDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateModalActionTypeDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateModalActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.Alert;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateConfirmationModal;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;

class CertificateConfirmationModalConverterTest {

  @Test
  void shouldConvertNullToNull() {
    assertNull(
        new CertificateConfirmationModalConverter().convert(null)
    );
  }

  @Test
  void shouldConvertConfirmationModal() {
    final var expected = CertificateConfirmationModalDTO.builder()
        .text("TEXT")
        .title("TITLE")
        .alert(
            AlertDTO.builder()
                .text("ALERT")
                .type(MessageLevel.INFO)
                .build()
        )
        .checkboxText("CHECKBOX")
        .primaryAction(CertificateModalActionTypeDTO.READ)
        .secondaryAction(CertificateModalActionTypeDTO.CANCEL)
        .build();

    final var result = new CertificateConfirmationModalConverter().convert(
        CertificateConfirmationModal.builder()
            .text("TEXT")
            .title("TITLE")
            .alert(
                Alert.builder()
                    .text("ALERT")
                    .type(MessageLevel.INFO)
                    .build()
            ).checkboxText("CHECKBOX")
            .primaryAction(CertificateModalActionType.READ)
            .secondaryAction(CertificateModalActionType.CANCEL)
            .build()
    );

    assertEquals(expected, result);
  }
}