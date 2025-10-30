package se.inera.intyg.certificateservice.integrationtest.common.setup;

import jakarta.jms.Message;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;

@Slf4j
public class MessageListener {

  private static final ConcurrentHashMap<String, Message> CERTIFICATE_INDEX = new ConcurrentHashMap<>();

  @JmsListener(destination = "${certificate.event.queue.name}")
  public void onMessage(Message message) {
    try {
      final var certificateId = message.getStringProperty("certificateId");
      if (certificateId != null) {
        CERTIFICATE_INDEX.put(certificateId, message);
      }
    } catch (Exception e) {
      log.info("onMessage (instance {}) received but couldn't read properties: {}",
          System.identityHashCode(this), message);
    }
  }

  public Message awaitByCertificateId(Duration timeout, String certificateId) {
    final var deadline = System.currentTimeMillis() + timeout.toMillis();

    while (System.currentTimeMillis() < deadline) {
      final var message = CERTIFICATE_INDEX.get(certificateId);
      if (message != null) {
        return message;
      }
    }

    return null;
  }
}
