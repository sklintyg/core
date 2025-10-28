package se.inera.intyg.certificateservice.domain.common.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;

@Value
@Builder
public class CertificatesRequest {

  LocalDateTime modifiedFrom;
  LocalDateTime modifiedTo;
  LocalDateTime createdFrom;
  LocalDateTime createdTo;
  List<Status> statuses;
  List<CertificateType> types;
  List<HsaId> issuedByStaffIds;
  @With
  List<HsaId> issuedUnitIds;
  @With
  HsaId careUnitId;
  PersonId personId;
  Boolean validCertificates;
  LocalDate signedFrom;
  LocalDate signedTo;

  public CertificatesRequest apply(ActionEvaluation actionEvaluation) {
    if (issuedUnitIds() == null && actionEvaluation.isIssuingUnitSubUnit()) {
      return this.withIssuedUnitIds(List.of(actionEvaluation.subUnit().hsaId()));
    }

    if (issuedUnitIds() == null && actionEvaluation.isIssuingUnitCareUnit()) {
      return this.withCareUnitId(actionEvaluation.careUnit().hsaId());
    }

    return this;
  }
}
