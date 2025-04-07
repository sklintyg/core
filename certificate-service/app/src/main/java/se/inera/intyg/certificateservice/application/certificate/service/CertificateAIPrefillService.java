package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateAIPrefillRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateAIPrefillResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.prefill.service.PrefillService;

@Service
@RequiredArgsConstructor
public class CertificateAIPrefillService {

  private final CertificateRepository certificateRepository;
  private final PrefillService prefillService;
  private final CertificateConverter certificateConverter;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final ResourceLinkConverter resourceLinkConverter;

  public CertificateAIPrefillResponse prefill(CertificateAIPrefillRequest request,
      String certificateId) {

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var certificate = certificateRepository.getById(new CertificateId(certificateId));
    final var prefilledResponse = prefillService.prefill(certificate, request.getPreFillData());
    certificateRepository.save(prefilledResponse.getCertificate());

    return CertificateAIPrefillResponse.builder()
        .certificate(
            certificateConverter.convert(
                certificate,
                certificate.actionsInclude(Optional.of(actionEvaluation)).stream()
                    .map(certificateAction ->
                        resourceLinkConverter.convert(
                            certificateAction,
                            Optional.of(certificate),
                            actionEvaluation
                        )
                    )
                    .toList(),
                actionEvaluation
            )
        )
        .build();
  }
}
