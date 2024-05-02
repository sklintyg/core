package se.inera.intyg.certificateservice.domain.common.model;

import java.util.Objects;

public record Code(String code, String codeSystem, String displayName) {

  
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Code code1 = (Code) o;
    return Objects.equals(code, code1.code) && Objects.equals(codeSystem,
        code1.codeSystem);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, codeSystem);
  }
}
