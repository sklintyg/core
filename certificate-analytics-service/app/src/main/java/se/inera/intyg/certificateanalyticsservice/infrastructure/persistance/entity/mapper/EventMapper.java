package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.application.event.Message;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;

public class EventMapper {

  public static EventEntity toEntity(Message message) {
    return EventEntity.builder()
        .certificate(CertificateEntityMapper.map(message.getCertificate()))
        .unit(UnitEntityMapper.map(message.getCertificate().getUnitId()))
        .careProvider(CareProviderEntityMapper.map(message.getCertificate().getCareProviderId()))
        .user(UserEntityMapper.map(message.getEvent().getStaffId()))
        .session(SessionEntityMapper.map(message.getEvent().getSessionId()))
        .time(TimeEntityMapper.map(message.getEvent().getTimestamp()))
        .origin(OriginEntityMapper.map(message.getEvent().getOrigin()))
        .device(DeviceEntityMapper.map())
        .eventType(EventTypeEntityMapper.map(message.getEvent().getMessageType()))
        .role(RoleEntityMapper.map(message.getEvent().getRole()))
        .build();
  }
}
