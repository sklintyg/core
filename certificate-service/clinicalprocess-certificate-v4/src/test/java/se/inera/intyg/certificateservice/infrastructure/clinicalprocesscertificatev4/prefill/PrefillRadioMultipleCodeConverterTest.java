package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.TestMarshaller.getElement;

import java.util.List;
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
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

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

  @Test
  void shouldReturnSupportsRadioMultipleCode() {
    assertEquals(ElementConfigurationRadioMultipleCode.class,
        prefillRadioMultipleCodeConverter.supports());
  }

  @Nested
  class PrefillAnswerWithForifyllnad {


    @Test
    void shouldReturnNullIfNoAnswersOrSubAnswers() {
      final var prefill = new Forifyllnad();

      PrefillAnswer result = prefillRadioMultipleCodeConverter.prefillAnswer(SPECIFICATION,
          prefill);

      assertNull(result);
    }

    @Test
    void shouldReturnPrefillAnswerWithInvalidFormat() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId("other");
      final var delsvar = new Delsvar();
      delsvar.setId(SPECIFICATION.id().id());
      delsvar.getContent().add("wrongValue");
      svar.getDelsvar().add(delsvar);
      prefill.getSvar().add(svar);

      final var result = prefillRadioMultipleCodeConverter.prefillAnswer(SPECIFICATION, prefill);

      assertEquals(
          PrefillErrorType.INVALID_FORMAT,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnPrefillAnswerIfSubAnswerExists() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId("other");
      final var delsvar = new Delsvar();
      delsvar.setId(SPECIFICATION.id().id());
      delsvar.getContent().add(createCVTypeElement());
      svar.getDelsvar().add(delsvar);
      prefill.getSvar().add(svar);

      final var result = prefillRadioMultipleCodeConverter.prefillAnswer(SPECIFICATION, prefill);

      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
          .build();

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnPrefillAnswerIfAnswerExists() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId(SPECIFICATION.id().id());
      final var delsvar = new Delsvar();
      delsvar.setId("other");
      delsvar.getContent().add(createCVTypeElement());
      svar.getDelsvar().add(delsvar);
      prefill.getSvar().add(svar);

      final var result = prefillRadioMultipleCodeConverter.prefillAnswer(SPECIFICATION, prefill);

      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
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

      final var result = prefillRadioMultipleCodeConverter.prefillAnswer(wrongSpec, prefill);

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfMultipleAnswers() {
      final var prefill = new Forifyllnad();
      final var svar1 = new Svar();
      svar1.setId(SPECIFICATION.id().id());

      final var svar2 = new Svar();
      svar2.setId(SPECIFICATION.id().id());

      prefill.getSvar().add(svar1);
      prefill.getSvar().add(svar2);

      final var result = prefillRadioMultipleCodeConverter.prefillAnswer(SPECIFICATION, prefill);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfMultipleSubAnswers() {
      final var prefill = new Forifyllnad();
      final var svar1 = new Svar();
      svar1.setId("other");
      final var delsvar1 = new Delsvar();
      delsvar1.setId(SPECIFICATION.id().id());
      svar1.getDelsvar().add(delsvar1);

      final var delsvar2 = new Delsvar();
      delsvar2.setId(SPECIFICATION.id().id());
      svar1.getDelsvar().add(delsvar2);

      prefill.getSvar().add(svar1);

      final var result = prefillRadioMultipleCodeConverter.prefillAnswer(SPECIFICATION, prefill);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfBothSubAnswerAndAnswerIsPresent() {
      final var prefill = new Forifyllnad();
      final var svar1 = new Svar();
      svar1.setId(SPECIFICATION.id().id());
      final var delsvar1 = new Delsvar();
      delsvar1.setId(SPECIFICATION.id().id());
      svar1.getDelsvar().add(delsvar1);

      prefill.getSvar().add(svar1);

      final var result = prefillRadioMultipleCodeConverter.prefillAnswer(SPECIFICATION, prefill);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }

    private static Element createCVTypeElement() {
      final var cvType = new CVType();
      cvType.setCode(CODE);
      cvType.setCodeSystem("S1");
      cvType.setDisplayName("D1");
      final var factory = new ObjectFactory();
      return getElement(cvType, factory::createCv);
    }
  }
}