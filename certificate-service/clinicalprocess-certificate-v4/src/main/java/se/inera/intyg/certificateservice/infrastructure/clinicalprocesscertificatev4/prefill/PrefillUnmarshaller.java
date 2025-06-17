package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.List;
import java.util.Optional;

public final class PrefillUnmarshaller {

  private PrefillUnmarshaller() {
  }


  public static Optional<String> unmarshallString(List<Object> content) {
    try {
      return Optional.of((String) content.getFirst());
    } catch (Exception e) {
      return Optional.empty();
    }
  }

}
