package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.OcularAcuity;
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
        .rightEye(OcularAcuity.builder()
            .doubleId(new FieldId("id1"))
            .withoutCorrection(1.1)
            .withCorrection(1.2)
            .build()
        )
        .leftEye(OcularAcuity.builder()
            .doubleId(new FieldId("id2"))
            .withoutCorrection(2.1)
            .withCorrection(2.2)
            .build()
        )
        .binocular(OcularAcuity.builder()
            .doubleId(new FieldId("id3"))
            .withoutCorrection(3.1)
            .withCorrection(3.2)
            .build()
        )
        .build();

    final var mappedElementValueOcularAcuities = MappedElementValueOcularAcuities.builder()
        .rightEye(MappedElementValueOcularAcuity.builder()
            .id("id1")
            .withoutCorrection(1.1)
            .withCorrection(1.2)
            .build()
        )
        .leftEye(MappedElementValueOcularAcuity.builder()
            .id("id2")
            .withoutCorrection(2.1)
            .withCorrection(2.2)
            .build()
        )
        .binocular(MappedElementValueOcularAcuity.builder()
            .id("id3")
            .withoutCorrection(3.1)
            .withCorrection(3.2)
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
    final var expectedValue = MappedElementValueOcularAcuities.builder()
        .rightEye(MappedElementValueOcularAcuity.builder()
            .id("id1")
            .withoutCorrection(1.1)
            .withCorrection(1.2)
            .build()
        )
        .leftEye(MappedElementValueOcularAcuity.builder()
            .id("id2")
            .withoutCorrection(2.1)
            .withCorrection(2.2)
            .build()
        )
        .binocular(MappedElementValueOcularAcuity.builder()
            .id("id3")
            .withoutCorrection(3.1)
            .withCorrection(3.2)
            .build()
        )
        .build();

    final var elementValueOcularAcuities = ElementValueOcularAcuities.builder()
        .rightEye(OcularAcuity.builder()
            .doubleId(new FieldId("id1"))
            .withoutCorrection(1.1)
            .withCorrection(1.2)
            .build()
        )
        .leftEye(OcularAcuity.builder()
            .doubleId(new FieldId("id2"))
            .withoutCorrection(2.1)
            .withCorrection(2.2)
            .build()
        )
        .binocular(OcularAcuity.builder()
            .doubleId(new FieldId("id3"))
            .withoutCorrection(3.1)
            .withCorrection(3.2)
            .build()
        )
        .build();

    final var actualValue = elementValueMapperOcularAcuities.toMapped(
        elementValueOcularAcuities
    );

    assertEquals(expectedValue, actualValue);
  }

}
