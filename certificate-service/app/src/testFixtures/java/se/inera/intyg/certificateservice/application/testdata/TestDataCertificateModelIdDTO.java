package se.inera.intyg.certificateservice.application.testdata;

import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants;

public class TestDataCertificateModelIdDTO {

  private TestDataCertificateModelIdDTO() {
    throw new IllegalStateException("Utility class");
  }

  public static final CertificateModelIdDTO FK7804_CERTIFICATE_MODEL_ID_DTO = fk7804CertificateModelIdDTO().build();

  public static CertificateModelIdDTO.CertificateModelIdDTOBuilder fk7804CertificateModelIdDTO() {
    return CertificateModelIdDTO.builder()
        .type(TestDataCertificateModelConstants.FK7804_TYPE.type())
        .version(TestDataCertificateModelConstants.FK7804_VERSION.version());
  }
}