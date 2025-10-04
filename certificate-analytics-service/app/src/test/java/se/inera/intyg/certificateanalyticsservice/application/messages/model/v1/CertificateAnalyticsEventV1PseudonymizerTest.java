package se.inera.intyg.certificateanalyticsservice.application.messages.model.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataMessages.draftMessageBuilder;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataMessages.receivedQuestionMessageBuilder;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataMessages.replaceMessageBuilder;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataMessages.sentMessageBuilder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.CertificateAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.infrastructure.pseudonymization.PseudonymizationTokenGenerator;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

@ExtendWith(MockitoExtension.class)
class CertificateAnalyticsEventV1PseudonymizerTest {

  @Mock
  private PseudonymizationTokenGenerator pseudonymizationTokenGenerator;

  @InjectMocks
  private CertificateAnalyticsEventV1Pseudonymizer certificateAnalyticsEventV1Pseudonymizer;

  @Test
  void shallReturnTrueCanPseudonymizeCertificateAnalyticEventV1() {
    final var expected = true;
    final var message = draftMessageBuilder().build();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.canPseudonymize(message);

    assertEquals(expected, actual);
  }

  @Test
  void shallReturnFalseCanPseudonymizeUnknownMessage() {
    final var expected = false;
    final var message = mock(CertificateAnalyticsMessage.class);

    final var actual = certificateAnalyticsEventV1Pseudonymizer.canPseudonymize(message);

    assertEquals(expected, actual);
  }

