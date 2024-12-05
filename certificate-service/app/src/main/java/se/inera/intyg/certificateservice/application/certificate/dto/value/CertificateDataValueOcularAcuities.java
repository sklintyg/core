package se.inera.intyg.certificateservice.application.certificate.dto.value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueOcularAcuities.CertificateDataValueOcularAcuitiesBuilder;


@JsonDeserialize(builder = CertificateDataValueOcularAcuitiesBuilder.class)
@Value
@Builder
public class CertificateDataValueOcularAcuities implements CertificateDataValue {

  @Getter(onMethod = @__(@Override))
  CertificateDataValueType type = CertificateDataValueType.OCULAR_ACUITIES;
  CertificateDataValueOcularAcuity rightEye;
  CertificateDataValueOcularAcuity leftEye;
  CertificateDataValueOcularAcuity binocular;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataValueOcularAcuitiesBuilder {

  }
}
