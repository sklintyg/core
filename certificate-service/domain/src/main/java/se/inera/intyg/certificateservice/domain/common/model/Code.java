package se.inera.intyg.certificateservice.domain.common.model;

public record Code(String code, String codeSystem, String displayName) {

  public boolean matches(Code code) {
    return this.codeSystem.equals(code.codeSystem) && this.code.equals(code.code);
  }
}
