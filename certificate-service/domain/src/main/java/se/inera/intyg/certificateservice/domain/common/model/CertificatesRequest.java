package se.inera.intyg.certificateservice.domain.common.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

@Value
@Builder
public class CertificatesRequest {

  LocalDateTime modifiedFrom;
  LocalDateTime modifiedTo;
  LocalDateTime createdFrom;
  LocalDateTime createdTo;
  List<Status> statuses;
  HsaId issuedByStaffId;
  @With
  HsaId issuedUnitId;
  @With
  HsaId careUnitId;
  PersonId personId;
  Boolean validCertificates;

  public CertificatesRequest apply(ActionEvaluation actionEvaluation) {
    if (issuedUnitId() == null && actionEvaluation.isIssuingUnitSubUnit()) {
      return this.withIssuedUnitId(actionEvaluation.subUnit().hsaId());
    }

    if (issuedUnitId() == null && actionEvaluation.isIssuingUnitCareUnit()) {
      return this.withCareUnitId(actionEvaluation.careUnit().hsaId());
    }

    return this;
  }
}
