package se.inera.intyg.certificateservice.domain.certificate.service;

import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.UPDATE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@RequiredArgsConstructor
public class GetCertificatePdfDomainService {

  private final CertificateRepository certificateRepository;
  private final PdfGeneratorProvider pdfGeneratorProvider;
  private final CertificateEventDomainService certificateEventDomainService;


  public Pdf get(CertificateId certificateId, ActionEvaluation actionEvaluation,
      String additionalInfoText) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());

    final var certificate = certificateRepository.getById(certificateId);

    if (!certificate.allowTo(CertificateActionType.PRINT, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to print certificate for %s".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.PRINT, Optional.of(actionEvaluation))
      );
    }

    if (certificate.isDraft() && certificate.allowTo(UPDATE, Optional.of(actionEvaluation))) {
      certificate.updateMetadata(actionEvaluation);
    }

    final var generatedPdf = pdfGeneratorProvider.provider(certificate)
        .generate(certificate, additionalInfoText, false);

    certificateEventDomainService.publish(
        CertificateEvent.builder()
            .type(CertificateEventType.PRINT)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .certificate(certificate)
            .actionEvaluation(actionEvaluation)
            .build()
    );

    return generatedPdf;
  }
}
