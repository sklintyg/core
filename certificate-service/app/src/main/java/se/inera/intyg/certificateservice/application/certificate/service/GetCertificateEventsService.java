package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateEventConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.GetCertificateEventsRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateEventsDomainService;

@Service
@RequiredArgsConstructor
public class GetCertificateEventsService {

  private final GetCertificateEventsDomainService getCertificateEventsDomainService;
  private final GetCertificateEventsRequestValidator getCertificateEventsRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final CertificateEventConverter certificateEventConverter;

  public GetCertificateEventsResponse get(GetCertificateEventsRequest request,
      String certificateId) {
    getCertificateEventsRequestValidator.validate(request, certificateId);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var events = getCertificateEventsDomainService.get(new CertificateId(certificateId),
        actionEvaluation);

    return GetCertificateEventsResponse.builder()
        .events(
            events.stream()
                .map(certificateEventConverter::convert)
                .toList()
        )
        .build();
  }
}
