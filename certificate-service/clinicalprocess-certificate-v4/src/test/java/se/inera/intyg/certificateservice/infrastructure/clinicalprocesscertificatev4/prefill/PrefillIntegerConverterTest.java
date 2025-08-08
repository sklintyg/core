package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter.PrefillIntegerConverter;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

class PrefillIntegerConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId INTEGER_ID = new FieldId("2");
  private static final int VALUE = 42;
  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationInteger.builder()
              .id(INTEGER_ID)
              .build()
      )
      .build();

  private final PrefillIntegerConverter converter = new PrefillIntegerConverter();

  @Test
  void shouldReturnSupportsInteger() {
    assertEquals(ElementConfigurationInteger.class, converter.supports());
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
    void shouldReturnErrorIfInvalidIntegerValue() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      svar.setId(SPECIFICATION.id().id());
      final var delsvar = new Delsvar();
      delsvar.setId(INTEGER_ID.value());
      delsvar.getContent().add("invalid");
      svar.getDelsvar().add(delsvar);
      prefill.getSvar().add(svar);

      final var result = converter.prefillAnswer(SPECIFICATION, prefill);

      assertEquals(PrefillErrorType.INVALID_FORMAT, result.getErrors().getFirst().type());
    }

    @Test
    void shouldReturnPrefillAnswerIfSubAnswerExists() {
      final var prefill = getPrefill(ELEMENT_ID.id(), VALUE);

      final var result = converter.prefillAnswer(SPECIFICATION, prefill);

      final var expected = PrefillAnswer.builder()
          .elementData(
              ElementData.builder()
                  .id(new ElementId(ELEMENT_ID.id()))
                  .value(
                      ElementValueInteger.builder()
                          .integerId(INTEGER_ID)
                          .value(VALUE)
                          .build()
                  ).build()
          )
          .build();

      assertEquals(expected, result);
    }
  }

  private static Forifyllnad getPrefill(String id, Integer value) {
    final var prefill = new Forifyllnad();
    final var svar = new Svar();
    svar.setId(id);
    final var delsvar = new Delsvar();
    delsvar.setId(INTEGER_ID.value());
    delsvar.getContent().add(String.valueOf(value));
    svar.getDelsvar().add(delsvar);
    prefill.getSvar().add(svar);
    return prefill;
  }
}
