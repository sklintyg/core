package se.inera.intyg.certificateservice.domain.common.model;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;

@Value
@Builder
public class SickLeavesRequest {

  PersonId personId;
  List<CertificateType> certificateTypes;
  LocalDate signedFrom;
  LocalDate signedTo;
  List<HsaId> issuedByUnitIds;
}
