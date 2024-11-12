package se.inera.intyg.certificateservice.domain.message.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AnswerTest {

  private static final Content CONTENT = new Content("content");

  @Nested
  class SendTests {

    @Test
    void shallUpdateStatusToHandled() {
      final var answer = Answer.builder().build();
      answer.send(AJLA_DOKTOR, CONTENT);
      assertEquals(MessageStatus.HANDLED, answer.status());
    }

    @Test
    void shallUpdateContent() {
      final var answer = Answer.builder().build();
      answer.send(AJLA_DOKTOR, CONTENT);
      assertEquals(CONTENT, answer.content());
    }

    @Test
    void shallUpdateAuthoredStaff() {
      final var answer = Answer.builder().build();
      answer.send(AJLA_DOKTOR, CONTENT);
      assertEquals(AJLA_DOKTOR, answer.authoredStaff());
    }

    @Test
    void shallUpdateSent() {
      final var answer = Answer.builder().build();
      answer.send(AJLA_DOKTOR, CONTENT);
      assertNotNull(answer.sent());
    }

    @Test
    void shallUpdateAuthor() {
      final var answer = Answer.builder().build();
      answer.send(AJLA_DOKTOR, CONTENT);
      assertEquals(AJLA_DOKTOR.name().fullName(), answer.author().name());
    }
  }

  @Nested
  class DeleteTests {

    @Test
    void shallUpdateStatusToDeletedDraft() {
      final var answer = Answer.builder().build();
      answer.delete();
      assertEquals(MessageStatus.DELETED_DRAFT, answer.status());
    }
  }

  @Nested
  class SaveTests {

    @Test
    void shallUpdateContent() {
      final var answer = Answer.builder().build();
      answer.save(AJLA_DOKTOR, CONTENT);
      assertEquals(CONTENT, answer.content());
    }

    @Test
    void shallUpdateAuthoredStaff() {
      final var answer = Answer.builder().build();
      answer.save(AJLA_DOKTOR, CONTENT);
      assertEquals(AJLA_DOKTOR, answer.authoredStaff());
    }

    @Test
    void shallUpdateAuthor() {
      final var answer = Answer.builder().build();
      answer.save(AJLA_DOKTOR, CONTENT);
      assertEquals(AJLA_DOKTOR.name().fullName(), answer.author().author());
    }

    @Test
    void shallUpdateModified() {
      final var answer = Answer.builder().build();
      answer.save(AJLA_DOKTOR, CONTENT);
      assertNotNull(answer.modified());
    }
  }
}