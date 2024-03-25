package se.inera.intyg.certificateservice.integrationtest.util;

import jakarta.jms.TextMessage;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestListener {

  public final List<TextMessage> messages = new ArrayList<>();

  @JmsListener(destination = "certificate-service-event-queue")
  public void log(TextMessage message) {
    log.info("Certificate event received: {}", message);
    System.out.println(message);
    messages.add(message);
  }
}

