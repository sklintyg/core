package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATLAS_REACT_ABRAHAMSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementDTOBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customForwardCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultForwardCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.questions;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class ForwardCertificateMessageIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Om intyget är signerat och skickat skall ärendekommunikation gå att vidarebefordra")
  void shallUpdateCertificateWithForwardedTrueIfStatusOnCertificateIsSignedAndSent() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    api().forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateMessageRequest()
    );

    final var messagesForCertificate = api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    assertTrue(
        questions(messagesForCertificate.getBody()).get(0).isForwarded(),
        "Should return true after message is forwarded!"
    );
  }

  @Test
  @DisplayName("Om intyget är signerat men inte skickat skall felkod 403 (FORBIDDEN) returneras")
  void shallReturnForbiddenIfCertificateIsSignedButNotSent() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateMessageRequest()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Läkare - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall ärendekommunikation gå att vidarebefordra")
  void shallUpdateCertificateMessagesIfUserIsDoctorAndPatientIsProtectedPerson() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), CertificateStatusTypeDTO.SIGNED)
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .personId(ANONYMA_REACT_ATTILA_DTO.getId())
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    api().forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateMessageRequest()
    );

    final var messagesForCertificate = api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    assertTrue(
        questions(messagesForCertificate.getBody()).get(0).isForwarded(),
        "Should return true after message is forwarded!"
    );
  }

  @Test
  @DisplayName("Vårdadmin - Om intyget är utfärdat på en patient som har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
  void shallReturnForbiddenIfPatientIsProtectedPersonAndUserCareAdmin() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), CertificateStatusTypeDTO.SIGNED)
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateMessageRequest()
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om patienten är avliden skall meddelandet vidarebefordras")
  void shallReturnForbiddenIfPatientIsDeceased() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), CertificateStatusTypeDTO.SIGNED)
            .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
            .build()
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .personId(ATLAS_REACT_ABRAHAMSSON_DTO.getId())
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateMessageRequest()
    );

    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om användaren är blockerad skall felkod 403 (FORBIDDEN) returneras")
  void shallReturnForbiddenIfUserIsBlocked() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateMessageRequest()
            .user(
                ajlaDoktorDtoBuilder()
                    .blocked(Boolean.TRUE)
                    .build()
            )
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma mottagning skall ärendekommunikation gå att vidarebefordra")
  void shallUpdateCertificateToForwardedIfCertificateOnSameUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    api().forwardCertificate(
        certificateId(testCertificates),
        defaultForwardCertificateMessageRequest()
    );

    final var messagesForCertificate = api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    assertTrue(
        questions(messagesForCertificate.getBody()).get(0).isForwarded(),
        "Should return true after message is forwarded!"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på mottagning men på samma vårdenhet skall ärendekommunikation gå att vidarebefordra")
  void shallUpdateCertificateToForwardedIfIssuedOnSameCareUnitDifferentSubUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    api().forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateMessageRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var messagesForCertificate = api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    assertTrue(
        questions(messagesForCertificate.getBody()).get(0).isForwarded(),
        "Should return true after message is forwarded!"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på samma vårdenhet skall ärendekommunikation gå att vidarebefordra")
  void shallUpdateCertificateToForwardedIfIssuedOnSameCareUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    api().forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateMessageRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var messagesForCertificate = api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    assertTrue(
        questions(messagesForCertificate.getBody()).get(0).isForwarded(),
        "Should return true after message is forwarded!"
    );
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan mottagning skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfUnitIsSubUnitAndNotOnSameUnit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().forwardCertificate(
        certificateId(testCertificates),
        customForwardCertificateMessageRequest()
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }
}
