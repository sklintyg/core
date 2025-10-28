package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.converter.CertificateTypeInfoConverter;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.validator.CertificateTypeInfoValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.service.ListAvailableCertificateModelsDomainService;

@Service
@RequiredArgsConstructor
public class GetCertificateTypeInfoService {

  private final CertificateTypeInfoValidator certificateTypeInfoValidator;
  private final CertificateTypeInfoConverter certificateTypeInfoConverter;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final ListAvailableCertificateModelsDomainService availableCertificateModelsDomainService;

  public GetCertificateTypeInfoResponse getActiveCertificateTypeInfos(
      GetCertificateTypeInfoRequest getCertificateTypeInfoRequest) {
    certificateTypeInfoValidator.validate(getCertificateTypeInfoRequest);
    final var actionEvaluation = actionEvaluationFactory.create(
        getCertificateTypeInfoRequest.getPatient(),
        getCertificateTypeInfoRequest.getUser(),
        getCertificateTypeInfoRequest.getUnit(),
        getCertificateTypeInfoRequest.getCareUnit(),
        getCertificateTypeInfoRequest.getCareProvider()
    );

    final var certificateModels = availableCertificateModelsDomainService.getLatestVersions(
        actionEvaluation);
    return GetCertificateTypeInfoResponse.builder()
        .list(
            certificateModels.stream()
                .map(certificateModel ->
                    certificateTypeInfoConverter.convert(
                        certificateModel,
                        certificateModel.actionsInclude(Optional.of(actionEvaluation)),
                        actionEvaluation
                    )
                )
                .toList()
        )
        .build();
  }
}
