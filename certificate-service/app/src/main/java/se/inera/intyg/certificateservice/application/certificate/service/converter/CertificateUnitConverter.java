package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.util.Optional;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.unit.model.IssuingUnit;

@Component
public class CertificateUnitConverter {


  public UnitDTO convert(IssuingUnit issuingUnit, Optional<ElementData> elementData) {
    return elementData
        .map(data -> {
          final var elementValue = (ElementValueUnitContactInformation) data.value();
          return UnitDTO.builder()
              .unitId(issuingUnit.hsaId().id())
              .unitName(issuingUnit.name().name())
              .address(elementValue.address())
              .city(elementValue.city())
              .zipCode(elementValue.zipCode())
              .phoneNumber(elementValue.phoneNumber())
              .email(
                  issuingUnit.contactInfo() == null ? null : issuingUnit.contactInfo().email()
              )
              .isInactive(
                  issuingUnit.inactive() == null ? Boolean.FALSE : issuingUnit.inactive().value()
              )
              .build();
        })
        .orElse(
            UnitDTO.builder()
                .unitId(issuingUnit.hsaId().id())
                .unitName(issuingUnit.name().name())
                .address(
                    issuingUnit.address() == null ? null : issuingUnit.address().address()
                )
                .city(
                    issuingUnit.address() == null ? null : issuingUnit.address().city()
                )
                .zipCode(
                    issuingUnit.address() == null ? null : issuingUnit.address().zipCode()
                )
                .phoneNumber(
                    issuingUnit.contactInfo() == null ? null
                        : issuingUnit.contactInfo().phoneNumber()
                )
                .email(
                    issuingUnit.contactInfo() == null ? null : issuingUnit.contactInfo().email()
                )
                .isInactive(
                    issuingUnit.inactive() == null ? Boolean.FALSE : issuingUnit.inactive().value()
                )
                .build()
        );
  }
}
