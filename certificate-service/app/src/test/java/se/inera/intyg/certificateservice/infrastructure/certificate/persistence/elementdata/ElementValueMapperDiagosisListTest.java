package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueMapperDiagosisListTest {

  private static final String EXPECTED_ID = "expectedId";
  private static final String EXPECTED_DESCRIPTION = "expectedDescription";
  private static final String EXPECTED_TERMINOLOGY = "expectedTerminology";
  private static final String EXPECTED_CODE = "expectedCode";
  private ElementValueMapperDiagosisList elementValueMapperDiagosisList;

  @BeforeEach
  void setUp() {
    elementValueMapperDiagosisList = new ElementValueMapperDiagosisList();
  }

  @Test
  void shallReturnTrueIfClassMappedElementValueCode() {
    assertTrue(elementValueMapperDiagosisList.supports(MappedElementValueDiagnosisList.class));
  }

  @Test
  void shallReturnTrueIfClassElementValueCode() {
    assertTrue(elementValueMapperDiagosisList.supports(ElementValueDiagnosisList.class));
  }

  @Test
  void shallReturnFalseForUnsupportedClass() {
    assertFalse(elementValueMapperDiagosisList.supports(String.class));
  }


  @Test
  void shallMapToDomain() {
    final var expectedValue = ElementValueDiagnosisList.builder()
        .diagnoses(
            List.of(
                ElementValueDiagnosis.builder()
                    .id(new FieldId(EXPECTED_ID))
                    .description(EXPECTED_DESCRIPTION)
                    .terminology(EXPECTED_TERMINOLOGY)
                    .code(EXPECTED_CODE)
                    .build()
            )
        )
        .build();

    final var mappedElementValueDiagnosisList = MappedElementValueDiagnosisList.builder()
        .mappedDiagnoses(
            List.of(
                MappedDiagnosis.builder()
                    .id(EXPECTED_ID)
                    .description(EXPECTED_DESCRIPTION)
                    .terminology(EXPECTED_TERMINOLOGY)
                    .code(EXPECTED_CODE)
                    .build()
            )
        )
        .build();

    final var actualValue = elementValueMapperDiagosisList.toDomain(
        mappedElementValueDiagnosisList
    );

    assertEquals(expectedValue, actualValue);
  }

  @Test
  void shallMapToMapped() {
    final var elementValueDiagnosisList = ElementValueDiagnosisList.builder()
        .diagnoses(
            List.of(
                ElementValueDiagnosis.builder()
                    .id(new FieldId(EXPECTED_ID))
                    .description(EXPECTED_DESCRIPTION)
                    .terminology(EXPECTED_TERMINOLOGY)
                    .code(EXPECTED_CODE)
                    .build()
            )
        )
        .build();

    final var expectedValue = MappedElementValueDiagnosisList.builder()
        .mappedDiagnoses(
            List.of(
                MappedDiagnosis.builder()
                    .id(EXPECTED_ID)
                    .description(EXPECTED_DESCRIPTION)
                    .terminology(EXPECTED_TERMINOLOGY)
                    .code(EXPECTED_CODE)
                    .build()
            )
        )
        .build();

    final var actualValue = elementValueMapperDiagosisList.toMapped(
        elementValueDiagnosisList
    );

    assertEquals(expectedValue, actualValue);
  }
}
