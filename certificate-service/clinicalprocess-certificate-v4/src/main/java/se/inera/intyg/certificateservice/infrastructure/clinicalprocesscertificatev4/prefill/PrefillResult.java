package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.Collection;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;

@Data
@Builder
public class PrefillResult {

  private ElementData elementData;
  private Collection<PrefillError> error;

  public Optional<String> getErrorDetails() {
    if (CollectionUtils.isEmpty(error)) {
      return Optional.empty();
    }

    return Optional.of(error.stream()
        .map(PrefillError::details)
        .reduce((a, b) -> a + "; " + b)
        .orElse(""));
  }
}
