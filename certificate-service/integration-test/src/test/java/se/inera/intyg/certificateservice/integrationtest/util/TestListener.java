package se.inera.intyg.certificateservice.integrationtest.util;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestListener {

  public final List<String> messages = new ArrayList<>();

  @RabbitListener(queues = {"q.certificate-service-event-queue"})
  public void log(String message) {
    log.info("Certificate event received: {}", message);
    System.out.println(message);
    messages.add(message);
  }
}