  @Test
  void shallThrowIfTryingToPseudonymizeUnknownMessage() {
    final var message = mock(CertificateAnalyticsMessage.class);

    assertThrows(IllegalArgumentException.class, () ->
        certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message)
    );
  }

  @Test
  void shallReturnPseudonymizedMessageId() {
    final var expected = "pseudonymized-message-id";
    final var message = draftMessageBuilder().build();

    when(pseudonymizationTokenGenerator.id(message.getMessageId()))
        .thenReturn(expected);

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getId());
  }

  @Test
  void shallReturnPseudonymizedCertificateId() {
    final var expected = "pseudonymized-certificate-id";
    final var message = draftMessageBuilder().build();

    when(pseudonymizationTokenGenerator.certificateId(message.getCertificate().getId()))
        .thenReturn(expected);

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getCertificateId());
  }

  @Test
  void shallReturnCertificateType() {
    final var message = draftMessageBuilder().build();
    final var expected = message.getCertificate().getType();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getCertificateType());
  }

  @Test
  void shallReturnCertificateTypeVersion() {
    final var message = draftMessageBuilder().build();
    final var expected = message.getCertificate().getTypeVersion();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getCertificateTypeVersion());
  }

  @Test
  void shallReturnPseudonymizedCertificatePatientId() {
    final var expected = "pseudonymized-patient-id";
    final var message = draftMessageBuilder().build();

    when(pseudonymizationTokenGenerator.patientId(message.getCertificate().getPatientId()))
        .thenReturn(expected);

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getCertificatePatientId());
  }

  @Test
  void shallReturnCertificateUnitId() {
    final var message = draftMessageBuilder().build();
    final var expected = message.getCertificate().getUnitId();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getCertificateUnitId());
  }

  @Test
  void shallReturnCertificateCareProviderId() {
    final var message = draftMessageBuilder().build();
    final var expected = message.getCertificate().getCareProviderId();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getCertificateCareProviderId());
  }

  @Test
  void shallReturnPseudonymizedCertificateRelationParentId() {
    final var expected = "pseudonymized-certificate-id";
    final var message = replaceMessageBuilder().build();

    when(pseudonymizationTokenGenerator.parentCertificateId(
        message.getCertificate().getParent().getId()))
        .thenReturn(expected);

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getCertificateRelationParentId());
  }

  @Test
  void shallReturnPseudonymizedCertificateRelationParentType() {
    final var message = replaceMessageBuilder().build();
    final var expected = message.getCertificate().getParent().getType();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getCertificateRelationParentType());
  }

  @Test
  void shallReturnEventTimeStamp() {
    final var message = draftMessageBuilder().build();
    final var expected = message.getEvent().getTimestamp();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getEventTimestamp());
  }

  @Test
  void shallReturnEventMessageType() {
    final var message = draftMessageBuilder().build();
    final var expected = message.getEvent().getMessageType();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getEventMessageType());
  }

  @Test
  void shallReturnPseudonymizedEventSessionId() {
    final var expected = "pseudonymized-session-id";
    final var message = draftMessageBuilder().build();

    when(pseudonymizationTokenGenerator.sessionId(message.getEvent().getSessionId()))
        .thenReturn(expected);

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getEventSessionId());
  }

  @Test
  void shallReturnPseudonymizedEventStaffId() {
    final var expected = "pseudonymized-staff-id";
    final var message = draftMessageBuilder().build();

    when(pseudonymizationTokenGenerator.staffId(message.getEvent().getUserId()))
        .thenReturn(expected);

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getEventUserId());
  }

  @Test
  void shallReturnEventUnitId() {
    final var message = draftMessageBuilder().build();
    final var expected = message.getEvent().getUnitId();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getEventUnitId());
  }

  @Test
  void shallReturnEventCareProviderId() {
    final var message = draftMessageBuilder().build();
    final var expected = message.getEvent().getCareProviderId();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getEventCareProviderId());
  }

  @Test
  void shallReturnEventRole() {
    final var message = draftMessageBuilder().build();
    final var expected = message.getEvent().getRole();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getEventRole());
  }

  @Test
  void shallReturnEventOrigin() {
    final var message = draftMessageBuilder().build();
    final var expected = message.getEvent().getOrigin();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getEventOrigin());
  }

  @Test
  void shallReturnRecipient() {
    final var message = sentMessageBuilder().build();
    final var expected = message.getRecipient().getId();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getRecipientId());
  }

  @Test
  void shallReturnPseudonymizedAdministrativeMessageId() {
    final var expected = "pseudonymized-administrative-message-id";
    final var message = receivedQuestionMessageBuilder()
        .build();

    when(pseudonymizationTokenGenerator.messageId(
        message.getMessage().getId()))
        .thenReturn(expected);

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getMessageId());
  }

  @Test
  void shallReturnAdministrativeMessageAnswerId() {
    final var message = receivedQuestionMessageBuilder()
        .build();
    when(pseudonymizationTokenGenerator.messageAnswerId(
        message.getMessage().getAnswerId()))
        .thenReturn(TestDataConstants.HASHED_MESSAGE_ANSWER_ID);

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(TestDataConstants.HASHED_MESSAGE_ANSWER_ID,
        actual.getMessageAnswerId());
  }

  @Test
  void shallReturnAdministrativeMessageReminderId() {
    final var message = receivedQuestionMessageBuilder()
        .build();
    when(pseudonymizationTokenGenerator.messageReminderId(
        message.getMessage().getReminderId()))
        .thenReturn(TestDataConstants.HASHED_MESSAGE_REMINDER_ID);

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(TestDataConstants.HASHED_MESSAGE_REMINDER_ID,
        actual.getMessageReminderId());
  }

  @Test
  void shallReturnAdministrativeMessageType() {
    final var message = receivedQuestionMessageBuilder()
        .build();
    final var expected = message.getMessage().getType();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getMessageType());
  }

  @Test
  void shallReturnAdministrativeMessageSent() {
    final var message = receivedQuestionMessageBuilder()
        .build();
    final var expected = message.getMessage().getSent();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getMessageSent());
  }

  @Test
  void shallReturnAdministrativeMessageLastDateToAnswer() {
    final var message = receivedQuestionMessageBuilder()
        .build();
    final var expected = message.getMessage().getLastDateToAnswer();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getMessageLastDateToAnswer());
  }

  @Test
  void shallReturnAdministrativeMessageQuestionId() {
    final var message = receivedQuestionMessageBuilder()
        .build();
    final var expected = message.getMessage().getQuestionIds();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getMessageQuestionIds());
  }

  @Test
  void shallReturnAdministrativeMessageSender() {
    final var message = receivedQuestionMessageBuilder()
        .build();
    final var expected = message.getMessage().getSender();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getMessageSenderId());
  }

  @Test
  void shallReturnAdministrativeMessageRecipient() {
    final var message = receivedQuestionMessageBuilder()
        .build();
    final var expected = message.getMessage().getRecipient();

    final var actual = certificateAnalyticsEventV1Pseudonymizer.pseudonymize(message);

    assertEquals(expected, actual.getMessageRecipientId());
  }
}