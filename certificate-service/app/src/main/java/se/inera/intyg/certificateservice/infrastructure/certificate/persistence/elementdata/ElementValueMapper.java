package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;

public interface ElementValueMapper {

  boolean supports(Class<?> c);

  ElementValue toDomain(MappedElementValue mappedValue);

  MappedElementValue toMapped(ElementValue value);
//  public ElementValue toDomain(MappedElementValue mappedValue) {
//    if (mappedValue instanceof MappedElementValueDate valueDate) {
//      return ElementValueDate.builder()
//          .dateId(new FieldId(valueDate.dateId))
//          .date(valueDate.getDate())
//          .build();
//    }
//    if (mappedValue instanceof MappedElementValueIssuingUnit valueIssuingUnit) {
//      return ElementValueUnitContactInformation.builder()
//          .address(valueIssuingUnit.getAddress())
//          .city(valueIssuingUnit.getCity())
//          .zipCode(valueIssuingUnit.getZipCode())
//          .phoneNumber(valueIssuingUnit.getPhoneNumber())
//          .build();
//    }
//
//    throw new IllegalStateException("MappedElementValue not supported '%s'".formatted(mappedValue));
//  }
//
//  public MappedElementValue toMapped(ElementValue value) {
//    if (value instanceof ElementValueDate elementValueDate) {
//      return MappedElementValueDate.builder()
//          .dateId(elementValueDate.dateId().value())
//          .date(elementValueDate.date())
//          .build();
//    }
//
//    if (value instanceof ElementValueUnitContactInformation elementValueUnitContactInformation) {
//      return MappedElementValueIssuingUnit.builder()
//          .address(elementValueUnitContactInformation.address())
//          .zipCode(elementValueUnitContactInformation.zipCode())
//          .city(elementValueUnitContactInformation.city())
//          .phoneNumber(elementValueUnitContactInformation.phoneNumber())
//          .build();
//    }
//
//    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
//  }
}
