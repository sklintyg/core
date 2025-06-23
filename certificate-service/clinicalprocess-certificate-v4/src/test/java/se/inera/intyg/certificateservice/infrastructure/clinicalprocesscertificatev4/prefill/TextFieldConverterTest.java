package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionAnnanGrundForMedicinsktUnderlag.questionAnnanGrundForMedicinsktUnderlag;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

class TextFieldConverterTest {

  private static final String SUB_ANSWER_ID = "1.3";
  private static final String ANSWER_ID = "1";
  private final TextFieldConverter textFieldConverter = new TextFieldConverter();
  private static final String ANSWER_VALUE = "This is the answer";
  private static final Svar answer = createSvar();
  private static final Delsvar subAnswer = getSubAnswer(ANSWER_VALUE);

  @Test
  void shallPrefillSubAnswer() {
    var expected = PrefillAnswer.builder()
        .elementData(ElementData.builder()
            .id(new ElementId(SUB_ANSWER_ID))
            .value(ElementValueText.builder()
                .textId(new FieldId(SUB_ANSWER_ID))
                .text(ANSWER_VALUE)
                .build())
            .build())
        .build();

    var result = textFieldConverter.prefillSubAnswer(List.of(subAnswer),
        questionAnnanGrundForMedicinsktUnderlag());

    assertEquals(expected, result);
  }

  @Test
  void shallReturnErrorIfTooManySubAnswers() {
    var result = textFieldConverter.prefillSubAnswer(List.of(subAnswer, subAnswer),
        questionAnnanGrundForMedicinsktUnderlag());

    assertFalse(result.getErrors().isEmpty());
  }

  @Test
  void shallReturnErrorIfNoSubAnswers() {
    var result = textFieldConverter.prefillSubAnswer(Collections.emptyList(),
        questionAnnanGrundForMedicinsktUnderlag());

    assertFalse(result.getErrors().isEmpty());
  }

  @Test
  void shallReturnErrorIfSubAnswerContentIsEmpty() {
    var result = textFieldConverter.prefillSubAnswer(List.of(getSubAnswer(null)),
        questionAnnanGrundForMedicinsktUnderlag());

    assertFalse(result.getErrors().isEmpty());
  }

  @Test
  void shallReturnErrorIfTooManyAnswers() {
    var result = textFieldConverter.prefillAnswer(List.of(answer, answer),
        questionAnnanGrundForMedicinsktUnderlag());

    assertFalse(result.getErrors().isEmpty());
  }

  private static Svar createSvar() {
    var answer = new Svar();
    answer.setId(ANSWER_ID);
    final var subAnswer = getSubAnswer(ANSWER_VALUE);
    answer.getDelsvar().add(subAnswer);
    return answer;
  }

  private static Delsvar getSubAnswer(String content) {
    var subAnswer = new Delsvar();
    subAnswer.setId(SUB_ANSWER_ID);
    subAnswer.getContent().add(content);
    return subAnswer;
  }

}