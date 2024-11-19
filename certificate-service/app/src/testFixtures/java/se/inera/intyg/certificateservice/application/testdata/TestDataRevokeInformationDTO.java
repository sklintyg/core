package se.inera.intyg.certificateservice.application.testdata;

import se.inera.intyg.certificateservice.application.certificate.dto.RevokeInformationDTO;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReason;

public class TestDataRevokeInformationDTO {

  private static final String REASON = RevokedReason.INCORRECT_PATIENT.name();
  private static final String MESSAGE = "REVOKE_MESSAGE";

  private TestDataRevokeInformationDTO() {
    throw new IllegalStateException("Utility class");
  }

  public static final RevokeInformationDTO REVOKE_INFORMATION_DTO = revokeInformationDTOBuilder().build();

  public static RevokeInformationDTO.RevokeInformationDTOBuilder revokeInformationDTOBuilder() {
    return RevokeInformationDTO.builder()
        .reason(REASON)
        .message(MESSAGE);

  }
}
