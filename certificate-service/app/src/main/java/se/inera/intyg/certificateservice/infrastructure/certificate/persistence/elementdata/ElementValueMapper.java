package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIssuingUnit;

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
    if (mappedValue instanceof MappedElementValueIssuingUnit valueIssuingUnit) {
      return ElementValueIssuingUnit.builder()
          .address(valueIssuingUnit.getAddress())
          .city(valueIssuingUnit.getCity())
          .zipCode(valueIssuingUnit.getZipCode())
          .phoneNumber(valueIssuingUnit.getPhoneNumber())
          .build();
    }

    throw new IllegalStateException("MappedElementValue not supported '%s'".formatted(mappedValue));
  }

  public static MappedElementValue toMapped(ElementValue value) {
    if (value instanceof ElementValueDate elementValueDate) {
      return MappedElementValueDate.builder()
          .date(elementValueDate.date())
          .build();
    }

    if (value instanceof ElementValueIssuingUnit elementValueIssuingUnit) {
      return MappedElementValueIssuingUnit.builder()
          .address(elementValueIssuingUnit.address())
          .zipCode(elementValueIssuingUnit.zipCode())
          .city(elementValueIssuingUnit.city())
          .phoneNumber(elementValueIssuingUnit.phoneNumber())
          .build();
    }

    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
  }
}
