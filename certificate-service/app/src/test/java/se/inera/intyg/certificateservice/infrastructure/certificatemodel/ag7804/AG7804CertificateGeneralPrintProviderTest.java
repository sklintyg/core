package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

class AG7804CertificateGeneralPrintProviderTest {

  public static final String LEFT_MARGIN_TEXT = "%s %s %s - Fastställd av %s";
  public static final String DRAFT_ALERT_INFO_TEXT = "arbetsgivaren";

  @Test
  void shouldIncludeGeneralPrintText() {
    final var provider = new AG7804CertificateGeneralPrintProvider();

    final var certificate = MedicalCertificate.builder()
        .certificateModel(CertificateModel.builder()
            .generalPrintProvider(provider)
            .build())
        .build();

    assertEquals(LEFT_MARGIN_TEXT, certificate.getGeneralPrintText().get().leftMarginInfoText());
    assertEquals(DRAFT_ALERT_INFO_TEXT,
        certificate.getGeneralPrintText().get().draftAlertInfoText());
  }

  @Test
  void shouldReturnEmptyIfGeneralPrintTextIsNull() {

    final var certificate = MedicalCertificate.builder()
        .certificateModel(CertificateModel.builder()
            .build())
        .build();

    assertFalse(certificate.getGeneralPrintText().isPresent());
  }

}