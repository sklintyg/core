package se.inera.intyg.certificateservice.application.citizen.service;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.validation.CitizenCertificateRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.citizen.service.SendCitizenCertificateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

@Service
@RequiredArgsConstructor
public class SendCitizenCertificateService {

  private final SendCitizenCertificateDomainService sendCitizenCertificateDomainService;
  private final CitizenCertificateRequestValidator citizenCertificateRequestValidator;
  private final CertificateConverter certificateConverter;


  public SendCitizenCertificateResponse send(SendCitizenCertificateRequest request,
      String certificateId) {
    citizenCertificateRequestValidator.validate(certificateId, request.getPersonId());
    final var certificate = sendCitizenCertificateDomainService.send(
        new CertificateId(certificateId),
        PersonId.builder()
            .id(request.getPersonId().getId())
            .type(request.getPersonId().getType().toPersonIdType())
            .build()
    );

    return SendCitizenCertificateResponse.builder()
        .citizenCertificate(
            certificateConverter.convertForCitizen(certificate, Collections.emptyList()))
        .build();
  }

}