package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;

public class CodeFactory {

  private CodeFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementConfigurationCode elementConfigurationCode(Code code) {
    return new ElementConfigurationCode(
        new FieldId(code.code()),
        code.displayName(),
        code
    );
  }

}
