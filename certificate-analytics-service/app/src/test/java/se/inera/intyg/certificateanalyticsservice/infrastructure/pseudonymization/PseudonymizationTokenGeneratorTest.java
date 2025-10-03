package se.inera.intyg.certificateanalyticsservice.infrastructure.pseudonymization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateanalyticsservice.testdata.TestDataConstants;

@ExtendWith(SpringExtension.class)
class PseudonymizationTokenGeneratorTest {

  @InjectMocks
  private PseudonymizationTokenGenerator pseudonymizationTokenGenerator;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(pseudonymizationTokenGenerator, "key",
        "thisisasecretkeythatisusedaspepper".getBytes(StandardCharsets.UTF_8)
    );
    ReflectionTestUtils.setField(pseudonymizationTokenGenerator, "context", "analytics-test");
  }

  /**
   * These tests ensure that the pseudonymization algorithm produces the same output for the same
   * input over time, given a fixed key and context. If this test fails, it indicates that the
   * algorithm or its parameters have changed, which could impact data consistency and integrity.
   */
  @Nested
  class TestConsistencyOverTime {

    @Test
    void shallGeneratePseudonymizedMessageThatIsTheSameOverTime() {
      final var expected = "kWK4wB8ZyUZThriVDvMOTQ";
      final var messageId = "messageId";

      final var actual = pseudonymizationTokenGenerator.messageId(messageId);

      assertEquals(expected, actual);
    }

    @Test
    void shallGeneratePseudonymizedStaffIdThatIsTheSameOverTime() {
      final var expected = "q4mqOSKxPQwxpUoB6eqFJg";
      final var staffId = "staffId";

      final var actual = pseudonymizationTokenGenerator.staffId(staffId);

      assertEquals(expected, actual);
    }

    @Test
    void shallGeneratePseudonymizedCertificateIdThatIsTheSameOverTime() {
      final var expected = "y3iWsfAvFHqP4xtd7qcBLA";
      final var certificateId = "certificateId";

      final var actual = pseudonymizationTokenGenerator.certificateId(certificateId);

      assertEquals(expected, actual);
    }

    @Test
    void shallGeneratePseudonymizedParentCertificateIdThatIsTheSameOverTime() {
      final var expected = "3CMu4xpRBVNUVQ9ARZKY8Q";
      final var parentCertificateId = "parentCertificateId";

      final var actual = pseudonymizationTokenGenerator.parentCertificateId(parentCertificateId);

      assertEquals(expected, actual);
    }

    @Test
    void shallGeneratePseudonymizedSessionIdThatIsTheSameOverTime() {
      final var expected = "nTxVe__MOq5J6sbFWhRweg";
      final var sessionId = "sessionId";

      final var actual = pseudonymizationTokenGenerator.sessionId(sessionId);

      assertEquals(expected, actual);
    }

    @Test
    void shallGeneratePseudonymizedPatientIdThatIsTheSameOverTime() {
      final var expected = "3M8x-GCLyXq0hXzdpmquZA";
      final var patientId = "patientId";

      final var actual = pseudonymizationTokenGenerator.patientId(patientId);

      assertEquals(expected, actual);
    }

    @Test
    void shallGeneratePseudonymizedAdministrativeMessageIdThatIsTheSameOverTime() {

      final var actual = pseudonymizationTokenGenerator.administrativeMessageId(
          TestDataConstants.ADMINISTRATIVE_MESSAGE_ID);

      assertEquals(TestDataConstants.HASHED_ADMINISTRATIVE_MESSAGE_ID, actual);
    }
  }

  /**
   * These tests ensure that the pseudonymization algorithm produces the same output for the same
   * input values when called multiple times. This is crucial for maintaining consistent
   * pseudonymized identifiers across different parts of the system that may process the same data.
   */
  @Nested
  class TestConsistencyBetweenCalls {

    @Test
    void shallGenerateSamePseudonymizedMessageIdFromSameValue() {
      final var messageId = "messageId";

      final var messageIdOne = pseudonymizationTokenGenerator.messageId(messageId);
      final var messageIdTwo = pseudonymizationTokenGenerator.messageId(messageId);

      assertEquals(messageIdOne, messageIdTwo);
    }

    @Test
    void shallGenerateSamePseudonymizedStaffIdFromSameValue() {
      final var staffId = "staffId";

      final var staffIdOne = pseudonymizationTokenGenerator.staffId(staffId);
      final var staffIdTwo = pseudonymizationTokenGenerator.staffId(staffId);

      assertEquals(staffIdOne, staffIdTwo);
    }

    @Test
    void shallGenerateSamePseudonymizedCertificateIdFromSameValue() {
      final var certificateId = "certificateId";

      final var certificateIdOne = pseudonymizationTokenGenerator.certificateId(certificateId);
      final var certificateIdTwo = pseudonymizationTokenGenerator.certificateId(certificateId);

      assertEquals(certificateIdOne, certificateIdTwo);
    }

    @Test
    void shallGenerateSamePseudonymizedParentCertificateIdFromSameValue() {
      final var parentCertificateId = "parentCertificateId";

      final var parentCertificateIdOne = pseudonymizationTokenGenerator.parentCertificateId(
          parentCertificateId);
      final var parentCertificateIdTwo = pseudonymizationTokenGenerator.parentCertificateId(
          parentCertificateId);

      assertEquals(parentCertificateIdOne, parentCertificateIdTwo);
    }

    @Test
    void shallGenerateSamePseudonymizedSessionIdFromSameValue() {
      final var sessionId = "sessionId";

      final var sessionIdOne = pseudonymizationTokenGenerator.sessionId(sessionId);
      final var sessionIdTwo = pseudonymizationTokenGenerator.sessionId(sessionId);

      assertEquals(sessionIdOne, sessionIdTwo);
    }

    @Test
    void shallGenerateSamePseudonymizedPatientIdFromSameValue() {
      final var patientId = "patientId";

      final var patientIdOne = pseudonymizationTokenGenerator.patientId(patientId);
      final var patientIdTwo = pseudonymizationTokenGenerator.patientId(patientId);

      assertEquals(patientIdOne, patientIdTwo);
    }

    @Test
    void shallGenerateSamePseudonymizedAdministrativeMessageIdFromSameValue() {
      final var administrativeMessageId = "administrativeMessageId";

      final var administrativeMessageIdOne = pseudonymizationTokenGenerator.administrativeMessageId(
          administrativeMessageId);
      final var administrativeMessageIdTwo = pseudonymizationTokenGenerator.administrativeMessageId(
          administrativeMessageId);

      assertEquals(administrativeMessageIdOne, administrativeMessageIdTwo);
    }
  }

  /**
   * These tests ensure that the pseudonymization algorithm generates unique outputs for the same
   * input value when the input is associated with different types of identifiers. This is important
   * to prevent collisions and ensure that identifiers remain distinct even if they share the same
   * original value.
   */
  @Nested
  class TestTokenTypeUniqueness {

    @Test
    void shallGenerateDifferentTokensForSameValueAcrossTypes() {
      final var value = "samevalue";

      final var messageIdToken = pseudonymizationTokenGenerator.messageId(value);
      final var staffIdToken = pseudonymizationTokenGenerator.staffId(value);
      final var sessionIdToken = pseudonymizationTokenGenerator.sessionId(value);
      final var certificateIdToken = pseudonymizationTokenGenerator.certificateId(value);
      final var patientIdToken = pseudonymizationTokenGenerator.patientId(value);
      final var administrativeMessageIdToken = pseudonymizationTokenGenerator.administrativeMessageId(
          value);

      final var tokens = List.of(
          messageIdToken,
          staffIdToken,
          sessionIdToken,
          certificateIdToken,
          patientIdToken,
          administrativeMessageIdToken
      );

      final var uniqueTokens = tokens.stream().distinct().count();

      assertEquals(tokens.size(), uniqueTokens,
          "Tokens for same value across types should be unique: %s".formatted(tokens)
      );
    }

    @Test
    void shallGenerateSameTokenForSameValueForCertificateIdTypes() {
      final var value = "samevalue";

      final var certificateIdToken = pseudonymizationTokenGenerator.certificateId(value);
      final var parentCertificateIdToken = pseudonymizationTokenGenerator.parentCertificateId(
          value);

      final var tokens = List.of(
          certificateIdToken,
          parentCertificateIdToken
      );

      final var uniqueTokens = tokens.stream().distinct().count();

      assertEquals(1, uniqueTokens,
          "Tokens for same value across certificateId types should be same: %s".formatted(tokens)
      );
    }
  }

  /**
   * These tests ensure that the pseudonymization algorithm produces different outputs for the same
   * input when the context is changed. This is important for scenarios where the same identifier
   * might be used in different environments or applications, and we want to ensure that the
   * pseudonymized values remain distinct across these contexts.
   */
  @Nested
  class TestUniquenessBetweenDifferentContexts {

    @Test
    void shallGenerateDifferentPseudonymizedMessageIdFromSameValueButDifferentContext() {
      final var messageId = "messageId";

      final var messageIdOne = pseudonymizationTokenGenerator.messageId(messageId);
      ReflectionTestUtils.setField(pseudonymizationTokenGenerator, "context", "analytics-dev");
      final var messageIdTwo = pseudonymizationTokenGenerator.messageId(messageId);

      assertNotEquals(messageIdOne, messageIdTwo);
    }

    @Test
    void shallGenerateDifferentPseudonymizedStaffIdFromSameValueButDifferentContext() {
      final var staffId = "staffId";

      final var staffIdOne = pseudonymizationTokenGenerator.staffId(staffId);
      ReflectionTestUtils.setField(pseudonymizationTokenGenerator, "context", "analytics-dev");
      final var staffIdTwo = pseudonymizationTokenGenerator.staffId(staffId);

      assertNotEquals(staffIdOne, staffIdTwo);
    }

    @Test
    void shallGenerateDifferentPseudonymizedCertificateIdFromSameValueButDifferentContext() {
      final var certificateId = "certificateId";

      final var certificateIdOne = pseudonymizationTokenGenerator.certificateId(certificateId);
      ReflectionTestUtils.setField(pseudonymizationTokenGenerator, "context", "analytics-dev");
      final var certificateIdTwo = pseudonymizationTokenGenerator.certificateId(certificateId);

      assertNotEquals(certificateIdOne, certificateIdTwo);
    }

    @Test
    void shallGenerateDifferentPseudonymizedParentCertificateIdFromSameValueButDifferentContext() {
      final var parentCertificateId = "parentCertificateId";

      final var parentCertificateIdOne = pseudonymizationTokenGenerator.parentCertificateId(
          parentCertificateId);
      ReflectionTestUtils.setField(pseudonymizationTokenGenerator, "context", "analytics-dev");
      final var parentCertificateIdTwo = pseudonymizationTokenGenerator.parentCertificateId(
          parentCertificateId);

      assertNotEquals(parentCertificateIdOne, parentCertificateIdTwo);
    }

    @Test
    void shallGenerateDifferentPseudonymizedSessionIdFromSameValueButDifferentContext() {
      final var sessionId = "sessionId";

      final var sessionIdOne = pseudonymizationTokenGenerator.sessionId(sessionId);
      ReflectionTestUtils.setField(pseudonymizationTokenGenerator, "context", "analytics-dev");
      final var sessionIdTwo = pseudonymizationTokenGenerator.sessionId(sessionId);

      assertNotEquals(sessionIdOne, sessionIdTwo);
    }

    @Test
    void shallGenerateDifferentPseudonymizedPatientIdFromSameValueButDifferentContext() {
      final var patientId = "patientId";

      final var patientIdOne = pseudonymizationTokenGenerator.patientId(patientId);
      ReflectionTestUtils.setField(pseudonymizationTokenGenerator, "context", "analytics-dev");
      final var patientIdTwo = pseudonymizationTokenGenerator.patientId(patientId);

      assertNotEquals(patientIdOne, patientIdTwo);
    }

    @Test
    void shallGenerateDifferentPseudonymizedAdministrativeMessageIdFromSameValueButDifferentContext() {
      final var administrativeMessageId = "administrativeMessageId";

      final var administrativeMessageIdOne = pseudonymizationTokenGenerator.administrativeMessageId(
          administrativeMessageId);
      ReflectionTestUtils.setField(pseudonymizationTokenGenerator, "context", "analytics-dev");
      final var administrativeMessageIdTwo = pseudonymizationTokenGenerator.administrativeMessageId(
          administrativeMessageId);

      assertNotEquals(administrativeMessageIdOne, administrativeMessageIdTwo);
    }
  }
}