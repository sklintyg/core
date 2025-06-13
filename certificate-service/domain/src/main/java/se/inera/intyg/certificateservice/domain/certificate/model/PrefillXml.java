package se.inera.intyg.certificateservice.domain.certificate.model;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrefillXml {

  private String value;

  public String decode() {
    if (value == null || value.isEmpty()) {
      return null;
    }
    return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
  }

}
