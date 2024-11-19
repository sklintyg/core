package se.inera.intyg.certificateservice.application.certificate.dto.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;

class MessageLevelTest {

  @Test
  void shallConvertInfo() {
    assertEquals(
        se.inera.intyg.certificateservice.application.certificate.dto.config.MessageLevel.INFO,
        se.inera.intyg.certificateservice.application.certificate.dto.config.MessageLevel.toMessageLevel(
            MessageLevel.INFO));
  }

  @Test
  void shallConvertObserve() {
    assertEquals(
        se.inera.intyg.certificateservice.application.certificate.dto.config.MessageLevel.OBSERVE,
        se.inera.intyg.certificateservice.application.certificate.dto.config.MessageLevel.toMessageLevel(
            MessageLevel.OBSERVE));
  }

  @Test
  void shallConvertError() {
    assertEquals(
        se.inera.intyg.certificateservice.application.certificate.dto.config.MessageLevel.ERROR,
        se.inera.intyg.certificateservice.application.certificate.dto.config.MessageLevel.toMessageLevel(
            MessageLevel.ERROR));
  }
}
