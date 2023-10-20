package se.inera.intyg.intygproxyservice.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MonitorLogServiceImpl implements MonitorLogService {

  private static final String SPACE = " ";

  private String buildMessage(MonitoringEvent logEvent) {
    final var logMsg = new StringBuilder();
    logMsg.append(logEvent.name()).append(SPACE).append(logEvent.getMessage());
    return logMsg.toString();
  }

  private enum MonitoringEvent {
    PLACEHOLDER("Placeholder");
    private final String message;

    MonitoringEvent(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }
  }
}
