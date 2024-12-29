package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK3226_CERTIFICATE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigVisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataVisualAcuitiesConfigConverterTest {

  private CertificateDataVisualAcuitiesConfigConverter converter;

  private static final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .configuration(ElementConfigurationVisualAcuities.builder()
          .id(new FieldId("id"))
          .withCorrectionLabel("withCorrectionLabel")
          .withoutCorrectionLabel("withoutCorrectionLabel")
          .min(0.0)
          .max(2.0)
          .name("name")
          .rightEye(ElementVisualAcuity.builder()
              .label("label1")
              .withCorrectionId("withCorrectionId1")
              .withoutCorrectionId("withoutCorrectionId1")
              .build()
          )
          .leftEye(ElementVisualAcuity.builder()
              .label("label2")
              .withCorrectionId("withCorrectionId2")
              .withoutCorrectionId("withoutCorrectionId2")
              .build()
          )
          .binocular(ElementVisualAcuity.builder()
              .label("label3")
              .withCorrectionId("withCorrectionId3")
              .withoutCorrectionId("withoutCorrectionId3")
              .build()
          )
          .build()
      )
      .build();

  @BeforeEach
  void setUp() {
    converter = new CertificateDataVisualAcuitiesConfigConverter();
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
  void shouldReturnvisualAcuitiesType() {
    assertEquals(ElementType.VISUAL_ACUITIES, converter.getType());
  }

  @Test
  void shouldSetId() {
    final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    assertEquals("id", result.getId());
  }

  @Test
  void shouldSetWithCorrectionLabel() {
    final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    assertEquals("withCorrectionLabel", result.getWithCorrectionLabel());
  }

  @Test
  void shouldSetWithoutCorrectionLabel() {
    final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    assertEquals("withoutCorrectionLabel", result.getWithoutCorrectionLabel());
  }

  @Test
  void shouldSetName() {
    final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    assertEquals("name", result.getText());
  }

  @Test
  void shallIncludeMin() {
    final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    assertEquals(0.0, result.getMin());
  }

  @Test
  void shallIncludeMax() {
    final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
        converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

    assertEquals(2.0, result.getMax());
  }

  @Nested
  class RightEyeTest {

    @Test
    void shouldSetWithCorrectionId() {
      final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("withCorrectionId1", result.getRightEye().getWithCorrectionId());
    }

    @Test
    void shouldSetWithoutCorrectionId() {
      final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("withoutCorrectionId1", result.getRightEye().getWithoutCorrectionId());
    }

    @Test
    void shouldSetLabel() {
      final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("label1", result.getRightEye().getLabel());
    }
  }

  @Nested
  class LeftEyeTest {

    @Test
    void shouldSetWithCorrectionId() {
      final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("withCorrectionId2", result.getLeftEye().getWithCorrectionId());
    }

    @Test
    void shouldSetWithoutCorrectionId() {
      final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("withoutCorrectionId2", result.getLeftEye().getWithoutCorrectionId());
    }

    @Test
    void shouldSetLabel() {
      final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("label2", result.getLeftEye().getLabel());
    }
  }

  @Nested
  class BinocularEyeTest {

    @Test
    void shouldSetWithCorrectionId() {
      final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("withCorrectionId3", result.getBinocular().getWithCorrectionId());
    }

    @Test
    void shouldSetWithoutCorrectionId() {
      final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("withoutCorrectionId3", result.getBinocular().getWithoutCorrectionId());
    }

    @Test
    void shouldSetLabel() {
      final CertificateDataConfigVisualAcuity result = (CertificateDataConfigVisualAcuity)
          converter.convert(ELEMENT_SPECIFICATION, FK3226_CERTIFICATE);

      assertEquals("label3", result.getBinocular().getLabel());
    }
  }

}