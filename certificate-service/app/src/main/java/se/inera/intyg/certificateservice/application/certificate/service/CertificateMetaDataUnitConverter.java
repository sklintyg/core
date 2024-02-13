package se.inera.intyg.certificateservice.application.certificate.service;

import static se.inera.intyg.certificateservice.application.common.CertificateConstants.ISSUING_UNIT;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIssuingUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.IssuingUnit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

@Component
public class CertificateMetaDataUnitConverter {


  public UnitDTO convert(IssuingUnit issuingUnit, List<ElementData> elementData) {
    final var elementIdElementValueMap = elementData.stream()
        .collect(Collectors.toMap(ElementData::id, ElementData::value));

    final var elementValue = (ElementValueIssuingUnit) elementIdElementValueMap.getOrDefault(
        new ElementId(ISSUING_UNIT), null);

    return UnitDTO.builder()
        .unitId(issuingUnit.hsaId().id())
        .unitName(issuingUnit.name().name())
        .address(elementValue != null ? elementValue.address() : issuingUnit.address().address())
        .city(elementValue != null ? elementValue.city() : issuingUnit.address().city())
        .zipCode(elementValue != null ? elementValue.zipCode() : issuingUnit.address().zipCode())
        .phoneNumber(elementValue != null ?
            elementValue.phoneNumber()
            : issuingUnit.contactInfo().phoneNumber()
        )
        .email(issuingUnit.contactInfo().email())
        .isInactive(issuingUnit.inactive().value())
        .build();
  }
}
