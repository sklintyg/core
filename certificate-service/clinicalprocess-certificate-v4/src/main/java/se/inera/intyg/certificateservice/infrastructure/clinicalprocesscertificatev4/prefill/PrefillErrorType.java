package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

public enum PrefillErrorType {
  ANSWER_NOT_FOUND,
  WRONG_NUMBER_OF_ANSWERS,
  TECHNICAL_ERROR,
  INVALID_FORMAT,
  INVALID_SUB_ANSWER_ID,
  INVALID_BOOLEAN_VALUE,
  MISSING_CONVERTER,
  INVALID_DIAGNOSIS_CODE
}