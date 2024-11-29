package se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration;

public abstract class PuConstants {

  private PuConstants() {
    throw new IllegalStateException("Class to keep constants");
  }

  public static final String CODE_PERSONAL_ID = "1.2.752.129.2.1.3.1";
  public static final String CODE_COORDINATION_NUMBER = "1.2.752.129.2.1.3.3";
  public static final int COORDINATION_NUMBER_MONTH_INDEX = 6;
  public static final int COORDINATION_NUMBER_MONTH_VALUE_MIN = 6;
  public static final String DEREGISTRATION_REASON_CODE_FOR_DECEASED = "AV";
}
