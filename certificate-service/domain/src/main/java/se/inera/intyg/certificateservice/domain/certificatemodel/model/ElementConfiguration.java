package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;

public interface ElementConfiguration {

  default String name() {
    return null;
  }

  default String description() {
    return null;
  }

  default String label() {
    return null;
  }

  default String header() {
    return null;
  }

  ElementType type();

  ElementValue emptyValue();

  default Optional<ElementSimplifiedValue> simplified(ElementValue value) {
    return Optional.empty();
  }

  ElementMessage message();

  FieldId id();

  default Optional<ElementData> convert(ElementData elementData,
      ElementSpecification specification) {
    return Optional.of(elementData);
  }
}