package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SubUnit implements IssuingUnit {

  HsaId hsaId;
}
