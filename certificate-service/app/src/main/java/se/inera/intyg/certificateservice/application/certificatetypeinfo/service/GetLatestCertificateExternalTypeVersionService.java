package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateExternalTypeVersionResponse;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.model.Code;

@Service
@RequiredArgsConstructor
public class GetLatestCertificateExternalTypeVersionService {

  private final CertificateModelRepository certificateModelRepository;

  public GetLatestCertificateExternalTypeVersionResponse get(
      String codeSystem, String code) {
    validateParameters(codeSystem, code);

    return certificateModelRepository.findLatestActiveByExternalType(
            new Code(code, codeSystem, null)
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

  private static void validateParameters(String codeSystem, String code) {
    if (codeSystem == null || codeSystem.isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: 'codeSystem'");
    }

    if (code == null || code.isBlank()) {
      throw new IllegalArgumentException("Required parameter missing: 'code'");
    }
  }
}
