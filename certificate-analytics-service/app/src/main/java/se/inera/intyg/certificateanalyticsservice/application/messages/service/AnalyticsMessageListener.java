package se.inera.intyg.certificateanalyticsservice.application.messages.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.certificateanalyticsservice.infrastructure.logging.MdcCloseableMap;
import se.inera.intyg.certificateanalyticsservice.infrastructure.logging.MdcHelper;
import se.inera.intyg.certificateanalyticsservice.infrastructure.logging.MdcLogConstants;

@Component
@Slf4j
@RequiredArgsConstructor
public class AnalyticsMessageListener {

  private final AnalyticsMessageService analyticsMessageService;

  @Transactional
  @JmsListener(destination = "${certificate.analytics.message.queue.name}")
  public void onMessage(
      @Payload String body,
      @Header(name = "_type") String type,
      @Header(name = "schemaVersion", required = false) String schemaVersion,
      @Header(name = "sessionId", required = false) String sessionId,
      @Header(name = "traceId", required = false) String traceId
  ) {
    try (MdcCloseableMap mdc = MdcCloseableMap.builder()
        .put(MdcLogConstants.TRACE_ID_KEY, traceId == null ? MdcHelper.traceId() : traceId)
        .put(MdcLogConstants.SPAN_ID_KEY, MdcHelper.spanId())
        .put(MdcLogConstants.SESSION_ID_KEY, sessionId == null ? "-" : sessionId)
        .build()) {
      analyticsMessageService.process(body, type, schemaVersion);
    }
  }
}
