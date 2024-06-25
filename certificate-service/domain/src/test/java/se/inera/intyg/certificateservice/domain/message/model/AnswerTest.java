package se.inera.intyg.certificateservice.domain.message.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AnswerTest {

  @Test
  void shallUpdateStatus() {
    final var answer = Answer.builder()
        .status(MessageStatus.DRAFT)
        .build();

    answer.updateStatus(MessageStatus.DELETED_DRAFT);
    assertEquals(MessageStatus.DELETED_DRAFT, answer.status());
  }
}
