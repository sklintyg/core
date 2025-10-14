package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

@Value
@Builder
public class PdfGeneratorOptions {

  String additionalInfoText;
  boolean citizenFormat;
  List<ElementId> hiddenElements;
}
