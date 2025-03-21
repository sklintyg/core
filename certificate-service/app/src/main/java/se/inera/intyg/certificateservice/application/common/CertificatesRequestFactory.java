package se.inera.intyg.certificateservice.application.common;

import java.util.Collections;
import java.util.Objects;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.unit.dto.CertificatesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;

@Component
public class CertificatesRequestFactory {

  public CertificatesRequest create() {
    return CertificatesRequest.builder()
        .statuses(Status.unsigned())
        .build();
  }

  public CertificatesRequest create(CertificatesQueryCriteriaDTO queryCriteria) {
    return CertificatesRequest.builder()
        .modifiedFrom(queryCriteria.getFrom())
        .modifiedTo(queryCriteria.getTo())
        .issuedByStaffId(
            queryCriteria.getIssuedByStaffId() != null
                ? new HsaId(queryCriteria.getIssuedByStaffId())
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
                    .map(this::convertStatus)
                    .filter(Objects::nonNull)
                    .toList()
        )
        .validCertificates(queryCriteria.getValidForSign())
        .build();
  }

  private Status convertStatus(CertificateStatusTypeDTO status) {
    return switch (status) {
      case UNSIGNED -> Status.DRAFT;
      case SIGNED -> Status.SIGNED;
      case LOCKED -> Status.LOCKED_DRAFT;
      case LOCKED_REVOKED, REVOKED -> null;
    };
  }
}
