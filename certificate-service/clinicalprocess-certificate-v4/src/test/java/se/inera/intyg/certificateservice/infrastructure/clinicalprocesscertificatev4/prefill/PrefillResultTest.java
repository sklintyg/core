package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@ExtendWith(MockitoExtension.class)
class PrefillResultTest {

  @Mock
  private CertificateModel certificateModel;
  @Mock
  private PrefillHandler prefillHandler;


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