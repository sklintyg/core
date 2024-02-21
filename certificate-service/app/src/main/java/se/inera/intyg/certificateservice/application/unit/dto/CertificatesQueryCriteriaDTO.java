package se.inera.intyg.certificateservice.application.unit.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.unit.dto.CertificatesQueryCriteriaDTO.CertificatesQueryCriteriaDTOBuilder;

@JsonDeserialize(builder = CertificatesQueryCriteriaDTOBuilder.class)
@Value
@Builder
public class CertificatesQueryCriteriaDTO {

  LocalDateTime from;
  LocalDateTime to;
  List<CertificateStatusTypeDTO> statuses;
  String issuedByStaffId;
  PersonIdDTO personId;
  Boolean forwarded;
  Boolean validForSign;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificatesQueryCriteriaDTOBuilder {

  }
}
