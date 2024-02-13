package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;

public class ElementValueMapper {

  private ElementValueMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementValue toDomain(MappedElementValue mappedValue) {
    if (mappedValue instanceof MappedElementValueDate valueDate) {
      return ElementValueDate.builder()
          .date(valueDate.getDate())
          .build();
    }

    return null;
  }

  public static MappedElementValue toMapped(ElementValue element) {
    if (element instanceof ElementValueDate elementValueDate) {
      return MappedElementValueDate.builder()
          .date(elementValueDate.date())
          .build();
    }

    return null;
  }

}
