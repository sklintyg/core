package se.inera.intyg.intygproxyservice.integration.api.constants;

public abstract class PuConstants {

  public static final String FAKE_PU_PROFILE = "fakepu";
  public static final String PU_PROFILE_V3 = "pu_v3";
  public static final String PU_PROFILE_V4 = "pu_v4";
  public static final String PU_PROFILE_V5 = "pu_v5";

  private PuConstants() {
    throw new IllegalStateException("Class to keep constants");
  }
}
