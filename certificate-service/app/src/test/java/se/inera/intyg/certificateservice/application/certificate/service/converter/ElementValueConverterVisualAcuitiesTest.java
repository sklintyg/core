package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDouble;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueVisualAcuities;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueVisualAcuity;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueConverterVisualAcuitiesTest {

  private ElementValueConverterVisualAcuities converter;


  @BeforeEach
  void setUp() {
    converter = new ElementValueConverterVisualAcuities();
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

    private final CertificateDataValueVisualAcuities visualAcuities = CertificateDataValueVisualAcuities.builder()
        .rightEye(
            CertificateDataValueVisualAcuity.builder()
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
            CertificateDataValueVisualAcuity.builder()
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
            CertificateDataValueVisualAcuity.builder()
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

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).rightEye();

        assertEquals(expectedResult, actualResult.withCorrection().id());
      }

      @Test
      void shouldConvertValueForWithCorrection() {
        final var expectedResult = 1.0;

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).rightEye();

        assertEquals(expectedResult, actualResult.withCorrection().value());
      }

      @Test
      void shouldConvertIdForWithoutCorrection() {
        final var expectedResult = new FieldId("id2");

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).rightEye();

        assertEquals(expectedResult, actualResult.withoutCorrection().id());
      }

      @Test
      void shouldConvertValueForWithoutCorrection() {
        final var expectedResult = 2.0;

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).rightEye();

        assertEquals(expectedResult, actualResult.withoutCorrection().value());
      }
    }

    @Nested
    class LeftEyeTest {

      @Test
      void shouldConvertIdForWithCorrection() {
        final var expectedResult = new FieldId("id1");

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).leftEye();

        assertEquals(expectedResult, actualResult.withCorrection().id());
      }

      @Test
      void shouldConvertValueForWithCorrection() {
        final var expectedResult = 1.0;

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).leftEye();

        assertEquals(expectedResult, actualResult.withCorrection().value());
      }

      @Test
      void shouldConvertIdForWithoutCorrection() {
        final var expectedResult = new FieldId("id2");

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).leftEye();

        assertEquals(expectedResult, actualResult.withoutCorrection().id());
      }

      @Test
      void shouldConvertValueForWithoutCorrection() {
        final var expectedResult = 2.0;

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).leftEye();

        assertEquals(expectedResult, actualResult.withoutCorrection().value());
      }
    }

    @Nested
    class BinocularTest {

      @Test
      void shouldConvertIdForWithCorrection() {
        final var expectedResult = new FieldId("id1");

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).binocular();

        assertEquals(expectedResult, actualResult.withCorrection().id());
      }

      @Test
      void shouldConvertValueForWithCorrection() {
        final var expectedResult = 1.0;

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).binocular();

        assertEquals(expectedResult, actualResult.withCorrection().value());
      }

      @Test
      void shouldConvertIdForWithoutCorrection() {
        final var expectedResult = new FieldId("id2");

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).binocular();

        assertEquals(expectedResult, actualResult.withoutCorrection().id());
      }

      @Test
      void shouldConvertValueForWithoutCorrection() {
        final var expectedResult = 2.0;

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).binocular();

        assertEquals(expectedResult, actualResult.withoutCorrection().value());
      }
    }
  }

  @Nested
  class NullOrEmptyTests {

    private final CertificateDataValueVisualAcuities visualAcuities = CertificateDataValueVisualAcuities.builder()
        .rightEye(
            CertificateDataValueVisualAcuity.builder()
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
            CertificateDataValueVisualAcuity.builder()
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
            CertificateDataValueVisualAcuity.builder()
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

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).rightEye();

        assertEquals(expectedResult, actualResult.withCorrection().id());
      }

      @Test
      void shouldConvertValueForWithCorrectionIfNull() {
        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).rightEye();

        assertNull(actualResult.withCorrection().value());
      }

      @Test
      void shouldConvertIdForWithoutCorrection() {
        final var expectedResult = new FieldId("id2");

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).rightEye();

        assertEquals(expectedResult, actualResult.withoutCorrection().id());
      }

      @Test
      void shouldConvertValueForWithoutCorrectionIfNull() {
        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).rightEye();

        assertNull(actualResult.withoutCorrection().value());
      }
    }

    @Nested
    class LeftEyeTest {

      @Test
      void shouldConvertIdForWithCorrection() {
        final var expectedResult = new FieldId("id1");

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).leftEye();

        assertEquals(expectedResult, actualResult.withCorrection().id());
      }

      @Test
      void shouldConvertValueForWithCorrectionIfNull() {
        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).leftEye();

        assertNull(actualResult.withCorrection().value());
      }

      @Test
      void shouldConvertIdForWithoutCorrection() {
        final var expectedResult = new FieldId("id2");

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).leftEye();

        assertEquals(expectedResult, actualResult.withoutCorrection().id());
      }

      @Test
      void shouldConvertValueForWithoutCorrectionIfNull() {
        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).leftEye();

        assertNull(actualResult.withoutCorrection().value());
      }
    }

    @Nested
    class BinocularTest {

      @Test
      void shouldConvertIdForWithCorrection() {
        final var expectedResult = new FieldId("id1");

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).binocular();

        assertEquals(expectedResult, actualResult.withCorrection().id());
      }

      @Test
      void shouldConvertValueForWithCorrectionIfNull() {
        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).binocular();

        assertNull(actualResult.withCorrection().value());
      }

      @Test
      void shouldConvertIdForWithoutCorrection() {
        final var expectedResult = new FieldId("id2");

        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).binocular();

        assertEquals(expectedResult, actualResult.withoutCorrection().id());
      }

      @Test
      void shouldConvertValueForWithoutCorrectionIfNull() {
        final var actualResult = ((ElementValueVisualAcuities)
            converter.convert(visualAcuities)).binocular();

        assertNull(actualResult.withoutCorrection().value());
      }
    }
  }
}