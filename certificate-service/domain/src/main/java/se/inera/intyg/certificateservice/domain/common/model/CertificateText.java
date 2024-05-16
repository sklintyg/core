package se.inera.intyg.certificateservice.domain.common.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CertificateText {

  String text;
  CertificateTextType type;
  List<CertificateLink> links;

}
