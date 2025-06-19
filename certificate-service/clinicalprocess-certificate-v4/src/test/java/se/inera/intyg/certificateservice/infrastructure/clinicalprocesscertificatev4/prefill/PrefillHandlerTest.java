package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;

class PrefillHandlerTest {

  @Nested
  class Answer {

    @Test
    void shouldReturnPrefillErrorIfMissingConverterForClass() {
      PrefillHandler prefillHandler = new PrefillHandler(List.of(new PrefillTextAreaConverter()));

      final var result = prefillHandler.prefillAnswer(
          List.of(new Svar()),
          ElementSpecification.builder()
              .configuration(
                  ElementConfigurationDate.builder().build()
              ).build()
      );

      assertEquals(PrefillErrorType.MISSING_CONVERTER, result.getErrors().getFirst().type());
    }
  }

  @Nested
  class SubAnswer {

    @Test
    void shouldReturnPrefillErrorIfMissingConverterForClass() {
      PrefillHandler prefillHandler = new PrefillHandler(List.of(new PrefillTextAreaConverter()));

      final var result = prefillHandler.prefillSubAnswer(
          List.of(new Svar.Delsvar()),
          ElementSpecification.builder()
              .configuration(
                  ElementConfigurationDate.builder().build()
              ).build()
      );

      assertEquals(PrefillErrorType.MISSING_CONVERTER, result.getErrors().getFirst().type());
    }
  }
}