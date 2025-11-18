package se.inera.intyg.certificateservice.application.citizen.service;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateListRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateListResponse;
import se.inera.intyg.certificateservice.application.citizen.validation.CitizenCertificateRequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

@Service
@RequiredArgsConstructor
public class GetCitizenCertificateListService {

  private final CertificateConverter certificateConverter;
  private final CitizenCertificateRequestValidator citizenCertificateRequestValidator;
  private final CertificateRepository certificateRepository;

  public GetCitizenCertificateListResponse get(
      GetCitizenCertificateListRequest getCitizenCertificateListRequest) {
    citizenCertificateRequestValidator.validate(getCitizenCertificateListRequest.getPersonId());

    final var certificates = certificateRepository.findByCertificatesRequest(
        CertificatesRequest.builder()
            .personId(PersonId.builder()
                .id(getCitizenCertificateListRequest.getPersonId().getId())
                .type(getCitizenCertificateListRequest.getPersonId().getType().toPersonIdType())
                .build()
            )
            .statuses(List.of(Status.SIGNED))
            .build()
    );

    certificateRepository.updateCertificateMetadataFromSignInstances(certificates);

    return GetCitizenCertificateListResponse.builder()
        .citizenCertificates(certificates.stream()
            .filter(certificate -> certificate.certificateModel().availableForCitizen())
            .filter(
                certificate -> !certificate.certificateMetaData().patient().testIndicated().value()
            )
            .map(certificate -> certificateConverter.convert(certificate, Collections.emptyList(),
                null))
            .toList()
        )
        .build();
  }
}
