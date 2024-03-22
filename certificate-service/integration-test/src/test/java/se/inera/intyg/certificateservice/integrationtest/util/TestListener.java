package se.inera.intyg.certificateservice.integrationtest.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class TestListener {

  public final List<String> messages = new ArrayList<>();

  @RabbitListener(queuesToDeclare = @Queue("certificate-service-event-queue"))
  void listen(String data) {
    this.messages.add(data);
  }

}

