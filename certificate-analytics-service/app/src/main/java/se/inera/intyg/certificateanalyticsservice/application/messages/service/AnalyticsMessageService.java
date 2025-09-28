package se.inera.intyg.certificateanalyticsservice.application.messages.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateanalyticsservice.application.messages.repository.AnalyticMessageRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnalyticsMessageService {

  private final AnalyticsMessageConverterProvider analyticsMessageConverterProvider;
  private final AnalyticMessageRepository analyticMessageRepository;

  public void process(String body, String type, String schemaVersion) {
    final var converter = analyticsMessageConverterProvider.converter(type, schemaVersion);
    final var message = converter.convert(body);
    analyticMessageRepository.store(message);
  }
}
