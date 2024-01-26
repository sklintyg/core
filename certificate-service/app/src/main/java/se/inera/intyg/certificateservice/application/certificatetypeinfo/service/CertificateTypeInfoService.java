package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.repository.CertificateModelRepository;

@Service
@RequiredArgsConstructor
public class CertificateTypeInfoService {

  private final CertificateTypeInfoValidator certificateTypeInfoValidator;
  private final CertificateModelRepository certificateModelRepository;
  private final CertificateTypeInfoConverter certificateTypeInfoConverter;
  private final ActionEvaluationFactory actionEvaluationFactory;

  public List<CertificateTypeInfoDTO> getActiveCertificateTypeInfos(
      GetCertificateTypeInfoRequest getCertificateTypeInfoRequest) {
    certificateTypeInfoValidator.validate(getCertificateTypeInfoRequest);
    final var actionEvaluation = actionEvaluationFactory.create(
        getCertificateTypeInfoRequest.getPatient(),
        getCertificateTypeInfoRequest.getUser()
    );
    final var certificateModels = certificateModelRepository.findAllActive();
    return certificateModels.stream()
        .map(certificateModel -> {
              final var certificateActions = certificateModel.actions(actionEvaluation);
              return certificateTypeInfoConverter.convert(certificateModel, certificateActions);
            }
        )
        .toList();
  }
}
