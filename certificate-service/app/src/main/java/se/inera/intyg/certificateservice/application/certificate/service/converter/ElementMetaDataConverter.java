package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;

@Component
public class ElementMetaDataConverter {

  public List<ElementData> convert(CertificateMetadataDTO certificateMetadataDTO) {
    final var unit = certificateMetadataDTO.getUnit();
    return List.of(
        ElementData.builder()
            .id(UNIT_CONTACT_INFORMATION)
            .value(
                ElementValueUnitContactInformation.builder()
                    .address(unit.getAddress())
                    .city(unit.getCity())
                    .zipCode(unit.getZipCode())
                    .phoneNumber(unit.getPhoneNumber())
                    .build()
            )
            .build()
    );
  }
}
