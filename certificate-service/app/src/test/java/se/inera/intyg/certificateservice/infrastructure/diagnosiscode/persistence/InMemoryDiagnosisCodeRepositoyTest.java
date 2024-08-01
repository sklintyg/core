package se.inera.intyg.certificateservice.infrastructure.diagnosiscode.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.Diagnosis;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisCode;
import se.inera.intyg.certificateservice.infrastructure.diagnosiscode.DiagnosisCodeProvider;

@ExtendWith(MockitoExtension.class)
class InMemoryDiagnosisCodeRepositoyTest {


  private static final DiagnosisCode DIAGNOSIS_CODE_ONE = new DiagnosisCode("codeOne");
  private static final DiagnosisCode DIAGNOSIS_CODE_TWO = new DiagnosisCode("codeTwo");
  @Mock
  private DiagnosisCodeProvider diagnosisCodeProvider;

  private InMemoryDiagnosisCodeRepositoy codeRepositoy;

  @Nested
  class GetByCodeTests {

    @BeforeEach
    void setUp() {
      codeRepositoy = new InMemoryDiagnosisCodeRepositoy(
          List.of(diagnosisCodeProvider)
      );
    }

    @Test
    void shallThrowIfCodeNotPresentInDiagnosesMap() {
      doReturn(List.of(Diagnosis.builder().code(DIAGNOSIS_CODE_TWO).build())).when(
          diagnosisCodeProvider).get();
      assertThrows(IllegalArgumentException.class,
          () -> codeRepositoy.getByCode(DIAGNOSIS_CODE_ONE));
    }

    @Test
    void shallReturnCodeIfPresentInDiagnosesMap() {
      final var expectedCode = Diagnosis.builder()
          .code(DIAGNOSIS_CODE_ONE)
          .build();

      doReturn(
          List.of(
              expectedCode,
              Diagnosis.builder()
                  .code(DIAGNOSIS_CODE_TWO)
                  .build()
          ))
          .when(diagnosisCodeProvider).get();

      final var actualCode = codeRepositoy.getByCode(DIAGNOSIS_CODE_ONE);
      assertEquals(expectedCode, actualCode);
    }
  }

  @Nested
  class FindByCodeTests {

    @BeforeEach
    void setUp() {
      codeRepositoy = new InMemoryDiagnosisCodeRepositoy(
          List.of(diagnosisCodeProvider)
      );
    }

    @Test
    void shallReturnEmptyIfCodeNotPresentInDiagnosesMap() {
      doReturn(List.of(Diagnosis.builder().code(DIAGNOSIS_CODE_TWO).build())).when(
          diagnosisCodeProvider).get();
      assertTrue(codeRepositoy.findByCode(DIAGNOSIS_CODE_ONE).isEmpty());
    }

    @Test
    void shallReturnCodeIfPresentInDiagnosesMap() {
      final var expectedCode = Diagnosis.builder()
          .code(DIAGNOSIS_CODE_ONE)
          .build();

      doReturn(
          List.of(
              expectedCode,
              Diagnosis.builder()
                  .code(DIAGNOSIS_CODE_TWO)
                  .build()
          ))
          .when(diagnosisCodeProvider).get();

      final var actualCode = codeRepositoy.findByCode(DIAGNOSIS_CODE_ONE);
      assertEquals(Optional.of(expectedCode), actualCode);
    }
  }
}
