package se.inera.intyg.certificateservice.domain.common.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HsaIdTest {

  @Test
  void shouldThrowExceptionWhenIdIsNull() {
    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
        IllegalArgumentException.class, () -> new HsaId(null));
    assertEquals("Missing id", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenIdIsBlank() {
    IllegalArgumentException exception = org.junit.jupiter.api.Assertions.assertThrows(
        IllegalArgumentException.class, () -> new HsaId(" "));
    assertEquals("Missing id", exception.getMessage());
  }

  @Test
  void shouldConvertToUpperCase() {
    final var hsaId = new HsaId("hsaid");
    assertEquals("HSAID", hsaId.id());
  }
}