package se.inera.intyg.certificateservice.application.citizen.service.converter;

import java.util.Collections;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateLinkDTO;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateTextDTO;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateTextTypeDTO;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;

@Component()
public class CertificateTextConverter {

  public CertificateTextDTO convert(CertificateText certificateText) {
    return CertificateTextDTO.builder()
        .text(certificateText.text())
        .type(convertCertificateTextType(certificateText.type()))
        .links(certificateText.links() == null ? Collections.emptyList()
            : certificateText.links().stream()
                .map(link -> CertificateLinkDTO.builder()
                    .url(link.url())
                    .id(link.id())
                    .name(link.name())
                    .build()
                )
                .toList())
        .build();
  }

  private CertificateTextTypeDTO convertCertificateTextType(
      CertificateTextType certificateTextType) {
    if (certificateTextType == CertificateTextType.PREAMBLE_TEXT) {
      return CertificateTextTypeDTO.PREAMBLE_TEXT;
    }
    throw new IllegalArgumentException(
        "Certificate text type '%s' is not supported".formatted(certificateTextType));
  }
}
