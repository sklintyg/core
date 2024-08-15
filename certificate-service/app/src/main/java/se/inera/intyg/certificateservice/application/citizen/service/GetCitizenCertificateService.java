package se.inera.intyg.certificateservice.application.citizen.service;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.service.converter.AvailableFunctionsFactory;
import se.inera.intyg.certificateservice.application.citizen.service.converter.CertificateTextConverter;
import se.inera.intyg.certificateservice.application.citizen.validation.CitizenCertificateRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.citizen.service.GetCitizenCertificateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

@Service
@RequiredArgsConstructor
public class GetCitizenCertificateService {

  private final GetCitizenCertificateDomainService getCitizenCertificateDomainService;
  private final CertificateConverter certificateConverter;
  private final CitizenCertificateRequestValidator citizenCertificateRequestValidator;
  private final CertificateTextConverter certificateTextConverter;
  private final AvailableFunctionsFactory availableFunctionsFactory;


  public GetCitizenCertificateResponse get(
      GetCitizenCertificateRequest getCitizenCertificateRequest,
      String certificateId) {
    citizenCertificateRequestValidator.validate(certificateId,
        getCitizenCertificateRequest.getPersonId());

    final var certificate = getCitizenCertificateDomainService.get(
        new CertificateId(certificateId),
        PersonId.builder()
            .id(getCitizenCertificateRequest.getPersonId().getId())
            .type(getCitizenCertificateRequest.getPersonId().getType().toPersonIdType())
            .build()
    );

    return GetCitizenCertificateResponse.builder()
        .certificate(certificateConverter.convert(certificate, Collections.emptyList(), null))
        .texts(
            certificate.certificateModel().texts().stream()
                .map(certificateTextConverter::convert)
                .toList()
        )
        .availableFunctions(availableFunctionsFactory.get(certificate))
        .build();
  }
}
