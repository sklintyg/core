package se.inera.intyg.certificateservice.application.certificate.dto.value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDouble.CertificateDataValueDoubleBuilder;

@JsonDeserialize(builder = CertificateDataValueDoubleBuilder.class)
@Value
@Builder
public class CertificateDataValueDouble implements CertificateDataValue {

  @Getter(onMethod = @__(@Override))
  CertificateDataValueType type = CertificateDataValueType.DOUBLE;
  String id;
  Double value;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataValueDoubleBuilder {

  }
}
