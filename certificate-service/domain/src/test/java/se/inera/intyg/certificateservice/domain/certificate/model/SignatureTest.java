package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SignatureTest {

  @Test
  void shallReturnEmptyTrueIfNull() {
    assertTrue(new Signature(null).isEmpty());
  }

  @Test
  void shallReturnEmptyTrueIfEmptyString() {
    assertTrue(new Signature(" ").isEmpty());
  }
}