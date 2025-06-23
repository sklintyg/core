package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.DATE_ELEMENT_SPECIFICATION;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

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

  @Nested
  class UnknownAnswerIds {

    Forifyllnad forifyllnad;

    @Test
    void shouldReturnPrefillErrorIfAnswerIdNotInModel() {

      forifyllnad = new Forifyllnad();

      final var svar = new Svar();
      svar.setId("testSvarId");
      final var delsvar = new Delsvar();
      svar.getDelsvar().add(delsvar);

      forifyllnad.getSvar().add(svar);

      final var expected = List.of(PrefillAnswer.answerNotFound("testSvarId"));

      PrefillHandler prefillHandler = new PrefillHandler(List.of(new PrefillTextAreaConverter()));

      final var result = prefillHandler.unknownAnswerIds(
          CertificateModel.builder()
              .elementSpecifications(
                  List.of(DATE_ELEMENT_SPECIFICATION))
              .build(),
          forifyllnad
      );

      assertEquals(expected, result);
    }

    @Test
    void shouldNotReturnPrefillErrorIfAnswerIdInModel() {

      forifyllnad = new Forifyllnad();

      final var svar = new Svar();
      svar.setId(DATE_ELEMENT_ID);
      final var delsvar = new Delsvar();
      svar.getDelsvar().add(delsvar);

      forifyllnad.getSvar().add(svar);

      PrefillHandler prefillHandler = new PrefillHandler(List.of(new PrefillTextAreaConverter()));

      final var result = prefillHandler.unknownAnswerIds(
          CertificateModel.builder()
              .elementSpecifications(
                  List.of(DATE_ELEMENT_SPECIFICATION))
              .build(),
          forifyllnad
      );

      assertTrue(result.isEmpty());
    }
  }
}