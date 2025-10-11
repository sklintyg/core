package se.inera.intyg.certificateservice.integrationtest.common.util;

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
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRecipientDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationsDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesWithQAInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ComplementCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateDraftFromCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalMetadataResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SendCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ValidateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ValidationErrorDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueBoolean;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCode;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCodeList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateRange;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateRangeList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDiagnosisList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.application.citizen.dto.CitizenCertificateExistsResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.message.dto.CreateMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateFromMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageInternalResponse;
import se.inera.intyg.certificateservice.application.message.dto.GetCertificateMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.HandleMessageResponse;
import se.inera.intyg.certificateservice.application.message.dto.MessageExistsResponse;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
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

  public static String certificateId(ReplaceCertificateResponse response) {
    final var certificate = certificate(response);
    if (certificate == null || certificate.getMetadata() == null) {
      return null;
    }
    return certificate.getMetadata().getId();
  }

  public static String certificateId(RenewCertificateResponse response) {
    final var certificate = certificate(response);
    if (certificate == null || certificate.getMetadata() == null) {
      return null;
    }
    return certificate.getMetadata().getId();
  }

  public static String certificateId(CreateDraftFromCertificateResponse response) {
    final var certificate = certificate(response);
    if (certificate == null || certificate.getMetadata() == null) {
      return null;
    }
    return certificate.getMetadata().getId();
  }

  public static String certificateId(ComplementCertificateResponse response) {
    final var certificate = certificate(response);
    if (certificate == null || certificate.getMetadata() == null) {
      return null;
    }
    return certificate.getMetadata().getId();
  }

  public static String certificateId(GetCertificateFromMessageResponse response) {
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

  public static String messageId(QuestionDTO questionDTO) {
    if (questionDTO == null) {
      return null;
    }
    return questionDTO.getId();
  }

  public static List<QuestionDTO> messages(GetCertificateMessageInternalResponse response) {
    if (response == null) {
      throw new IllegalStateException("Missing response object!");
    }
    return response.getQuestions();
  }

  public static CertificateStatusTypeDTO status(RevokeCertificateResponse responses) {
    if (responses == null || responses.getCertificate() == null) {
      return null;
    }
    return responses.getCertificate().getMetadata().getStatus();
  }

  public static CertificateRelationsDTO relation(ReplaceCertificateResponse response) {
    if (response == null || response.getCertificate() == null) {
      return null;
    }
    return response.getCertificate().getMetadata().getRelations();
  }

  public static CertificateRelationsDTO relation(RenewCertificateResponse response) {
    if (response == null || response.getCertificate() == null) {
      return null;
    }
    return response.getCertificate().getMetadata().getRelations();
  }

  public static boolean hasQuestions(GetCertificateMessageResponse response) {
    if (response == null || response.getQuestions() == null) {
      throw new IllegalStateException("GetCertificateMessageResponse is null");
    }
    return !response.getQuestions().isEmpty();
  }

  public static List<QuestionDTO> questions(GetCertificateMessageResponse response) {
    if (response == null || response.getQuestions() == null) {
      throw new IllegalStateException("GetCertificateMessageResponse is null");
    }
    return response.getQuestions();
  }

  public static QuestionDTO question(HandleMessageResponse response) {
    if (response == null || response.getQuestion() == null) {
      throw new IllegalStateException("HandleMessageResponse is null");
    }
    return response.getQuestion();
  }

  public static QuestionDTO question(CreateMessageResponse response) {
    if (response == null || response.getQuestion() == null) {
      throw new IllegalStateException("CreateMessageResponse is null");
    }
    return response.getQuestion();
  }

  public static List<ResourceLinkDTO> resourceLink(GetCertificateMessageResponse response) {
    if (response == null || response.getQuestions() == null) {
      throw new IllegalStateException("GetCertificateMessageResponse is null");
    }
    return response.getQuestions().getFirst().getLinks();
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

  public static long version(ReplaceCertificateResponse response) {
    final var certificate = certificate(response);
    if (certificate == null || certificate.getMetadata() == null) {
      return 0L;
    }
    return certificate.getMetadata().getVersion();
  }

  public static long version(ComplementCertificateResponse response) {
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

    return certificate(responses.getFirst());
  }

  public static CertificateDTO certificate(CreateCertificateResponse response) {
    if (response == null || response.getCertificate() == null) {
      return null;
    }
    return response.getCertificate();
  }

  public static CertificateDTO certificate(ReplaceCertificateResponse response) {
    if (response == null || response.getCertificate() == null) {
      return null;
    }
    return response.getCertificate();
  }

  public static CertificateDTO certificate(RenewCertificateResponse response) {
    if (response == null || response.getCertificate() == null) {
      return null;
    }
    return response.getCertificate();
  }

  public static CertificateDTO certificate(CreateDraftFromCertificateResponse response) {
    if (response == null || response.getCertificate() == null) {
      return null;
    }
    return response.getCertificate();
  }


  public static CertificateDTO certificate(ComplementCertificateResponse response) {
    if (response == null || response.getCertificate() == null) {
      return null;
    }
    return response.getCertificate();
  }

  public static CertificateDTO certificate(UpdateCertificateResponse response) {
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

  public static CertificateDTO certificate(GetCertificateFromMessageResponse response) {
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

  public static CertificateDTO certificate(ResponseEntity<SignCertificateResponse> response) {
    if (response.getBody() == null || response.getBody() == null) {
      return null;
    }
    return response.getBody().getCertificate();
  }

  public static CertificateRecipientDTO getRecipient(
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

  public static boolean exists(CitizenCertificateExistsResponse response) {
    if (response == null) {
      return false;
    }

    return response.isExists();
  }

  public static boolean exists(MessageExistsResponse response) {
    if (response == null) {
      return false;
    }

    return response.isExists();
  }

  public static boolean forwarded(ForwardCertificateResponse response) {
    if (response == null || response.getCertificate() == null) {
      return false;
    }

    return response.getCertificate().getMetadata().isForwarded();
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

  public static CertificateDataElement updateBooleanValue(CertificateDTO certificateDTO,
      String questionId, Boolean value) {
    final var certificate = Objects.requireNonNull(certificateDTO.getData().get(questionId));
    return CertificateDataElement.builder()
        .id(certificate.getId())
        .parent(certificate.getParent())
        .config(certificate.getConfig())
        .validation(certificate.getValidation())
        .value(
            CertificateDataValueBoolean.builder()
                .id(((CertificateDataValueBoolean) certificate.getValue()).getId())
                .selected(value)
                .build()
        )
        .build();
  }

  public static CertificateDataElement updateTextValue(CertificateDTO certificateDTO,
      String questionId, String newText) {
    final var certificate = Objects.requireNonNull(certificateDTO.getData().get(questionId));
    return CertificateDataElement.builder()
        .id(certificate.getId())
        .parent(certificate.getParent())
        .config(certificate.getConfig())
        .validation(certificate.getValidation())
        .value(
            CertificateDataValueText.builder()
                .id(((CertificateDataValueText) certificate.getValue()).getId())
                .text(newText)
                .build()
        )
        .build();
  }

  public static CertificateDataElement updateDateRangeListValue(CertificateDTO certificateDTO,
      String questionId, List<CertificateDataValueDateRange> newList) {
    final var certificate = Objects.requireNonNull(certificateDTO.getData().get(questionId));
    return CertificateDataElement.builder()
        .id(certificate.getId())
        .parent(certificate.getParent())
        .config(certificate.getConfig())
        .validation(certificate.getValidation())
        .value(
            CertificateDataValueDateRangeList.builder()
                .list(newList)
                .build()
        )
        .build();
  }

  public static CertificateDataElement updateDateRangeValue(CertificateDTO certificateDTO,
      String questionId, CertificateDataValueDateRange newValue) {
    final var certificate = Objects.requireNonNull(certificateDTO.getData().get(questionId));
    return CertificateDataElement.builder()
        .id(certificate.getId())
        .parent(certificate.getParent())
        .config(certificate.getConfig())
        .validation(certificate.getValidation())
        .value(
            newValue
        )
        .build();
  }

  public static CertificateDataElement updateDateListValue(CertificateDTO certificateDTO,
      String questionId, List<CertificateDataValueDate> newList) {
    final var certificate = Objects.requireNonNull(certificateDTO.getData().get(questionId));
    return CertificateDataElement.builder()
        .id(certificate.getId())
        .parent(certificate.getParent())
        .config(certificate.getConfig())
        .validation(certificate.getValidation())
        .value(
            CertificateDataValueDateList.builder()
                .list(newList)
                .build()
        )
        .build();
  }

  public static LocalDate getValueDate(ResponseEntity<UpdateCertificateResponse> response,
      String questionId) {
    if (response == null || response.getBody() == null) {
      return null;
    }
    return ((CertificateDataValueDate) response.getBody().getCertificate().getData().get(questionId)
        .getValue()).getDate();
  }

  public static String getValueText(ResponseEntity<UpdateCertificateResponse> response,
      String questionId) {
    if (response == null || response.getBody() == null) {
      return null;
    }
    return ((CertificateDataValueText) response.getBody().getCertificate().getData().get(questionId)
        .getValue()).getText();
  }

  public static Boolean getBooleanValue(ResponseEntity<UpdateCertificateResponse> response,
      String questionId) {
    if (response == null || response.getBody() == null) {
      return null;
    }
    return ((CertificateDataValueBoolean) response.getBody().getCertificate().getData()
        .get(questionId)
        .getValue()).getSelected();
  }

  public static List<CertificateDataValueDateRange> getValueDateRangeList(
      ResponseEntity<UpdateCertificateResponse> response,
      String questionId) {
    if (response == null || response.getBody() == null) {
      return null;
    }
    return ((CertificateDataValueDateRangeList) response.getBody().getCertificate().getData()
        .get(questionId)
        .getValue()).getList();
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

  public static CertificatesWithQAInternalResponse certificatesWithQAResponse(
      ResponseEntity<CertificatesWithQAInternalResponse> response) {
    if (response == null || response.getBody() == null) {
      throw new IllegalArgumentException("Missing response!");
    }
    return response.getBody();
  }

  public static CertificateMetadataDTO metadata(
      ResponseEntity<GetCertificateInternalMetadataResponse> response) {
    if (response == null || response.getBody() == null) {
      throw new IllegalArgumentException("Missing response!");
    }
    return response.getBody().getCertificateMetadata();
  }

  public static CertificateMetadataDTO metadata(
      CertificateDTO certificateDTO) {
    if (certificateDTO == null || certificateDTO.getMetadata() == null) {
      throw new IllegalArgumentException("Missing response!");
    }
    return certificateDTO.getMetadata();
  }

  public static CertificateDTO certificate(GetCertificateInternalResponse response) {
    if (response == null) {
      throw new IllegalArgumentException("Missing response!");
    }
    return response.getCertificate();
  }

  public static RevokeCertificateResponse revokeCertificateResponse(
      ResponseEntity<RevokeCertificateResponse> response) {
    if (response == null || response.getBody() == null) {
      throw new IllegalArgumentException("Missing response!");
    }
    return response.getBody();
  }

  public static ReplaceCertificateResponse replaceCertificateResponse(
      ResponseEntity<ReplaceCertificateResponse> response) {
    if (response == null || response.getBody() == null) {
      throw new IllegalArgumentException("Missing response!");
    }
    return response.getBody();
  }

  public static RenewCertificateResponse renewCertificateResponse(
      ResponseEntity<RenewCertificateResponse> response) {
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

  public static CertificateDataValueDateList getValueDateList(
      ResponseEntity<UpdateCertificateResponse> response,
      String questionId) {
    if (response == null || response.getBody() == null) {
      return null;
    }
    return ((CertificateDataValueDateList) response.getBody().getCertificate().getData()
        .get(questionId)
        .getValue());
  }

  public static CertificateDataElement updateValue(CertificateDTO certificateDTO,
      String questionId, CertificateDataValue newValue) {
    final var certificate = Objects.requireNonNull(certificateDTO.getData().get(questionId));
    return CertificateDataElement.builder()
        .id(certificate.getId())
        .parent(certificate.getParent())
        .config(certificate.getConfig())
        .validation(certificate.getValidation())
        .value(newValue)
        .build();
  }

  public static CertificateDataValueDiagnosisList getValueDiagnosisList(
      ResponseEntity<UpdateCertificateResponse> response,
      String questionId) {
    if (response == null || response.getBody() == null) {
      return null;
    }
    return ((CertificateDataValueDiagnosisList) response.getBody().getCertificate().getData()
        .get(questionId)
        .getValue());
  }

  public static CertificateDataValueCode getValueCode(
      ResponseEntity<UpdateCertificateResponse> response,
      String questionId) {
    if (response == null || response.getBody() == null) {
      return null;
    }
    return ((CertificateDataValueCode) response.getBody().getCertificate().getData()
        .get(questionId)
        .getValue());
  }

  public static CertificateDataValueBoolean getValueBoolean(
      ResponseEntity<UpdateCertificateResponse> response,
      String questionId) {
    if (response == null || response.getBody() == null) {
      return null;
    }
    return ((CertificateDataValueBoolean) response.getBody().getCertificate().getData()
        .get(questionId)
        .getValue());
  }

  public static CertificateDataValueCodeList getValueCodeList(
      ResponseEntity<UpdateCertificateResponse> response,
      String questionId) {
    if (response == null || response.getBody() == null) {
      return null;
    }
    return ((CertificateDataValueCodeList) response.getBody().getCertificate().getData()
        .get(questionId)
        .getValue());
  }

  public static CertificateDTO getCitizenCertificate(
      ResponseEntity<SendCitizenCertificateResponse> response) {
    if (response == null || response.getBody() == null) {
      return null;
    }
    return response.getBody().getCitizenCertificate();
  }
}