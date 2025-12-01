package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueTable;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.VisualAcuity;

class ElementConfigurationVisualAcuitiesTest {

  private static final ElementConfigurationVisualAcuities CONFIG = ElementConfigurationVisualAcuities.builder()
      .rightEye(ElementVisualAcuity.builder()
          .label("Höger öga")
          .build())
      .leftEye(ElementVisualAcuity.builder()
          .label("Vänster öga")
          .build())
      .binocular(ElementVisualAcuity.builder()
          .label("Binokulärt")
          .build())
      .withoutCorrectionLabel("H1")
      .withCorrectionLabel("H2")
      .build();

  private static final ElementValueVisualAcuities VALUE = ElementValueVisualAcuities.builder()
      .rightEye(
          VisualAcuity.builder()
              .withoutCorrection(
                  Correction.builder()
                      .value(1D)
                      .id(new FieldId("1.1"))
                      .build()
              )
              .build()
      )
      .leftEye(
          VisualAcuity.builder()
              .withoutCorrection(
                  Correction.builder()
                      .value(2.5)
                      .id(new FieldId("1.3"))
                      .build())
              .withCorrection(
                  Correction.builder()
                      .value(3D)
                      .id(new FieldId("1.4"))
                      .build()
              )
              .build()
      )
      .binocular(
          VisualAcuity.builder()
              .withCorrection(
                  Correction.builder()
                      .value(10.1)
                      .id(new FieldId("1"))
                      .build())
              .withoutCorrection(Correction.builder().build())
              .build()
      )
      .build();

  @Test
  void shouldReturnSimplifiedValue() {
    final var expected = Optional.of(
        ElementSimplifiedValueTable.builder()
            .headings(List.of("H1", "H2"))
            .values(List.of(
                    List.of("Höger öga", String.format("%.1f", 1.0), "-"),
                    List.of("Vänster öga", String.format("%.1f", 2.5), String.format("%.1f", 3.0)),
                    List.of("Binokulärt", "-", String.format("%.1f", 10.1))
                )
            )
            .build()
    );

    assertEquals(expected, CONFIG.simplified(VALUE));
  }

  @Test
  void shouldReturnSimplifiedValueIfEmpty() {
    final var expected = Optional.of(
        ElementSimplifiedValueText.builder()
            .text("Ej angivet")
            .build()
    );

    final var emptyValue = ElementValueVisualAcuities.builder()
        .rightEye(VisualAcuity.builder()
            .withoutCorrection(Correction.builder().build())
            .withCorrection(Correction.builder().build())
            .build())
        .leftEye(VisualAcuity.builder()
            .withoutCorrection(Correction.builder().build())
            .withCorrection(Correction.builder().build())
            .build())
        .binocular(VisualAcuity.builder()
            .withoutCorrection(Correction.builder().build())
            .withCorrection(Correction.builder().build())
            .build())
        .build();

    assertEquals(expected, CONFIG.simplified(emptyValue));
  }
}