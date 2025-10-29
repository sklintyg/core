package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.xml.bind.JAXBElement;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;

class XmlGeneratorDiagnosisListTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final String FIELD_ID = "FIELD_ID";
  private static final String CODE_ONE = "CODE_ONE";
  private static final String CODE_TWO = "CODE_TWO";
  private static final String DESCRIPTION_ONE = "DESCRIPTION_ONE";
  private static final String DESCRIPTION_TWO = "DESCRIPTION_TWO";
  private static final String CODE_SYSTEM = "CODE_SYSTEM";
  private static final String ICD_10_SE = "icd-10-se";

  private static ElementData data;

  private XmlGeneratorDiagnosisList xmlGenerator;

  @BeforeEach
  void setUp() {
    xmlGenerator = new XmlGeneratorDiagnosisList();
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
                              List.of()))
                  )
                  .build()
          )
          .build();
    }

    @Test
    void shallMapSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var first = response.get(0);
      assertAll(
          () -> assertEquals(1, response.size()),
          () -> assertEquals(QUESTION_ID, first.getId())
      );
    }

    @Test
    void shallMapDelsvarForCode() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarCode = response.get(0).getDelsvar().get(0);
      final var jaxbElement = (JAXBElement<CVType>) delsvarCode.getContent().get(0);
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

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarDescription = response.get(0).getDelsvar().get(1);
      final var delsvarDescriptionAsStr = delsvarDescription.getContent().get(0);

      assertAll(
          () -> assertEquals(2, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".1", delsvarDescription.getId()),
          () -> assertEquals(DESCRIPTION_ONE, delsvarDescriptionAsStr)
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
    }

    @Test
    void shallMapMultipleSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var first = response.get(0);
      final var second = response.get(1);
      assertAll(
          () -> assertEquals(2, response.size()),
          () -> assertEquals(QUESTION_ID, first.getId()),
          () -> assertEquals(QUESTION_ID, second.getId())
      );
    }

    @Test
    void shallMapDelsvarForCodeForMultipleSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvarFirst = response.get(0).getDelsvar();
      final var delsvarFirstCode = delsvarFirst.get(0);
      final var delsvarSecond = response.get(1).getDelsvar();
      final var delsvarSecondCode = delsvarSecond.get(0);
      final var jaxbElement1 = (JAXBElement<CVType>) delsvarFirstCode.getContent().get(0);
      final var jaxbElement2 = (JAXBElement<CVType>) delsvarSecondCode.getContent().get(0);
      final var cvType1 = jaxbElement1.getValue();
      final var cvType2 = jaxbElement2.getValue();

      assertAll(
          () -> assertEquals(2, response.size()),
          () -> assertEquals(2, delsvarFirst.size()),
          () -> assertEquals(2, delsvarSecond.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarFirstCode.getId()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarSecondCode.getId()),
          () -> assertEquals(CODE_ONE, cvType1.getCode()),
          () -> assertEquals(CODE_SYSTEM, cvType1.getCodeSystem()),
          () -> assertEquals(DESCRIPTION_ONE, cvType1.getDisplayName()),
          () -> assertEquals(CODE_TWO, cvType2.getCode()),
          () -> assertEquals(CODE_SYSTEM, cvType2.getCodeSystem()),
          () -> assertEquals(DESCRIPTION_TWO, cvType2.getDisplayName())
      );
    }


    @Test
    void shallMapDescriptionForMultipleSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvarFirst = response.get(0).getDelsvar();
      final var delsvarFirstDescription = delsvarFirst.get(1);
      final var delsvarSecond = response.get(1).getDelsvar();
      final var delsvarSecondDescription = delsvarSecond.get(1);

      assertAll(
          () -> assertEquals(2, delsvarFirst.size()),
          () -> assertEquals(2, delsvarSecond.size()),
          () -> assertEquals(QUESTION_ID + ".1", delsvarFirstDescription.getId()),
          () -> assertEquals(DESCRIPTION_ONE, delsvarFirstDescription.getContent().get(0)),
          () -> assertEquals(QUESTION_ID + ".1", delsvarSecondDescription.getId()),
          () -> assertEquals(DESCRIPTION_TWO, delsvarSecondDescription.getContent().get(0))
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
