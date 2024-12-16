package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementSimplifiedValueTable implements ElementSimplifiedValue {

  List<String> headings;
  List<List<String>> values;
}
