package se.inera.intyg.certificateservice.application.citizen.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.citizen.dto.CertificateMetadataDTO.CertificateMetadataDTOBuilder;

@Value
@Builder
@JsonDeserialize(builder = CertificateMetadataDTOBuilder.class)
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
  String sentTo;
  CertificateRecipientDTO recipient;
  CertificateRelationsDTO relations;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;
  PatientDTO patient;
  StaffDTO issuedBy;
  long version;
  boolean latestMajorVersion;
  LocalDateTime readyForSign;
  String responsibleHospName;
  CertificateSummaryDTO summary;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateMetadataDTOBuilder {

  }
}