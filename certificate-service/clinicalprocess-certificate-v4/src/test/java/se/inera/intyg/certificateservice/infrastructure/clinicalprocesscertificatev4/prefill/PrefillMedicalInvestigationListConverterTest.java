package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.xml.bind.JAXBContext;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate.XmlGeneratorMedicalInvestigationList;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

class PrefillMedicalInvestigationListConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId CODE_FIELD_ID = new FieldId("FC1");
  private static final FieldId DATE_FIELD_ID = new FieldId("FD1");
  private static final FieldId TEXT_FIELD_ID = new FieldId("FT1");
  private static final FieldId FIELD_ID = new FieldId("F");
  private static final FieldId CODE_2_FIELD_ID = new FieldId("FC2");
  private static final FieldId DATE_2_FIELD_ID = new FieldId("FD2");
  private static final FieldId TEXT_2_FIELD_ID = new FieldId("FT2");
  private static final FieldId FIELD_2_ID = new FieldId("F2");

  private static final String CODE = "code1";
  private static final String CODE_2 = "code2";
  private static final LocalDate DATE = LocalDate.now();
  private static final LocalDate DATE_2 = LocalDate.now().plusDays(1);
  private static final String TEXT = "text1";
  private static final String TEXT_2 = "text2";

  private static final List<Code> TYPE_OPTIONS = List.of(
      new Code(CODE, "S1", "Type 1"),
      new Code(CODE_2, "S1", "Type 2")
  );

  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationMedicalInvestigationList.builder()
              .id(FIELD_ID)
              .list(
                  List.of(
                      MedicalInvestigationConfig.builder()
                          .id(FIELD_ID)
                          .dateId(DATE_FIELD_ID)
                          .investigationTypeId(CODE_FIELD_ID)
                          .informationSourceId(TEXT_FIELD_ID)
                          .typeOptions(TYPE_OPTIONS)
                          .build(),
                      MedicalInvestigationConfig.builder()
                          .id(FIELD_2_ID)
                          .dateId(DATE_2_FIELD_ID)
                          .investigationTypeId(CODE_2_FIELD_ID)
                          .informationSourceId(TEXT_2_FIELD_ID)
                          .typeOptions(TYPE_OPTIONS)
                          .build()
                  )
              )
              .build()
      )
      .build();
  private static final ElementData EXPECTED_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueMedicalInvestigationList.builder()
              .id(FIELD_ID)
              .list(
                  List.of(
                      MedicalInvestigation.builder()
                          .date(ElementValueDate.builder()
                              .dateId(DATE_FIELD_ID)
                              .date(DATE)
                              .build())
                          .investigationType(ElementValueCode.builder()
                              .codeId(CODE_FIELD_ID)
                              .code(CODE)
                              .build())
                          .informationSource(ElementValueText.builder()
                              .textId(TEXT_FIELD_ID)
                              .text(TEXT)
                              .build())
                          .build(),
                      MedicalInvestigation.builder()
                          .date(ElementValueDate.builder()
                              .dateId(DATE_2_FIELD_ID)
                              .date(DATE_2)
                              .build())
                          .investigationType(ElementValueCode.builder()
                              .codeId(CODE_2_FIELD_ID)
                              .code(CODE_2)
                              .build())
                          .informationSource(ElementValueText.builder()
                              .textId(TEXT_2_FIELD_ID)
                              .text(TEXT_2)
                              .build())
                          .build()
                  )
              )
              .build()
      ).build();
  private static final ElementData EXPECTED_ELEMENT_DATA_SUB_ANSWER = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueMedicalInvestigationList.builder()
              .id(FIELD_ID)
              .list(
                  List.of(
                      MedicalInvestigation.builder()
                          .date(ElementValueDate.builder()
                              .dateId(DATE_FIELD_ID)
                              .date(DATE)
                              .build())
                          .investigationType(ElementValueCode.builder()
                              .codeId(CODE_FIELD_ID)
                              .code(CODE)
                              .build())
                          .informationSource(ElementValueText.builder()
                              .textId(TEXT_FIELD_ID)
                              .text(TEXT)
                              .build())
                          .build()
                  )
              )
              .build()
      ).build();

  private final PrefillMedicalInvestigationListConverter prefillMedicalInvestigationListConverter = new PrefillMedicalInvestigationListConverter();
  private final XmlGeneratorMedicalInvestigationList xmlGeneratorMedicalInvestigationList = new XmlGeneratorMedicalInvestigationList();

  @Test
  void shouldReturnSupportsMedicalInvestigationList() {
    assertEquals(ElementConfigurationMedicalInvestigationList.class,
        prefillMedicalInvestigationListConverter.supports());
  }

  @Nested
  class Answer {

    @Test
    void shouldReturnAdditionalIds() {
      assertEquals(
          List.of("1.1", "1.2", "1.3"),
          prefillMedicalInvestigationListConverter.additionalIds(ELEMENT_ID)
      );
    }

    @Test
    void shouldCreatePrefillAnswerForMedicalInvestigationList() throws Exception {
      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
          .build();

      final var answer = getMedicalInvestigationAnswer(CODE, TEXT, DATE, 1);
      final var answer2 = getMedicalInvestigationAnswer(CODE_2, TEXT_2, DATE_2, 2);

      final var result = prefillMedicalInvestigationListConverter.prefillAnswer(
          List.of(answer, answer2), SPECIFICATION);

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnErrorIfWrongConfigurationType() {
      final var wrongConfiguration = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(ElementConfigurationCategory.builder().build())
          .build();

      final var result = prefillMedicalInvestigationListConverter.prefillAnswer(
          List.of(new Svar()),
          wrongConfiguration
      );

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfNoAnswers() {
      final var result = prefillMedicalInvestigationListConverter.prefillAnswer(List.of(),
          SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfInvalidMedicalInvestigationListFormat() {
      final var answer = new Svar();
      final var subAnswer = new Delsvar();
      final var content = List.of("invalid-medicalInvestigationList-format");
      subAnswer.getContent().add(content);
      answer.getDelsvar().add(subAnswer);

      final var result = prefillMedicalInvestigationListConverter.prefillAnswer(List.of(answer),
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
          .elementData(EXPECTED_ELEMENT_DATA_SUB_ANSWER)
          .build();

      final var answer = getMedicalInvestigationAnswer(CODE, TEXT, DATE, 1);

      final var result = prefillMedicalInvestigationListConverter.prefillSubAnswer(
          answer.getDelsvar(),
          SPECIFICATION
      );

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnErrorIfWrongConfigurationTypeForSubAnswer() throws Exception {
      final var wrongConfiguration = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(ElementConfigurationCategory.builder().build())
          .build();
      final var answer = getMedicalInvestigationAnswer(CODE, TEXT, DATE, 1);

      final var result = prefillMedicalInvestigationListConverter.prefillSubAnswer(
          answer.getDelsvar(),
          wrongConfiguration
      );

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfNotThreeSubAnswers() {
      final var answer = xmlGeneratorMedicalInvestigationList.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );
      final var answer2 = xmlGeneratorMedicalInvestigationList.generate(
          EXPECTED_ELEMENT_DATA,
          SPECIFICATION
      );

      final var result = prefillMedicalInvestigationListConverter.prefillSubAnswer(
          List.of(answer.getFirst().getDelsvar().getFirst(),
              answer2.getFirst().getDelsvar().getFirst()), SPECIFICATION);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfInvalidFormat() {
      final var subAnswer = new Delsvar();
      final var content = List.of("invalid-medicalInvestigationList-format");
      subAnswer.getContent().add(content);

      final var result = prefillMedicalInvestigationListConverter.prefillSubAnswer(
          List.of(subAnswer, subAnswer, subAnswer),
          SPECIFICATION);

      assertEquals(
          PrefillErrorType.INVALID_FORMAT,
          result.getErrors().getFirst().type()
      );
    }
  }

  private static Element createCVTypeElement(String code) throws Exception {
    final var cvType = new CVType();
    cvType.setCode(code);
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

  private static Svar getMedicalInvestigationAnswer(String code, String text, LocalDate date,
      int instance) throws Exception {
    final var answer = new Svar();
    answer.setId(ELEMENT_ID.id());
    answer.setInstans(instance);

    final var subAnswerCode = new Delsvar();
    final var subAnswerDate = new Delsvar();
    final var subAnswerText = new Delsvar();

    subAnswerCode.setId(ELEMENT_ID.id() + ".1");
    subAnswerDate.setId(ELEMENT_ID.id() + ".2");
    subAnswerText.setId(ELEMENT_ID.id() + ".3");

    subAnswerCode.getContent().add(createCVTypeElement(code));
    subAnswerDate.getContent().add(date.toString());
    subAnswerText.getContent().add(text);

    answer.getDelsvar().add(subAnswerCode);
    answer.getDelsvar().add(subAnswerDate);
    answer.getDelsvar().add(subAnswerText);

    return answer;
  }
}