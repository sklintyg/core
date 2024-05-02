package se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.dto.GetLatestCertificateExternalTypeVersionRequest;
import se.inera.intyg.certificateservice.application.certificateexternaltypeinfo.dto.GetLatestCertificateExternalTypeVersionResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.model.Code;

@Service
@RequiredArgsConstructor
public class GetLatestCertificateExternalTypeVersionService {

  private final CertificateModelRepository certificateModelRepository;

  public GetLatestCertificateExternalTypeVersionResponse get(
      GetLatestCertificateExternalTypeVersionRequest request) {
    validateRequest(request);

    return certificateModelRepository.findLatestActiveByExternalType(
            new Code(
                request.getCode().getCode(),
                request.getCode().getCodeSystem(),
                null
            )
        )
        .map(certificateModel ->
            GetLatestCertificateExternalTypeVersionResponse.builder()
                .certificateModelId(
                    CertificateModelIdDTO.builder()
                        .type(certificateModel.id().type().type())
                        .version(certificateModel.id().version().version())
                        .build()
                )
                .build()
        )
        .orElse(
            GetLatestCertificateExternalTypeVersionResponse.builder().build()
        );
  }

  private static void validateRequest(GetLatestCertificateExternalTypeVersionRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request was null");
    }

    if (request.getCode() == null) {
      throw new IllegalArgumentException("Required parameter missing: 'codeDTO'");
    }

    if (request.getCode().getCodeSystem() == null || request.getCode().getCodeSystem().isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: 'codeSystem'");
    }

    if (request.getCode().getCode() == null || request.getCode().getCode().isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: 'code'");
    }
  }
}
