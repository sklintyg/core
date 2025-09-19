package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueTable;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;

class ElementConfigurationDiagnosisTest {

  @Test
  void simplifiedShouldReturnTableWithHeadingsAndRows() {
    final var config = ElementConfigurationDiagnosis.builder().build();
    final var diagnosis1 = ElementValueDiagnosis.builder()
        .code("A01")
        .description("Description 1")
        .build();
    final var diagnosis2 = ElementValueDiagnosis.builder()
        .code("B02")
        .description("Description 2")
        .build();
    final var value = ElementValueDiagnosisList.builder()
        .diagnoses(List.of(diagnosis1, diagnosis2))
        .build();

    final var simplified = config.simplified(value);

    assertAll(
        () -> assertTrue(simplified.isPresent()),
        () -> {
          final var table = (ElementSimplifiedValueTable) simplified.get();
          assertAll(
              () -> assertEquals(List.of("Diagnoskod enligt ICD-10 SE", ""), table.headings()),
              () -> assertEquals(2, table.values().size()),
              () -> assertEquals(List.of("A01", "Description 1"), table.values().getFirst()),
              () -> assertEquals(List.of("B02", "Description 2"), table.values().get(1))
          );
        }
    );
  }

  @Test
  void simplifiedShouldReturnEmptyOptionalIfEmpty() {
    final var config = ElementConfigurationDiagnosis.builder().build();
    final var value = ElementValueDiagnosisList.builder().diagnoses(List.of()).build();
    final var simplified = config.simplified(value);
    assertAll(
        () -> assertTrue(simplified.isEmpty())
    );
  }
}
