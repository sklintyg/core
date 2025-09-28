package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;

public class EventMapperV1 {

  public static EventEntity toEntity(CertificateAnalyticsEventMessageV1 message) {
    final var certificate = message.getCertificate();
    final var event = message.getEvent();
    return EventEntity.builder()
        .certificate(CertificateEntityMapperV1.map(certificate))
        .unit(UnitEntityMapperV1.map(certificate.getUnitId()))
        .careProvider(CareProviderEntityMapperV1.map(certificate.getCareProviderId()))
        .user(UserEntityMapperV1.map(event.getStaffId()))
        .session(SessionEntityMapperV1.map(event.getSessionId()))
        .time(TimeEntityMapperV1.map(event.getTimestamp()))
        .origin(OriginEntityMapperV1.map(event.getOrigin()))
        .device(DeviceEntityMapperV1.map())
        .eventType(EventTypeEntityMapperV1.map(event.getMessageType()))
        .role(RoleEntityMapperV1.map(event.getRole()))
        .build();
  }
}
