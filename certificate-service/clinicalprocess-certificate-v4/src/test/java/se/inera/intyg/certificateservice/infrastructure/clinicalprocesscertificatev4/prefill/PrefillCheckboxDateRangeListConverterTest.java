package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.TestMarshaller.getElement;

import jakarta.xml.bind.JAXBContext;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0008;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter.PrefillCheckboxDateRangeListConverter;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.DatePeriodType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

class PrefillCheckboxDateRangeListConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId FIELD_ID = new FieldId("F2");
  private static final LocalDate START_DATE = LocalDate.of(2024, 1, 1);
  private static final LocalDate END_DATE = LocalDate.of(2024, 12, 31);
  private static final ObjectFactory factory = new ObjectFactory();
  private static final List<ElementConfigurationCode> dateRanges = List.of(
      new ElementConfigurationCode(
          new FieldId(CodeSystemKvFkmu0008.EN_ATTONDEL.code()),
          "LABEL",
          new Code(
              CodeSystemKvFkmu0008.EN_ATTONDEL.code(),
              "KV_FKMU_0001",
              "TEXT")
      ),
      new ElementConfigurationCode(
          new FieldId(CodeSystemKvFkmu0008.EN_FJARDEDEL.code()),
          "LABEL",
          new Code(
              CodeSystemKvFkmu0008.EN_FJARDEDEL.code(),
              "KV_FKMU_0001",
              "TEXT")
      )
  );

  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationCheckboxDateRangeList.builder()
              .id(FIELD_ID)
              .name("TEXT")
              .label("LABEL")
              .message(ElementMessage.builder()
                  .content("CONTENT")
                  .includedForStatuses(List.of())
                  .level(MessageLevel.INFO)
                  .build()
              )
              .hideWorkingHours(false)
              .min(Period.of(2024, 1, 1))
              .max(Period.of(2024, 12, 31))
              .dateRanges(dateRanges)
              .build()
      )
      .build();

  private static final ElementData EXPECTED_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueDateRangeList.builder()
              .dateRangeListId(FIELD_ID)
              .dateRangeList(List.of(
                  DateRange.builder()
                      .dateRangeId(new FieldId(CodeSystemKvFkmu0008.EN_ATTONDEL.code()))
                      .from(START_DATE)
                      .to(END_DATE)
                      .build()
              ))
              .build()
      )
      .build();

  private static final ElementData EXPECTED_MULTIPLE_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueDateRangeList.builder()
              .dateRangeListId(FIELD_ID)
              .dateRangeList(List.of(
                  DateRange.builder()
                      .dateRangeId(new FieldId(CodeSystemKvFkmu0008.EN_ATTONDEL.code()))
                      .from(START_DATE)
                      .to(END_DATE)
                      .build(),
                  DateRange.builder()
                      .dateRangeId(new FieldId(CodeSystemKvFkmu0008.EN_FJARDEDEL.code()))
                      .from(START_DATE)
                      .to(END_DATE)
                      .build()
              ))
              .build()
      )
      .build();

  PrefillCheckboxDateRangeListConverter prefillCheckboxDateRangeListConverter = new PrefillCheckboxDateRangeListConverter();

  @Test
  void shouldReturnSupportsDateRange() {
    assertEquals(ElementConfigurationCheckboxDateRangeList.class,
        prefillCheckboxDateRangeListConverter.supports());
  }

  @Nested
  class PrefillAnswerWithForifyllnad {

    @Test
    void shouldReturnNullIfNoAnswers() {
      Forifyllnad prefill = new Forifyllnad();

      PrefillAnswer result = prefillCheckboxDateRangeListConverter.prefillAnswer(SPECIFICATION,
          prefill);

      assertNull(result);
    }

    @Test
    void shouldReturnErrorIfWrongConfigurationType() {
      final var prefill = new Forifyllnad();
      final var wrongSpec = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(ElementConfigurationCategory.builder().build())
          .build();

      final var result = prefillCheckboxDateRangeListConverter.prefillAnswer(wrongSpec,
          prefill);

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnPrefillAnswerWithForifyllnad() {

      var prefill = createForifyllnad();

      final var result = prefillCheckboxDateRangeListConverter.prefillAnswer(
          SPECIFICATION, prefill);

      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
          .build();

      assertEquals(expected, result);

    }

    @Test
    void shouldReturnPrefillAnswersWithForifyllnad() {

      var prefill = createForifyllnadWithMultipleSubAnswers();

      final var result = prefillCheckboxDateRangeListConverter.prefillAnswer(
          SPECIFICATION, prefill);

      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_MULTIPLE_ELEMENT_DATA)
          .build();

      assertEquals(expected, result);

    }

    @Test
    void shouldReturnElementDataIfOneAnswerHasInvalidFormat() {

      final var forifyllnad = createForifyllnadWithMultipleSubAnswers();
      forifyllnad.getSvar().getFirst().getDelsvar().getFirst().getContent().clear();

      final var result = prefillCheckboxDateRangeListConverter.prefillAnswer(
          SPECIFICATION, forifyllnad);

      final var expectedData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDateRangeList.builder()
                  .dateRangeListId(FIELD_ID)
                  .dateRangeList(List.of(
                      DateRange.builder()
                          .dateRangeId(new FieldId(CodeSystemKvFkmu0008.EN_FJARDEDEL.code()))
                          .from(START_DATE)
                          .to(END_DATE)
                          .build()
                  ))
                  .build()
          )
          .build();

      assertAll(
          () -> assertEquals(expectedData, result.getElementData()),
          () -> assertEquals(1, result.getErrors().size()),
          () -> assertEquals(PrefillErrorType.INVALID_FORMAT, result.getErrors().getFirst().type())
      );

    }
  }

  private static Forifyllnad createForifyllnad() {
    try {

      var forifyllnad = new Forifyllnad();
      var svar = new Svar();
      svar.setId(ELEMENT_ID.id());
      svar.getDelsvar().add(createDelsvarCode(CodeSystemKvFkmu0008.EN_ATTONDEL.code(),
          "TEXT"));
      svar.getDelsvar().add(createDelsvarDate());
      forifyllnad.getSvar().add(svar);

      return forifyllnad;
    } catch (Exception e) {
      throw new RuntimeException("Error creating Forifyllnad", e);
    }
  }

  private static Forifyllnad createForifyllnadWithMultipleSubAnswers() {
    try {

      var forifyllnad = new Forifyllnad();
      var svar = new Svar();
      svar.setId(ELEMENT_ID.id());
      svar.getDelsvar().add(createDelsvarCode(CodeSystemKvFkmu0008.EN_ATTONDEL.code(),
          "TEXT"));
      svar.getDelsvar().add(createDelsvarDate());

      var svar2 = new Svar();
      svar2.setId(ELEMENT_ID.id());
      svar2.getDelsvar().add(createDelsvarCode(CodeSystemKvFkmu0008.EN_FJARDEDEL.code(),
          "TEXT"));
      svar2.getDelsvar().add(createDelsvarDate());

      forifyllnad.getSvar().add(svar);
      forifyllnad.getSvar().add(svar2);

      return forifyllnad;
    } catch (Exception e) {
      throw new RuntimeException("Error creating Forifyllnad", e);
    }
  }

  private static Delsvar createDelsvarDate() throws Exception {
    final var delsvarDate = new Delsvar();
    delsvarDate.setId("1.2");
    delsvarDate.getContent().add(createDateRangeTypeElement());
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

  private static Element createDateRangeTypeElement() throws Exception {
    final var dateRange = new DatePeriodType();
    dateRange.setStart(toXmlGregorianCalendar(START_DATE));
    dateRange.setEnd(toXmlGregorianCalendar(END_DATE));

    final var factory = new ObjectFactory();
    final var jaxbElement = factory.createDatePeriod(dateRange);

    final var context = JAXBContext.newInstance(DatePeriodType.class);
    final var marshaller = context.createMarshaller();
    final var writer = new StringWriter();
    marshaller.marshal(jaxbElement, writer);

    final var docFactory = DocumentBuilderFactory.newInstance();
    docFactory.setNamespaceAware(true);
    final var doc = docFactory.newDocumentBuilder()
        .parse(new org.xml.sax.InputSource(new StringReader(writer.toString())));
    return doc.getDocumentElement();
  }

  private static XMLGregorianCalendar toXmlGregorianCalendar(LocalDate date) {
    if (date == null) {
      return null;
    }

    try {
      return DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());
    } catch (Exception ex) {
      throw new IllegalStateException("Could not convert date to XML Gregorian format", ex);
    }
  }
}