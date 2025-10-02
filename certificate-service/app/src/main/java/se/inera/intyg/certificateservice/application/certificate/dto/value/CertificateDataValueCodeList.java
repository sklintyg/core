package se.inera.intyg.certificateservice.application.certificate.dto.value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCodeList.CertificateDataValueCodeListBuilder;

@JsonDeserialize(builder = CertificateDataValueCodeListBuilder.class)
@Value
@Builder
public class CertificateDataValueCodeList implements CertificateDataValue {

  @Getter(onMethod = @__(@Override))
  CertificateDataValueType type = CertificateDataValueType.CODE_LIST;
  @With
  List<CertificateDataValueCode> list;
  String id;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateDataValueCodeListBuilder {

  }
}