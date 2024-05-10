package se.inera.intyg.certificateservice.application.common.dto;

import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.CREATE;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.DELETE;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.PRINT;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.READ;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.RENEW;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.REPLACE;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.REPLACE_CONTINUE;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.REVOKE;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.SEND;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.SIGN;
import static se.inera.intyg.certificateservice.domain.action.model.CertificateActionType.UPDATE;

import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;

public enum ResourceLinkTypeDTO {
  EDIT_CERTIFICATE,
  READ_CERTIFICATE,
  REMOVE_CERTIFICATE,
  FORWARD_CERTIFICATE,
  FORWARD_QUESTION,
  READY_FOR_SIGN,
  SIGN_CERTIFICATE,
  SEND_CERTIFICATE,
  REVOKE_CERTIFICATE,
  REPLACE_CERTIFICATE,
  REPLACE_CERTIFICATE_CONTINUE,
  RENEW_CERTIFICATE,
  PRINT_CERTIFICATE,
  COPY_CERTIFICATE,
  COPY_CERTIFICATE_CONTINUE,
  FMB,
  QUESTIONS,
  QUESTIONS_NOT_AVAILABLE,
  CREATE_QUESTIONS,
  ANSWER_QUESTION,
  HANDLE_QUESTION,
  COMPLEMENT_CERTIFICATE,
  CANNOT_COMPLEMENT_CERTIFICATE,
  CREATE_CERTIFICATE_FROM_TEMPLATE,
  CREATE_CERTIFICATE_FROM_CANDIDATE,
  CREATE_CERTIFICATE_FROM_CANDIDATE_WITH_MESSAGE,
  ACCESS_SEARCH_CREATE_PAGE,
  ACCESS_DRAFT_LIST,
  ACCESS_QUESTION_LIST,
  ACCESS_SIGNED_CERTIFICATES_LIST,
  LOG_OUT,
  CREATE_CERTIFICATE,
  CHOOSE_UNIT,
  CHANGE_UNIT,
  PRIVATE_PRACTITIONER_PORTAL,
  NAVIGATE_BACK_BUTTON,
  WARNING_NORMAL_ORIGIN,
  SUBSCRIPTION_WARNING,
  WARNING_DODSBEVIS_INTEGRATED,
  CREATE_DODSBEVIS_CONFIRMATION,
  CREATE_LUAENA_CONFIRMATION,
  WARNING_LUAENA_INTEGRATED,
  SIGN_CERTIFICATE_CONFIRMATION,
  DISPLAY_PATIENT_ADDRESS_IN_CERTIFICATE,
  MISSING_RELATED_CERTIFICATE_CONFIRMATION,
  SHOW_RELATED_CERTIFICATE,
  SRS_FULL_VIEW,
  SRS_MINIMIZED_VIEW;

  public static ResourceLinkTypeDTO toResourceLinkType(CertificateActionType type) {
    return switch (type) {
      case CREATE -> CREATE_CERTIFICATE;
      case READ -> READ_CERTIFICATE;
      case UPDATE -> EDIT_CERTIFICATE;
      case DELETE -> REMOVE_CERTIFICATE;
      case SIGN -> SIGN_CERTIFICATE;
      case SEND -> SEND_CERTIFICATE;
      case PRINT -> PRINT_CERTIFICATE;
      case REVOKE -> REVOKE_CERTIFICATE;
      case REPLACE -> REPLACE_CERTIFICATE;
      case REPLACE_CONTINUE -> REPLACE_CERTIFICATE_CONTINUE;
      case RENEW -> RENEW_CERTIFICATE;
    };
  }

  public CertificateActionType toCertificateActionType() {
    return switch (this) {
      case CREATE_CERTIFICATE -> CREATE;
      case READ_CERTIFICATE -> READ;
      case EDIT_CERTIFICATE -> UPDATE;
      case REMOVE_CERTIFICATE -> DELETE;
      case SIGN_CERTIFICATE -> SIGN;
      case SEND_CERTIFICATE -> SEND;
      case PRINT_CERTIFICATE -> PRINT;
      case REVOKE_CERTIFICATE -> REVOKE;
      case REPLACE_CERTIFICATE -> REPLACE;
      case REPLACE_CERTIFICATE_CONTINUE -> REPLACE_CONTINUE;
      case RENEW_CERTIFICATE -> RENEW;
      default -> throw new IllegalArgumentException(
          "Cannot convert %s to certificate action type!".formatted(this)
      );
    };
  }
}
