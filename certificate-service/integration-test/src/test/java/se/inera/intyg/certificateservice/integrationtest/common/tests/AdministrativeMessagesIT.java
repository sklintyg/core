package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ALVE_REACT_ALFREDSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingAnswerMessageBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingQuestionMessageBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingReminderMessageBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataQuestionDTO.questionDTOBuilder;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customHandleMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customSaveMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultCreateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultDeleteAnswerRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultDeleteMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultHandleMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultRenewCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultReplaceCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultRevokeCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSaveAnswerRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSendAnswerRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSendMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.messageId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.messages;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.metadata;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.question;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.questions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class AdministrativeMessagesIT extends BaseIntegrationIT {


  @Test
  @DisplayName("Användare skall kunna skapa administrativa frågor")
  void shallCreateAdministrativeQuestion() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    final var response = api().createMessage(defaultCreateMessageRequest(),
        certificateId(testCertificates));

    assertEquals(200, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Användare skall kunna uppdatera administrativa frågor")
  void shallSaveAdministrativeQuestion() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    final var createdQuestion = question(
        api().createMessage(defaultCreateMessageRequest(),
            certificateId(testCertificates)).getBody());

    api().saveMessage(customSaveMessageRequest()
            .question(questionDTOBuilder()
                .certificateId(certificateId(testCertificates))
                .build())
            .build(),
        messageId(createdQuestion));

    final var questions = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    assertEquals(TestDataMessageConstants.CONTENT, questions.get(0).getMessage());
  }

  @Test
  @DisplayName("Användare skall kunna skicka administrativa frågor")
  void shallSendAdministrativeQuestion() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    final var createdQuestion = question(api().createMessage(defaultCreateMessageRequest(),
        certificateId(testCertificates)).getBody());

    api().sendMessage(defaultSendMessageRequest(), messageId(createdQuestion));

    final var questions = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    assertNotNull(questions.get(0).getSent(),
        "Expected administrative question to have a sent timestamp");
  }

  @Test
  @DisplayName("Användare skall kunna radera utkast för administrativa frågor")
  void shallDeleteAdministrativeQuestionDraft() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    final var createdQuestion = question(api().createMessage(defaultCreateMessageRequest(),
        certificateId(testCertificates)).getBody());

    api().sendMessage(defaultSendMessageRequest(), messageId(createdQuestion));

    api().saveMessage(customSaveMessageRequest()
            .question(questionDTOBuilder()
                .certificateId(certificateId(testCertificates))
                .build())
            .build(),
        messageId(createdQuestion));

    api().deleteMessage(defaultDeleteMessageRequest(), messageId(createdQuestion));

    final var questions = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    assertNull(questions.get(0).getAnswer(),
        "Expected administrative question draft to be deleted (null)");
  }

  @Test
  @DisplayName("Användare skall kunna skapa svar på administrativa frågor")
  void shallCreateAnswerForAdministrativeQuestion() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));
    api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build());

    final var questionsBefore = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    api().saveAnswer(defaultSaveAnswerRequest(), messageId(questionsBefore.get(0)));

    final var questionsAfter = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    assertNotNull(questionsAfter.get(0).getAnswer(),
        "Should delete answer draft when certificate i revoked"
    );
  }

  @Test
  @DisplayName("Användare skall kunna skicka svar på administrativa frågor")
  void shallSendAnswerForAdministrativeQuestion() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));
    api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build());

    final var questionsBefore = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    api().saveAnswer(defaultSaveAnswerRequest(), messageId(questionsBefore.get(0)));
    api().sendAnswer(defaultSendAnswerRequest(), messageId(questionsBefore.get(0)));

    final var questionsAfter = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    assertNotNull(questionsAfter.get(0).getAnswer().getSent(),
        "Expected administrative answer to have a sent timestamp"
    );
  }

  @Test
  @DisplayName("Användare skall kunna radera utkast av svar på administrativa frågor")
  void shallDeleteAnswerDraftForAdministrativeQuestion() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));
    api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build());

    final var questionsBefore = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    api().saveAnswer(defaultSaveAnswerRequest(), messageId(questionsBefore.get(0)));
    api().deleteAnswer(defaultDeleteAnswerRequest(), messageId(questionsBefore.get(0)));

    final var questionsAfter = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    assertNull(questionsAfter.get(0).getAnswer().getSent(),
        "Expected administrative answer to be deleted (null)"
    );
  }

  @Test
  @DisplayName("Intyg skall kunna ta emot administrativa frågor")
  void shallReturn200IfQuestionCanBeReceived() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build()
    );

    final var questions = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    assertEquals(1, questions.size());
  }

  @Test
  @DisplayName("Intyg skall kunna ta emot svar på administrativa frågor")
  void shallReceiveAnswerToAdministrativeQuestion() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    final var createdQuestion = question(api().createMessage(defaultCreateMessageRequest(),
        certificateId(testCertificates)).getBody());

    api().saveMessage(customSaveMessageRequest()
            .question(questionDTOBuilder()
                .certificateId(certificateId(testCertificates))
                .build())
            .build(),
        messageId(createdQuestion));

    api().sendMessage(defaultSendMessageRequest(), messageId(createdQuestion));

    api().receiveMessage(incomingAnswerMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .answerMessageId(messageId(createdQuestion))
        .build());

    final var questions = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    assertEquals(TestDataMessageConstants.ANSWER_MESSAGE_ID, questions.get(0).getAnswer().getId());
  }

  @Test
  @DisplayName("Intyg skall kunna ta emot påminnelse för administrativa frågor")
  void shallReturn200IfReminderOnAdministrativeMessageCanBeReceived() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build());

    api().receiveMessage(incomingReminderMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build());

    final var messagesForCertificate = api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates));

    assertEquals(1, questions(messagesForCertificate.getBody()).get(0).getReminders().size());
  }

  @Test
  @DisplayName("Utkast skall inte kunna ta emot administrativa frågor - 403 (FORBIDDEN)")
  void shallReturn403IfIncomingQuestionOnCertificateIsDraft() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion()));

    final var response = api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Makulerade intyg skall inte kunna ta emot administrativa frågor - 403 (FORBIDDEN)")
  void shallReturn403IfIncomingQuestionOnRevokedCertificate() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().revokeCertificate(defaultRevokeCertificateRequest(), certificateId(testCertificates));

    final var response = api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Intyg skall inte kunna ta emot administrativa frågor på fel patient - 403 (FORBIDDEN)")
  void shallReturn403IfQuestionIsOnDifferentPatient() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    final var response = api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .personId(ALVE_REACT_ALFREDSSON_DTO.getId())
        .build());

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Administrativ fråga skall inte sättas som hanterad när ersättande intyg signeras")
  void shallNotSetMessageAsHandledWhenReplacingCertificateIsSigned() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    final var response = api().replaceCertificate(
        defaultReplaceCertificateRequest(), certificateId(testCertificates));

    api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build());

    api().signCertificate(defaultSignCertificateRequest(), certificateId(response.getBody()),
        metadata(certificate(response.getBody())).getVersion());

    final var questions = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    assertFalse(questions.get(0).isHandled(),
        "Expected administrative message not to be handled"
    );
  }

  @Test
  @DisplayName("Administrativ fråga skall inte sättas som hanterad när förnyande intyg signeras")
  void shallNotSetMessageAsHandledWhenRenewingCertificateIsSigned() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    final var response = api().renewCertificate(
        defaultRenewCertificateRequest(), certificateId(testCertificates));

    api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build());

    api().signCertificate(defaultSignCertificateRequest(), certificateId(response.getBody()),
        metadata(certificate(response.getBody())).getVersion());

    final var questions = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    assertFalse(questions.get(0).isHandled(),
        "Expected administrative message not to be handled"
    );
  }

  @Test
  @DisplayName("Administrativ fråga skall sättas som hanterad när intyg makuleras")
  void shallSetMessageAsHandledWhenCertificateIsRevoked() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build());

    api().revokeCertificate(defaultRevokeCertificateRequest(), certificateId(testCertificates));

    final var questions = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    assertTrue(questions.get(0).isHandled(),
        "Expected administrative message to be handled"
    );
  }

  @Test
  @DisplayName("Administrativ fråga ska kunna sättas som hanterad av användaren")
  void shallSetAdministrativeMessageAsHandledWhenHandledByUser() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build());

    final var questions = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());
    final var question = question(api().handleMessage(defaultHandleMessageRequest(),
        messageId(questions.get(0))).getBody());

    assertTrue(question.isHandled(), "Expected administrative message not to be handled");
  }

  @Test
  @DisplayName("Administrativ fråga på makulerat intyg ska inte kunna sättas till ej hanterad av användaren - 403 (FORBIDDEN)")
  void shallReturn403WhenUnhandlingQuestionOnRevokedCertificate() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build());

    api().revokeCertificate(defaultRevokeCertificateRequest(), certificateId(testCertificates));

    final var questions = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    final var response = api().handleMessage(
        customHandleMessageRequest()
            .handled(false)
            .build(),
        messageId(questions.get(0))
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Administrativ fråga skall sättas som hanterad när den besvaras av användare")
  void shallSetAdministrativeQuestionsToHandledWhenAnswered() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build());

    final var questionsBefore = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    api().saveAnswer(defaultSaveAnswerRequest(), messageId(questionsBefore.get(0)));
    api().sendAnswer(defaultSendAnswerRequest(), messageId(questionsBefore.get(0)));

    final var questionsAfter = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    assertTrue(questionsAfter.get(0).isHandled(),
        "Should return true for handled when question has been answered"
    );
  }

  @Test
  @DisplayName("Utkast för svar på administrativ fråga skall raderas när intyget makuleras")
  void shallDeleteAnswerDraftIfCertificateIsRevoked() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    api().receiveMessage(incomingQuestionMessageBuilder()
        .certificateId(certificateId(testCertificates))
        .build());

    final var questionsBefore = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    api().saveAnswer(defaultSaveAnswerRequest(), messageId(questionsBefore.get(0)));
    api().revokeCertificate(defaultRevokeCertificateRequest(), certificateId(testCertificates));

    final var questionsAfter = questions(api().getMessagesForCertificate(
        defaultGetCertificateMessageRequest(), certificateId(testCertificates)).getBody());

    assertNull(questionsAfter.get(0).getAnswer(),
        "Should delete answer draft when certificate i revoked"
    );
  }

  @Test
  @DisplayName("Xml för ärendekommunikation skall gå att hämta")
  void shallReturnMessageXml() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED));

    api().sendCertificate(defaultSendCertificateRequest(), certificateId(testCertificates));

    final var createdQuestion = question(api().createMessage(defaultCreateMessageRequest(),
        certificateId(testCertificates)).getBody());

    api().sendMessage(defaultSendMessageRequest(), messageId(createdQuestion));

    final var response = internalApi().getMessages(
        certificateId(testCertificates)
    );

    final var messages = messages(response.getBody());

    final var xml = internalApi().getMessageXml(messages.get(0).getId()).getBody().getXml();

    assertNotNull(xml);
  }
}
