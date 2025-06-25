package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

class CheckboxMultipleDatesConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId FIELD_ID = new FieldId("F2");
  private static final FieldId CHECKBOXDATE_FIELD_ID_1 = new FieldId("CF1");
  private static final FieldId CHECKBOXDATE_FIELD_ID_2 = new FieldId("CF2");

  private static final List<CheckboxDate> checkboxDates = List.of(
      CheckboxDate.builder()
          .id(CHECKBOXDATE_FIELD_ID_1)
          .code(CodeSystemKvFkmu0001.FYSISKUNDERSOKNING)
          .min(null)
          .max(Period.ofDays(0))
          .build(),
      CheckboxDate.builder()
          .id(CHECKBOXDATE_FIELD_ID_2)
          .code(CodeSystemKvFkmu0001.DIGITALUNDERSOKNING)
          .min(null)
          .max(Period.ofDays(0))
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

  private static final ElementData EXPECTED_MULTIPLE_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueDateList.builder()
              .dateListId(FIELD_ID)
              .dateList(
                  List.of(
                      ElementValueDate.builder()
                          .dateId(CHECKBOXDATE_FIELD_ID_1)
                          .date(LocalDate.now())
                          .build(),
                      ElementValueDate.builder()
                          .dateId(CHECKBOXDATE_FIELD_ID_2)
                          .date(LocalDate.now())
                          .build()
                  )
              )
              .build()
      )
      .build();

  private static final ElementData EXPECTED_SINGLE_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueDateList.builder()
              .dateListId(FIELD_ID)
              .dateList(
                  List.of(
                      ElementValueDate.builder()
                          .dateId(CHECKBOXDATE_FIELD_ID_1)
                          .date(LocalDate.now())
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
    void shouldReturnPrefillCheckboxMultipleDatesIfExists()
        throws JAXBException, ParserConfigurationException {

      var forifyllnad = new Forifyllnad();

      var svar = new Svar();
      svar.setId("1");

      final var delsvarCode = new Delsvar();
      delsvarCode.setId("1.1");

      final var cvType = new CVType();
      cvType.setCode("FYSISKUNDERSOKNING");
      cvType.setCodeSystem("KV_FKMU_0001");
      cvType.setDisplayName("min undersökning vid fysiskt vårdmöte");

      final var delsvarDate = new Delsvar();
      delsvarDate.setId("1.2");
      delsvarDate.getContent().add(LocalDate.now().toString());

      delsvarCode.getContent().add(createCheckboxMultipleDatesTypeElement(cvType));

      svar.getDelsvar().add(delsvarDate);
      svar.getDelsvar().add(delsvarCode);

      forifyllnad.getSvar().add(svar);

      final var result = prefillCheckboxMultipleDatesConverter.prefillAnswer(
          SPECIFICATION, forifyllnad);

      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_SINGLE_ELEMENT_DATA)
          .build();

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnMultiplePrefillCheckboxMultipleDatesIfExists()
        throws JAXBException, ParserConfigurationException {

      var forifyllnad = new Forifyllnad();

      var svar = new Svar();
      svar.setId("1");

      final var delsvarCode = new Delsvar();
      delsvarCode.setId("1.1");

      final var cvType = new CVType();
      cvType.setCode("FYSISKUNDERSOKNING");
      cvType.setCodeSystem("KV_FKMU_0001");
      cvType.setDisplayName("TEXT");

      final var delsvarDate = new Delsvar();
      delsvarDate.setId("1.2");
      delsvarDate.getContent().add(LocalDate.now().toString());

      delsvarCode.getContent().add(createCheckboxMultipleDatesTypeElement(cvType));

      svar.getDelsvar().add(delsvarDate);
      svar.getDelsvar().add(delsvarCode);

      forifyllnad.getSvar().add(svar);

      // FIX ME
      var svar2 = new Svar();
      svar2.setId("1");

      final var delsvarCode2 = new Delsvar();
      delsvarCode2.setId("1.1");

      final var cvType2 = new CVType();
      cvType2.setCode("DIGITALUNDERSOKNING");
      cvType2.setCodeSystem("KV_FKMU_0001");
      cvType2.setDisplayName("TEXT");

      final var delsvarDate2 = new Delsvar();
      delsvarDate2.setId("1.2");
      delsvarDate2.getContent().add(LocalDate.now().toString());

      delsvarCode2.getContent().add(createCheckboxMultipleDatesTypeElement(cvType2));

      svar2.getDelsvar().add(delsvarDate2);
      svar2.getDelsvar().add(delsvarCode2);

      forifyllnad.getSvar().add(svar2);

      final var result = prefillCheckboxMultipleDatesConverter.prefillAnswer(
          SPECIFICATION, forifyllnad);

      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_MULTIPLE_ELEMENT_DATA)
          .build();

      assertEquals(expected, result);
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
  }

  private static Element createCheckboxMultipleDatesTypeElement(CVType cvType)
      throws ParserConfigurationException, JAXBException {
    JAXBContext context = JAXBContext.newInstance(CVType.class);
    Marshaller marshaller = context.createMarshaller();

    QName qName = new QName("urn:riv:clinicalprocess:healthcond:certificate:types:v3", "CVType");
    JAXBElement<CVType> root = new JAXBElement<>(qName, CVType.class, cvType);

    Document document = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder().newDocument();

    marshaller.marshal(root, document);

    return document.getDocumentElement();
  }
}