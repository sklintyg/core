package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

@Value
@Builder
public class ElementMessage {

  String content;
  MessageLevel level;
  List<Status> includedForStatuses;

  public boolean include(Certificate certificate) {
    return includedForStatuses.contains(certificate.status());
  }
}
