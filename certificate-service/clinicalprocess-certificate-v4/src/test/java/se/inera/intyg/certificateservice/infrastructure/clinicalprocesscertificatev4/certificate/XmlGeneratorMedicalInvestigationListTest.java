package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.xml.bind.JAXBElement;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorMedicalInvestigationListTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final String VALUE_ID = "ANSWER_ID";

  private static final String DATE_ID_ONE = "DATE_ID_ONE";
  private static final LocalDate DATE_ONE = LocalDate.now().minusDays(1);
  private static final String CODE_ID_ONE = "CODE_ID_ONE";
  private static final String CODE_ONE = "CODE_ONE";
  private static final String TEXT_ID_ONE = "TEXT_ID_ONE";
  private static final String TEXT_ONE = "TEXT_ONE";

  private static final String DATE_ID_TWO = "DATE_ID_TWO";
  private static final LocalDate DATE_TWO = LocalDate.now().minusDays(2);
  private static final String CODE_ID_TWO = "CODE_ID_TWO";
  private static final String CODE_TWO = "CODE_TWO";
  private static final String TEXT_ID_TWO = "TEXT_ID_TWO";
  private static final String TEXT_TWO = "TEXT_TWO";
  private static final String CODE_SYSTEM = "CODE_SYSTEM";
  private static final String DISPLAY_NAME = "DISPLAY_NAME";
  private static final String DISPLAY_NAME_TWO = "DISPLAY_NAME_TWO";

  private static ElementData data;
  private ElementSpecification elementSpecification;

  @InjectMocks
  private XmlGeneratorMedicalInvestigationList xmlGenerator;

  @Nested
  class TestOneRow {

    @BeforeEach
    void setup() {
      data = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(new FieldId(VALUE_ID))
                  .list(
                      List.of(
                          MedicalInvestigation.builder()
                              .id(new FieldId("ROW1"))
                              .date(
                                  ElementValueDate.builder()
                                      .dateId(new FieldId(DATE_ID_ONE))
                                      .date(DATE_ONE)
                                      .build()
                              )
                              .investigationType(
                                  ElementValueCode.builder()
                                      .codeId(new FieldId(CODE_ID_ONE))
                                      .code(CODE_ONE)
                                      .build()
                              )
                              .informationSource(
                                  ElementValueText.builder()
                                      .textId(new FieldId(TEXT_ID_ONE))
                                      .text(TEXT_ONE)
                                      .build()
                              )
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationMedicalInvestigationList.builder()
                  .id(new FieldId(VALUE_ID))
                  .list(
                      List.of(
                          MedicalInvestigationConfig.builder()
                              .id(new FieldId("ROW_1"))
                              .investigationTypeId(new FieldId(CODE_ID_ONE))
                              .dateId(new FieldId(DATE_ID_ONE))
                              .informationSourceId(new FieldId(TEXT_ID_ONE))
                              .typeOptions(List.of(
                                  new Code(
                                      CODE_ONE,
                                      CODE_SYSTEM,
                                      DISPLAY_NAME
                                  )
                              ))
                              .build()
                      )
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
          () -> assertEquals(QUESTION_ID, first.getId()),
          () -> assertEquals(1, first.getInstans())
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
          () -> assertEquals(3, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".1", delsvarCode.getId()),
          () -> assertEquals(CODE_ONE, cvType.getCode()),
          () -> assertEquals(CODE_SYSTEM, cvType.getCodeSystem()),
          () -> assertEquals(DISPLAY_NAME, cvType.getDisplayName())
      );
    }

    @Test
    void shallMapDelsvarForDate() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarDate = response.get(0).getDelsvar().get(1);
      final var delsvarDateAsStr = delsvarDate.getContent().get(0);

      assertAll(
          () -> assertEquals(3, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarDate.getId()),
          () -> assertEquals(DATE_ONE.toString(), delsvarDateAsStr)
      );
    }

    @Test
    void shallMapDelsvarForText() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarText = response.get(0).getDelsvar().get(2);
      final var delsvarTextAsStr = delsvarText.getContent().get(0);

      assertAll(
          () -> assertEquals(3, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".3", delsvarText.getId()),
          () -> assertEquals(TEXT_ONE, delsvarTextAsStr)
      );
    }

    @Test
    void shallMapEmptyIfEmptyCode() {
      final var data = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(new FieldId(VALUE_ID))
                  .list(
                      List.of(
                          MedicalInvestigation.builder()
                              .id(new FieldId("ROW1"))
                              .date(
                                  ElementValueDate.builder()
                                      .dateId(new FieldId(DATE_ID_ONE))
                                      .date(DATE_ONE)
                                      .build()
                              )
                              .investigationType(
                                  ElementValueCode.builder()
                                      .build()
                              )
                              .informationSource(
                                  ElementValueText.builder()
                                      .textId(new FieldId(TEXT_ID_ONE))
                                      .text(TEXT_ONE)
                                      .build()
                              )
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var response = xmlGenerator.generate(data, elementSpecification);

      assertTrue(response.isEmpty());
    }

    @Test
    void shallMapEmptyIfEmptyDate() {
      final var data = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(new FieldId(VALUE_ID))
                  .list(
                      List.of(
                          MedicalInvestigation.builder()
                              .id(new FieldId("ROW1"))
                              .date(
                                  ElementValueDate.builder()
                                      .build()
                              )
                              .investigationType(
                                  ElementValueCode.builder()
                                      .codeId(new FieldId(CODE_ID_ONE))
                                      .code(CODE_ONE)
                                      .build()
                              )
                              .informationSource(
                                  ElementValueText.builder()
                                      .textId(new FieldId(TEXT_ID_ONE))
                                      .text(TEXT_ONE)
                                      .build()
                              )
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var response = xmlGenerator.generate(data, elementSpecification);

      assertTrue(response.isEmpty());
    }

    @Test
    void shallMapEmptyIfEmptyInformationSource() {
      final var data = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(new FieldId(VALUE_ID))
                  .list(
                      List.of(
                          MedicalInvestigation.builder()
                              .id(new FieldId("ROW1"))
                              .date(
                                  ElementValueDate.builder()
                                      .dateId(new FieldId(DATE_ID_ONE))
                                      .date(DATE_ONE)
                                      .build()
                              )
                              .investigationType(
                                  ElementValueCode.builder()
                                      .codeId(new FieldId(CODE_ID_ONE))
                                      .code(CODE_ONE)
                                      .build()
                              )
                              .informationSource(
                                  ElementValueText.builder()
                                      .build()
                              )
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var response = xmlGenerator.generate(data, elementSpecification);

      assertTrue(response.isEmpty());
    }
  }

  @Nested
  class TestMultipleDates {

    @BeforeEach
    void setup() {
      data = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(new FieldId(VALUE_ID))
                  .list(
                      List.of(
                          MedicalInvestigation.builder()
                              .id(new FieldId("ROW_1"))
                              .date(
                                  ElementValueDate.builder()
                                      .dateId(new FieldId(DATE_ID_ONE))
                                      .date(DATE_ONE)
                                      .build()
                              )
                              .investigationType(
                                  ElementValueCode.builder()
                                      .codeId(new FieldId(CODE_ID_ONE))
                                      .code(CODE_ONE)
                                      .build()
                              )
                              .informationSource(
                                  ElementValueText.builder()
                                      .textId(new FieldId(TEXT_ID_ONE))
                                      .text(TEXT_ONE)
                                      .build()
                              )
                              .build(),
                          MedicalInvestigation.builder()
                              .id(new FieldId("ROW_2"))
                              .date(
                                  ElementValueDate.builder()
                                      .dateId(new FieldId(DATE_ID_TWO))
                                      .date(DATE_TWO)
                                      .build()
                              )
                              .investigationType(
                                  ElementValueCode.builder()
                                      .codeId(new FieldId(CODE_ID_TWO))
                                      .code(CODE_TWO)
                                      .build()
                              )
                              .informationSource(
                                  ElementValueText.builder()
                                      .textId(new FieldId(TEXT_ID_TWO))
                                      .text(TEXT_TWO)
                                      .build()
                              )
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationMedicalInvestigationList.builder()
                  .id(new FieldId(VALUE_ID))
                  .list(
                      List.of(
                          MedicalInvestigationConfig.builder()
                              .id(new FieldId("ROW_1"))
                              .investigationTypeId(new FieldId(CODE_ID_ONE))
                              .dateId(new FieldId(DATE_ID_ONE))
                              .informationSourceId(new FieldId(TEXT_ID_ONE))
                              .typeOptions(List.of(
                                  new Code(
                                      CODE_ONE,
                                      CODE_SYSTEM,
                                      DISPLAY_NAME
                                  ),
                                  new Code(
                                      CODE_TWO,
                                      CODE_SYSTEM,
                                      DISPLAY_NAME
                                  )
                              ))
                              .build(),
                          MedicalInvestigationConfig.builder()
                              .id(new FieldId("ROW_2"))
                              .investigationTypeId(new FieldId(CODE_ID_TWO))
                              .dateId(new FieldId(DATE_ID_TWO))
                              .informationSourceId(new FieldId(TEXT_ID_TWO))
                              .typeOptions(List.of(
                                  new Code(
                                      CODE_ONE,
                                      CODE_SYSTEM,
                                      DISPLAY_NAME
                                  ),
                                  new Code(
                                      CODE_TWO,
                                      CODE_SYSTEM,
                                      DISPLAY_NAME_TWO
                                  )
                              ))
                              .build()
                      )
                  )
                  .build()
          )
          .build();
    }

    @Test
    void shallMapFirstSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var first = response.get(0);
      assertAll(
          () -> assertEquals(2, response.size()),
          () -> assertEquals(QUESTION_ID, first.getId()),
          () -> assertEquals(1, first.getInstans())
      );
    }

    @Test
    void shallMapSecondSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var first = response.get(1);
      assertAll(
          () -> assertEquals(2, response.size()),
          () -> assertEquals(QUESTION_ID, first.getId()),
          () -> assertEquals(2, first.getInstans())
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
          () -> assertEquals(3, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".1", delsvarCode.getId()),
          () -> assertEquals(CODE_ONE, cvType.getCode()),
          () -> assertEquals(CODE_SYSTEM, cvType.getCodeSystem()),
          () -> assertEquals(DISPLAY_NAME, cvType.getDisplayName())
      );
    }

    @Test
    void shallMapSecondDelsvarForCode() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(1).getDelsvar();
      final var delsvarCode = delsvar.get(0);
      final var jaxbElement = (JAXBElement<CVType>) delsvarCode.getContent().get(0);
      final var cvType = jaxbElement.getValue();

      assertAll(
          () -> assertEquals(3, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".1", delsvarCode.getId()),
          () -> assertEquals(CODE_TWO, cvType.getCode()),
          () -> assertEquals(CODE_SYSTEM, cvType.getCodeSystem()),
          () -> assertEquals(DISPLAY_NAME_TWO, cvType.getDisplayName())
      );
    }

    @Test
    void shallMapFirstDelsvarForDate() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarDate = delsvar.get(1);
      final var delsvarDateAsStr = delsvarDate.getContent().get(0);

      assertAll(
          () -> assertEquals(3, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarDate.getId()),
          () -> assertEquals(DATE_ONE.toString(), delsvarDateAsStr)
      );
    }

    @Test
    void shallMapSecondDelsvarForDate() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(1).getDelsvar();
      final var delsvarDate = delsvar.get(1);
      final var delsvarDateAsStr = delsvarDate.getContent().get(0);

      assertAll(
          () -> assertEquals(3, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarDate.getId()),
          () -> assertEquals(DATE_TWO.toString(), delsvarDateAsStr)
      );
    }

    @Test
    void shallMapFirstDelsvarForText() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarText = delsvar.get(2);
      final var delsvarTextAsStr = delsvarText.getContent().get(0);

      assertAll(
          () -> assertEquals(3, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".3", delsvarText.getId()),
          () -> assertEquals(TEXT_ONE, delsvarTextAsStr)
      );
    }

    @Test
    void shallMapSecondDelsvarForText() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(1).getDelsvar();
      final var delsvarText = delsvar.get(2);
      final var delsvarTextAsStr = delsvarText.getContent().get(0);

      assertAll(
          () -> assertEquals(3, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".3", delsvarText.getId()),
          () -> assertEquals(TEXT_TWO, delsvarTextAsStr)
      );
    }
  }

  @Test
  void shallMapEmptyIfNoValue() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueDateList.builder()
                .dateListId(new FieldId(VALUE_ID))
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
            ElementValueMedicalInvestigationList.builder()
                .id(new FieldId(VALUE_ID))
                .list(
                    List.of(
                        MedicalInvestigation.builder().build()
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
  void shallMapEmptyIfValueIsNotDateRangeList() {
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
}