package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDouble;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueOcularAcuities;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueOcularAcuity;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueConverterOcularAcuitiesTest {

  private ElementValueConverterOcularAcuities converter;


  @BeforeEach
  void setUp() {
    converter = new ElementValueConverterOcularAcuities();
  }

  @Test
  void shouldThrowExceptionIfInvalidValueType() {
    final var invalidType = CertificateDataValueText.builder().build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(invalidType)
    );
  }

  @Nested
  class ConverterTest {

    private final CertificateDataValueOcularAcuities ocularAcuities = CertificateDataValueOcularAcuities.builder()
        .rightEye(
            CertificateDataValueOcularAcuity.builder()
                .withCorrection(
                    CertificateDataValueDouble.builder()
                        .id("id1")
                        .value(1.0)
                        .build()
                )
                .withoutCorrection(
                    CertificateDataValueDouble.builder()
                        .id("id2")
                        .value(2.0)
                        .build()
                )
                .build()
        )
        .leftEye(
            CertificateDataValueOcularAcuity.builder()
                .withCorrection(
                    CertificateDataValueDouble.builder()
                        .id("id1")
                        .value(1.0)
                        .build()
                )
                .withoutCorrection(
                    CertificateDataValueDouble.builder()
                        .id("id2")
                        .value(2.0)
                        .build()
                )
                .build()
        )
        .binocular(
            CertificateDataValueOcularAcuity.builder()
                .withCorrection(
                    CertificateDataValueDouble.builder()
                        .id("id1")
                        .value(1.0)
                        .build()
                )
                .withoutCorrection(
                    CertificateDataValueDouble.builder()
                        .id("id2")
                        .value(2.0)
                        .build()
                )
                .build()
        )
        .build();

    @Nested
    class RightEyeTest {

      @Test
      void shouldConvertIdForWithCorrection() {
        final var expectedResult = new FieldId("id1");

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).rightEye();

        assertEquals(expectedResult, actualResult.withCorrection().id());
      }

      @Test
      void shouldConvertValueForWithCorrection() {
        final var expectedResult = 1.0;

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).rightEye();

        assertEquals(expectedResult, actualResult.withCorrection().value());
      }

      @Test
      void shouldConvertIdForWithoutCorrection() {
        final var expectedResult = new FieldId("id2");

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).rightEye();

        assertEquals(expectedResult, actualResult.withoutCorrection().id());
      }

      @Test
      void shouldConvertValueForWithoutCorrection() {
        final var expectedResult = 2.0;

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).rightEye();

        assertEquals(expectedResult, actualResult.withoutCorrection().value());
      }
    }

    @Nested
    class LeftEyeTest {

      @Test
      void shouldConvertIdForWithCorrection() {
        final var expectedResult = new FieldId("id1");

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).leftEye();

        assertEquals(expectedResult, actualResult.withCorrection().id());
      }

      @Test
      void shouldConvertValueForWithCorrection() {
        final var expectedResult = 1.0;

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).leftEye();

        assertEquals(expectedResult, actualResult.withCorrection().value());
      }

      @Test
      void shouldConvertIdForWithoutCorrection() {
        final var expectedResult = new FieldId("id2");

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).leftEye();

        assertEquals(expectedResult, actualResult.withoutCorrection().id());
      }

      @Test
      void shouldConvertValueForWithoutCorrection() {
        final var expectedResult = 2.0;

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).leftEye();

        assertEquals(expectedResult, actualResult.withoutCorrection().value());
      }
    }

    @Nested
    class BinocularTest {

      @Test
      void shouldConvertIdForWithCorrection() {
        final var expectedResult = new FieldId("id1");

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).binocular();

        assertEquals(expectedResult, actualResult.withCorrection().id());
      }

      @Test
      void shouldConvertValueForWithCorrection() {
        final var expectedResult = 1.0;

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).binocular();

        assertEquals(expectedResult, actualResult.withCorrection().value());
      }

      @Test
      void shouldConvertIdForWithoutCorrection() {
        final var expectedResult = new FieldId("id2");

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).binocular();

        assertEquals(expectedResult, actualResult.withoutCorrection().id());
      }

      @Test
      void shouldConvertValueForWithoutCorrection() {
        final var expectedResult = 2.0;

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).binocular();

        assertEquals(expectedResult, actualResult.withoutCorrection().value());
      }
    }
  }

  @Nested
  class NullOrEmptyTests {

    private final CertificateDataValueOcularAcuities ocularAcuities = CertificateDataValueOcularAcuities.builder()
        .rightEye(
            CertificateDataValueOcularAcuity.builder()
                .withCorrection(
                    CertificateDataValueDouble.builder()
                        .id("id1")
                        .build()
                )
                .withoutCorrection(
                    CertificateDataValueDouble.builder()
                        .id("id2")
                        .build()
                )
                .build()
        )
        .leftEye(
            CertificateDataValueOcularAcuity.builder()
                .withCorrection(
                    CertificateDataValueDouble.builder()
                        .id("id1")
                        .build()
                )
                .withoutCorrection(
                    CertificateDataValueDouble.builder()
                        .id("id2")
                        .build()
                )
                .build()
        )
        .binocular(
            CertificateDataValueOcularAcuity.builder()
                .withCorrection(
                    CertificateDataValueDouble.builder()
                        .id("id1")
                        .build()
                )
                .withoutCorrection(
                    CertificateDataValueDouble.builder()
                        .id("id2")
                        .build()
                )
                .build()
        )
        .build();

    @Nested
    class RightEyeTest {

      @Test
      void shouldConvertIdForWithCorrection() {
        final var expectedResult = new FieldId("id1");

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).rightEye();

        assertEquals(expectedResult, actualResult.withCorrection().id());
      }

      @Test
      void shouldConvertValueForWithCorrectionIfNull() {
        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).rightEye();

        assertNull(actualResult.withCorrection().value());
      }

      @Test
      void shouldConvertIdForWithoutCorrection() {
        final var expectedResult = new FieldId("id2");

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).rightEye();

        assertEquals(expectedResult, actualResult.withoutCorrection().id());
      }

      @Test
      void shouldConvertValueForWithoutCorrectionIfNull() {
        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).rightEye();

        assertNull(actualResult.withoutCorrection().value());
      }
    }

    @Nested
    class LeftEyeTest {

      @Test
      void shouldConvertIdForWithCorrection() {
        final var expectedResult = new FieldId("id1");

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).leftEye();

        assertEquals(expectedResult, actualResult.withCorrection().id());
      }

      @Test
      void shouldConvertValueForWithCorrectionIfNull() {
        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).leftEye();

        assertNull(actualResult.withCorrection().value());
      }

      @Test
      void shouldConvertIdForWithoutCorrection() {
        final var expectedResult = new FieldId("id2");

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).leftEye();

        assertEquals(expectedResult, actualResult.withoutCorrection().id());
      }

      @Test
      void shouldConvertValueForWithoutCorrectionIfNull() {
        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).leftEye();

        assertNull(actualResult.withoutCorrection().value());
      }
    }

    @Nested
    class BinocularTest {

      @Test
      void shouldConvertIdForWithCorrection() {
        final var expectedResult = new FieldId("id1");

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).binocular();

        assertEquals(expectedResult, actualResult.withCorrection().id());
      }

      @Test
      void shouldConvertValueForWithCorrectionIfNull() {
        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).binocular();

        assertNull(actualResult.withCorrection().value());
      }

      @Test
      void shouldConvertIdForWithoutCorrection() {
        final var expectedResult = new FieldId("id2");

        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).binocular();

        assertEquals(expectedResult, actualResult.withoutCorrection().id());
      }

      @Test
      void shouldConvertValueForWithoutCorrectionIfNull() {
        final var actualResult = ((ElementValueOcularAcuities)
            converter.convert(ocularAcuities)).binocular();

        assertNull(actualResult.withoutCorrection().value());
      }
    }
  }
}