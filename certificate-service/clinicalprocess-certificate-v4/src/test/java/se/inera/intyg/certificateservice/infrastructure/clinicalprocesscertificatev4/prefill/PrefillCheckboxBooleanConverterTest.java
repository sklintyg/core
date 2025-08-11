package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter.PrefillCheckboxBooleanConverter;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

class PrefillCheckboxBooleanConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId CHECKBOX_ID = new FieldId("2");
  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationCheckboxBoolean.builder()
              .id(CHECKBOX_ID)
              .build()
      )
      .build();

  private final PrefillCheckboxBooleanConverter converter = new PrefillCheckboxBooleanConverter();

  @Test
  void shouldReturnSupportsCheckboxBoolean() {
    assertEquals(ElementConfigurationCheckboxBoolean.class, converter.supports());
  }

  @Nested
  class PrefillAnswerWithForifyllnad {

    @Test
    void shouldReturnNullIfNoAnswersOrSubAnswers() {
      final var prefill = new Forifyllnad();

      final var result = converter.prefillAnswer(SPECIFICATION, prefill);

      assertNull(result);
    }

    @Test
    void shouldReturnErrorIfWrongConfigurationType() {
      final var prefill = new Forifyllnad();
      final var wrongSpec = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(ElementConfigurationCategory.builder().build())
          .build();

      final var result = converter.prefillAnswer(wrongSpec, prefill);

      assertEquals(PrefillErrorType.TECHNICAL_ERROR, result.getErrors().getFirst().type());
    }

    @Test
    void shouldReturnErrorIfInvalidBooleanValue() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId(ELEMENT_ID.id());
      final var delsvar = new Delsvar();
      delsvar.setId(CHECKBOX_ID.value());
      delsvar.getContent().add("notABoolean");
      svar.getDelsvar().add(delsvar);
      prefill.getSvar().add(svar);

      final var result = converter.prefillAnswer(SPECIFICATION, prefill);

      assertEquals(PrefillErrorType.INVALID_BOOLEAN_VALUE, result.getErrors().getFirst().type());
    }

    @Test
    void shouldReturnPrefillAnswerTrueIfSubAnswerExists() {
      final var prefill = getPrefill(true, ELEMENT_ID.id());
      final var expected = PrefillAnswer.builder()
          .elementData(
              ElementData.builder()
                  .id(new ElementId(ELEMENT_ID.id()))
                  .value(
                      ElementValueBoolean.builder()
                          .booleanId(CHECKBOX_ID)
                          .value(true)
                          .build()
                  ).build()
          )
          .build();

      final var result = converter.prefillAnswer(SPECIFICATION, prefill);

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnPrefillAnswerFalseIfSubAnswerExists() {
      final var prefill = getPrefill(false, ELEMENT_ID.id());
      final var expected = PrefillAnswer.builder()
          .elementData(
              ElementData.builder()
                  .id(new ElementId(ELEMENT_ID.id()))
                  .value(
                      ElementValueBoolean.builder()
                          .booleanId(CHECKBOX_ID)
                          .value(false)
                          .build()
                  ).build()
          ).build();
      final var result = converter.prefillAnswer(SPECIFICATION, prefill);
      assertEquals(expected, result);
    }
  }

  private static Forifyllnad getPrefill(boolean answer, String id) {
    final var prefill = new Forifyllnad();
    final var svar = new Svar();
    svar.setId(id);
    final var delsvar = new Delsvar();
    delsvar.setId(CHECKBOX_ID.value());
    delsvar.getContent().add(String.valueOf(answer));
    svar.getDelsvar().add(delsvar);
    prefill.getSvar().add(svar);
    return prefill;
  }
}
