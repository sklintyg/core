package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK3226_CERTIFICATE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigOcularAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementOcularAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataOcularAcuitiesConfigConverterTest {

  private CertificateDataOcularAcuitiesConfigConverter converter;

  private static final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .configuration(ElementConfigurationOcularAcuities.builder()
          .id(new FieldId("id"))
          .withCorrectionLabel("withCorrectionLabel")
          .withoutCorrectionLabel("withoutCorrectionLabel")
          .leftEye(ElementOcularAcuity.builder()
              .label("label")
              .withCorrectionId("withCorrectionId")
              .withoutCorrectionId("withoutCorrectionId")
              .build()
          )
          .rightEye(ElementOcularAcuity.builder()
              .label("label")
              .withCorrectionId("withCorrectionId")
              .withoutCorrectionId("withoutCorrectionId")
              .build()
          )
          .binocular(ElementOcularAcuity.builder()
              .label("label")
              .withCorrectionId("withCorrectionId")
              .withoutCorrectionId("withoutCorrectionId")
              .build()
          )
          .build()
      )
      .build();

  @BeforeEach
  void setUp() {
    converter = new CertificateDataOcularAcuitiesConfigConverter();
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDate.builder().build()
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(elementSpecification, FK3226_CERTIFICATE)
    );
  }

  @Test
  void shouldReturnOcularAcuitiesType() {
    assertEquals(ElementType.OCULAR_ACUITIES, converter.getType());
  }

  @Test
  void shouldSetId() {
    final CertificateDataConfigOcularAcuity result = (CertificateDataConfigOcularAcuity)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    assertEquals("id", result.getId());
  }

  @Test
  void shouldSetWithCorrectionLabel() {
    final CertificateDataConfigOcularAcuity result = (CertificateDataConfigOcularAcuity)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    assertEquals("withCorrectionLabel", result.getWithCorrectionLabel());
  }

  @Test
  void shouldSetWithoutCorrectionLabel() {
    final CertificateDataConfigOcularAcuity result = (CertificateDataConfigOcularAcuity)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    assertEquals("withoutCorrectionLabel", result.getWithoutCorrectionLabel());
  }

  @Nested
  class RightEyeTest {

    @Test
    void shouldSetWithCorrectionId() {
      final CertificateDataConfigOcularAcuity result = (CertificateDataConfigOcularAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("withCorrectionId", result.getRightEye().getWithCorrectionId());
    }

    @Test
    void shouldSetWithoutCorrectionId() {
      final CertificateDataConfigOcularAcuity result = (CertificateDataConfigOcularAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("withoutCorrectionId", result.getRightEye().getWithoutCorrectionId());
    }

    @Test
    void shouldSetLabel() {
      final CertificateDataConfigOcularAcuity result = (CertificateDataConfigOcularAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("label", result.getRightEye().getLabel());
    }
  }

  @Nested
  class LeftEyeTest {

    @Test
    void shouldSetWithCorrectionId() {
      final CertificateDataConfigOcularAcuity result = (CertificateDataConfigOcularAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("withCorrectionId", result.getLeftEye().getWithCorrectionId());
    }

    @Test
    void shouldSetWithoutCorrectionId() {
      final CertificateDataConfigOcularAcuity result = (CertificateDataConfigOcularAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("withoutCorrectionId", result.getLeftEye().getWithoutCorrectionId());
    }

    @Test
    void shouldSetLabel() {
      final CertificateDataConfigOcularAcuity result = (CertificateDataConfigOcularAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("label", result.getLeftEye().getLabel());
    }
  }

  @Nested
  class BinocularEyeTest {

    @Test
    void shouldSetWithCorrectionId() {
      final CertificateDataConfigOcularAcuity result = (CertificateDataConfigOcularAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("withCorrectionId", result.getBinocular().getWithCorrectionId());
    }

    @Test
    void shouldSetWithoutCorrectionId() {
      final CertificateDataConfigOcularAcuity result = (CertificateDataConfigOcularAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("withoutCorrectionId", result.getBinocular().getWithoutCorrectionId());
    }

    @Test
    void shouldSetLabel() {
      final CertificateDataConfigOcularAcuity result = (CertificateDataConfigOcularAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("label", result.getBinocular().getLabel());
    }
  }

}
