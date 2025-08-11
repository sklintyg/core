package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.CATEGORY_ELEMENT_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.DATE_ELEMENT_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.ISSUING_UNIT_ELEMENT_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.MESSAGE_ELEMENT_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.categoryElementSpecificationBuilder;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter.PrefillCustomConverter;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter.PrefillStandardConverter;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter.PrefillTextAreaConverter;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@ExtendWith(MockitoExtension.class)
class PrefillHandlerTest {

  @Nested
  class HandlePrefill {

    @Mock
    PrefillStandardConverter prefillConverter;
    @Mock
    PrefillCustomConverter customConverter;

    private PrefillHandler prefillHandler;

    @BeforeEach
    void setup() {
      doReturn(ElementConfigurationDate.class).when(prefillConverter).supports();
      prefillHandler = new PrefillHandler(List.of(prefillConverter), List.of(customConverter));
    }

    @Test
    void shouldReturnPrefillAnswer() {

      final var prefill = new Forifyllnad();

      final var expected = PrefillAnswer.builder()
          .elementData(ElementData.builder()
              .id(new ElementId("id"))
              .build())
          .build();
      when(prefillConverter.prefillAnswer(DATE_ELEMENT_SPECIFICATION, prefill)).thenReturn(
          expected);

      final var result = prefillHandler.handlePrefill(
          CertificateModel.builder()
              .elementSpecifications(List.of(DATE_ELEMENT_SPECIFICATION))
              .build(),
          prefill
      );

      assertEquals(List.of(expected), result);
    }

    @Test
    void shouldReturnResultWithErrorWhenExceptionOccurs() {
      final var prefill = new Forifyllnad();

      final var expected = PrefillAnswer.builder()
          .errors(List.of(PrefillError.technicalError(DATE_ELEMENT_SPECIFICATION.id().id())))
          .build();

      when(prefillConverter.prefillAnswer(DATE_ELEMENT_SPECIFICATION, prefill)).thenThrow(
          new RuntimeException("Test exception"));

      final var result = prefillHandler.handlePrefill(
          CertificateModel.builder()
              .elementSpecifications(List.of(DATE_ELEMENT_SPECIFICATION))
              .build(),
          prefill
      );
      assertEquals(List.of(expected), result);
    }

    @Test
    void shouldOnlyHandleQuestionSpecifications() {

      final var prefill = new Forifyllnad();

      final var expected = PrefillAnswer.builder()
          .elementData(ElementData.builder()
              .id(new ElementId("id"))
              .build())
          .build();

      when(prefillConverter.prefillAnswer(DATE_ELEMENT_SPECIFICATION, prefill)).thenReturn(
          expected);

      final var messageSpec = MESSAGE_ELEMENT_SPECIFICATION;
      final var issuingUnitSpec = ISSUING_UNIT_ELEMENT_SPECIFICATION;
      final var categorySpec = categoryElementSpecificationBuilder()
          .children(List.of(DATE_ELEMENT_SPECIFICATION, CATEGORY_ELEMENT_SPECIFICATION, messageSpec,
              issuingUnitSpec))
          .build();

      final var result = prefillHandler.handlePrefill(
          CertificateModel.builder()
              .elementSpecifications(
                  List.of(DATE_ELEMENT_SPECIFICATION, categorySpec,
                      messageSpec, issuingUnitSpec))
              .build(),
          prefill
      );

      assertEquals(List.of(expected, expected), result);
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

      final var prefillHandler = new PrefillHandler(
          List.of(new PrefillTextAreaConverter()), List.of());

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

      final var prefillHandler = new PrefillHandler(
          List.of(new PrefillTextAreaConverter()), List.of());

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