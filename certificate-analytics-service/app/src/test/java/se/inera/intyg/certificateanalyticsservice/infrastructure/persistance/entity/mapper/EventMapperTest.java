package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities.certificateRelationEntity;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataEntities.sentEventEntityBuilder;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataPseudonymized.sentPseudonymizedMessageBuilder;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CareProviderEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.OriginEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RecipientEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RoleEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.SessionEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UnitEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UserEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CareProviderRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateRelationEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.EventTypeRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.OriginRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.RecipientRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.RoleRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.SessionRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.UnitRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class EventMapperTest {

  @InjectMocks
  private EventMapper eventMapper;
  @Mock
  private UnitRepository unitRepository;
  @Mock
  private CareProviderRepository careProviderRepository;
  @Mock
  private UserRepository userRepository;
  @Mock
  private SessionRepository sessionRepository;
  @Mock
  private OriginRepository originRepository;
  @Mock
  private EventTypeRepository eventTypeRepository;
  @Mock
  private RoleRepository roleRepository;
  @Mock
  private RecipientRepository recipientRepository;
  @Mock
  private CertificateEntityMapper certificateEntityMapper;
  @Mock
  private CertificateRelationEntityRepository certificateRelationEntityRepository;

  @Test
  void shouldMapPseudonymizedAnalyticsMessageCorrectly() {
    final var message = sentPseudonymizedMessageBuilder().build();

    final var expectedCertificate = mock(CertificateEntity.class);
    final var expectedUnit = mock(UnitEntity.class);
    final var expectedCareProvider = mock(CareProviderEntity.class);
    final var expectedUser = mock(UserEntity.class);
    final var expectedSession = mock(SessionEntity.class);
    final var expectedOrigin = mock(OriginEntity.class);
    final var expectedEventType = mock(EventTypeEntity.class);
    final var expectedRole = mock(RoleEntity.class);
    final var expectedRecipient = mock(RecipientEntity.class);

    when(certificateEntityMapper.map(message)).thenReturn(expectedCertificate);
    when(unitRepository.findOrCreate(message.getCertificateUnitId())).thenReturn(expectedUnit);
    when(careProviderRepository.findOrCreate(message.getCertificateCareProviderId())).thenReturn(
        expectedCareProvider);
    when(userRepository.findOrCreate(message.getEventUserId())).thenReturn(expectedUser);
    when(sessionRepository.findOrCreate(message.getEventSessionId())).thenReturn(expectedSession);
    when(originRepository.findOrCreate(message.getEventOrigin())).thenReturn(expectedOrigin);
    when(eventTypeRepository.findOrCreate(message.getEventMessageType())).thenReturn(
        expectedEventType);
    when(roleRepository.findOrCreate(message.getEventRole())).thenReturn(expectedRole);
    when(recipientRepository.findOrCreate(message.getRecipientId())).thenReturn(expectedRecipient);

    final var expected = EventEntity.builder()
        .certificate(expectedCertificate)
        .unit(expectedUnit)
        .careProvider(expectedCareProvider)
        .user(expectedUser)
        .session(expectedSession)
        .timestamp(message.getEventTimestamp())
        .origin(expectedOrigin)
        .eventType(expectedEventType)
        .role(expectedRole)
        .recipient(expectedRecipient)
        .messageId(message.getMessageId())
        .build();

    final var result = eventMapper.toEntity(message);

    assertEquals(expected, result);
    assertEquals(message.getMessageId(), result.getMessageId());
  }

  @Test
  void shouldMapEventEntityToDomainCorrectly() {
    final var expected = sentPseudonymizedMessageBuilder().build();
    final var entity = sentEventEntityBuilder().build();
    when(certificateRelationEntityRepository.findAll())
        .thenReturn(List.of(certificateRelationEntity()));
    final var actual = eventMapper.toDomain(entity);
    assertEquals(expected, actual);
  }
}
