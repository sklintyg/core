package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;

public interface ElementConfiguration {

  default String name() {
    return null;
  }

  default String label() {
    return null;
  }

  ElementType type();

  ElementValue emptyValue();
}
