package se.inera.intyg.certificateservice.application.certificate.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.LOCKED;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.REVOKED;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.UNSIGNED;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

class CertificateStatusTypeDTOTest {


  @Test
  void shallConvertDraft() {
    assertEquals(UNSIGNED, CertificateStatusTypeDTO.toType(Status.DRAFT));
  }

  @Test
  void shallConvertRevoked() {
    assertEquals(REVOKED, CertificateStatusTypeDTO.toType(Status.REVOKED));
  }

  @Test
  void shallConvertSigned() {
    assertEquals(SIGNED, CertificateStatusTypeDTO.toType(Status.SIGNED));
  }

  @Test
  void shallConvertLockedDraft() {
    assertEquals(LOCKED, CertificateStatusTypeDTO.toType(Status.LOCKED_DRAFT));
  }

  @Test
  void shallThrowIfConvertDeletedDraft() {
    assertThrows(IllegalArgumentException.class,
        () -> CertificateStatusTypeDTO.toType(Status.DELETED_DRAFT));
  }
}
