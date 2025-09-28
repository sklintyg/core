package se.inera.intyg.certificateanalyticsservice.application.messages.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.AnalyticsMessageConverter;

@ExtendWith(MockitoExtension.class)
class AnalyticsMessageConverterProviderTest {

  @Mock
  private AnalyticsMessageConverter analyticsMessageConverter;

  private AnalyticsMessageConverterProvider analyticsMessageConverterProvider;

  @BeforeEach
  void setUp() {
    analyticsMessageConverterProvider = new AnalyticsMessageConverterProvider(
        List.of(analyticsMessageConverter)
    );
  }

  @Test
  void shallReturnMatchingConverterIfAvailable() {
    when(analyticsMessageConverter.canConvert("type", "version")).thenReturn(true);
    final var actual = analyticsMessageConverterProvider.converter("type", "version");
    assertEquals(analyticsMessageConverter, actual);
  }

  @Test
  void shallThrowIllegalArgumentExceptionIfNoConverterAvailable() {
    when(analyticsMessageConverter.canConvert("type", "version")).thenReturn(false);
    assertThrows(IllegalArgumentException.class,
        () -> analyticsMessageConverterProvider.converter("type", "version")
    );
  }
}