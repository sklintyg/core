package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedDeregistration;

public abstract class DeceasedConverter {

  public static final String DEREGISTRATION_REASON_CODE_FOR_DECEASED = "AV";

  private DeceasedConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean convert(ParsedDeregistration deregistration) {
    return deregistration != null && DEREGISTRATION_REASON_CODE_FOR_DECEASED.equals(
        deregistration.getDeregistrationReasonCode()
    );
  }
}
