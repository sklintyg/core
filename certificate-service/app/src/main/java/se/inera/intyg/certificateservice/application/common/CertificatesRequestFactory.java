package se.inera.intyg.certificateservice.application.common;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.unit.dto.CertificatesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;

@Component
public class CertificatesRequestFactory {

  public CertificatesRequest create() {
    return CertificatesRequest.builder()
        .statuses(Status.all())
        .build();
  }

  public CertificatesRequest create(CertificatesQueryCriteriaDTO queryCriteria) {
    return CertificatesRequest.builder()
        .from(queryCriteria.getFrom())
        .to(queryCriteria.getTo())
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
            containsStatusToMapToDraft(queryCriteria.getStatuses())
                ? List.of(Status.DRAFT)
                : Collections.emptyList()
        )
        .build();
  }

  private static boolean containsStatusToMapToDraft(List<CertificateStatusTypeDTO> statuses) {
    return statuses == null || statuses.isEmpty()
        || statuses.contains(CertificateStatusTypeDTO.UNSIGNED);
  }
}
