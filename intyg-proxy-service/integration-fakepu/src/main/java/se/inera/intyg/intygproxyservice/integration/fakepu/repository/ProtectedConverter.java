package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

public abstract class ProtectedConverter {

  private ProtectedConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static boolean convert(boolean protectedPersonIndicator,
      boolean protectedPopulationRecord) {
    return protectedPersonIndicator || protectedPopulationRecord;
  }
}
