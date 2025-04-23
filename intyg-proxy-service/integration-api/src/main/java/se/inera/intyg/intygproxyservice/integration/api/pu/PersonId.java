package se.inera.intyg.intygproxyservice.integration.api.pu;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.io.Serializable;

public record PersonId(String id) implements Serializable {

  @JsonCreator
  public PersonId(String id) {
    this.id = id != null ? id.replace("-", "").replace("+", "") : null;
  }

  public static PersonId of(String id) {
    return new PersonId(id);
  }

}