package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.unit.model.IssuingUnit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidator;

@Value
@Builder
public class ElementValueUnitContactInformation implements ElementValue {

  String address;
  String city;
  String zipCode;
  String phoneNumber;

  @Override
  public boolean isEmpty() {
    return !ElementValidator.isTextDefined(address) || !ElementValidator.isTextDefined(city)
        || !ElementValidator.isTextDefined(zipCode) || !ElementValidator.isTextDefined(phoneNumber);
  }

  public ElementValueUnitContactInformation copy(IssuingUnit issuingUnit) {
    return ElementValueUnitContactInformation.builder()
        .address(
            ElementValidator.isTextDefined(issuingUnit.address().address())
                ? issuingUnit.address().address()
                : this.address
        )
        .city(
            ElementValidator.isTextDefined(issuingUnit.address().city())
                ? issuingUnit.address().city()
                : this.city
        )
        .zipCode(
            ElementValidator.isTextDefined(issuingUnit.address().zipCode())
                ? issuingUnit.address().zipCode()
                : this.zipCode
        )
        .phoneNumber(
            ElementValidator.isTextDefined(issuingUnit.contactInfo().phoneNumber())
                ? issuingUnit.contactInfo().phoneNumber()
                : this.phoneNumber
        )
        .build();
  }
}
