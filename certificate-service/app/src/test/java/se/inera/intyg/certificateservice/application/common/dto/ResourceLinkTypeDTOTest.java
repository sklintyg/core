package se.inera.intyg.certificateservice.application.common.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;

class ResourceLinkTypeDTOTest {

  @Nested
  class ResourceLinkType {

    @Test
    void shallReturnResourceLinkTypeDTOCreateCertificate() {
      assertEquals(ResourceLinkTypeDTO.CREATE_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.CREATE)
      );
    }

    @Test
    void shallReturnResourceLinkTypeDTOReadCertificate() {
      assertEquals(ResourceLinkTypeDTO.READ_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.READ)
      );
    }

    @Test
    void shallReturnResourceLinkTypeDTOEditCertificate() {
      assertEquals(ResourceLinkTypeDTO.EDIT_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.UPDATE)
      );
    }

    @Test
    void shallReturnResourceLinkTypeDTORemoveCertificate() {
      assertEquals(ResourceLinkTypeDTO.REMOVE_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.DELETE)
      );
    }

    @Test
    void shallReturnResourceLinkTypeDTOSignCertificate() {
      assertEquals(ResourceLinkTypeDTO.SIGN_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.SIGN)
      );
    }

    @Test
    void shallReturnResourceLinkTypeDTOPrintCertificate() {
      assertEquals(ResourceLinkTypeDTO.PRINT_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.PRINT)
      );
    }

    @Test
    void shallReturnResourceLinkTypeDTOSendCertificate() {
      assertEquals(ResourceLinkTypeDTO.SEND_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.SEND)
      );
    }

    @Test
    void shallReturnResourceLinkTypeDTORevokeCertificate() {
      assertEquals(ResourceLinkTypeDTO.REVOKE_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.REVOKE)
      );
    }

    @Test
    void shallReturnResourceLinkTypeDTOReplaceCertificate() {
      assertEquals(ResourceLinkTypeDTO.REPLACE_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.REPLACE)
      );
    }

    @Test
    void shallReturnResourceLinkTypeDTOReplaceCertificateContinue() {
      assertEquals(ResourceLinkTypeDTO.REPLACE_CERTIFICATE_CONTINUE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.REPLACE_CONTINUE)
      );
    }

    @Test
    void shallReturnResourceLinkTypeDTORenewCertificate() {
      assertEquals(ResourceLinkTypeDTO.RENEW_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.RENEW)
      );
    }

    @Test
    void shallReturnResourceLinkTypeDTOSendAfterSign() {
      assertEquals(ResourceLinkTypeDTO.SEND_AFTER_SIGN_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.SEND_AFTER_SIGN));
    }

    @Test
    void shallReturnResourceLinkTypeDTOSendAfterComplement() {
      assertEquals(ResourceLinkTypeDTO.SEND_AFTER_SIGN_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.SEND_AFTER_COMPLEMENT));
    }

    @Test
    void shallReturnResourceLinkTypeDTOCannotComplementCertificate() {
      assertEquals(ResourceLinkTypeDTO.CANNOT_COMPLEMENT_CERTIFICATE_ONLY_MESSAGE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.CANNOT_COMPLEMENT));
    }

    @Test
    void shallReturnResourceLinkTypeDTOForwardQuestion() {
      assertEquals(ResourceLinkTypeDTO.FORWARD_QUESTION,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.FORWARD_MESSAGE));
    }

    @Test
    void shallReturnResourceLinkTypeDTOHandleQuestion() {
      assertEquals(ResourceLinkTypeDTO.HANDLE_QUESTION,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.HANDLE_COMPLEMENT));
    }

    @Test
    void shallReturnResourceLinkTypeDTOComplement() {
      assertEquals(ResourceLinkTypeDTO.COMPLEMENT_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.COMPLEMENT));
    }

    @Test
    void shallReturnResourceLinkTypeDTOCreateQuestions() {
      assertEquals(ResourceLinkTypeDTO.CREATE_QUESTIONS,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.CREATE_MESSAGE));
    }

    @Test
    void shallReturnResourceLinkTypeDTOAnswerQuestions() {
      assertEquals(ResourceLinkTypeDTO.ANSWER_QUESTION,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.ANSWER_MESSAGE));
    }

    @Test
    void shallThrowIfResourceLinkTypeDTOSaveMessage() {
      assertThrows(IllegalArgumentException.class, () ->
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.SAVE_MESSAGE));
    }

    @Test
    void shallThrowIfResourceLinkTypeDTODeleteMessage() {
      assertThrows(IllegalArgumentException.class, () ->
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.DELETE_MESSAGE));
    }

    @Test
    void shallThrowIfResourceLinkTypeDTOSendMessage() {
      assertThrows(IllegalArgumentException.class, () ->
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.SEND_MESSAGE));
    }

    @Test
    void shallThrowIfResourceLinkTypeDTOAccessForRoles() {
      assertThrows(IllegalArgumentException.class, () ->
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.LIST_CERTIFICATE_TYPE));
    }

    @Test
    void shallThrowIfResourceLinkTypeDTOSaveAnswer() {
      assertThrows(IllegalArgumentException.class, () ->
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.SAVE_ANSWER));
    }

    @Test
    void shallThrowIfResourceLinkTypeDTODeleteAnswer() {
      assertThrows(IllegalArgumentException.class, () ->
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.DELETE_ANSWER));
    }

    @Test
    void shallThrowIfResourceLinkTypeDTOSendAnswer() {
      assertThrows(IllegalArgumentException.class, () ->
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.SEND_ANSWER));
    }

    @Test
    void shallReturnResourceLinkTypeDTOForwardCertificate() {
      assertEquals(ResourceLinkTypeDTO.FORWARD_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.FORWARD_CERTIFICATE));
    }

    @Test
    void shallReturnResourceLinkTypeDTOReadyForSign() {
      assertEquals(ResourceLinkTypeDTO.READY_FOR_SIGN,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.READY_FOR_SIGN));
    }

    @Test
    void shallReturnResourceLinkTypeDTOForwardCertificateFromList() {
      assertEquals(ResourceLinkTypeDTO.FORWARD_CERTIFICATE_FROM_LIST,
          ResourceLinkTypeDTO.toResourceLinkType(
              CertificateActionType.FORWARD_CERTIFICATE_FROM_LIST));
    }

    @Test
    void shallReturnResourceLinkTypeDTOFMB() {
      assertEquals(ResourceLinkTypeDTO.FMB,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.FMB));
    }

    @Test
    void shallReturnResourceLinkTypeDTOSrsFullView() {
      assertEquals(ResourceLinkTypeDTO.SRS_FULL_VIEW,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.SRS_DRAFT));
    }

    @Test
    void shallReturnResourceLinkTypeDTOSrsMinimizedView() {
      assertEquals(ResourceLinkTypeDTO.SRS_MINIMIZED_VIEW,
          ResourceLinkTypeDTO.toResourceLinkType(CertificateActionType.SRS_SIGNED));
    }

    @Test
    void shallReturnResourceLinkTypeDTOCreateCertificateFromCertificate() {
      assertEquals(ResourceLinkTypeDTO.CREATE_CERTIFICATE_FROM_TEMPLATE,
          ResourceLinkTypeDTO.toResourceLinkType(
              CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE));
    }

    @Test
    void shallReturnResourceLinkTypeDTOInactiveCertificateFromCertificate() {
      assertEquals(ResourceLinkTypeDTO.INACTIVE_CERTIFICATE,
          ResourceLinkTypeDTO.toResourceLinkType(
              CertificateActionType.INACTIVE_CERTIFICATE_MODEL));
    }
  }
}