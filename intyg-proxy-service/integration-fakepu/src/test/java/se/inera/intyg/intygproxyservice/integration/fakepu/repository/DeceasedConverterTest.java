package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.intygproxyservice.integration.fakepu.repository.DeceasedConverter.DEREGISTRATION_REASON_CODE_FOR_DECEASED;

import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedDeregistration;

class DeceasedConverterTest {

  @Test
  void shallReturnTrueIfDeregistrationCodeIsAV() {
    assertTrue(
        DeceasedConverter.convert(
            ParsedDeregistration.builder()
                .deregistrationReasonCode(DEREGISTRATION_REASON_CODE_FOR_DECEASED)
                .build()
        )
    );
  }

  @Test
  void shallReturnFalseIfDeregistrationCodeIsNotAV() {
    assertFalse(
        DeceasedConverter.convert(
            ParsedDeregistration.builder()
                .deregistrationReasonCode("NOT_AV")
                .build()
        )
    );
  }

  @Test
  void shallReturnFalseIfDeregistrationCodeIsNull() {
    assertFalse(
        DeceasedConverter.convert(
            ParsedDeregistration.builder()
                .deregistrationReasonCode(null)
                .build()
        )
    );
  }

  @Test
  void shallReturnFalseIfDeregistrationIsNull() {
    assertFalse(
        DeceasedConverter.convert(null)
    );
  }
}