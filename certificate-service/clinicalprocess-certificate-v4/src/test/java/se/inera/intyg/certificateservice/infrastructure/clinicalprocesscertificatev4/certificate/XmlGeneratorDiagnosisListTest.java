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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CodeSystem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
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

  private static ElementData data;
  private ElementSpecification elementSpecification;

  private XmlGeneratorDiagnosisList xmlGenerator;

  @BeforeEach
  void setUp() {
    xmlGenerator = new XmlGeneratorDiagnosisList();
  }

  @Nested
  class TestOneDiagnosis {

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
                  .codeSystem(new CodeSystem(CODE_SYSTEM))
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
                              .build(),
                          ElementValueDiagnosis.builder()
                              .code(CODE_TWO)
                              .id(new FieldId("CODE_ID"))
                              .description(DESCRIPTION_TWO)
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
                  .codeSystem(new CodeSystem(CODE_SYSTEM))
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
    void shallMapFirstDelsvarForCode() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarCode = delsvar.get(0);
      final var jaxbElement = (JAXBElement<CVType>) delsvarCode.getContent().get(0);
      final var cvType = jaxbElement.getValue();

      assertAll(
          () -> assertEquals(4, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarCode.getId()),
          () -> assertEquals(CODE_ONE, cvType.getCode()),
          () -> assertEquals(CODE_SYSTEM, cvType.getCodeSystem()),
          () -> assertEquals(DESCRIPTION_ONE, cvType.getDisplayName())
      );
    }

    @Test
    void shallMapSecondDelsvarForCode() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarCode = delsvar.get(2);
      final var jaxbElement = (JAXBElement<CVType>) delsvarCode.getContent().get(0);
      final var cvType = jaxbElement.getValue();

      assertAll(
          () -> assertEquals(4, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".4", delsvarCode.getId()),
          () -> assertEquals(CODE_TWO, cvType.getCode()),
          () -> assertEquals(CODE_SYSTEM, cvType.getCodeSystem()),
          () -> assertEquals(DESCRIPTION_TWO, cvType.getDisplayName())
      );
    }

    @Test
    void shallMapFirstDelsvarForDescription() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarDate = delsvar.get(1);
      final var delsvarDateAsStr = delsvarDate.getContent().get(0);

      assertAll(
          () -> assertEquals(4, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".1", delsvarDate.getId()),
          () -> assertEquals(DESCRIPTION_ONE, delsvarDateAsStr)
      );
    }

    @Test
    void shallMapSecondDelsvarForDescription() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarDate = delsvar.get(3);
      final var delsvarDateAsStr = delsvarDate.getContent().get(0);

      assertAll(
          () -> assertEquals(4, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".3", delsvarDate.getId()),
          () -> assertEquals(DESCRIPTION_TWO, delsvarDateAsStr)
      );
    }
  }

  @Test
  void shallMapEmptyIfNoValue() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueDiagnosis.builder()
                .id(new FieldId(FIELD_ID))
                .build()
        )
        .build();

    final var response = xmlGenerator.generate(data, elementSpecification);

    assertTrue(response.isEmpty());
  }

  @Test
  void shallThrowIfIncorrectConfiguration() {
    final var data = ElementData.builder()
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
        () -> xmlGenerator.generate(data, elementSpecification)
    );
  }

  @Test
  void shallMapEmptyIfValueIsNotDiagnosis() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueUnitContactInformation.builder()
                .build()
        )
        .build();

    final var response = xmlGenerator.generate(data, elementSpecification);

    assertTrue(response.isEmpty());
  }

  @Test
  void shallReturnEmptyIfDiagnosesIsNull() {
    elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDiagnosis.builder().build()
        )
        .build();
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueDiagnosisList.builder()
                .diagnoses(null)
                .build()
        )
        .build();

    final var response = xmlGenerator.generate(data, elementSpecification);

    assertTrue(response.isEmpty());
  }

  @Test
  void shallReturnEmptyIfDiagnosesIsEmpty() {
    elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDiagnosis.builder().build()
        )
        .build();
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueDiagnosisList.builder()
                .diagnoses(Collections.emptyList())
                .build()
        )
        .build();

    final var response = xmlGenerator.generate(data, elementSpecification);

    assertTrue(response.isEmpty());
  }
}
