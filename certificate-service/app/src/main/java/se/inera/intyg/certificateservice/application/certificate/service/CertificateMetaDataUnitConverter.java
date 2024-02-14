package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Optional;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIssuingUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.IssuingUnit;

@Component
public class CertificateMetaDataUnitConverter {


  public UnitDTO convert(IssuingUnit issuingUnit, Optional<ElementData> elementData) {
    return elementData
        .map(data -> {
          final var elementValue = (ElementValueIssuingUnit) data.value();
          return UnitDTO.builder()
              .unitId(issuingUnit.hsaId().id())
              .unitName(issuingUnit.name().name())
              .address(elementValue.address())
              .city(elementValue.city())
              .zipCode(elementValue.zipCode())
              .phoneNumber(elementValue.phoneNumber())
              .email(issuingUnit.contactInfo().email())
              .isInactive(issuingUnit.inactive().value())
              .build();
        })
        .orElse(
            UnitDTO.builder()
                .unitId(issuingUnit.hsaId().id())
                .unitName(issuingUnit.name().name())
                .address(issuingUnit.address().address())
                .city(issuingUnit.address().city())
                .zipCode(issuingUnit.address().zipCode())
                .phoneNumber(issuingUnit.contactInfo().phoneNumber())
                .email(issuingUnit.contactInfo().email())
                .isInactive(issuingUnit.inactive().value())
                .build()
        );
  }
}
