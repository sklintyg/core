package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CareProviderRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.EventTypeRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.OriginRepository;
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

  public EventEntity toEntity(PseudonymizedAnalyticsMessage message) {
    final var certificateEntity = certificateEntityMapper.map(message);
    return EventEntity.builder()
        .certificate(certificateEntity)
        .unit(unitRepository.findOrCreate(message.getCertificateUnitId()))
        .careProvider(careProviderRepository.findOrCreate(message.getCertificateCareProviderId()))
        .user(userRepository.findOrCreate(message.getEventStaffId()))
        .session(sessionRepository.findOrCreate(message.getEventSessionId()))
        .timestamp(message.getEventTimestamp())
        .origin(originRepository.findOrCreate(message.getEventOrigin()))
        .eventType(eventTypeRepository.findOrCreate(message.getEventMessageType()))
        .role(roleRepository.findOrCreate(message.getEventRole()))
        .messageId(message.getMessageId())
        .build();
  }
}
