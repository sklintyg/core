package se.inera.intyg.intygproxyservice.integration.pu.client.converter;

import static se.inera.intyg.intygproxyservice.integration.pu.configuration.PuConstants.DEREGISTRATION_REASON_CODE_FOR_DECEASED;

import se.riv.strategicresourcemanagement.persons.person.v3.DeregistrationType;

public abstract class DeregistrationTypeConverter {

  private DeregistrationTypeConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean deceased(DeregistrationType deregistrationType) {
    return deregistrationType != null && DEREGISTRATION_REASON_CODE_FOR_DECEASED.equals(
        deregistrationType.getDeregistrationReasonCode()
    );
  }
}
