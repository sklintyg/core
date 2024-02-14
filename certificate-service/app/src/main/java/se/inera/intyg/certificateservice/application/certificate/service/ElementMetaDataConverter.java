package se.inera.intyg.certificateservice.application.certificate.service;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIssuingUnit.ISSUING_UNIT;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIssuingUnit;

@Component
public class ElementMetaDataConverter {

  public ElementData convert(UnitDTO unit) {
    return
        ElementData.builder()
            .id(ISSUING_UNIT)
            .value(
                ElementValueIssuingUnit.builder()
                    .address(unit.getAddress())
                    .city(unit.getCity())
                    .zipCode(unit.getZipCode())
                    .phoneNumber(unit.getPhoneNumber())
                    .build()
            )
            .build();
  }
}
