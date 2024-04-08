package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;

public interface ElementValueMapper {

  boolean supports(Class<?> c);

  ElementValue toDomain(MappedElementValue mappedValue);

  MappedElementValue toMapped(ElementValue value);
}
