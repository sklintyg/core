package se.inera.intyg.certificateservice.application.testdata;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;

public class TestDataCertificateDataEntity {

  private TestDataCertificateDataEntity() {
    throw new IllegalStateException("Utility class");
  }

  private static final String JSON =
      "[{\"id\":\"F10\",\"value\":{\"type\":\"DATE\",\"dateId\":\"dateId\",\"date\":["
          + LocalDateTime.now().getYear() + "," + LocalDateTime.now().getMonthValue() + ","
          + LocalDateTime.now().getDayOfMonth()
          + "]}}]";

  public static final CertificateDataEntity CERTIFICATE_DATA_ENTITY
      = new CertificateDataEntity(JSON);

}
