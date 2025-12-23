package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;

class ActionRuleSentTest {

  @Test
  void shouldReturnCustomGetReasonForPermissionDenied() {
    final var expected =
        "För att genomföra den begärda åtgärden behöver intyget vara skickat till en intygsmottagare.";
    final var reasonForPermissionDenied = new ActionRuleSent(true).getReasonForPermissionDenied();
    assertEquals(expected, reasonForPermissionDenied);
  }

  @Nested
  class TestActionRuleSentTrue {

    private ActionRuleSent actionRuleSent;

    @BeforeEach
    void setUp() {
      actionRuleSent = new ActionRuleSent(true);
    }

    @Test
    void shallReturnTrueIfSent() {
      final Optional<Certificate> certificate = Optional.of(
          TestDataCertificate.fk7210CertificateBuilder()
              .sent(TestDataCertificate.SENT)
              .build()
      );

      assertTrue(
          actionRuleSent.evaluate(certificate, Optional.of(ACTION_EVALUATION))
      );
    }

    @Test
    void shallReturnFalseIfNotSent() {
      final Optional<Certificate> certificate = Optional.of(
          TestDataCertificate.fk7210CertificateBuilder()
              .sent(null)
              .build()
      );

      assertFalse(
          actionRuleSent.evaluate(certificate, Optional.of(ACTION_EVALUATION))
      );
    }
  }

  @Nested
  class TestActionRuleSenFalse {

    private ActionRuleSent actionRuleSent;

    @BeforeEach
    void setUp() {
      actionRuleSent = new ActionRuleSent(false);
    }

    @Test
    void shallReturnFalseIfSent() {
      final Optional<Certificate> certificate = Optional.of(
          TestDataCertificate.fk7210CertificateBuilder()
              .sent(TestDataCertificate.SENT)
              .build()
      );

      assertFalse(
          actionRuleSent.evaluate(certificate, Optional.of(ACTION_EVALUATION))
      );
    }

    @Test
    void shallReturnTrueIfNotSent() {
      final Optional<Certificate> certificate = Optional.of(
          TestDataCertificate.fk7210CertificateBuilder()
              .sent(null)
              .build()
      );

      assertTrue(
          actionRuleSent.evaluate(certificate, Optional.of(ACTION_EVALUATION))
      );
    }
  }
}