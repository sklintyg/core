package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateTypeVersionResponse;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;

@Service
@RequiredArgsConstructor
public class GetLatestCertificateTypeVersionService {

  private final CertificateModelRepository certificateModelRepository;

  public GetLatestCertificateTypeVersionResponse get(String type) {
    if (type == null || type.isBlank()) {
      throw new IllegalArgumentException("Required parameter type missing: '%s'".formatted(type));
    }

    return certificateModelRepository.findLatestActiveByType(new CertificateType(type))
        .map(certificateModel ->
            GetLatestCertificateTypeVersionResponse.builder()
                .certificateModelId(
                    CertificateModelIdDTO.builder()
                        .type(certificateModel.id().type().type())
                        .version(certificateModel.id().version().version())
                        .build()
                )
                .build()
        )
        .orElse(
            GetLatestCertificateTypeVersionResponse.builder().build()
        );
  }
}
