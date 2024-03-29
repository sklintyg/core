package se.inera.intyg.certificateservice.application.unit.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.CertificatesRequestFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesResponse;
import se.inera.intyg.certificateservice.application.unit.service.validator.GetUnitCertificatesRequestValidator;
import se.inera.intyg.certificateservice.domain.unit.service.GetUnitCertificatesDomainService;

@Service
@RequiredArgsConstructor
public class GetUnitCertificatesService {

  private final GetUnitCertificatesRequestValidator getUnitCertificatesRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final CertificatesRequestFactory certificatesRequestFactory;
  private final GetUnitCertificatesDomainService getUnitCertificatesDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  public GetUnitCertificatesResponse get(GetUnitCertificatesRequest getUnitCertificatesRequest) {
    getUnitCertificatesRequestValidator.validate(getUnitCertificatesRequest);

    final var actionEvaluation = actionEvaluationFactory.create(
        getUnitCertificatesRequest.getPatient(),
        getUnitCertificatesRequest.getUser(),
        getUnitCertificatesRequest.getUnit(),
        getUnitCertificatesRequest.getCareUnit(),
        getUnitCertificatesRequest.getCareProvider()
    );

    final var certificatesRequest = certificatesRequestFactory.create(
        getUnitCertificatesRequest.getCertificatesQueryCriteria()
    );

    final var certificates = getUnitCertificatesDomainService.get(
        certificatesRequest,
        actionEvaluation
    );

    return GetUnitCertificatesResponse.builder()
        .certificates(certificates.stream()
            .map(certificate -> certificateConverter.convert(
                    certificate,
                    certificate.actions(actionEvaluation).stream()
                        .map(resourceLinkConverter::convert)
                        .toList()
                )
            )
            .toList()
        )
        .build();
  }
}
