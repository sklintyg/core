package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidator;

@Value
@Builder
public class ElementValueIcf implements ElementValue {

  FieldId id;
  @With
  String text;
  @With
  @Builder.Default
  List<String> icfCodes = Collections.emptyList();

  @Override
  public boolean isEmpty() {
    return !ElementValidator.isTextDefined(text);
  }

  public String formatIcfValueText(ElementConfigurationIcf elementConfigurationIcf) {
    if (icfCodes.isEmpty()) {
      return text;
    }

    return """
        %s %s
        
        %s
        """.formatted(
        elementConfigurationIcf.collectionsLabel(),
        String.join(" - ", icfCodes),
        text
    );
  }
}