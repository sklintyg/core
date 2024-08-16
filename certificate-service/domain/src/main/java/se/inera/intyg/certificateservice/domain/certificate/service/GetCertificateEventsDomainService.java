package se.inera.intyg.certificateservice.domain.certificate.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;

@RequiredArgsConstructor
public class GetCertificateEventsDomainService {

  private final CertificateRepository certificateRepository;

  public List<CertificateEvent> get(CertificateId certificateId,
      ActionEvaluation actionEvaluation) {
    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.READ, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to read certificate %s, cannot get events".formatted(certificate.id()),
          certificate.reasonNotAllowed(CertificateActionType.READ, Optional.of(actionEvaluation))
      );
    }

    return eventsOf(certificate);
  }

  private static List<CertificateEvent> eventsOf(Certificate certificate) {
    final var eventTypes = Arrays.asList(CertificateEventType.values());

    return eventTypes.stream()
        .map(type -> eventsOfType(certificate, type))
        .flatMap(List::stream)
        .filter(event -> event.certificateId() != null)
        .sorted(Comparator.comparing(CertificateEvent::timestamp))
        .toList();
  }

  private static List<CertificateEvent> eventsOfType(Certificate certificate,
      CertificateEventType type) {
    switch (type) {
      case CREATED -> {
        return List.of(eventFromTimestamp(certificate, certificate.created(), type));
      }
      case SIGNED, AVAILABLE_FOR_PATIENT -> {
        return List.of(eventFromTimestamp(certificate, certificate.signed(), type));
      }
      case SENT -> {
        return certificate.sent() != null
            ? List.of(eventFromTimestamp(certificate, certificate.sent().sentAt(), type))
            : Collections.emptyList();
      }
      case INCOMING_MESSAGE -> {
        return certificate.messages()
            .stream()
            .filter(message -> message.type() != MessageType.ANSWER
                && message.type() != MessageType.COMPLEMENT)
            .filter(message -> message.author().author().equals("FK"))
            .map(message -> eventFromTimestamp(certificate, message.sent(), type))
            .toList();
      }
      case OUTGOING_MESSAGE -> {
        return certificate.messages()
            .stream()
            .filter(message -> message.type() != MessageType.ANSWER
                && message.type() != MessageType.COMPLEMENT)
            .filter(message -> !message.author().author().equals("FK"))
            .map(message -> eventFromTimestamp(certificate, message.sent(), type))
            .toList();
      }
      case INCOMING_ANSWER -> {
        return certificate.messages(MessageType.ANSWER)
            .stream()
            .filter(message -> message.author().author().equals("FK"))
            .map(message -> eventFromTimestamp(certificate, message.sent(), type))
            .toList();
      }
      case REQUEST_FOR_COMPLEMENT -> {
        return certificate.messages(MessageType.COMPLEMENT)
            .stream()
            .map(complement -> eventFromTimestamp(certificate, complement.sent(), type))
            .toList();
      }
      case REVOKED -> {
        return certificate.revoked() != null
            ? List.of(eventFromTimestamp(certificate, certificate.revoked().revokedAt(), type))
            : Collections.emptyList();
      }
      case REPLACED -> {
        return certificateEventsForChildRelation(certificate, type, RelationType.REPLACE);
      }
      case REPLACES -> {
        return certificateEventsForParentRelation(certificate, type, RelationType.REPLACE);
      }
      case COMPLEMENTS -> {
        return certificateEventsForParentRelation(certificate, type, RelationType.COMPLEMENT);
      }
      case COMPLEMENTED -> {
        return certificateEventsForChildRelation(certificate, type, RelationType.COMPLEMENT);
      }
      case EXTENDED -> {
        return certificateEventsForChildRelation(certificate, type, RelationType.RENEW);
      }
      case DELETED, LOCKED, READY_FOR_SIGN, INCOMING_MESSAGE_HANDLED, INCOMING_MESSAGE_REMINDER,
           OUTGOING_MESSAGE_HANDLED,
           COPIED_BY, COPIED_FROM, CREATED_FROM, RELATED_CERTIFICATE_REVOKED -> {
        return Collections.emptyList();
      }
    }
    return Collections.emptyList();
  }

  private static List<CertificateEvent> certificateEventsForChildRelation(Certificate certificate,
      CertificateEventType type, RelationType relationType) {
    if (certificate.children() == null) {
      return Collections.emptyList();
    }

    return certificate.children().stream()
        .filter(child -> child.type().equals(relationType))
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
                certificate, certificate.parent().certificate(), certificate.created(), type
            )
        )
        : Collections.emptyList();
  }

  private static CertificateEvent eventFromRelation(Certificate certificate,
      Certificate relatedCertificate,
      LocalDateTime timestamp, CertificateEventType type) {
    if (timestamp == null) {
      return null;
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
}
