package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Map;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfConfigurationDateRangeList implements PdfConfiguration {

  Map<FieldId, PdfConfigurationDateRangeCheckbox> dateRanges;
}
