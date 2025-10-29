package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.certificate.model.CustomMapperId.UNIFIED_DIAGNOSIS_LIST;

import jakarta.xml.bind.JAXBElement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisTerminology;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.Diagnosis;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisCode;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisDescription;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorUnifiedDiagnosisListTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final String FIELD_ID = "FIELD_ID";
  private static final String CODE_ONE = "CODE_ONE";
  private static final String CODE_TWO = "CODE_TWO";
  private static final String DESCRIPTION_ONE = "DESCRIPTION_ONE";
  private static final String DESCRIPTION_TWO = "DESCRIPTION_TWO";
  private static final String CODE_SYSTEM = "CODE_SYSTEM";
  private static final String ICD_10_SE = "icd-10-se";

  private static ElementData data;

  private XmlGeneratorUnifiedDiagnosisList xmlGenerator;
  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  @BeforeEach
  void setUp() {
    xmlGenerator = new XmlGeneratorUnifiedDiagnosisList(diagnosisCodeRepository);
  }

  @Test
  void shallReturnIdUnifiedDiagnosisList() {
    assertEquals(UNIFIED_DIAGNOSIS_LIST, xmlGenerator.id());
  }

  @Nested
  class TestOneDiagnosis {

    private ElementSpecification elementSpecification;

    @BeforeEach
    void setup() {
      data = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueDiagnosisList.builder()
                  .diagnoses(
                      List.of(
                          ElementValueDiagnosis.builder()
                              .code(CODE_ONE)
                              .id(new FieldId("CODE_ID"))
                              .description(DESCRIPTION_ONE)
                              .terminology(ICD_10_SE)
                              .build()
                      )
                  )
                  .build()
          )
          .build();
      elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationDiagnosis.builder()
                  .id(new FieldId(FIELD_ID))
                  .terminology(
                      List.of(
                          new ElementDiagnosisTerminology(ICD_10_SE, "label", CODE_SYSTEM,
                              List.of())
                      )
                  )
                  .build()
          )
          .build();

      when(diagnosisCodeRepository.findByCode(any(DiagnosisCode.class)))
          .thenAnswer(invocation -> {
            DiagnosisCode code = invocation.getArgument(0, DiagnosisCode.class);
            if (CODE_ONE.equals(code.value())) {
              return Optional.of(Diagnosis.builder()
                  .code(new DiagnosisCode(CODE_ONE))
                  .description(new DiagnosisDescription(DESCRIPTION_ONE))
                  .build());
            }
            return Optional.empty();
          });
    }

    @Test
    void shallMapSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var first = response.getFirst();
      assertAll(
          () -> assertEquals(1, response.size()),
          () -> assertEquals(QUESTION_ID, first.getId())
      );
    }

    @Test
    void shallMapDelsvarForCode() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.getFirst().getDelsvar();
      final var delsvarCode = response.getFirst().getDelsvar().getLast();
      final var jaxbElement = (JAXBElement<CVType>) delsvarCode.getContent().getFirst();
      final var cvType = jaxbElement.getValue();

      assertAll(
          () -> assertEquals(2, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarCode.getId()),
          () -> assertEquals(CODE_ONE, cvType.getCode()),
          () -> assertEquals(CODE_SYSTEM, cvType.getCodeSystem()),
          () -> assertEquals(DESCRIPTION_ONE, cvType.getDisplayName())
      );
    }

    @Test
    void shallMapDelsvarForDescription() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.getFirst().getDelsvar();
      final var delsvarDescription = response.getFirst().getDelsvar().getFirst();
      final var delsvarDescriptionAsStr = delsvarDescription.getContent().getFirst();

      assertAll(
          () -> assertEquals(2, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".1", delsvarDescription.getId()),
          () -> assertEquals(DESCRIPTION_ONE, delsvarDescriptionAsStr)
      );
    }

    @Test
    void shallExcludeDescriptionIfMissingDiagnosisCodeInRepository() {
      final var elementData = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueDiagnosisList.builder()
                  .diagnoses(
                      List.of(
                          ElementValueDiagnosis.builder()
                              .code(CODE_TWO)
                              .id(new FieldId(CODE_SYSTEM))
                              .description(DESCRIPTION_ONE)
                              .terminology(ICD_10_SE)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var response = xmlGenerator.generate(elementData, elementSpecification);

      final var delsvar = response.getFirst().getDelsvar();
      final var delsvarCode = response.getFirst().getDelsvar().getLast();
      final var jaxbElement = (JAXBElement<CVType>) delsvarCode.getContent().getFirst();
      final var cvType = jaxbElement.getValue();

      assertAll(
          () -> assertEquals(2, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarCode.getId()),
          () -> assertEquals(CODE_TWO, cvType.getCode()),
          () -> assertEquals(CODE_SYSTEM, cvType.getCodeSystem()),
          () -> assertNull(cvType.getDisplayName())
      );
    }
  }

  @Nested
  class TestMultipleDiagnoses {

    private ElementSpecification elementSpecification;

    @BeforeEach
    void setup() {
      data = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueDiagnosisList.builder()
                  .diagnoses(
                      List.of(
                          ElementValueDiagnosis.builder()
                              .code(CODE_ONE)
                              .id(new FieldId("CODE_ID"))
                              .description(DESCRIPTION_ONE)
                              .terminology(ICD_10_SE)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .code(CODE_TWO)
                              .id(new FieldId("CODE_ID"))
                              .description(DESCRIPTION_TWO)
                              .terminology(ICD_10_SE)
                              .build()
                      )
                  )
                  .build()
          )
          .build();
      elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationDiagnosis.builder()
                  .id(new FieldId(FIELD_ID))
                  .terminology(
                      List.of(
                          new ElementDiagnosisTerminology(ICD_10_SE, "label", CODE_SYSTEM,
                              List.of())
                      )
                  )
                  .build()
          )
          .build();

      when(diagnosisCodeRepository.findByCode(any(DiagnosisCode.class)))
          .thenAnswer(invocation -> {
            DiagnosisCode code = invocation.getArgument(0, DiagnosisCode.class);
            if (CODE_ONE.equals(code.value())) {
              return Optional.of(Diagnosis.builder()
                  .code(new DiagnosisCode(CODE_ONE))
                  .description(new DiagnosisDescription(DESCRIPTION_ONE))
                  .build());
            }
            return Optional.of(Diagnosis.builder()
                .code(new DiagnosisCode(CODE_TWO))
                .description(new DiagnosisDescription(DESCRIPTION_TWO))
                .build());
          });
    }

    @Test
    void shallMapToOneSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      assertAll(
          () -> assertEquals(1, response.size()),
          () -> assertEquals(QUESTION_ID, response.getFirst().getId())
      );
    }

    @Test
    void shallMapDelsvarForCode() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.getFirst().getDelsvar();
      final var delsvarFirstCode = delsvar.get(1);
      final var delsvarSecondCode = delsvar.getLast();
      final var jaxbElement1 = (JAXBElement<CVType>) delsvarFirstCode.getContent().getFirst();
      final var jaxbElement2 = (JAXBElement<CVType>) delsvarSecondCode.getContent().getFirst();
      final var cvType1 = jaxbElement1.getValue();
      final var cvType2 = jaxbElement2.getValue();

      assertAll(
          () -> assertEquals(1, response.size()),
          () -> assertEquals(4, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarFirstCode.getId()),
          () -> assertEquals(QUESTION_ID + ".4", delsvarSecondCode.getId()),
          () -> assertEquals(CODE_ONE, cvType1.getCode()),
          () -> assertEquals(CODE_SYSTEM, cvType1.getCodeSystem()),
          () -> assertEquals(DESCRIPTION_ONE, cvType1.getDisplayName()),
          () -> assertEquals(CODE_TWO, cvType2.getCode()),
          () -> assertEquals(CODE_SYSTEM, cvType2.getCodeSystem()),
          () -> assertEquals(DESCRIPTION_TWO, cvType2.getDisplayName())
      );
    }

    @Test
    void shallMapDelsvarForDescription() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.getFirst().getDelsvar();
      final var delsvarFirstDescription = delsvar.getFirst();
      final var delsvarSecondDescription = delsvar.get(2);

      assertAll(
          () -> assertEquals(4, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".1", delsvarFirstDescription.getId()),
          () -> assertEquals(DESCRIPTION_ONE, delsvarFirstDescription.getContent().getFirst()),
          () -> assertEquals(QUESTION_ID + ".3", delsvarSecondDescription.getId()),
          () -> assertEquals(DESCRIPTION_TWO, delsvarSecondDescription.getContent().getFirst())
      );
    }
  }

  @Nested
  class IncompleDataTests {

    private ElementSpecification elementSpecification;

    @Test
    void shallMapEmptyIfNoValue() {

      final var elementData = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueDiagnosis.builder()
                  .id(new FieldId(FIELD_ID))
                  .build()
          )
          .build();

      final var response = xmlGenerator.generate(elementData, elementSpecification);

      assertTrue(response.isEmpty());
    }

    @Test
    void shallThrowIfIncorrectConfiguration() {
      final var elementData = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueDiagnosisList.builder()
                  .diagnoses(
                      List.of(
                          ElementValueDiagnosis.builder()
                              .code(CODE_ONE)
                              .id(new FieldId("CODE_ID"))
                              .description(DESCRIPTION_ONE)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> xmlGenerator.generate(elementData, elementSpecification)
      );
    }

    @Test
    void shallMapEmptyIfValueIsNotDiagnosis() {
      final var elementData = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueUnitContactInformation.builder()
                  .build()
          )
          .build();

      final var response = xmlGenerator.generate(elementData, elementSpecification);

      assertTrue(response.isEmpty());
    }

    @Test
    void shallReturnEmptyIfDiagnosesIsNull() {
      elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationDiagnosis.builder().build()
          )
          .build();
      final var elementData = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueDiagnosisList.builder()
                  .diagnoses(null)
                  .build()
          )
          .build();

      final var response = xmlGenerator.generate(elementData, elementSpecification);

      assertTrue(response.isEmpty());
    }

    @Test
    void shallReturnEmptyIfDiagnosesIsEmpty() {
      elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationDiagnosis.builder().build()
          )
          .build();
      final var elementData = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueDiagnosisList.builder()
                  .diagnoses(Collections.emptyList())
                  .build()
          )
          .build();

      final var response = xmlGenerator.generate(elementData, elementSpecification);

      assertTrue(response.isEmpty());
    }
  }
}
