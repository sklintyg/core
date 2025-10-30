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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter.PrefillCheckboxMultipleCodeConverter;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

class PrefillCheckboxMultipleCodeConverterTest {

  private static final ObjectFactory OBJECT_FACTORY = new ObjectFactory();
  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId FIELD_ID = new FieldId("F");
  private static final String CODE_1 = "code1";
  private static final String CODE_2 = "code2";
  private static final String D_1 = "D1";
  private static final String D_2 = "D2";
  private static final FieldId CODE_2_ID = new FieldId(CODE_2);
  private static final FieldId CODE_ID = new FieldId(CODE_1);
  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationCheckboxMultipleCode.builder()
              .id(FIELD_ID)
              .list(
                  List.of(
                      new ElementConfigurationCode(
                          CODE_ID, "Code 1", new Code(CODE_1, "S1", D_1)),
                      new ElementConfigurationCode(
                          CODE_2_ID, "Code 2", new Code(CODE_2, "S1", D_2))
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
                          .codeId(CODE_ID)
                          .code(CODE_1)
                          .build(),
                      ElementValueCode.builder()
                          .codeId(CODE_2_ID)
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
      subAnswer.setId(FIELD_ID.value());

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
      subAnswer.setId(FIELD_ID.value());

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
    void shouldReturnElementDataFromMatchingSubAnswer() {
      final var prefill = new Forifyllnad();

      final var svar = new Svar();
      svar.setId(SPECIFICATION.id().id());

      final var delsvar = new Delsvar();
      final var delsvar2 = new Delsvar();

      delsvar.setId("other");
      delsvar2.setId(FIELD_ID.value());
      delsvar2.getContent().add(getElement(getCvType(CODE_2, D_2), OBJECT_FACTORY::createCv));

      svar.getDelsvar().add(delsvar);
      svar.getDelsvar().add(delsvar2);
      prefill.getSvar().add(svar);

      final var result = prefillCheckboxMultipleCodeConverter.prefillAnswer(SPECIFICATION, prefill);
      assertEquals(CODE_2,
          ((ElementValueCodeList) result.getElementData().value()).list().getFirst().code());
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

    @Test
    void shouldPrefillFromConfigurationIfNotIncludedInXml() {
      final var expectedElementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueCodeList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          ElementValueCode.builder()
                              .codeId(CODE_ID)
                              .code(CODE_1)
                              .build(),
                          ElementValueCode.builder()
                              .codeId(CODE_2_ID)
                              .code(CODE_2)
                              .build()
                      )
                  )
                  .build()
          )
          .build();
      final var specification = ElementSpecification.builder()
          .includeInXml(false)
          .id(ELEMENT_ID)
          .configuration(
              ElementConfigurationCheckboxMultipleCode.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          new ElementConfigurationCode(
                              CODE_ID, "Code 1", new Code(CODE_1, "S1", D_1)),
                          new ElementConfigurationCode(
                              CODE_2_ID, "Code 2", new Code(CODE_2, "S1", D_2))
                      )
                  )
                  .build()
          )
          .build();

      final var prefill = getPrefill();

      final var result = prefillCheckboxMultipleCodeConverter.prefillAnswer(specification, prefill);
      assertEquals(expectedElementData, result.getElementData());
    }

    @Test
    void shouldUseElementMappingIdWhenMappingExists() {
      final var mappedElementId = new ElementId("9");
      final var specification = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .mapping(
              new ElementMapping(
                  mappedElementId,
                  null
              ))
          .configuration(
              ElementConfigurationCheckboxMultipleCode.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          new ElementConfigurationCode(
                              CODE_ID, "Code 1", new Code(CODE_1, "S1", D_1))
                      )
                  )
                  .build()
          )
          .build();

      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId(mappedElementId.id());

      final var delsvar = new Delsvar();
      delsvar.setId(FIELD_ID.value());
      delsvar.getContent().add(getElement(getCvType(CODE_1, D_1), OBJECT_FACTORY::createCv));

      svar.getDelsvar().add(delsvar);
      prefill.getSvar().add(svar);

      final var result = prefillCheckboxMultipleCodeConverter.prefillAnswer(specification, prefill);

      final var expectedElementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueCodeList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          ElementValueCode.builder()
                              .codeId(CODE_ID)
                              .code(CODE_1)
                              .build()
                      )
                  )
                  .build()
          ).build();

      assertEquals(expectedElementData, result.getElementData());
    }

    @Test
    void shouldNotMatchWhenMappingDoesNotExistAndSvarIdDoesNotMatchElementId() {
      final var specification = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(
              ElementConfigurationCheckboxMultipleCode.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          new ElementConfigurationCode(
                              CODE_ID, "Code 1", new Code(CODE_1, "S1", D_1))
                      )
                  )
                  .build()
          )
          .build();

      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId("9");

      final var delsvar = new Delsvar();
      delsvar.setId(FIELD_ID.value());
      delsvar.getContent().add(getElement(getCvType(CODE_1, D_1), OBJECT_FACTORY::createCv));

      svar.getDelsvar().add(delsvar);
      prefill.getSvar().add(svar);

      final var result = prefillCheckboxMultipleCodeConverter.prefillAnswer(specification, prefill);

      assertNull(result);
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

    delsvar.setId(FIELD_ID.value());
    delsvar2.setId(FIELD_ID.value());
    delsvar.getContent().add(getElement(getCvType(CODE_1, D_1), OBJECT_FACTORY::createCv));
    delsvar2.getContent().add(getElement(getCvType(CODE_2, D_2), OBJECT_FACTORY::createCv));

    svar.getDelsvar().add(delsvar);
    svar2.getDelsvar().add(delsvar2);

    prefill.getSvar().add(svar);
    prefill.getSvar().add(svar2);
    return prefill;
  }

  private static CVType getCvType(String code, String display) {
    final var cvType2 = new CVType();
    cvType2.setCode(code);
    cvType2.setDisplayName(display);
    cvType2.setCodeSystem("S1");
    return cvType2;
  }
}