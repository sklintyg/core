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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorDateListTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final String VALUE_ID = "ANSWER_ID";
  private static final String DATE_ID_ONE = "DATE_ID_ONE";
  private static final LocalDate DATE_ONE = LocalDate.now().minusDays(1);

  private static final String DATE_ID_TWO = "DATE_ID_TWO";
  private static final LocalDate DATE_TWO = LocalDate.now().minusDays(2);

  private static ElementData data;
  private ElementSpecification elementSpecification;

  @InjectMocks
  private XmlGeneratorDateList xmlGenerator;

  @Nested
  class TestOneDate {

    @BeforeEach
    void setup() {
      data = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueDateList.builder()
                  .dateListId(new FieldId(VALUE_ID))
                  .dateList(
                      List.of(
                          ElementValueDate.builder()
                              .dateId(new FieldId(DATE_ID_ONE))
                              .date(DATE_ONE)
                              .build()
                      )
                  )
                  .build()
          )
          .build();
      elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationCheckboxMultipleDate.builder()
                  .id(new FieldId(VALUE_ID))
                  .dates(
                      List.of(
                          CheckboxDate.builder()
                              .id(new FieldId(DATE_ID_ONE))
                              .label("label")
                              .code(
                                  new Code(
                                      "CODE",
                                      "CODE_SYSTEM",
                                      "DISPLAY_NAME"
                                  )
                              )
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
          () -> assertEquals(2, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".1", delsvarCode.getId()),
          () -> assertEquals("CODE", cvType.getCode()),
          () -> assertEquals("CODE_SYSTEM", cvType.getCodeSystem()),
          () -> assertEquals("DISPLAY_NAME", cvType.getDisplayName())
      );
    }

    @Test
    void shallMapDelsvarForDate() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarDate = response.get(0).getDelsvar().get(1);
      final var delsvarDateAsStr = delsvarDate.getContent().get(0);

      assertAll(
          () -> assertEquals(2, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarDate.getId()),
          () -> assertEquals(DATE_ONE.toString(), delsvarDateAsStr)
      );
    }
  }

  @Nested
  class TestMultipleDates {

    @BeforeEach
    void setup() {
      data = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(
              ElementValueDateList.builder()
                  .dateListId(new FieldId(VALUE_ID))
                  .dateList(
                      List.of(
                          ElementValueDate.builder()
                              .dateId(new FieldId(DATE_ID_ONE))
                              .date(DATE_ONE)
                              .build(),
                          ElementValueDate.builder()
                              .dateId(new FieldId(DATE_ID_TWO))
                              .date(DATE_TWO)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationCheckboxMultipleDate.builder()
                  .id(new FieldId(VALUE_ID))
                  .dates(
                      List.of(
                          CheckboxDate.builder()
                              .id(new FieldId(DATE_ID_ONE))
                              .label("label")
                              .code(
                                  new Code(
                                      "CODE",
                                      "CODE_SYSTEM",
                                      "DISPLAY_NAME"
                                  )
                              )
                              .build(),
                          CheckboxDate.builder()
                              .id(new FieldId(DATE_ID_TWO))
                              .label("labelTwo")
                              .code(
                                  new Code(
                                      "CODE_TWO",
                                      "CODE_SYSTEM",
                                      "DISPLAY_NAME_TWO"
                                  )
                              )
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
          () -> assertEquals(2, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".1", delsvarCode.getId()),
          () -> assertEquals("CODE", cvType.getCode()),
          () -> assertEquals("CODE_SYSTEM", cvType.getCodeSystem()),
          () -> assertEquals("DISPLAY_NAME", cvType.getDisplayName())
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
          () -> assertEquals(2, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".1", delsvarCode.getId()),
          () -> assertEquals("CODE_TWO", cvType.getCode()),
          () -> assertEquals("CODE_SYSTEM", cvType.getCodeSystem()),
          () -> assertEquals("DISPLAY_NAME_TWO", cvType.getDisplayName())
      );
    }

    @Test
    void shallMapFirstDelsvarForDate() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarDate = delsvar.get(1);
      final var delsvarDateAsStr = delsvarDate.getContent().get(0);

      assertAll(
          () -> assertEquals(2, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarDate.getId()),
          () -> assertEquals(DATE_ONE.toString(), delsvarDateAsStr)
      );
    }

    @Test
    void shallMapSecondDelsvarForDateRange() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(1).getDelsvar();
      final var delsvarDate = delsvar.get(1);
      final var delsvarDateAsStr = delsvarDate.getContent().get(0);

      assertAll(
          () -> assertEquals(2, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarDate.getId()),
          () -> assertEquals(DATE_TWO.toString(), delsvarDateAsStr)
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
            ElementValueDateList.builder()
                .dateListId(new FieldId(VALUE_ID))
                .dateList(
                    List.of(
                        ElementValueDate.builder()
                            .dateId(new FieldId(DATE_ID_ONE))
                            .date(DATE_ONE)
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