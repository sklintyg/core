package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.RECIPIENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.SENT;

import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRecipientDTO;

public class TestDataCertificateRecipientDTO {

  private TestDataCertificateRecipientDTO() {
    throw new IllegalStateException("Utility class");
  }

  public static final CertificateRecipientDTO CERTIFICATE_RECIPIENT_DTO = CertificateRecipientDTO.builder()
      .id(RECIPIENT.id().id())
      .name(RECIPIENT.name())
      .sent(SENT.sentAt())
      .build();

}
