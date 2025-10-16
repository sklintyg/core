package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.TestMarshaller.getElement;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter.PrefillCheckboxMultipleDatesConverter;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

class PrefillCheckboxMultipleDatesConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId FIELD_ID = new FieldId("F2");
  private static final FieldId CHECKBOXDATE_FIELD_ID_1 = new FieldId("CF1");
  private static final FieldId CHECKBOXDATE_FIELD_ID_2 = new FieldId("CF2");
  private static final ObjectFactory factory = new ObjectFactory();

  private static final List<CheckboxDate> checkboxDates = List.of(
      CheckboxDate.builder()
          .id(CHECKBOXDATE_FIELD_ID_1)
          .code(CodeSystemKvFkmu0001.FYSISKUNDERSOKNING)
          .min(null)
          .max(Period.ofDays(5))
          .build(),
      CheckboxDate.builder()
          .id(CHECKBOXDATE_FIELD_ID_2)
          .code(CodeSystemKvFkmu0001.DIGITALUNDERSOKNING)
          .min(null)
          .max(Period.ofDays(5))
          .build()
  );

  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationCheckboxMultipleDate.builder()
              .id(FIELD_ID)
              .name("TEXT")
              .dates(checkboxDates)
              .build()
      )
      .build();

  private static final LocalDate DATE_NOW = LocalDate.now();
  private static final ElementData EXPECTED_MULTIPLE_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueDateList.builder()
              .dateListId(FIELD_ID)
              .dateList(
                  List.of(
                      ElementValueDate.builder()
                          .dateId(CHECKBOXDATE_FIELD_ID_1)
                          .date(DATE_NOW)
                          .build(),
                      ElementValueDate.builder()
                          .dateId(CHECKBOXDATE_FIELD_ID_2)
                          .date(DATE_NOW)
                          .build()
                  )
              )
              .build()
      )
      .build();


  PrefillCheckboxMultipleDatesConverter prefillCheckboxMultipleDatesConverter = new PrefillCheckboxMultipleDatesConverter();

  @Nested
  class PrefillAnswerWithForifyllnad {

    @Test
    void shouldReturnNullIfNoAnswers() {
      Forifyllnad prefill = new Forifyllnad();
      PrefillAnswer result = prefillCheckboxMultipleDatesConverter.prefillAnswer(SPECIFICATION,
          prefill);
      assertNull(result);
    }


    @Test
    void shouldReturnElementDataIfExists() {

      final var forifyllnad = createForifyllnad();

      final var result = prefillCheckboxMultipleDatesConverter.prefillAnswer(
          SPECIFICATION, forifyllnad);

      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_MULTIPLE_ELEMENT_DATA)
          .build();

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnElementDataWithTodaysDateIfOnlyCodeSubAnswer() {
      final var validSvar = new Svar();
      validSvar.setId("1");
      validSvar.getDelsvar().add(createDelsvarDate());
      validSvar.getDelsvar().add(createDelsvarCode("DIGITALUNDERSOKNING", "TEXT"));

      final var incompleteSvar = new Svar();
      incompleteSvar.setId("1");
      incompleteSvar.getDelsvar().add(createDelsvarCode("FYSISKUNDERSOKNING",
          "min undersökning vid fysiskt vårdmöte"));

      final var forifyllnad = new Forifyllnad();
      forifyllnad.getSvar().add(validSvar);
      forifyllnad.getSvar().add(incompleteSvar);

      final var result = prefillCheckboxMultipleDatesConverter.prefillAnswer(SPECIFICATION,
          forifyllnad);

      final var expectedData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueDateList.builder()
              .dateListId(FIELD_ID)
              .dateList(
                  List.of(
                      ElementValueDate.builder()
                          .dateId(CHECKBOXDATE_FIELD_ID_2)
                          .date(DATE_NOW)
                          .build(),
                      ElementValueDate.builder()
                          .dateId(CHECKBOXDATE_FIELD_ID_1)
                          .date(LocalDate.now(ZoneId.systemDefault()))
                          .build()
                  )
              )
              .build())
          .build();

      assertAll(
          () -> assertEquals(expectedData, result.getElementData()),
          () -> assertEquals(0, result.getErrors().size())
      );
    }

    @Test
    void shouldReturnErrorIfWrongConfigurationType() {
      final var prefill = new Forifyllnad();
      final var wrongSpec = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(ElementConfigurationCategory.builder().build())
          .build();

      final var result = prefillCheckboxMultipleDatesConverter.prefillAnswer(wrongSpec, prefill);

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldDefaultToTodaysDateWhenDateIsMissing() {
      final var forifyllnad = new Forifyllnad();
      final var svar = new Svar();
      svar.setId("1");
      svar.getDelsvar().add(createDelsvarCode("FYSISKUNDERSOKNING",
          "min undersökning vid fysiskt vårdmöte"));
      forifyllnad.getSvar().add(svar);

      final var result = prefillCheckboxMultipleDatesConverter.prefillAnswer(
          SPECIFICATION, forifyllnad);

      final var expectedData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueDateList.builder()
              .dateListId(FIELD_ID)
              .dateList(
                  List.of(
                      ElementValueDate.builder()
                          .dateId(CHECKBOXDATE_FIELD_ID_1)
                          .date(LocalDate.now(ZoneId.systemDefault()))
                          .build()))
              .build())
          .build();

      assertAll(
          () -> assertEquals(expectedData, result.getElementData()),
          () -> assertEquals(0, result.getErrors().size())
      );
    }

    @Test
    void shouldDefaultToTodaysDateWhenDateContentIsEmpty() {
      final var forifyllnad = new Forifyllnad();
      final var svar = new Svar();
      svar.setId("1");
      final var emptyDelsvarDate = new Delsvar();
      emptyDelsvarDate.setId("1.2");
      svar.getDelsvar().add(emptyDelsvarDate);
      svar.getDelsvar().add(createDelsvarCode("DIGITALUNDERSOKNING", "TEXT"));
      forifyllnad.getSvar().add(svar);

      final var result = prefillCheckboxMultipleDatesConverter.prefillAnswer(
          SPECIFICATION, forifyllnad);

      final var expectedData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueDateList.builder()
              .dateListId(FIELD_ID)
              .dateList(
                  List.of(
                      ElementValueDate.builder()
                          .dateId(CHECKBOXDATE_FIELD_ID_2)
                          .date(LocalDate.now(ZoneId.systemDefault()))
                          .build()))
              .build())
          .build();

      assertAll(
          () -> assertEquals(expectedData, result.getElementData()),
          () -> assertEquals(0, result.getErrors().size())
      );
    }
  }

  private static Forifyllnad createForifyllnad() {
    try {

      var forifyllnad = new Forifyllnad();
      var svar = new Svar();
      svar.setId("1");
      svar.getDelsvar().add(createDelsvarDate());
      svar.getDelsvar().add(createDelsvarCode("FYSISKUNDERSOKNING",
          "min undersökning vid fysiskt vårdmöte"));
      forifyllnad.getSvar().add(svar);

      var svar2 = new Svar();
      svar2.setId("1");
      svar2.getDelsvar().add(createDelsvarDate());
      svar2.getDelsvar().add(createDelsvarCode("DIGITALUNDERSOKNING", "TEXT"));
      forifyllnad.getSvar().add(svar2);
      return forifyllnad;
    } catch (Exception e) {
      throw new RuntimeException("Error creating Forifyllnad", e);
    }
  }

  private static Delsvar createDelsvarDate() {
    final var delsvarDate = new Delsvar();
    delsvarDate.setId("1.2");
    delsvarDate.getContent().add(DATE_NOW.toString());
    return delsvarDate;
  }

  private static Delsvar createDelsvarCode(String code, String displayName) {
    final var delsvarCode = new Delsvar();
    delsvarCode.setId("1.1");

    final var cvType = new CVType();
    cvType.setCode(code);
    cvType.setCodeSystem("KV_FKMU_0001");
    cvType.setDisplayName(displayName);
    delsvarCode.getContent().add(getElement(cvType, factory::createCv));
    return delsvarCode;
  }
}

