package se.inera.intyg.certificateservice.domain.certificatemodel.model;

public interface ElementConfiguration {

  default String name() {
    return null;
  }

  ElementType type();

}
