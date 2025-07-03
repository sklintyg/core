package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter.PrefillRadioBooleanConverter;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

class PrefillRadioBooleanConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId RADIOBOOLEAN_ID = new FieldId("2");
  private static final Boolean BOOLEAN = true;
  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationRadioBoolean.builder()
              .id(RADIOBOOLEAN_ID)
              .build()
      )
      .build();
  private static final ElementData EXPECTED_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueBoolean.builder()
              .booleanId(RADIOBOOLEAN_ID)
              .value(BOOLEAN)
              .build()
      ).build();

  private final PrefillRadioBooleanConverter prefillRadioBooleanConverter = new PrefillRadioBooleanConverter();

  @Test
  void shouldReturnSupportsRadioBoolean() {
    assertEquals(ElementConfigurationRadioBoolean.class, prefillRadioBooleanConverter.supports());
  }


  @Nested
  class PrefillAnswerWithForifyllnad {


    @Test
    void shouldReturnNullIfNoAnswersOrSubAnswers() {
      final var prefill = new Forifyllnad();
      final var converter = new PrefillRadioBooleanConverter();

      PrefillAnswer result = converter.prefillAnswer(SPECIFICATION, prefill);

      assertNull(result);
    }

    @Test
    void shouldReturnPrefillAnswerWithInvalidFormat() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId(ELEMENT_ID.id());
      final var delsvar = new Delsvar();
      delsvar.setId(RADIOBOOLEAN_ID.value());
      delsvar.getContent().add("wrongValue");
      svar.getDelsvar().add(delsvar);
      prefill.getSvar().add(svar);

      final var result = prefillRadioBooleanConverter.prefillAnswer(SPECIFICATION, prefill);

      assertEquals(
          PrefillErrorType.INVALID_BOOLEAN_VALUE,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnPrefillAnswerWithInvalidSubAnswerId() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId(ELEMENT_ID.id());
      final var delsvar = new Delsvar();
      delsvar.setId("wrongId");
      delsvar.getContent().add(BOOLEAN);
      svar.getDelsvar().add(delsvar);
      prefill.getSvar().add(svar);

      final var result = prefillRadioBooleanConverter.prefillAnswer(SPECIFICATION, prefill);

      assertEquals(
          PrefillErrorType.INVALID_SUB_ANSWER_ID,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnPrefillAnswerIfSubAnswerExists() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId("other");
      final var delsvar = new Delsvar();
      delsvar.setId(RADIOBOOLEAN_ID.value());
      delsvar.getContent().add(BOOLEAN.toString());
      svar.getDelsvar().add(delsvar);
      prefill.getSvar().add(svar);

      final var specification = ElementSpecification.builder()
          .id(new ElementId(RADIOBOOLEAN_ID.value()))
          .configuration(
              ElementConfigurationRadioBoolean.builder()
                  .id(RADIOBOOLEAN_ID)
                  .build()
          )
          .build();

      final var result = prefillRadioBooleanConverter.prefillAnswer(specification, prefill);

      final var expected = PrefillAnswer.builder()
          .elementData(
              ElementData.builder()
                  .id(new ElementId(RADIOBOOLEAN_ID.value()))
                  .value(
                      ElementValueBoolean.builder()
                          .booleanId(RADIOBOOLEAN_ID)
                          .value(BOOLEAN)
                          .build()
                  ).build()
          )
          .build();

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnPrefillAnswerIfAnswerExists() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId(ELEMENT_ID.id());
      final var delsvar = new Delsvar();
      delsvar.setId(RADIOBOOLEAN_ID.value());
      delsvar.getContent().add(BOOLEAN.toString());
      svar.getDelsvar().add(delsvar);
      prefill.getSvar().add(svar);

      final var result = prefillRadioBooleanConverter.prefillAnswer(SPECIFICATION, prefill);

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

      final var result = prefillRadioBooleanConverter.prefillAnswer(wrongSpec, prefill);

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

      final var result = prefillRadioBooleanConverter.prefillAnswer(SPECIFICATION, prefill);

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
      delsvar1.getContent().add(BOOLEAN.toString());
      svar1.getDelsvar().add(delsvar1);

      final var delsvar2 = new Delsvar();
      delsvar2.setId(SPECIFICATION.id().id());
      delsvar2.getContent().add(BOOLEAN.toString());
      svar1.getDelsvar().add(delsvar2);

      prefill.getSvar().add(svar1);

      final var result = prefillRadioBooleanConverter.prefillAnswer(SPECIFICATION, prefill);

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
      delsvar1.getContent().add(BOOLEAN.toString());
      svar1.getDelsvar().add(delsvar1);

      prefill.getSvar().add(svar1);

      final var result = prefillRadioBooleanConverter.prefillAnswer(SPECIFICATION, prefill);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getFirst().type()
      );
    }
  }
}