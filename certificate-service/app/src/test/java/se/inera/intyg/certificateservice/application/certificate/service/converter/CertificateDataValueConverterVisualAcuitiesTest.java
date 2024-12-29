package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDouble;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.VisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataValueConverterVisualAcuitiesTest {

  private static final String ID_1 = "id1";
  private static final String ID_2 = "id2";
  private static final double VALUE = 1.0;
  private static final VisualAcuity VISUAL_ACUITY = VisualAcuity.builder()
      .withCorrection(
          Correction.builder()
              .id(new FieldId(ID_1))
              .value(VALUE)
              .build()
      )
      .withoutCorrection(
          Correction.builder()
              .id(new FieldId(ID_2))
              .value(VALUE)
              .build()
      )
      .build();

  private static final ElementValueVisualAcuities VISUALL_ACUITIES =
      ElementValueVisualAcuities.builder()
          .leftEye(VISUAL_ACUITY)
          .binocular(VISUAL_ACUITY)
          .rightEye(VISUAL_ACUITY)
          .build();

  private CertificateDataValueConverterVisualAcuities convertervisualAcuities;

  @BeforeEach
  void setUp() {
    convertervisualAcuities = new CertificateDataValueConverterVisualAcuities();
  }

  @Test
  void shallThrowIfInvalidType() {
    final var elementValueDate = ElementValueDate.builder().build();
    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> convertervisualAcuities.convert(null,
            elementValueDate));
    assertEquals("Invalid value type. Type was '%s'".formatted(elementValueDate.getClass()),
        illegalStateException.getMessage());
  }

  @Nested
  class RightEyeTests {

    @Test
    void shallIncludeWithCorrection() {
      final var expectedResult = CertificateDataValueDouble.builder()
          .id(ID_1)
          .value(VALUE)
          .build();

      final var certificateDataValue = (CertificateDataValueVisualAcuities)
          convertervisualAcuities.convert(null, VISUALL_ACUITIES);

      assertEquals(expectedResult, certificateDataValue.getRightEye().getWithCorrection());
    }

    @Test
    void shallIncludeWithoutCorrection() {
      final var expectedResult = CertificateDataValueDouble.builder()
          .id(ID_2)
          .value(VALUE)
          .build();

      final var certificateDataValue = (CertificateDataValueVisualAcuities)
          convertervisualAcuities.convert(null, VISUALL_ACUITIES);

      assertEquals(expectedResult, certificateDataValue.getRightEye().getWithoutCorrection());
    }
  }

  @Nested
  class LeftEyeTests {

    @Test
    void shallIncludeWithCorrection() {
      final var expectedResult = CertificateDataValueDouble.builder()
          .id(ID_1)
          .value(VALUE)
          .build();

      final var certificateDataValue = (CertificateDataValueVisualAcuities)
          convertervisualAcuities.convert(null, VISUALL_ACUITIES);

      assertEquals(expectedResult, certificateDataValue.getLeftEye().getWithCorrection());
    }

    @Test
    void shallIncludeWithoutCorrection() {
      final var expectedResult = CertificateDataValueDouble.builder()
          .id(ID_2)
          .value(VALUE)
          .build();

      final var certificateDataValue = (CertificateDataValueVisualAcuities)
          convertervisualAcuities.convert(null, VISUALL_ACUITIES);

      assertEquals(expectedResult, certificateDataValue.getLeftEye().getWithoutCorrection());
    }
  }

  @Nested
  class BinocularTests {

    @Test
    void shallIncludeWithCorrection() {
      final var expectedResult = CertificateDataValueDouble.builder()
          .id(ID_1)
          .value(VALUE)
          .build();

      final var certificateDataValue = (CertificateDataValueVisualAcuities)
          convertervisualAcuities.convert(null, VISUALL_ACUITIES);

      assertEquals(expectedResult, certificateDataValue.getBinocular().getWithCorrection());
    }

    @Test
    void shallIncludeWithoutCorrection() {
      final var expectedResult = CertificateDataValueDouble.builder()
          .id(ID_2)
          .value(VALUE)
          .build();

      final var certificateDataValue = (CertificateDataValueVisualAcuities)
          convertervisualAcuities.convert(null, VISUALL_ACUITIES);

      assertEquals(expectedResult, certificateDataValue.getBinocular().getWithoutCorrection());
    }
  }
}