package se.inera.intyg.intygproxyservice.integration.api.pu;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PersonIdTest {

  @Test
  void shallRemoveHyphen() {
    final var personId = PersonId.of("19121212-1212");
    assertEquals("191212121212", personId.id());
  }

  @Test
  void shallRemovePlus() {
    final var personId = PersonId.of("+191212121212");
    assertEquals("191212121212", personId.id());
  }
}