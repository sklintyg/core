package se.inera.intyg.certificateservice.application.certificate.dto.value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueOcularAcuity.CertificateDataValueVisualAcuityBuilder;


@JsonDeserialize(builder = CertificateDataValueVisualAcuityBuilder.class)
@Value
@Builder
public class CertificateDataValueOcularAcuity implements CertificateDataValue {

  @Getter(onMethod = @__(@Override))
  CertificateDataValueType type = CertificateDataValueType.OCULAR_ACUITY;
  String id;
  CertificateDataValueDouble withoutCorrection;
  CertificateDataValueDouble withCorrection;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataValueVisualAcuityBuilder {

  }
}
