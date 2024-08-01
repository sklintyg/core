package se.inera.intyg.certificateservice.application.certificate.dto.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessageLevel;

class MessageLevelTest {

  @Test
  void shallConvertInfo() {
    assertEquals(MessageLevel.INFO, MessageLevel.toMessageLevel(ElementMessageLevel.INFO));
  }

  @Test
  void shallConvertObserve() {
    assertEquals(MessageLevel.OBSERVE, MessageLevel.toMessageLevel(ElementMessageLevel.OBSERVE));
  }

  @Test
  void shallConvertError() {
    assertEquals(MessageLevel.ERROR, MessageLevel.toMessageLevel(ElementMessageLevel.ERROR));
  }
}
