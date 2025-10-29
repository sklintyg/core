package se.inera.intyg.certificateservice.application.common;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalRequest;
import se.inera.intyg.certificateservice.application.unit.dto.CertificatesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;

public class CertificatesRequestFactory {

  public static CertificatesRequest create() {
    return CertificatesRequest.builder()
        .statuses(Status.unsigned())
        .build();
  }

  public static CertificatesRequest create(CertificatesQueryCriteriaDTO queryCriteria) {
    return CertificatesRequest.builder()
        .modifiedFrom(queryCriteria.getFrom())
        .modifiedTo(queryCriteria.getTo())
        .issuedByStaffIds(
            queryCriteria.getIssuedByStaffId() != null
                ? List.of(new HsaId(queryCriteria.getIssuedByStaffId()))
                : null
        )
        .personId(
            queryCriteria.getPersonId() != null
                ? PersonId.builder()
                .id(queryCriteria.getPersonId().getId())
                .type(PersonIdType.valueOf(queryCriteria.getPersonId().getType()))
                .build()
                : null
        )
        .statuses(
            queryCriteria.getStatuses() == null ? Collections.emptyList() :
                queryCriteria.getStatuses()
                    .stream()
                    .map(CertificatesRequestFactory::convertStatus)
                    .filter(Objects::nonNull)
                    .toList()
        )
        .validCertificates(queryCriteria.getValidForSign())
        .build();
  }

  public static CertificatesRequest convert(GetSickLeaveCertificatesInternalRequest request) {
    return CertificatesRequest.builder()
        .personId(PersonId.builder().id(request.getPersonId()
                .getId())
            .type(PersonIdType.valueOf(request.getPersonId().getType()))
            .build())
        .signedFrom(request.getSignedFrom())
        .signedTo(request.getSignedTo())
        .types(
            request.getCertificateTypes().stream()
                .map(CertificateType::new)
                .toList()
        )
        .issuedUnitIds(request.getIssuedByUnitIds().stream().map(HsaId::new).toList())
        .issuedByStaffIds(request.getIssuedByStaffIds().stream().map(HsaId::new).toList())
        .build();
  }

  private static Status convertStatus(CertificateStatusTypeDTO status) {
    return switch (status) {
      case UNSIGNED -> Status.DRAFT;
      case SIGNED -> Status.SIGNED;
      case LOCKED -> Status.LOCKED_DRAFT;
      case LOCKED_REVOKED, REVOKED -> null;
    };
  }
}
