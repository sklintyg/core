package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;

@Component
public class ElementValueMapperIssuingUnit implements ElementValueMapper {

  @Override
  public boolean supports(Class<?> c) {
    return c.equals(MappedElementValueIssuingUnit.class)
        || c.equals(ElementValueUnitContactInformation.class);
  }

  @Override
  public ElementValue toDomain(MappedElementValue mappedValue) {
    if (mappedValue instanceof MappedElementValueIssuingUnit valueIssuingUnit) {
      return ElementValueUnitContactInformation.builder()
          .address(valueIssuingUnit.getAddress())
          .city(valueIssuingUnit.getCity())
          .zipCode(valueIssuingUnit.getZipCode())
          .phoneNumber(valueIssuingUnit.getPhoneNumber())
          .build();
    }
    throw new IllegalStateException("MappedElementValue not supported '%s'".formatted(mappedValue));
  }

  @Override
  public MappedElementValue toMapped(ElementValue value) {
    if (value instanceof ElementValueUnitContactInformation elementValueUnitContactInformation) {
      return MappedElementValueIssuingUnit.builder()
          .address(elementValueUnitContactInformation.address())
          .zipCode(elementValueUnitContactInformation.zipCode())
          .city(elementValueUnitContactInformation.city())
          .phoneNumber(elementValueUnitContactInformation.phoneNumber())
          .build();
    }
    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
  }
}
