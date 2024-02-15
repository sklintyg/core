package se.inera.intyg.certificateservice.integrationtest.util;

import java.time.LocalDate;
import java.util.Objects;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;

public class CertificateUtil {

  public static String certificateId(CreateCertificateResponse response) {
    final var certificate = certificate(response);
    if (certificate == null || certificate.getMetadata() == null) {
      return null;
    }
    return certificate.getMetadata().getId();
  }

  public static long version(CreateCertificateResponse response) {
    final var certificate = certificate(response);
    if (certificate == null || certificate.getMetadata() == null) {
      return 0L;
    }
    return certificate.getMetadata().getVersion();
  }

  public static CertificateDTO certificate(CreateCertificateResponse response) {
    if (response == null || response.getCertificate() == null) {
      return null;
    }

    return response.getCertificate();
  }

  public static CertificateDTO certificate(GetCertificateResponse response) {
    if (response == null || response.getCertificate() == null) {
      return null;
    }

    return response.getCertificate();
  }

  public static boolean exists(CertificateExistsResponse response) {
    if (response == null) {
      return false;
    }

    return response.isExists();
  }

  public static CertificateDataElement updateDateValue(CertificateDTO certificateDTO,
      String questionId, LocalDate newDate) {
    final var certificate = Objects.requireNonNull(certificateDTO.getData().get(questionId));
    return CertificateDataElement.builder()
        .id(certificate.getId())
        .parent(certificate.getParent())
        .config(certificate.getConfig())
        .validation(certificate.getValidation())
        .value(
            CertificateDataValueDate.builder()
                .id(((CertificateDataValueDate) certificate.getValue()).getId())
                .date(newDate)
                .build()
        )
        .build();
  }

  public static LocalDate getValueFromData(UpdateCertificateResponse response,
      CertificateDataValueType type, String questionId) {
    return switch (type) {
      case DATE -> ((CertificateDataValueDate) response.getCertificate().getData().get(questionId)
          .getValue()).getDate();
    };
  }
}
