package se.inera.intyg.certificateservice.domain.certificate.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;

public class GetCertificateEventsOfTypeDomainService {

  public List<CertificateEvent> events(Certificate certificate, CertificateEventType type) {
    return switch (type) {
      case CREATED ->
          List.of(eventFromTimestampAndRelation(certificate, certificate.created(), type));
      case AVAILABLE_FOR_PATIENT ->
          Boolean.TRUE.equals(certificate.certificateModel().availableForCitizen()) ? List.of(
              eventFromTimestamp(certificate, certificate.signed(), type))
              : Collections.emptyList();
      case SIGNED -> List.of(eventFromTimestamp(certificate, certificate.signed(), type));
      case SENT -> certificate.sent() != null
          ? List.of(eventFromTimestamp(certificate, certificate.sent().sentAt(), type))
          : Collections.emptyList();
      case INCOMING_MESSAGE -> certificate.messages()
          .stream()
          .filter(message -> message.type() != MessageType.ANSWER
              && message.type() != MessageType.COMPLEMENT)
          .filter(message -> message.author().fromFK())
          .map(message -> eventFromTimestamp(certificate, message.sent(), type))
          .toList();
      case OUTGOING_MESSAGE -> certificate.messages()
          .stream()
          .filter(message -> message.type() != MessageType.ANSWER
              && message.type() != MessageType.COMPLEMENT)
          .filter(message -> !message.author().fromFK())
          .map(message -> eventFromTimestamp(certificate, message.sent(), type))
          .toList();
      case INCOMING_ANSWER -> certificate.messages(MessageType.ANSWER)
          .stream()
          .filter(message -> message.author().fromFK())
          .map(message -> eventFromTimestamp(certificate, message.sent(), type))
          .toList();
      case REQUEST_FOR_COMPLEMENT -> certificate.messages()
          .stream()
          .filter(message -> !message.complements().isEmpty())
          .map(complement -> eventFromTimestamp(certificate, complement.sent(), type))
          .toList();
      case REVOKED -> certificate.revoked() != null
          ? List.of(eventFromTimestamp(certificate, certificate.revoked().revokedAt(), type))
          : Collections.emptyList();
      case REPLACED -> certificateEventsForChildRelation(certificate, type, RelationType.REPLACE);
      case REPLACES -> certificateEventsForParentRelation(certificate, type, RelationType.REPLACE);
      case COMPLEMENTS ->
          certificateEventsForParentRelation(certificate, type, RelationType.COMPLEMENT);
      case COMPLEMENTED ->
          certificateEventsForChildRelation(certificate, type, RelationType.COMPLEMENT);
      case EXTENDED -> certificateEventsForParentRelation(certificate, type, RelationType.RENEW);
      case DELETED, LOCKED, READY_FOR_SIGN, INCOMING_MESSAGE_HANDLED, INCOMING_MESSAGE_REMINDER,
           OUTGOING_MESSAGE_HANDLED, COPIED_BY, COPIED_FROM, CREATED_FROM,
           RELATED_CERTIFICATE_REVOKED -> Collections.emptyList();
    };
  }

  private static List<CertificateEvent> certificateEventsForChildRelation(Certificate certificate,
      CertificateEventType type, RelationType relationType) {
    if (certificate.children() == null) {
      return Collections.emptyList();
    }

    return certificate.children().stream()
        .filter(child -> child.type() == relationType)
        .map(
            child -> eventFromRelation(certificate, child.certificate(), child.created(), type)
        )
        .toList();
  }

  private static List<CertificateEvent> certificateEventsForParentRelation(
      Certificate certificate,
      CertificateEventType type, RelationType relationType) {
    if (certificate.parent() == null) {
      return Collections.emptyList();
    }

    return certificate.parent().type() == relationType ?
        List.of(
            eventFromRelation(
                certificate, certificate.parent().certificate(), certificate.parent().created(),
                type
            )
        ) : Collections.emptyList();
  }

  private static CertificateEvent eventFromRelation(Certificate certificate,
      Certificate relatedCertificate,
      LocalDateTime timestamp, CertificateEventType type) {
    if (timestamp == null) {
      return CertificateEvent.builder().build();
    }

    return CertificateEvent.builder()
        .certificateId(certificate.id())
        .relatedCertificateId(relatedCertificate.id())
        .relatedCertificateStatus(relatedCertificate.status())
        .type(type)
        .timestamp(timestamp)
        .build();
  }

  private static CertificateEvent eventFromTimestamp(Certificate certificate,
      LocalDateTime timestamp, CertificateEventType type) {
    if (timestamp == null) {
      return CertificateEvent.builder().build();
    }

    return CertificateEvent.builder()
        .certificateId(certificate.id())
        .type(type)
        .timestamp(timestamp)
        .build();
  }

  private static CertificateEvent eventFromTimestampAndRelation(Certificate certificate,
      LocalDateTime timestamp, CertificateEventType type) {

    if (certificate.parent() != null && (certificate.parent().type().equals(RelationType.REPLACE)
        || certificate.parent().type().equals(RelationType.COMPLEMENT)
        || certificate.parent().type().equals(RelationType.RENEW))) {
      return CertificateEvent.builder().build();
    }

    return eventFromTimestamp(certificate, timestamp, type);
  }
}
