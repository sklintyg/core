package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateConfirmationModalDTO.CertificateConfirmationModalDTOBuilder;

@JsonDeserialize(builder = CertificateConfirmationModalDTOBuilder.class)
@Value
@Builder
public class CertificateConfirmationModalDTO {

  String title;
  String text;
  AlertDTO alert;
  String checkboxText;
  CertificateModalActionTypeDTO primaryAction;
  CertificateModalActionTypeDTO secondaryAction;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateConfirmationModalDTOBuilder {

  }
}
