package se.inera.intyg.certificateanalyticsservice.application.messages.model.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.AnalyticsMessagePseudonymizer;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.CertificateAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.infrastructure.pseudonymization.PseudonymizationTokenGenerator;

@Component
@RequiredArgsConstructor
public class CertificateAnalyticsEventV1Pseudonymizer implements AnalyticsMessagePseudonymizer {

  private final PseudonymizationTokenGenerator pseudonymizationTokenGenerator;

  @Override
  public boolean canPseudonymize(CertificateAnalyticsMessage message) {
    return message instanceof CertificateAnalyticsMessageV1;
  }

  @Override
  public PseudonymizedAnalyticsMessage pseudonymize(CertificateAnalyticsMessage message) {
    if (!(message instanceof CertificateAnalyticsMessageV1 messageV1)) {
      throw new IllegalArgumentException("Invalid message type");
    }

    return PseudonymizedAnalyticsMessage.builder()
        .id(
            pseudonymizationTokenGenerator.id(
                messageV1.getMessageId()
            )
        )
        .eventTimestamp(messageV1.getEvent().getTimestamp())
        .eventMessageType(messageV1.getEvent().getMessageType())
        .eventUserId(
            pseudonymizationTokenGenerator.staffId(
                messageV1.getEvent().getUserId()
            )
        )
        .eventRole(messageV1.getEvent().getRole())
        .eventUnitId(messageV1.getEvent().getUnitId())
        .eventCareProviderId(messageV1.getEvent().getCareProviderId())
        .eventOrigin(messageV1.getEvent().getOrigin())
        .eventSessionId(
            pseudonymizationTokenGenerator.sessionId(
                messageV1.getEvent().getSessionId()
            )
        )
        .certificateId(
            pseudonymizationTokenGenerator.certificateId(
                messageV1.getCertificate().getId()
            )
        )
        .certificateType(messageV1.getCertificate().getType())
        .certificateTypeVersion(messageV1.getCertificate().getTypeVersion())
        .certificatePatientId(
            pseudonymizationTokenGenerator.patientId(
                messageV1.getCertificate().getPatientId()
            )
        )
        .certificateUnitId(messageV1.getCertificate().getUnitId())
        .certificateCareProviderId(messageV1.getCertificate().getCareProviderId())
        .certificateRelationParentId(
            missingParent(messageV1.getCertificate()) ? null :
                pseudonymizationTokenGenerator.parentCertificateId(
                    messageV1.getCertificate().getParent().getId()
                )
        )
        .certificateRelationParentType(
            missingParent(messageV1.getCertificate()) ? null :
                messageV1.getCertificate().getParent().getType()
        )
        .recipientId(
            missingRecipient(messageV1) ? null :
                messageV1.getRecipient().getId()
        )
        .messageId(
            missingMessage(messageV1) ? null :
                pseudonymizationTokenGenerator.messageId(
                    messageV1.getMessage().getId()
                )
        )
        .messageAnswerId(
            missingMessage(messageV1) ? null :
                // TODO: A answer id will only be present in answer messages
                pseudonymizationTokenGenerator.messageAnswerId(
                    messageV1.getMessage().getAnswerId()
                )
        )
        .messageReminderId(
            missingMessage(messageV1) ? null :
                // TODO: A reminder id will only be present in reminder messages
                pseudonymizationTokenGenerator.messageReminderId(
                    messageV1.getMessage().getReminderId()
                )
        )
        .messageType(
            missingMessage(messageV1) ? null :
                messageV1.getMessage().getType()
        )
        .messageSent(
            missingMessage(messageV1) ? null :
                messageV1.getMessage().getSent()
        )
        .messageLastDateToAnswer(
            missingMessage(messageV1) ? null :
                messageV1.getMessage().getLastDateToAnswer()
        )
        .messageQuestionIds(
            missingMessage(messageV1) ? null :
                messageV1.getMessage().getQuestionIds()
        )
        .messageSenderId(
            missingMessage(messageV1) ? null :
                messageV1.getMessage().getSender()
        )
        .messageRecipientId(
            missingMessage(messageV1) ? null :
                messageV1.getMessage().getRecipient()
        )
        .build();
  }

  private boolean missingParent(CertificateAnalyticsEventCertificateV1 certificate) {
    return certificate.getParent() == null || certificate.getParent().getId() == null;
  }

  private boolean missingRecipient(CertificateAnalyticsMessageV1 message) {
    return message.getRecipient() == null || message.getRecipient().getId() == null;
  }

  private boolean missingMessage(CertificateAnalyticsMessageV1 message) {
    return message.getMessage() == null || message.getMessage().getId() == null;
  }
}