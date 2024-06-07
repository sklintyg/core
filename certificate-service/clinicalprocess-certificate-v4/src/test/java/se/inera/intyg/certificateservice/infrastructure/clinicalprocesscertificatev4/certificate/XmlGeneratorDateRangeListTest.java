package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.xml.bind.JAXBElement;
import java.time.LocalDate;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorDateRangeListTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final String VALUE_ID = "ANSWER_ID";
  private static final String RANGE_ID = "HALVA";
  private static final LocalDate FROM = LocalDate.now().minusDays(1);
  private static final LocalDate TO = LocalDate.now();

  private static final String RANGE_ID_2 = "EN_FJARDEDEL";
  private static final LocalDate FROM_2 = LocalDate.now().minusDays(2);
  private static final LocalDate TO_2 = LocalDate.now().plusDays(1);
  private static ElementData data;

  @InjectMocks
  private XmlGeneratorDateRangeList xmlGenerator;
  private ElementSpecification elementSpecification;

  @Nested
  class OneDateRange {

    @BeforeEach
    void setup() {
      data = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(ElementValueDateRangeList.builder()
              .dateRangeListId(new FieldId(VALUE_ID))
              .dateRangeList(
                  List.of(
                      DateRange.builder()
                          .dateRangeId(new FieldId(RANGE_ID))
                          .from(FROM)
                          .to(TO)
                          .build()
                  )
              )
              .build())
          .build();

      elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationCheckboxDateRangeList.builder()
                  .id(new FieldId(VALUE_ID))
                  .dateRanges(
                      List.of(
                          new ElementConfigurationCode(
                              new FieldId(RANGE_ID),
                              "label",
                              new Code(
                                  "CODE",
                                  "CODE_SYSTEM",
                                  "DISPLAY_NAME"
                              )
                          )
                      )
                  )
                  .build()
          )
          .build();
    }

    @Test
    void shouldMapSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var first = response.get(0);
      assertAll(
          () -> assertEquals(1, response.size()),
          () -> assertEquals(QUESTION_ID, first.getId()),
          () -> assertEquals(1, first.getInstans())
      );
    }

    @Test
    void shouldMapDelsvarForCode() {
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
    void shouldMapDelsvarForDateRange() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarDateRange = response.get(0).getDelsvar().get(1);
      final var jaxbElement = (JAXBElement<DatePeriodType>) delsvarDateRange.getContent().get(0);
      final var dateRange = jaxbElement.getValue();

      assertAll(
          () -> assertEquals(2, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarDateRange.getId()),
          () -> assertEquals(FROM, toLocalDate(dateRange.getStart())),
          () -> assertEquals(TO, toLocalDate(dateRange.getEnd()))
      );
    }
  }

  @Nested
  class SeveralDateRanges {

    @BeforeEach
    void setup() {
      data = ElementData.builder()
          .id(new ElementId(QUESTION_ID))
          .value(ElementValueDateRangeList.builder()
              .dateRangeListId(new FieldId(VALUE_ID))
              .dateRangeList(
                  List.of(
                      DateRange.builder()
                          .dateRangeId(new FieldId(RANGE_ID))
                          .from(FROM)
                          .to(TO)
                          .build(),
                      DateRange.builder()
                          .dateRangeId(new FieldId(RANGE_ID_2))
                          .from(FROM_2)
                          .to(TO_2)
                          .build()
                  )
              )
              .build())
          .build();

      elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationCheckboxDateRangeList.builder()
                  .id(new FieldId(VALUE_ID))
                  .dateRanges(
                      List.of(
                          new ElementConfigurationCode(
                              new FieldId(RANGE_ID),
                              "label",
                              new Code(
                                  "CODE",
                                  "CODE_SYSTEM",
                                  "DISPLAY_NAME"
                              )
                          ),
                          new ElementConfigurationCode(
                              new FieldId(RANGE_ID_2),
                              "labelTwo",
                              new Code(
                                  "CODE_TWO",
                                  "CODE_SYSTEM",
                                  "DISPLAY_NAME_TWO"
                              )
                          )
                      )
                  )
                  .build()
          )
          .build();
    }

    @Test
    void shouldMapFirstSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var first = response.get(0);
      assertAll(
          () -> assertEquals(2, response.size()),
          () -> assertEquals(QUESTION_ID, first.getId()),
          () -> assertEquals(1, first.getInstans())
      );
    }

    @Test
    void shouldMapSecondSvar() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var first = response.get(1);
      assertAll(
          () -> assertEquals(2, response.size()),
          () -> assertEquals(QUESTION_ID, first.getId()),
          () -> assertEquals(2, first.getInstans())
      );
    }

    @Test
    void shouldMapFirstDelsvarForCode() {
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
    void shouldMapSecondDelsvarForCode() {
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
    void shouldMapFirstDelsvarForDateRange() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(0).getDelsvar();
      final var delsvarDateRange = delsvar.get(1);
      final var jaxbElement = (JAXBElement<DatePeriodType>) delsvarDateRange.getContent().get(0);
      final var dateRange = jaxbElement.getValue();

      assertAll(
          () -> assertEquals(2, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarDateRange.getId()),
          () -> assertEquals(FROM, toLocalDate(dateRange.getStart())),
          () -> assertEquals(TO, toLocalDate(dateRange.getEnd()))
      );
    }

    @Test
    void shouldMapSecondDelsvarForDateRange() {
      final var response = xmlGenerator.generate(data, elementSpecification);

      final var delsvar = response.get(1).getDelsvar();
      final var delsvarDateRange = delsvar.get(1);
      final var jaxbElement = (JAXBElement<DatePeriodType>) delsvarDateRange.getContent().get(0);
      final var dateRange = jaxbElement.getValue();

      assertAll(
          () -> assertEquals(2, delsvar.size()),
          () -> assertEquals(QUESTION_ID + ".2", delsvarDateRange.getId()),
          () -> assertEquals(FROM_2, toLocalDate(dateRange.getStart())),
          () -> assertEquals(TO_2, toLocalDate(dateRange.getEnd()))
      );
    }
  }

  @Test
  void shouldMapEmptyIfNoValue() {
    final var data = ElementData.builder()
        .value(ElementValueDateRangeList.builder()
            .dateRangeListId(new FieldId(VALUE_ID))
            .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGenerator.generate(data, elementSpecification);

    assertTrue(response.isEmpty());
  }

  @Test
  void shallThrowIfIncorrectConfiguration() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(
            ElementValueDateRangeList.builder()
                .dateRangeListId(new FieldId(VALUE_ID))
                .dateRangeList(
                    List.of(
                        DateRange.builder()
                            .dateRangeId(new FieldId(RANGE_ID))
                            .from(FROM)
                            .to(TO)
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
  void shouldMapEmptyIfValueIsNotDateRangeList() {
    final var data = ElementData.builder()
        .value(ElementValueUnitContactInformation.builder()
            .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGenerator.generate(data, elementSpecification);

    assertTrue(response.isEmpty());
  }

  private static LocalDate toLocalDate(XMLGregorianCalendar xmlFormat) {
    return LocalDate.of(
        xmlFormat.getYear(),
        xmlFormat.getMonth(),
        xmlFormat.getDay());
  }
}