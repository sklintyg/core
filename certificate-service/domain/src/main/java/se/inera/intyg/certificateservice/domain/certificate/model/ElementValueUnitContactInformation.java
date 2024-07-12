package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;
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
}
