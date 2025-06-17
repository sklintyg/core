package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillErrorType.ANSWER_NOT_FOUND;
import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillErrorType.SUB_ANSWER_NOT_FOUND;

public record PrefillError(PrefillErrorType type, String details) {

  public static PrefillError answerNotFound(String id) {
    return new PrefillError(ANSWER_NOT_FOUND,
        "Answer with id %s not found in certificate model".formatted(id));
  }

  public static PrefillError subAnswerNotFound(String answerId, String subAnswerId) {
    return new PrefillError(SUB_ANSWER_NOT_FOUND,
        "Sub-answer with id %s not found in answer with id %s".formatted(subAnswerId, answerId));
  }

  public static PrefillError wrongNumberOfAnswers(int expected, int actual) {
    return new PrefillError(PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
        "Expected %d answers but got %d".formatted(expected, actual));
  }

  public static PrefillError technicalError(String s) {
    return new PrefillError(PrefillErrorType.TECHNICAL_ERROR,
        "A technical error occurred %s".formatted(s));
  }

  public static PrefillError invalidDateFormat(String s) {
    return new PrefillError(PrefillErrorType.INVALID_DATE_FORMAT,
        "Invalid date format: %s".formatted(s));
  }
}
