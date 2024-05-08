package se.inera.intyg.certificateservice.application.citizen.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateLinkDTO;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateTextDTO;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateTextTypeDTO;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;

@Component()
public class CertificateTextConverter {

  public CertificateTextDTO convert(CertificateText certificateText) {
    return CertificateTextDTO.builder()
        .text(certificateText.text())
        .type(CertificateTextTypeDTO.valueOf(certificateText.type().name()))
        .links(certificateText.links().stream()
            .map(link -> CertificateLinkDTO.builder()
                .url(link.url())
                .id(link.id())
                .name(link.name())
                .build())
            .toList())
        .build();
  }
}
