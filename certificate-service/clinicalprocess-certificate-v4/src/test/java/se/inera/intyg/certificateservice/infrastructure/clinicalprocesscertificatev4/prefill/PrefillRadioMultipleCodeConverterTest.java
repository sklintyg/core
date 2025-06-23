package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.xml.bind.JAXBContext;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorCode;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

class PrefillRadioMultipleCodeConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId CODE_FIELD_ID = new FieldId("2");
  private static final FieldId FIELD_ID = new FieldId("F2");
  private static final String CODE = "code1";
  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationRadioMultipleCode.builder()
              .id(FIELD_ID)
              .list(
                  List.of(
                      new ElementConfigurationCode(
                          CODE_FIELD_ID, "Code 1", new Code(CODE, "S1", "D1")),
                      new ElementConfigurationCode(new FieldId("F2"), "Code 2",
                          new Code("C2", "S1", "D2"))
                  )
              )
              .build()
      )
      .build();
  private static final ElementData EXPECTED_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueCode.builder()
              .codeId(CODE_FIELD_ID)
              .code(CODE)
              .build()
      ).build();

  private final PrefillRadioMultipleCodeConverter prefillRadioMultipleCodeConverter = new PrefillRadioMultipleCodeConverter();
  private final XmlGeneratorCode xmlGeneratorRadioMultipleCode = new XmlGeneratorCode();

  @Test
  void shouldReturnSupportsRadioMultipleCode() {
    assertEquals(ElementConfigurationRadioMultipleCode.class,
        prefillRadioMultipleCodeConverter.supports());
  }

  @Nested
  class Answer {

    @Test
    void shouldReturnEmptyListForUnknownIds() {
      assertEquals(
          Collections.emptyList(),
          prefillRadioMultipleCodeConverter.unknownIds(null, null)
      );
    }

    @Test
    void shouldCreatPrefillAnswerForRadioMultipleCode() throws Exception {
      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
          .build();

      final var answer = new Svar();
      final var subAnswer = new Delsvar();
      subAnswer.getContent().add(createCVTypeElement());
      answer.getDelsvar().add(subAnswer);

      final var result = prefillRadioMultipleCodeConverter.prefillAnswer(List.of(answer),
          SPECIFICATION);

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnErrorIfWrongConfigurationType() {
      final var wrongConfiguration = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(ElementConfigurationCategory.builder().build())
          .build();

      final var result = prefillRadioMultipleCodeConverter.prefillAnswer(
          List.of(new Svar()),
          wrongConfiguration
      );

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfMoreThanOneAnswer() {
      final var answer = xmlGeneratorRadioMultipleCode.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );
      final var answer2 = xmlGeneratorRadioMultipleCode.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );

      final var result = prefillRadioMultipleCodeConverter.prefillAnswer(
          List.of(answer.getFirst(), answer2.getFirst()), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfNoAnswers() {
      final var result = prefillRadioMultipleCodeConverter.prefillAnswer(List.of(), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfInvalidRadioMultipleCodeFormat() {
      final var answer = new Svar();
      final var subAnswer = new Delsvar();
      final var content = List.of("invalid-radioMultipleCode-format");
      subAnswer.getContent().add(content);
      answer.getDelsvar().add(subAnswer);

      final var result = prefillRadioMultipleCodeConverter.prefillAnswer(List.of(answer),
          SPECIFICATION);

      assertEquals(
          PrefillErrorType.INVALID_FORMAT,
          result.getErrors().getFirst().type()
      );
    }
  }

  @Nested
  class SubAnswer {

    @Test
    void shouldReturnPrefillAnswerForSubAnswer() throws Exception {
      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
          .build();

      final var subAnswer = new Delsvar();
      subAnswer.getContent().add(createCVTypeElement());

      final var result = prefillRadioMultipleCodeConverter.prefillSubAnswer(
          List.of(subAnswer),
          SPECIFICATION);

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnErrorIfWrongConfigurationTypeForSubAnswer() {
      final var wrongConfiguration = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(ElementConfigurationCategory.builder().build())
          .build();

      final var result = prefillRadioMultipleCodeConverter.prefillSubAnswer(
          List.of(new Delsvar()),
          wrongConfiguration);

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfMoreThanOneSubAnswer() {
      final var answer = xmlGeneratorRadioMultipleCode.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );
      final var answer2 = xmlGeneratorRadioMultipleCode.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );

      final var result = prefillRadioMultipleCodeConverter.prefillSubAnswer(
          List.of(answer.getFirst().getDelsvar().getFirst(),
              answer2.getFirst().getDelsvar().getFirst()), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfNoSubAnswers() {
      final var result = prefillRadioMultipleCodeConverter.prefillSubAnswer(List.of(),
          SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfInvalidFormat() {
      final var subAnswer = new Delsvar();
      final var content = List.of("invalid-radioMultipleCode-format");
      subAnswer.getContent().add(content);

      final var result = prefillRadioMultipleCodeConverter.prefillSubAnswer(List.of(subAnswer),
          SPECIFICATION);

      assertEquals(
          PrefillErrorType.INVALID_FORMAT,
          result.getErrors().getFirst().type()
      );
    }
  }

  private static Element createCVTypeElement() throws Exception {
    final var cvType = new CVType();
    cvType.setCode(CODE);
    cvType.setDisplayName("D1");

    final var factory = new ObjectFactory();
    final var jaxbElement = factory.createCv(cvType);

    final var context = JAXBContext.newInstance(CVType.class);
    final var marshaller = context.createMarshaller();
    final var writer = new StringWriter();
    marshaller.marshal(jaxbElement, writer);

    final var docFactory = DocumentBuilderFactory.newInstance();
    docFactory.setNamespaceAware(true);
    final var doc = docFactory.newDocumentBuilder()
        .parse(new org.xml.sax.InputSource(new StringReader(writer.toString())));
    return doc.getDocumentElement();
  }
}