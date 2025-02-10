package se.inera.intyg.certificateservice.domain.common.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HsaIdTest {

  @Test
  void shouldCreateHsaId() {
    final var hsaId = HsaId.create("hsaid");
    assertEquals("hsaid", hsaId.id());
  }

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
  void shouldBeCaseInsensitiveWhenEquals() {
    final var hsaIdOne = new HsaId("hsaid");
    final var hsaIdTwo = new HsaId("HSAID");
    assertEquals(hsaIdOne, hsaIdTwo);
  }

  @Test
  void shouldProduceSameHashCodeIgnoringCase() {
    final var hsaIdOne = new HsaId("hsaid");
    final var hsaIdTwo = new HsaId("HSAID");
    assertEquals(hsaIdOne.hashCode(), hsaIdTwo.hashCode());
  }
}