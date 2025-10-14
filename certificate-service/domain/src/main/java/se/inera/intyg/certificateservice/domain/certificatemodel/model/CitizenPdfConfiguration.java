package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;

@Value
@Builder
public class CitizenPdfConfiguration implements PdfConfiguration {

  ElementId hiddenBy;
  Predicate<List<ElementData>> shouldHide;
  ElementSimplifiedValue replacementValue;

  public Optional<ElementSimplifiedValue> replacementElementValue(List<ElementId> hiddenElements,
      List<ElementData> allElementData, boolean isCitizenFormat) {
    if (!isCitizenFormat) {
      return Optional.empty();
    }

    final var shouldHideByCitizenChoice = hiddenBy != null && hiddenElements.contains(hiddenBy);
    final var shouldHideByValue = shouldHide != null && shouldHide.test(allElementData);

    if (shouldHideByCitizenChoice || shouldHideByValue) {
      return Optional.of(replacementValue);
    }

    return Optional.empty();
  }
}
