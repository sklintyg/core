package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ProtectedConverterTest {

  @Test
  void shallReturnTrueIfProtectedPersonIsTrue() {
    assertTrue(
        ProtectedConverter.convert(true, false)
    );
  }

  @Test
  void shallReturnTrueIfProtectedPopulationIsTrue() {
    assertTrue(
        ProtectedConverter.convert(false, true)
    );
  }

  @Test
  void shallReturnFalseIfNeitherProtectedPersonOrProtectedPopulationIsTrue() {
    assertFalse(
        ProtectedConverter.convert(false, false)
    );
  }
}