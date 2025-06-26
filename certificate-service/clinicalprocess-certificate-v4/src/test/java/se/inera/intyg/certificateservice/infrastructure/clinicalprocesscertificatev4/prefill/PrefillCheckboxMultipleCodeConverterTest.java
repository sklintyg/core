package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.TestMarshaller.getElement;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

class PrefillCheckboxMultipleCodeConverterTest {

  private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();
  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId CODE_FIELD_ID = new FieldId("F1");
  private static final FieldId FIELD_ID = new FieldId("F");
  private static final FieldId CODE_2_FIELD_ID = new FieldId("F2");
  private static final String CODE = "code1";
  private static final String CODE_2 = "code2";
  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationCheckboxMultipleCode.builder()
              .id(FIELD_ID)
              .list(
                  List.of(
                      new ElementConfigurationCode(
                          CODE_FIELD_ID, "Code 1", new Code(CODE, "S1", "D1")),
                      new ElementConfigurationCode(
                          CODE_2_FIELD_ID, "Code 2", new Code(CODE_2, "S1", "D2"))
                  )
              )
              .build()
      )
      .build();
  private static final ElementData EXPECTED_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueCodeList.builder()
              .id(FIELD_ID)
              .list(
                  List.of(
                      ElementValueCode.builder()
                          .codeId(CODE_FIELD_ID)
                          .code(CODE)
                          .build(),
                      ElementValueCode.builder()
                          .codeId(CODE_2_FIELD_ID)
                          .code(CODE_2)
                          .build()
                  )
              )
              .build()
      ).build();

  private final PrefillCheckboxMultipleCodeConverter prefillCheckboxMultipleCodeConverter = new PrefillCheckboxMultipleCodeConverter();

  @Test
  void shouldReturnSupportsCheckboxMultipleCode() {
    assertEquals(ElementConfigurationCheckboxMultipleCode.class,
        prefillCheckboxMultipleCodeConverter.supports());
  }

  @Nested
  class PrefillAnswerWithForifyllnad {

    @Test
    void shouldReturnNullIfNoAnswerExists() {
      Forifyllnad prefill = new Forifyllnad();

      PrefillAnswer result = prefillCheckboxMultipleCodeConverter.prefillAnswer(SPECIFICATION,
          prefill);

      assertNull(result);
    }

    @Test
    void shouldReturnPrefillAnswerIfAnswerExists() {
      final var prefill = getPrefill();

      final var result = prefillCheckboxMultipleCodeConverter.prefillAnswer(SPECIFICATION, prefill);

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

      final var result = prefillCheckboxMultipleCodeConverter.prefillAnswer(wrongSpec, prefill);

      assertEquals(
          PrefillErrorType.TECHNICAL_ERROR,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnErrorIfInvalidCheckboxMultipleCodeFormat() {
      final var prefill = new Forifyllnad();
      final var answer = new Svar();
      answer.setId(SPECIFICATION.id().id());

      final var subAnswer = new Delsvar();
      subAnswer.setId("other");

      final var content = List.of("invalid-checkboxMultipleCode-format");
      subAnswer.getContent().add(content);

      answer.getDelsvar().add(subAnswer);
      prefill.getSvar().add(answer);

      final var result = prefillCheckboxMultipleCodeConverter.prefillAnswer(
          SPECIFICATION, prefill);

      assertEquals(
          PrefillErrorType.INVALID_FORMAT,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnElementDataIfOneAnswerHasInvalidFormat() {

      final var prefill = getPrefill();

      final var answer = new Svar();
      answer.setId(SPECIFICATION.id().id());

      final var subAnswer = new Delsvar();
      subAnswer.setId("other");

      final var content = List.of("invalid-checkboxMultipleCode-format");
      subAnswer.getContent().add(content);

      answer.getDelsvar().add(subAnswer);
      prefill.getSvar().add(answer);

      final var result = prefillCheckboxMultipleCodeConverter.prefillAnswer(
          SPECIFICATION, prefill);

      assertAll(
          () -> assertEquals(EXPECTED_ELEMENT_DATA, result.getElementData()),
          () -> assertEquals(1, result.getErrors().size()),
          () -> assertEquals(PrefillErrorType.INVALID_FORMAT, result.getErrors().getFirst().type())
      );
    }

    @Test
    void shouldReturnErrorIfSubAnswersDoesNotExist() {
      final var prefill = new Forifyllnad();
      final var svar1 = new Svar();
      svar1.setId(SPECIFICATION.id().id());
      prefill.getSvar().add(svar1);

      final var result = prefillCheckboxMultipleCodeConverter.prefillAnswer(SPECIFICATION,
          prefill);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }
  }

  private static Forifyllnad getPrefill() {
    final var prefill = new Forifyllnad();

    final var svar = new Svar();
    final var svar2 = new Svar();

    svar.setId(SPECIFICATION.id().id());
    svar2.setId(SPECIFICATION.id().id());

    final var delsvar = new Delsvar();
    final var delsvar2 = new Delsvar();

    delsvar.setId("other");
    delsvar2.setId("other2");
    final var cvType1 = new CVType();
    cvType1.setCode(CODE);
    cvType1.setCodeSystem("S1");
    cvType1.setDisplayName("D1");
    final var cvType2 = new CVType();
    cvType2.setCode(CODE_2);
    cvType2.setDisplayName("D2");
    cvType2.setCodeSystem("S1");
    delsvar.getContent().add(getElement(cvType1, OBJECT_FACTORY::createCv));
    delsvar2.getContent().add(getElement(cvType2, OBJECT_FACTORY::createCv));

    svar.getDelsvar().add(delsvar);
    svar2.getDelsvar().add(delsvar2);

    prefill.getSvar().add(svar);
    prefill.getSvar().add(svar2);
    return prefill;
  }
}