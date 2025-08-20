package se.inera.intyg.intygproxyservice.integration.api.elva77;

import se.inera.intyg.intygproxyservice.integration.api.pu.Status;

public enum Result {
  OK, INFO, ERROR;

  public Status toStatus() {
    return switch (this) {
      case OK, INFO -> Status.FOUND;
      case ERROR -> Status.ERROR;
    };
  }
}