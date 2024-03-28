package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonStaffDTO.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.RevokedReason.INCORRECT_PATIENT;

import java.time.LocalDateTime;
import java.time.ZoneId;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokedDTO;

public class TestDataCertificateRevokedDTO {

  private TestDataCertificateRevokedDTO() {
    throw new IllegalStateException("Utility class");
  }

  public static final String REVOKED_MESSAGE = "REVOKED_MESSAGE";
  public static final RevokedDTO REVOKED_DTO = RevokedDTO.builder()
      .revokedBy(AJLA_DOKTOR)
      .revokedAt(LocalDateTime.now(ZoneId.systemDefault()))
      .reason(INCORRECT_PATIENT.name())
      .message(REVOKED_MESSAGE)
      .build();

}
