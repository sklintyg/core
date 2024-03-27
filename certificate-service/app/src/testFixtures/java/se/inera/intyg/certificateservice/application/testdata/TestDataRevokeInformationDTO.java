package se.inera.intyg.certificateservice.application.testdata;

import se.inera.intyg.certificateservice.application.certificate.dto.RevokeInformationDTO;

public class TestDataRevokeInformationDTO {

  private static final String REASON = "FEL_PATIENT";
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
