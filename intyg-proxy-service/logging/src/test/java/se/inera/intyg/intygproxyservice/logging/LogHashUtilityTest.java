package se.inera.intyg.intygproxyservice.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class LogHashUtilityTest {

  private final LogHashUtility hashUtility = new LogHashUtility();

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(hashUtility, "salt", "salt");
  }

  @Test
  void shouldReturnHashedValue() {
    final var payload = "123123123";
    final var hashedPayload = hashUtility.hash(payload);
    assertEquals("f0b9a3394c4a24871d26ed9e0b7e81dc08714204caafe9821e7fb141ae410286", hashedPayload);
  }

  @Test
  void shouldReturnEmptyHashConstantWhenPayloadIsNull() {
    final var hashedPayload = hashUtility.hash(null);
    assertEquals(LogHashUtility.EMPTY, hashedPayload);
  }

  @Test
  void shouldReturnEmptyHashConstantWhenPayloadIsEmpty() {
    final var payload = "";
    final var hashedPayload = hashUtility.hash(payload);
    assertEquals(LogHashUtility.EMPTY, hashedPayload);
  }
}