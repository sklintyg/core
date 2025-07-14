package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PdfConfigurationDropdownCode implements PdfConfiguration {

  PdfFieldId fieldId;
  Map<FieldId, String> codes;

  public static Map<FieldId, String> fromCodeConfig(List<ElementConfigurationCode> dropdownItems) {
    return dropdownItems.stream()
        .collect(
            Collectors.toMap(
                ElementConfigurationCode::id,
                ElementConfigurationCode::label
            )
        );
  }
}
