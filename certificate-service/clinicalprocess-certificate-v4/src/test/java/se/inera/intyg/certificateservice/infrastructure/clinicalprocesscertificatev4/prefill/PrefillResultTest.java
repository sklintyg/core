package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@ExtendWith(MockitoExtension.class)
class PrefillResultTest {

  @Mock
  private CertificateModel certificateModel;
  @Mock
  private PrefillHandler prefillHandler;
  @Mock
  private PrefillAnswer prefillAnswer;
  @Mock
  private ElementData elementData;

  @Test
  void shouldCreatePrefillResult() {
    final var forifyllnad = new Forifyllnad();

    final var result = PrefillResult.create(certificateModel, forifyllnad, prefillHandler);

    assertAll(
        () -> assertEquals(certificateModel, result.getCertificateModel()),
        () -> assertEquals(prefillHandler, result.getPrefillHandler()),
        () -> assertEquals(forifyllnad, result.getPrefill())
    );
  }

  @Test
  void shouldReturnEmptyPrefilledElementsIfNoAnswers() {
    final var forifyllnad = new Forifyllnad();

    final var result = PrefillResult.create(certificateModel, forifyllnad, prefillHandler);

    assertTrue(result.prefilledElements().isEmpty());
  }

  @Test
  void shouldReturnPrefilledElements() {
    final var forifyllnad = new Forifyllnad();

    when(prefillAnswer.getElementData()).thenReturn(elementData);

    final var prefillResult = PrefillResult.builder()
        .certificateModel(certificateModel)
        .prefillHandler(prefillHandler)
        .prefill(forifyllnad)
        .prefilledAnswers(List.of(prefillAnswer))
        .build();

    final var result = prefillResult.prefilledElements();

    assertEquals(Set.of(elementData), result);
  }

  @Test
  void shouldReturnJsonReportWithErrors() {
    final var forifyllnad = new Forifyllnad();

    final var error = PrefillError.answerNotFound("ID1");
    final var prefillAnswer = mock(PrefillAnswer.class);
    when(prefillAnswer.getErrors()).thenReturn(List.of(error));

    final var prefillResult = PrefillResult.builder()
        .certificateModel(certificateModel)
        .prefillHandler(prefillHandler)
        .prefill(forifyllnad)
        .prefilledAnswers(List.of(prefillAnswer))
        .build();

    final var json = prefillResult.toJsonReport();

    assertAll(
        () -> assertTrue(json.contains("ID1")),
        () -> assertTrue(json.contains("ANSWER_NOT_FOUND"))
    );
  }

  @Test
  void shouldNotReturnDuplicateElements() {
    final var prefilledAnswer = PrefillAnswer.builder()
        .elementData(ElementData.builder()
            .id(new ElementId("element1"))
            .value(ElementValueText.builder()
                .textId(new FieldId("fieldId"))
                .text("answerText")
                .build())
            .build())
        .build();

    final var duplicatePrefilledAnswer = PrefillAnswer.builder()
        .elementData(ElementData.builder()
            .id(new ElementId("element1"))
            .value(ElementValueText.builder()
                .textId(new FieldId("fieldId"))
                .text("answerText")
                .build())
            .build())
        .build();

    PrefillResult prefillResult = PrefillResult.builder()
        .prefilledAnswers(List.of(prefilledAnswer, duplicatePrefilledAnswer))
        .build();

    assertEquals(1, prefillResult.prefilledElements().size());
  }
}
