package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillErrorType.ANSWER_NOT_FOUND;
import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillErrorType.SUB_ANSWER_NOT_FOUND;
import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillErrorType.UNMARSHALL_ERROR;
import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillErrorType.WRONG_NUMBER_OF_ANSWERS;

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
    return new PrefillError(WRONG_NUMBER_OF_ANSWERS,
        "Expected %d answers but got %d".formatted(expected, actual));
  }

  public static PrefillError technicalError(String s) {
    return new PrefillError(PrefillErrorType.TECHNICAL_ERROR,
        "A technical error occurred %s".formatted(s));
  }

  public static PrefillError wrongConfigurationType() {
    return technicalError("Wrong configuration type");
  }

  public static PrefillError invalidFormat() {
    return new PrefillError(PrefillErrorType.INVALID_FORMAT, "Invalid format");
  }

  public static PrefillError subAnswersNotFound(String answerId) {
    return new PrefillError(SUB_ANSWER_NOT_FOUND,
        "Sub-answer expected in answer with id %s".formatted(answerId));
  }

  public static PrefillError unmarshallingError(String answerId, String subAnswerId) {
    return new PrefillError(UNMARSHALL_ERROR,
        "Content of sub-answer with id %s in answer with id %s could not be unmarshalled".formatted(
            answerId,
            subAnswerId));
  }

  public static PrefillError missingConverter(String converter) {
    return new PrefillError(PrefillErrorType.MISSING_CONVERTER,
        "No converter found for %s".formatted(converter));
  }


  public static PrefillError wrongNumberOfAnswers(String answerId, int expected, int actual) {
    return new PrefillError(WRONG_NUMBER_OF_ANSWERS,
        "Expected %d answers but got %d for answer %s".formatted(expected, actual, answerId));
  }

  public static PrefillError wrongNumberOfSubAnswers(String answerId, int expected, int actual) {
    return new PrefillError(WRONG_NUMBER_OF_ANSWERS,
        "Expected %d sub-answers but got %d for answer %s".formatted(expected, actual, answerId));
  }

  public static PrefillError duplicateAnswer(String answerId) {
    return new PrefillError(WRONG_NUMBER_OF_ANSWERS,
        "Multiple occurrences for answer %s".formatted(answerId));
  }
}