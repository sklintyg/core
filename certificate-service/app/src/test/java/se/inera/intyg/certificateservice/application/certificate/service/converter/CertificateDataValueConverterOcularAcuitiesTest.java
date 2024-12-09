package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDouble;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.OcularAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataValueConverterOcularAcuitiesTest {

  private static final String ID_1 = "id1";
  private static final String ID_2 = "id2";
  private static final double VALUE = 1.0;
  private static final OcularAcuity OCULAR_ACUITY = OcularAcuity.builder()
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
  private CertificateDataValueConverterOcularAcuities converterOcularAcuities;

  @BeforeEach
  void setUp() {
    converterOcularAcuities = new CertificateDataValueConverterOcularAcuities();
  }

  @Test
  void shallThrowIfInvalidType() {
    final var elementValueDate = ElementValueDate.builder().build();
    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> converterOcularAcuities.convert(null,
            elementValueDate));
    assertEquals("Invalid value type. Type was '%s'".formatted(elementValueDate.getClass()),
        illegalStateException.getMessage());
  }

  @Nested
  class RightEyeTests {

    private static final ElementValueOcularAcuities RIGHT_EYE_ELEMENT_VALUE_OCULAR_ACUITIES =
        ElementValueOcularAcuities.builder()
            .rightEye(OCULAR_ACUITY)
            .build();

    @Test
    void shallNotIncludeRightEyeIfNull() {
      final var elementValueOcularAcuities = ElementValueOcularAcuities.builder().build();
      final var certificateDataValue = (CertificateDataValueOcularAcuities)
          converterOcularAcuities.convert(null, elementValueOcularAcuities);
      assertNull(certificateDataValue.getRightEye());
    }

    @Test
    void shallIncludeWithCorrection() {
      final var expectedResult = CertificateDataValueDouble.builder()
          .id(ID_1)
          .value(VALUE)
          .build();

      final var certificateDataValue = (CertificateDataValueOcularAcuities)
          converterOcularAcuities.convert(null, RIGHT_EYE_ELEMENT_VALUE_OCULAR_ACUITIES);

      assertEquals(expectedResult, certificateDataValue.getRightEye().getWithCorrection());
    }

    @Test
    void shallIncludeWithoutCorrection() {
      final var expectedResult = CertificateDataValueDouble.builder()
          .id(ID_2)
          .value(VALUE)
          .build();

      final var certificateDataValue = (CertificateDataValueOcularAcuities)
          converterOcularAcuities.convert(null, RIGHT_EYE_ELEMENT_VALUE_OCULAR_ACUITIES);

      assertEquals(expectedResult, certificateDataValue.getRightEye().getWithoutCorrection());
    }
  }

  @Nested
  class LeftEyeTests {

    private static final ElementValueOcularAcuities RIGHT_EYE_ELEMENT_VALUE_OCULAR_ACUITIES =
        ElementValueOcularAcuities.builder()
            .leftEye(OCULAR_ACUITY)
            .build();

    @Test
    void shallNotIncludeLeftEyeIfNull() {
      final var elementValueOcularAcuities = ElementValueOcularAcuities.builder().build();
      final var certificateDataValue = (CertificateDataValueOcularAcuities)
          converterOcularAcuities.convert(null, elementValueOcularAcuities);
      assertNull(certificateDataValue.getLeftEye());
    }

    @Test
    void shallIncludeWithCorrection() {
      final var expectedResult = CertificateDataValueDouble.builder()
          .id(ID_1)
          .value(VALUE)
          .build();

      final var certificateDataValue = (CertificateDataValueOcularAcuities)
          converterOcularAcuities.convert(null, RIGHT_EYE_ELEMENT_VALUE_OCULAR_ACUITIES);

      assertEquals(expectedResult, certificateDataValue.getLeftEye().getWithCorrection());
    }

    @Test
    void shallIncludeWithoutCorrection() {
      final var expectedResult = CertificateDataValueDouble.builder()
          .id(ID_2)
          .value(VALUE)
          .build();

      final var certificateDataValue = (CertificateDataValueOcularAcuities)
          converterOcularAcuities.convert(null, RIGHT_EYE_ELEMENT_VALUE_OCULAR_ACUITIES);

      assertEquals(expectedResult, certificateDataValue.getLeftEye().getWithoutCorrection());
    }
  }

  @Nested
  class BinocularTests {

    private static final ElementValueOcularAcuities RIGHT_EYE_ELEMENT_VALUE_OCULAR_ACUITIES =
        ElementValueOcularAcuities.builder()
            .binocular(OCULAR_ACUITY)
            .build();

    @Test
    void shallNotIncludeBinocularIfNull() {
      final var elementValueOcularAcuities = ElementValueOcularAcuities.builder().build();
      final var certificateDataValue = (CertificateDataValueOcularAcuities)
          converterOcularAcuities.convert(null, elementValueOcularAcuities);
      assertNull(certificateDataValue.getBinocular());
    }

    @Test
    void shallIncludeWithCorrection() {
      final var expectedResult = CertificateDataValueDouble.builder()
          .id(ID_1)
          .value(VALUE)
          .build();

      final var certificateDataValue = (CertificateDataValueOcularAcuities)
          converterOcularAcuities.convert(null, RIGHT_EYE_ELEMENT_VALUE_OCULAR_ACUITIES);

      assertEquals(expectedResult, certificateDataValue.getBinocular().getWithCorrection());
    }

    @Test
    void shallIncludeWithoutCorrection() {
      final var expectedResult = CertificateDataValueDouble.builder()
          .id(ID_2)
          .value(VALUE)
          .build();

      final var certificateDataValue = (CertificateDataValueOcularAcuities)
          converterOcularAcuities.convert(null, RIGHT_EYE_ELEMENT_VALUE_OCULAR_ACUITIES);

      assertEquals(expectedResult, certificateDataValue.getBinocular().getWithoutCorrection());
    }
  }
}
