package se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter;

import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.DEREGISTRATION_REASON_CODE_FOR_DECEASED;

import se.riv.strategicresourcemanagement.persons.person.v5.DeregistrationType;


public class DeregistrationTypeConverter {

  private DeregistrationTypeConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean deceased(DeregistrationType deregistrationType) {
    return deregistrationType != null && DEREGISTRATION_REASON_CODE_FOR_DECEASED.equals(
        deregistrationType.getDeregistrationReasonCode()
    );
  }
}
