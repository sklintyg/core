package se.inera.intyg.certificateservice.domain.common.exception;

import lombok.Getter;
import se.inera.intyg.certificateservice.domain.unit.model.IssuingUnit;
import se.inera.intyg.certificateservice.domain.user.model.User;

@Getter
public class ConcurrentModificationException extends RuntimeException {

  private final transient User user;
  private final transient IssuingUnit unit;

  public ConcurrentModificationException(String message, User user, IssuingUnit unit) {
    super(message);
    this.unit = unit;
    this.user = user;
  }
}
