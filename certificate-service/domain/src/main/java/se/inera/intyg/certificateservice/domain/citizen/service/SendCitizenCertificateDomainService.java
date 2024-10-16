package se.inera.intyg.certificateservice.domain.citizen.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CitizenCertificateForbidden;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@RequiredArgsConstructor
public class SendCitizenCertificateDomainService {

  private final CertificateRepository certificateRepository;
  private final CertificateEventDomainService certificateEventDomainService;

  public Certificate send(CertificateId certificateId, PersonId personId) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());
    final var certificate = certificateRepository.getById(certificateId);

    if (!certificate.isCertificateIssuedOnPatient(personId)) {
      throw new CitizenCertificateForbidden(
          "Citizen is trying to send certificate with id '%s', but it is not issued on citizen"
              .formatted(certificateId)
      );
    }
    if (Boolean.FALSE.equals(certificate.certificateModel().availableForCitizen())) {
      throw new CitizenCertificateForbidden(
          "Citizen is trying to send certificate with id '%s', but it is not available for citizen"
              .formatted(certificateId)
      );
    }

    certificate.sendByCitizen();

    final var sentCertificate = certificateRepository.save(certificate);

    certificateEventDomainService.publish(
        CertificateEvent.builder()
            .type(CertificateEventType.SENT)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .certificate(sentCertificate)
            .actionEvaluation(null)
            .build()
    );

    return sentCertificate;
  }
}