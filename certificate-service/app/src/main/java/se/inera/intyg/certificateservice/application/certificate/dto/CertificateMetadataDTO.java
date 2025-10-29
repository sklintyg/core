package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO.CertificateMetadataDTOBuilder;

@JsonDeserialize(builder = CertificateMetadataDTOBuilder.class)
@Value
@Builder
public class CertificateMetadataDTO {

  String id;
  String type;
  String typeVersion;
  String typeName;
  String name;
  String description;
  LocalDateTime created;
  CertificateStatusTypeDTO status;
  boolean testCertificate;
  boolean forwarded;
  boolean sent;
  boolean availableForCitizen;
  String sentTo;
  CertificateRelationsDTO relations;
  @With
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;
  PatientDTO patient;
  StaffDTO issuedBy;
  long version;
  boolean latestMajorVersion;
  LocalDateTime readyForSign;
  LocalDateTime signed;
  LocalDateTime modified;
  String responsibleHospName;
  CertificateRecipientDTO recipient;
  CertificateSummaryDTO summary;
  boolean validForSign;
  String externalReference;
  List<CertificateMessageTypeDTO> messageTypes;
  CertificateConfirmationModalDTO confirmationModal;
  StaffDTO createdBy;
  LocalDateTime revokedAt;
  StaffDTO revokedBy;
  boolean isInactiveCertificateType;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateMetadataDTOBuilder {

  }
}