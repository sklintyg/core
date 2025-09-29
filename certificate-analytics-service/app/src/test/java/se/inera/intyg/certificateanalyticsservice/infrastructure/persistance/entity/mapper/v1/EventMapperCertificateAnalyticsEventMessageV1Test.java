package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.CertificateAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.EventEntity;

@ExtendWith(MockitoExtension.class)
class EventMapperCertificateAnalyticsEventMessageV1Test {

  @Mock
  private EventMapperV1 eventMapperV1;

  @InjectMocks
  private EventMapperCertificateAnalyticsEventMessageV1 eventMapper;

  @Test
  void shouldSupportCertificateAnalyticsEventMessageV1() {
    final var message = mock(CertificateAnalyticsEventMessageV1.class);
    assertTrue(eventMapper.supports(message), "Should support CertificateAnalyticsEventMessageV1");
  }

  @Test
  void shouldNotSupportOtherMessageTypes() {
    final var message = mock(CertificateAnalyticsMessage.class);
    assertFalse(eventMapper.supports(message), "Should not support other message types");
  }

  @Test
  void shouldDelegateToEntityMapping() {
    final var message = mock(CertificateAnalyticsEventMessageV1.class);
    final var expectedEntity = mock(EventEntity.class);
    when(eventMapperV1.toEntity(message)).thenReturn(expectedEntity);

    final var result = eventMapper.toEntity(message);

    assertEquals(expectedEntity, result,
        "Should delegate to EventMapperV1 and return the mapped entity");
  }

  @Test
  void shouldPropagateExceptionFromDelegateMapper() {
    final var message = mock(CertificateAnalyticsEventMessageV1.class);
    when(eventMapperV1.toEntity(message)).thenThrow(new RuntimeException("Delegate error"));

    final var thrown = assertThrows(RuntimeException.class, () -> eventMapper.toEntity(message));
    assertEquals("Delegate error", thrown.getMessage(),
        "Should propagate exception from delegate mapper");
  }
}
