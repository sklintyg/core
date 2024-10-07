package se.inera.intyg.certificateservice.domain.common.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RecipientTest {

  @Test
  void shouldReturnLogicalAddressForFKASSA() {
    assertEquals("2021005521", new Recipient(new RecipientId("FKASSA"), "FK").getLogicalAddress());
  }

  @Test
  void shouldReturnLogicalAddressForNotFkassa() {
    assertEquals("WC", new Recipient(new RecipientId("WC"), "Wc").getLogicalAddress());
  }

}