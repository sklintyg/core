package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.model.CertificateModel;

@Component
public class CertificateTypeInfoConverter {

  public CertificateTypeInfoDTO convert(CertificateModel certificateModel) {
    return CertificateTypeInfoDTO.builder()
        .type(certificateModel.getId().getType().type())
        .name(certificateModel.getName())
        .description(certificateModel.getDescription())
        .build();
  }
}
