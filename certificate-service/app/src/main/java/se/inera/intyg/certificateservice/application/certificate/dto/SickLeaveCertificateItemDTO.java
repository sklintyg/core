package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateItemDTO.SickLeaveCertificateItemDTOBuilder;

@JsonDeserialize(builder = SickLeaveCertificateItemDTOBuilder.class)
@Value
@Builder
public class SickLeaveCertificateItemDTO {

  String certificateId;
  String certificateType;
  LocalDateTime signingDateTime;
  String personId;
  String patientFullName;
  String personalHsaId;
  String personalFullName;
  String careUnitId;
  String careUnitName;
  String careProviderId;
  boolean deleted;
  boolean testCertificate;
  String diagnoseCode;
  List<SickLeaveCertificateItemWorkCapacityDTO> workCapacityList;
  String occupation;
  List<String> secondaryDiagnoseCodes;

  @JsonPOJOBuilder(withPrefix = "")
  public static class SickLeaveCertificateItemDTOBuilder {

  }
}