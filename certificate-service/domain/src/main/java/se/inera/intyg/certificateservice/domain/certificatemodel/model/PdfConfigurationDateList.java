package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Map;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfConfigurationDateList implements PdfConfiguration {

  Map<FieldId, PdfConfigurationDateCheckbox> dateCheckboxes;
}
