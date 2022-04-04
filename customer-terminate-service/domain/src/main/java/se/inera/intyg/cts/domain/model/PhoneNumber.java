package se.inera.intyg.cts.domain.model;

import java.util.Objects;

public record PhoneNumber(String number) {

  public PhoneNumber {
    Objects.requireNonNull(number, "Missing PhoneNumber");
  }
}
