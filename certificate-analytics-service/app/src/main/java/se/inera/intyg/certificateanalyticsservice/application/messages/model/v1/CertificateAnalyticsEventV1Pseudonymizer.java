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
    return message instanceof CertificateAnalyticsEventMessageV1;
  }

  @Override
  public PseudonymizedAnalyticsMessage pseudonymize(CertificateAnalyticsMessage message) {
    if (!(message instanceof CertificateAnalyticsEventMessageV1 messageV1)) {
      throw new IllegalArgumentException("Invalid message type");
    }

    return PseudonymizedAnalyticsMessage.builder()
        .messageId(
            pseudonymizationTokenGenerator.messageId(
                messageV1.getMessageId()
            )
        )
        .eventTimestamp(messageV1.getEvent().getTimestamp())
        .eventMessageType(messageV1.getEvent().getMessageType())
        .eventStaffId(
            pseudonymizationTokenGenerator.staffId(
                messageV1.getEvent().getStaffId()
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
        .build();
  }
}