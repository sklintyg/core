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

  @JmsListener(destination = "intygstjanst.certificate.event.queue")
  public void log(TextMessage message) {
    log.debug("Certificate event received: {}", message);
    messages.add(message);
  }
}
