package se.inera.intyg.certificateservice.integrationtest.util;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRecipientDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SendCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ValidateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ValidationErrorDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesResponse;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesResponse;

public class CertificateUtil {

  public static String certificateId(CreateCertificateResponse response) {
    final var certificate = certificate(response);
    if (certificate == null || certificate.getMetadata() == null) {
      return null;
    }
    return certificate.getMetadata().getId();
  }

  public static String certificateId(List<CreateCertificateResponse> responses) {
    final var certificate = certificate(responses);
    if (certificate == null || certificate.getMetadata() == null) {
      return null;
    }
    return certificate.getMetadata().getId();
  }

  public static CertificateStatusTypeDTO status(RevokeCertificateResponse responses) {
    if (responses == null || responses.getCertificate() == null) {
      return null;
    }
    return responses.getCertificate().getMetadata().getStatus();
  }

  public static long version(List<CreateCertificateResponse> responses) {
    final var certificate = certificate(responses);
    if (certificate == null || certificate.getMetadata() == null) {
      return 0L;
    }
    return certificate.getMetadata().getVersion();
  }

  public static long version(CreateCertificateResponse response) {
    final var certificate = certificate(response);
    if (certificate == null || certificate.getMetadata() == null) {
      return 0L;
    }
    return certificate.getMetadata().getVersion();
  }

  public static CertificateDTO certificate(List<CreateCertificateResponse> responses) {
    if (responses == null || responses.size() != 1) {
      return null;
    }

    return certificate(responses.get(0));
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

  public static byte[] pdfData(GetCertificatePdfResponse response) {
    if (response == null || response.getPdfData() == null) {
      return null;
    }
    return response.getPdfData();
  }

  public static String pdfFileName(GetCertificatePdfResponse response) {
    if (response == null || response.getFileName() == null) {
      return null;
    }
    return response.getFileName();
  }

  public static CertificateDTO certificate(ResponseEntity<SignCertificateResponse> response) {
    if (response.getBody() == null || response.getBody() == null) {
      return null;
    }
    return response.getBody().getCertificate();
  }

  public static CertificateRecipientDTO recipient(
      ResponseEntity<SendCertificateResponse> response) {
    if (response.getBody() == null || response.getBody() == null) {
      return CertificateRecipientDTO.builder().build();
    }
    return response.getBody().getCertificate().getMetadata().getRecipient();
  }

  public static List<CertificateDTO> certificates(GetPatientCertificatesResponse response) {
    if (response == null || response.getCertificates() == null) {
      return Collections.emptyList();
    }
    return response.getCertificates();
  }

  public static List<CertificateDTO> certificates(GetUnitCertificatesResponse response) {
    if (response == null || response.getCertificates() == null) {
      return Collections.emptyList();
    }
    return response.getCertificates();
  }

  public static boolean exists(List<CertificateDTO> certificates, CertificateDTO certificate) {
    if (certificates == null || certificate == null) {
      return false;
    }
    return certificates.stream()
        .anyMatch(certificateDTO -> certificateDTO.getMetadata().getId()
            .equals(certificate.getMetadata().getId())
        );
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

  public static List<ValidationErrorDTO> validationErrors(
      ResponseEntity<ValidateCertificateResponse> response) {
    if (response == null || response.getBody() == null) {
      throw new IllegalArgumentException("Missing response!");
    }
    return response.getBody().getValidationErrors();
  }

  public static GetCertificateInternalXmlResponse certificateInternalXmlResponse(
      ResponseEntity<GetCertificateInternalXmlResponse> response) {
    if (response == null || response.getBody() == null) {
      throw new IllegalArgumentException("Missing response!");
    }
    return response.getBody();
  }

  public static RevokeCertificateResponse revokeCertificateResponse(
      ResponseEntity<RevokeCertificateResponse> response) {
    if (response == null || response.getBody() == null) {
      throw new IllegalArgumentException("Missing response!");
    }
    return response.getBody();
  }

  public static String decodeXml(String xml) {
    return new String(
        Base64.getDecoder().decode(xml),
        StandardCharsets.UTF_8
    );
  }

  public static GetCertificateXmlResponse certificateXmlReponse(
      ResponseEntity<GetCertificateXmlResponse> response) {
    if (response == null || response.getBody() == null) {
      throw new IllegalArgumentException("Missing response!");
    }
    return response.getBody();
  }

  public static CertificateDTO updateUnit(List<CreateCertificateResponse> responses,
      UnitDTO unitDTO) {
    final var certificate = certificate(responses);
    return CertificateDTO.builder()
        .data(certificate.getData())
        .metadata(
            certificate.getMetadata().withUnit(
                unitDTO
            )
        )
        .build();
  }
}
