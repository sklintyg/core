package se.inera.intyg.certificateservice.infrastructure.diagnosiscode.icd10;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IcdCodeProviderTest {

  IcdCodeProvider icdCodeProvider;

  @BeforeEach
  void setUp() throws NoSuchFieldException, IllegalAccessException {
    icdCodeProvider = new IcdCodeProvider();
    final var field = icdCodeProvider.getClass()
        .getDeclaredField("diagnosisCodesFile");
    field.setAccessible(true);
    field.set(icdCodeProvider, "diagnosiscode/icd10se/icd-10-se.tsv");
  }

  @Test
  void shallReturnListOfDiagnosisFromFile() {
    final var diagnosisList = icdCodeProvider.get();
    assertFalse(diagnosisList.isEmpty());
  }

  @Test
  void shallIncludeCodeInDiagnosis() {
    final var diagnosisList = icdCodeProvider.get();
    diagnosisList.forEach(
        diagnosis -> assertNotNull(diagnosis.code())
    );
  }

  @Test
  void shallIncludeDescriptionInDiagnosis() {
    final var diagnosisList = icdCodeProvider.get();
    diagnosisList.forEach(
        diagnosis -> assertNotNull(diagnosis.description())
    );
  }
}
