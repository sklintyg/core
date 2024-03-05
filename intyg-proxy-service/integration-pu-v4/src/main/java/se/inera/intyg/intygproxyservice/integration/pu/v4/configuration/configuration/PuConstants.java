package se.inera.intyg.intygproxyservice.integration.pu.v4.configuration.configuration;

public abstract class PuConstants {

  private PuConstants() {
    throw new IllegalStateException("Class to keep constants");
  }

  public static final String KODVERK_PERSONNUMMER = "1.2.752.129.2.1.3.1";
  public static final String KODVERK_SAMORDNINGSNUMMER = "1.2.752.129.2.1.3.3";
  public static final int SAMORDNING_MONTH_INDEX = 6;
  public static final int SAMORDNING_MONTH_VALUE_MIN = 6;
  public static final String DEREGISTRATION_REASON_CODE_FOR_DECEASED = "AV";
}
