package se.inera.intyg.certificateservice.domain.certificate.model;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public record Xml(String xml) {

  public String base64() {
    return Base64.getEncoder().encodeToString(
        xml.getBytes(StandardCharsets.UTF_8)
    );
  }
}
