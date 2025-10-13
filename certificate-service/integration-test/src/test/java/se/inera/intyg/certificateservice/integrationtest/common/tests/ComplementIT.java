package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ALVE_REACT_ALFREDSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementDTOBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customHandleMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultComplementCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultHandleMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultReplaceCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultRevokeCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.messageId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.questions;

import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class ComplementIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Intyg skall kunna ta emot kompletteringsbegäran")
  void shallReturn200IfComplementCanBeReceived() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Intyg skall kunna ta emot påminnelse")
  void shallReturn200IfReminderOnComplementCanBeReceived() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(
                certificateId(testCertificates)
            )
            .build()
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(certificateId(testCertificates))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(questionId())
                .build()))
            .build()
    );

    final var messagesForCertificate = api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    assertEquals(1, Objects.requireNonNull(messagesForCertificate.getBody()).getQuestions().size());
  }

  @Test
  @DisplayName("Intyg som har kompletteringsbegäran ska kunna kompletteras")
  void shallReturnCertificateIfComplementingCertificateWithComplements() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
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

    final var response = api().complementCertificate(
        defaultComplementCertificateRequest(),
        certificateId(testCertificates)
    );

    assertEquals(200, response.getStatusCode().value());
    assertNotNull(Objects.requireNonNull(response.getBody()).getCertificate());
  }

  @Test
  @DisplayName("Intyg som har påbörjad komplettering ska inte kunna kompletteras - 403 (FORBIDDEN)")
  void shallReturn403IfDraftComplementAlreadyExists() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(
                certificateId(testCertificates)
            )
            .build()
    );

    api().complementCertificate(
        defaultComplementCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().complementCertificate(
        defaultComplementCertificateRequest(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Intyg som inte har kompletteringsbegäran ska inte kunna kompletteras - 403 (FORBIDDEN)")
  void shallReturn403IfNoComplementsFromRecipient() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().complementCertificate(
        defaultComplementCertificateRequest(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Utkast skall inte kunna ta emot kompletteringsbegäran - 403 (FORBIDDEN) ")
  void shallReturn403IfCertificateIsDraft() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(
                certificateId(testCertificates)
            )
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Makulerade intyg skall inte kunna ta emot kompletteringsbegäran - 403 (FORBIDDEN)")
  void shallReturn403IfCertificateIsRevoked() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().revokeCertificate(
        defaultRevokeCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(
                certificateId(testCertificates)
            )
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Intyg skall inte kunna ta emot kompletteringsbegäran på fel patient - 403 (FORBIDDEN)")
  void shallReturn403IfComplementIsOfDifferentPatient() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(
                certificateId(testCertificates)
            )
            .personId(ALVE_REACT_ALFREDSSON_DTO.getId())
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Kompletteringsbegäran skall sättas som hanterad när ersättande intyg signeras")
  void shallSetComplementAsHandledWhenComplementingCertificateIsSigned() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = api().replaceCertificate(
        defaultReplaceCertificateRequest(),
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

    api().signCertificate(
        defaultSignCertificateRequest(),
        certificateId(response.getBody()),
        Objects.requireNonNull(certificate(response.getBody())).getMetadata().getVersion()
    );

    final var messagesForCertificate = api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    assertTrue(questions(messagesForCertificate.getBody()).get(0).isHandled(),
        "Expected that complement message was handled, but it was not!"
    );
  }

  @Test
  @DisplayName("Kompletteringsbegäran ska kunna sättas som hanterad av användaren")
  void shallSetComplementAsHandledWhenHandledByUser() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
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

    final var messagesForCertificate = api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    final var questions = questions(messagesForCertificate.getBody());

    final var response = api().handleMessage(
        defaultHandleMessageRequest(),
        messageId(questions.getFirst())
    );

    assertTrue(Objects.requireNonNull(response.getBody()).getQuestion().isHandled(),
        "Expected that complement message was handled, but it was not!"
    );
  }

  @Test
  @DisplayName("Kompletteringsbegäran ska inte kunna byta status till ej hanterad av användaren")
  void shallNotSetComplementAsUnhandled() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
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

    final var messagesForCertificate = api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(),
        certificateId(testCertificates)
    );

    final var questions = questions(messagesForCertificate.getBody());

    api().handleMessage(
        defaultHandleMessageRequest(),
        messageId(questions.getFirst())
    );

    final var response = api().handleMessage(
        customHandleMessageRequest()
            .handled(false)
            .build(),
        messageId(questions.getFirst())
    );

    assertTrue(Objects.requireNonNull(response.getBody()).getQuestion().isHandled(),
        "Expected that complement message was handled, but it was not!"
    );
  }

  @Test
  @DisplayName("Om intyget redan kompletterats så ska felkod 403 (FORBIDDEN) returneras vid försök att ersätta")
  void shallReturn403IfCertificateAlreadyIsComplemented() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
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

    final var complement = api().complementCertificate(
        defaultComplementCertificateRequest(),
        certificateId(testCertificates)
    );

    api().signCertificate(
        defaultSignCertificateRequest(),
        complement.getBody().getCertificate().getMetadata().getId(),
        complement.getBody().getCertificate().getMetadata().getVersion()
    );

    final var response = api().replaceCertificate(
        defaultReplaceCertificateRequest(),
        certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }
}
