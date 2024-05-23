package se.inera.intyg.certificateservice.domain.message.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class MessageTest {

  @Nested
  class TestHandle {

    @Test
    void shallSetStatusToHandled() {
      final var unhandledMessage = complementMessageBuilder().build();
      unhandledMessage.handle();
      assertEquals(MessageStatus.HANDLED, unhandledMessage.status());
    }

    @Test
    void shallUpdateModified() {
      final var unhandledMessage = complementMessageBuilder().build();
      final var modifiedBefore = unhandledMessage.modified();
      unhandledMessage.handle();
      assertAll(
          () -> assertNotNull(unhandledMessage.modified()),
          () -> assertNotEquals(modifiedBefore, unhandledMessage.modified())
      );
    }

    @Test
    void shallNotUpdateModifiedIfAlreadyHandled() {
      final var unhandledMessage = complementMessageBuilder()
          .status(MessageStatus.HANDLED)
          .build();
      
      final var modifiedBefore = unhandledMessage.modified();
      unhandledMessage.handle();
      assertEquals(modifiedBefore, unhandledMessage.modified());
    }
  }
}