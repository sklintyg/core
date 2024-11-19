package se.inera.intyg.certificateservice.domain.message.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AuthorTest {

  @Test
  void shallReturnForsakringskassanIfAuthorIsFK() {
    final var author = new Author("FK");
    assertEquals("Försäkringskassan", author.name());
  }

  @Test
  void shallReturnAuthor() {
    final var author = new Author("WC");
    assertEquals("WC", author.name());
  }

  @Test
  void shallReturnFalseIfAuthorIsWC() {
    final var author = new Author("WC");
    assertFalse(author.fromFK());
  }

  @Test
  void shallReturnTrueIfAuthorIsFK() {
    final var author = new Author("FK");
    assertTrue(author.fromFK());
  }
}
