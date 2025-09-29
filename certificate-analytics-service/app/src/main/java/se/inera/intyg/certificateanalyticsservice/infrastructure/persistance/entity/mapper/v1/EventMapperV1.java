package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CareProviderRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.DeviceRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.EventTypeRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.OriginRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.RoleRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.SessionRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.UnitRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class EventMapperV1 {

  private final UnitRepository unitRepository;
  private final CareProviderRepository careProviderRepository;
  private final UserRepository userRepository;
  private final SessionRepository sessionRepository;
  private final OriginRepository originRepository;
  private final DeviceRepository deviceRepository;
  private final EventTypeRepository eventTypeRepository;
  private final RoleRepository roleRepository;
  private final CertificateEntityMapperV1 certificateEntityMapperV1;

  public EventEntity toEntity(CertificateAnalyticsEventMessageV1 message) {
    final var certificate = message.getCertificate();
    final var event = message.getEvent();
    return EventEntity.builder()
        .certificate(certificateEntityMapperV1.map(certificate))
        .unit(unitRepository.findOrCreate(certificate.getUnitId()))
        .careProvider(careProviderRepository.findOrCreate(certificate.getCareProviderId()))
        .user(userRepository.findOrCreate(event.getStaffId()))
        .session(sessionRepository.findOrCreate(event.getSessionId()))
        .time(TimeEntityMapperV1.map(event.getTimestamp()))
        .origin(originRepository.findOrCreate(event.getOrigin()))
        .device(deviceRepository.findOrCreate(null))
        .eventType(eventTypeRepository.findOrCreate(event.getMessageType()))
        .role(roleRepository.findOrCreate(event.getRole()))
        .build();
  }
}
