package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.OcularAcuity;
import se.inera.intyg.certificateservice.domain.certificate.model.WithCorrection;
import se.inera.intyg.certificateservice.domain.certificate.model.WithoutCorrection;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueMapperOcularAcuitiesTest {

  private ElementValueMapperOcularAcuities elementValueMapperOcularAcuities;

  @BeforeEach
  void setUp() {
    elementValueMapperOcularAcuities = new ElementValueMapperOcularAcuities();
  }

  @Test
  void shallSupportClassMappedElementValueOcularAcuities() {
    assertTrue(elementValueMapperOcularAcuities.supports(MappedElementValueOcularAcuities.class));
  }

  @Test
  void shallSupportClassElementValueOcularAcuities() {
    assertTrue(elementValueMapperOcularAcuities.supports(ElementValueOcularAcuities.class));
  }

  @Test
  void shallReturnFalseForUnsupportedClass() {
    assertFalse(elementValueMapperOcularAcuities.supports(String.class));
  }

  @Test
  void shallMapToDomain() {
    final var expectedValue = ElementValueOcularAcuities.builder()
        .rightEye(
            OcularAcuity.builder()
                .withoutCorrection(
                    WithoutCorrection.builder()
                        .id(new FieldId("id1"))
                        .value(1.1)
                        .build()
                )
                .withCorrection(
                    WithCorrection.builder()
                        .id(new FieldId("id2"))
                        .value(1.2)
                        .build()
                )
                .build()
        )
        .leftEye(
            OcularAcuity.builder()
                .withoutCorrection(
                    WithoutCorrection.builder()
                        .id(new FieldId("id3"))
                        .value(2.1)
                        .build()
                )
                .withCorrection(
                    WithCorrection.builder()
                        .id(new FieldId("id4"))
                        .value(2.2)
                        .build()
                )
                .build()
        )
        .binocular(
            OcularAcuity.builder()
                .withoutCorrection(
                    WithoutCorrection.builder()
                        .id(new FieldId("id5"))
                        .value(3.1)
                        .build()
                )
                .withCorrection(
                    WithCorrection.builder()
                        .id(new FieldId("id6"))
                        .value(3.2)
                        .build()
                )
                .build()
        )
        .build();

    final var mappedElementValueOcularAcuities = MappedElementValueOcularAcuities.builder()
        .rightEye(
            MappedElementValueOcularAcuity.builder()
                .withoutCorrection(
                    MappedElementValueDouble.builder()
                        .id("id1")
                        .value(1.1)
                        .build()
                )
                .withCorrection(
                    MappedElementValueDouble.builder()
                        .id("id2")
                        .value(1.2)
                        .build()
                )
                .build()
        )
        .leftEye(
            MappedElementValueOcularAcuity.builder()
                .withoutCorrection(
                    MappedElementValueDouble.builder()
                        .id("id3")
                        .value(2.1)
                        .build()
                )
                .withCorrection(
                    MappedElementValueDouble.builder()
                        .id("id4")
                        .value(2.2)
                        .build()
                )
                .build()
        )
        .binocular(
            MappedElementValueOcularAcuity.builder()
                .withoutCorrection(
                    MappedElementValueDouble.builder()
                        .id("id5")
                        .value(3.1)
                        .build()
                )
                .withCorrection(
                    MappedElementValueDouble.builder()
                        .id("id6")
                        .value(3.2)
                        .build()
                )
                .build()
        )
        .build();

    final var actualValue = elementValueMapperOcularAcuities.toDomain(
        mappedElementValueOcularAcuities
    );

    assertEquals(expectedValue, actualValue);
  }

  @Test
  void shallMapToMapped() {
    final var elementValueOcularAcuities = ElementValueOcularAcuities.builder()
        .rightEye(
            OcularAcuity.builder()
                .withoutCorrection(
                    WithoutCorrection.builder()
                        .id(new FieldId("id1"))
                        .value(1.1)
                        .build()
                )
                .withCorrection(
                    WithCorrection.builder()
                        .id(new FieldId("id2"))
                        .value(1.2)
                        .build()
                )
                .build()
        )
        .leftEye(
            OcularAcuity.builder()
                .withoutCorrection(
                    WithoutCorrection.builder()
                        .id(new FieldId("id3"))
                        .value(2.1)
                        .build()
                )
                .withCorrection(
                    WithCorrection.builder()
                        .id(new FieldId("id4"))
                        .value(2.2)
                        .build()
                )
                .build()
        )
        .binocular(
            OcularAcuity.builder()
                .withoutCorrection(
                    WithoutCorrection.builder()
                        .id(new FieldId("id5"))
                        .value(3.1)
                        .build()
                )
                .withCorrection(
                    WithCorrection.builder()
                        .id(new FieldId("id6"))
                        .value(3.2)
                        .build()
                )
                .build()
        )
        .build();

    final var expectedValue = MappedElementValueOcularAcuities.builder()
        .rightEye(
            MappedElementValueOcularAcuity.builder()
                .withoutCorrection(
                    MappedElementValueDouble.builder()
                        .id("id1")
                        .value(1.1)
                        .build()
                )
                .withCorrection(
                    MappedElementValueDouble.builder()
                        .id("id2")
                        .value(1.2)
                        .build()
                )
                .build()
        )
        .leftEye(
            MappedElementValueOcularAcuity.builder()
                .withoutCorrection(
                    MappedElementValueDouble.builder()
                        .id("id3")
                        .value(2.1)
                        .build()
                )
                .withCorrection(
                    MappedElementValueDouble.builder()
                        .id("id4")
                        .value(2.2)
                        .build()
                )
                .build()
        )
        .binocular(
            MappedElementValueOcularAcuity.builder()
                .withoutCorrection(
                    MappedElementValueDouble.builder()
                        .id("id5")
                        .value(3.1)
                        .build()
                )
                .withCorrection(
                    MappedElementValueDouble.builder()
                        .id("id6")
                        .value(3.2)
                        .build()
                )
                .build()
        )
        .build();

    final var actualValue = elementValueMapperOcularAcuities.toMapped(
        elementValueOcularAcuities
    );

    assertEquals(expectedValue, actualValue);
  }
}