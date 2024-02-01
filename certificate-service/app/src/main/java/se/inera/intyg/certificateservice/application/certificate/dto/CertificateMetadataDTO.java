package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO.CertificateMetadataDTOBuilder;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;

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
  String sentTo;
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
  CertificateRecipientDTO recipient;
  CertificateSummaryDTO summary;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateMetadataDTOBuilder {

  }
}
