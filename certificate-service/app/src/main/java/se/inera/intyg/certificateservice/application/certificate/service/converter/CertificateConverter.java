package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Component
@RequiredArgsConstructor
public class CertificateConverter {

  private final CertificateDataConverter certificateDataConverter;
  private final CertificateMetadataConverter certificateMetadataConverter;

  public CertificateDTO convert(Certificate certificate, List<ResourceLinkDTO> resourceLinks) {
    return CertificateDTO.builder()
        .metadata(
            certificateMetadataConverter.convert(certificate)
        )
        .data(
            certificateDataConverter.convert(certificate)
        )
        .links(resourceLinks)
        .build();
  }
}
