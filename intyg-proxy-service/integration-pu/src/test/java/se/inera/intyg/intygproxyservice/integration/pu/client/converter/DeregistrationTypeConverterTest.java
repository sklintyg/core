package se.inera.intyg.intygproxyservice.integration.pu.client.converter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.intygproxyservice.integration.pu.client.converter.DeregistrationTypeConverter.deceased;
import static se.inera.intyg.intygproxyservice.integration.pu.configuration.PuConstants.DEREGISTRATION_REASON_CODE_FOR_DECEASED;

import org.junit.jupiter.api.Test;
import se.riv.strategicresourcemanagement.persons.person.v3.DeregistrationType;

class DeregistrationTypeConverterTest {

  @Test
  void shallReturnTrueIfDeregistrationCodeIsAV() {
    final var deregistrationType = new DeregistrationType();
    deregistrationType.setDeregistrationReasonCode(DEREGISTRATION_REASON_CODE_FOR_DECEASED);
    assertTrue(
        deceased(deregistrationType)
    );
  }

  @Test
  void shallReturnFalseIfDeregistrationCodeIsNotAV() {
    final var deregistrationType = new DeregistrationType();
    deregistrationType.setDeregistrationReasonCode("NOT_AV");
    assertFalse(
        deceased(deregistrationType)
    );
  }

  @Test
  void shallReturnFalseIfDeregistrationCodeIsNull() {
    final var deregistrationType = new DeregistrationType();
    assertFalse(
        deceased(
            deregistrationType
        )
    );
  }

  @Test
  void shallReturnFalseIfDeregistrationIsNull() {
    assertFalse(deceased(null));
  }
}