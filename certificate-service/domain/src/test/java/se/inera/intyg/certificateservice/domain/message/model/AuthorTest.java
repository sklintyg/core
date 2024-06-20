package se.inera.intyg.certificateservice.domain.message.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
