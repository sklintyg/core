package se.inera.intyg.certificateservice.application.patient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.service.CertificateConverter;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesRequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesResponse;
import se.inera.intyg.certificateservice.domain.patient.GetPatientCertificatesDomainService;

@Service
@RequiredArgsConstructor
public class GetPatientCertificateService {

  private final GetPatientCertificatesRequestValidator getPatientCertificatesRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final GetPatientCertificatesDomainService getPatientCertificatesDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  public GetPatientCertificatesResponse get(
      GetPatientCertificatesRequest getPatientCertificatesRequest) {
    getPatientCertificatesRequestValidator.validate(getPatientCertificatesRequest);

    final var actionEvaluation = actionEvaluationFactory.create(
        getPatientCertificatesRequest.getPatient(),
        getPatientCertificatesRequest.getUser(),
        getPatientCertificatesRequest.getUnit(),
        getPatientCertificatesRequest.getCareUnit(),
        getPatientCertificatesRequest.getCareProvider()
    );

    final var certificates = getPatientCertificatesDomainService.get(actionEvaluation);

    return GetPatientCertificatesResponse.builder()
        .certificate(certificates.stream()
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
