package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.common.model.Code;

public record ElementMapping(ElementId elementId, Code code,
                             Optional<Class<? extends ElementValue>> elementValueMapper) {

  public ElementMapping(ElementId elementId, Code code) {
    this(elementId, code, Optional.empty());
  }
}