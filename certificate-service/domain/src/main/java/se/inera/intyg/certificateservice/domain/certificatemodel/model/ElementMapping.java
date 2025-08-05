package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.CustomMapperId;
import se.inera.intyg.certificateservice.domain.common.model.Code;

public record ElementMapping(ElementId elementId, Code code,
                             Optional<CustomMapperId> customMapperId) {

  public ElementMapping(ElementId elementId, Code code) {
    this(elementId, code, Optional.empty());
  }

  public ElementMapping(CustomMapperId customMapperId) {
    this(null, null, Optional.of(customMapperId));
  }
}