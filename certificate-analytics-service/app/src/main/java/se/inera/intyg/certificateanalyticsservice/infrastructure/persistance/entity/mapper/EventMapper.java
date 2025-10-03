package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CareProviderRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateRelationEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.EventTypeRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.OriginRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.RecipientRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.RoleRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.SessionRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.UnitRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class EventMapper {

  private final UnitRepository unitRepository;
  private final CareProviderRepository careProviderRepository;
  private final UserRepository userRepository;
  private final SessionRepository sessionRepository;
  private final OriginRepository originRepository;
  private final EventTypeRepository eventTypeRepository;
  private final RoleRepository roleRepository;
  private final CertificateEntityMapper certificateEntityMapper;
  private final RecipientRepository recipientRepository;
  private final CertificateRelationEntityRepository certificateRelationEntityRepository;

  public EventEntity toEntity(PseudonymizedAnalyticsMessage message) {
    final var certificateEntity = certificateEntityMapper.map(message);
    return EventEntity.builder()
        .certificate(certificateEntity)
        .unit(unitRepository.findOrCreate(message.getEventUnitId()))
        .careProvider(careProviderRepository.findOrCreate(message.getEventCareProviderId()))
        .user(userRepository.findOrCreate(message.getEventUserId()))
        .session(sessionRepository.findOrCreate(message.getEventSessionId()))
        .timestamp(message.getEventTimestamp())
        .origin(originRepository.findOrCreate(message.getEventOrigin()))
        .eventType(eventTypeRepository.findOrCreate(message.getEventMessageType()))
        .role(roleRepository.findOrCreate(message.getEventRole()))
        .recipient(recipientRepository.findOrCreate(message.getRecipientId()))
        .messageId(message.getMessageId())
        .build();
  }

  public PseudonymizedAnalyticsMessage toDomain(EventEntity entity) {
    final var domainBuilder = PseudonymizedAnalyticsMessage.builder()
        .messageId(entity.getMessageId())
        .eventTimestamp(entity.getTimestamp())
        .eventMessageType(entity.getEventType().getEventType())
        .eventUserId(entity.getUser() != null ? entity.getUser().getUserId() : null)
        .eventRole(entity.getRole() != null ? entity.getRole().getRole() : null)
        .eventUnitId(entity.getUnit() != null ? entity.getUnit().getHsaId() : null)
        .eventCareProviderId(
            entity.getCareProvider() != null ? entity.getCareProvider().getHsaId() : null)
        .eventOrigin(entity.getOrigin() != null ? entity.getOrigin().getOrigin() : null)
        .eventSessionId(entity.getSession() != null ? entity.getSession().getSessionId() : null)
        .certificateId(entity.getCertificate().getCertificateId())
        .certificateType(entity.getCertificate().getCertificateType().getCertificateType())
        .certificateTypeVersion(
            entity.getCertificate().getCertificateType().getCertificateTypeVersion())
        .certificatePatientId(entity.getCertificate().getPatient().getPatientId())
        .certificateUnitId(entity.getCertificate().getUnit().getHsaId())
        .certificateCareProviderId(entity.getCertificate().getCareProvider().getHsaId())
        .recipientId(entity.getRecipient() != null ? entity.getRecipient().getRecipient() : null);

    final var relation = certificateRelationEntityRepository
        .findAll().stream()
        .filter(rel ->
            rel.getChildCertificate().getCertificateId()
                .equals(entity.getCertificate().getCertificateId())
        )
        .findFirst();

    if (relation.isPresent()) {
      domainBuilder.certificateRelationParentId(
          relation.get().getParentCertificate().getCertificateId()
      );
      domainBuilder.certificateRelationParentType(
          relation.get().getRelationType().getRelationType()
      );
    }

    return domainBuilder.build();
  }
}
