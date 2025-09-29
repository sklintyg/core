package se.inera.intyg.certificateanalyticsservice.application.messages.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataMessages.draftMessageBuilder;
import static se.inera.intyg.certificateanalyticsservice.testdata.TestDataMessages.toJson;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.AnalyticsMessageConverter;
import se.inera.intyg.certificateanalyticsservice.application.messages.repository.AnalyticMessageRepository;

@ExtendWith(MockitoExtension.class)
class ProcessingAnalyticsMessageServiceTest {

  @Mock
  private AnalyticsMessageConverterProvider analyticsMessageConverterProvider;
  @Mock
  private AnalyticMessageRepository analyticMessageRepository;
  @Mock
  private AnalyticsMessageConverter analyticsMessageConverter;
  @InjectMocks
  private ProcessingAnalyticsMessageService processingAnalyticsMessageService;

  @Test
  void shallPersistProcessedMessage() {
    final var message = draftMessageBuilder().build();
    final var messageAsJson = toJson(message);

    when(analyticsMessageConverterProvider.converter(message.getType(),
        message.getSchemaVersion())).thenReturn(analyticsMessageConverter);
    when(analyticsMessageConverter.convert(messageAsJson)).thenReturn(message);

    processingAnalyticsMessageService.process(messageAsJson, message.getType(),
        message.getSchemaVersion());

    verify(analyticMessageRepository).store(message);
  }
}