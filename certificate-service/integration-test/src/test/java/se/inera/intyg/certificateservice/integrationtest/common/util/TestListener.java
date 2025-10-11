package se.inera.intyg.certificateservice.integrationtest.common.util;

import jakarta.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestListener {

  private static final List<TextMessage> messages = new ArrayList<>();

  public void emptyMessages() {
    messages.clear();
  }

  @JmsListener(destination = "${certificate.event.queue.name}")
  public void log(TextMessage message) {
    log.info("Certificate event received: {}", message);
    messages.add(message);
  }

  public List<TextMessage> messages() {
    return messages;
  }
}
