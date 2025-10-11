package se.inera.intyg.certificateservice.integrationtest.common.setup;

import jakarta.jms.Message;
import java.time.Duration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;

@Slf4j
public class MessageListener {

  private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();

  @JmsListener(destination = "${certificate.event.queue.name}")
  public void onMessage(Message msg) {
    log.info("onMessage: {}", msg);
    messages.add(msg);
  }

  public void clear() {
    messages.clear();
  }

  public Message awaitMessage(Duration timeout) {
    try {
      return messages.poll(timeout.toMillis(), TimeUnit.MILLISECONDS);
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
      return null;
    }
  }
}