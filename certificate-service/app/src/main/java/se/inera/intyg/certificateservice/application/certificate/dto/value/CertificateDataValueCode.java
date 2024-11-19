package se.inera.intyg.certificateservice.application.certificate.dto.value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCode.CertificateDataValueCodeBuilder;

@JsonDeserialize(builder = CertificateDataValueCodeBuilder.class)
@Value
@Builder
public class CertificateDataValueCode implements CertificateDataValue {

  @Getter(onMethod = @__(@Override))
  CertificateDataValueType type = CertificateDataValueType.CODE;
  String id;
  String code;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataValueCodeBuilder {

  }
}
