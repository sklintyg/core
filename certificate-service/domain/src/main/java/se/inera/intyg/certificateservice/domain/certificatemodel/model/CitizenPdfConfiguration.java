package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;

@Value
@Builder
public class CitizenPdfConfiguration implements PdfConfiguration {

  ElementId hiddenBy;
  Predicate<List<ElementData>> shouldHide;
  ElementSimplifiedValue replacementValue;

  public Optional<ElementSimplifiedValue> replacementElementValue(
      PdfGeneratorOptions pdfGeneratorOptions,
      List<ElementData> allElementData) {
    if (!pdfGeneratorOptions.citizenFormat()) {
      return Optional.empty();
    }

    final var shouldHideByCitizenChoice =
        hiddenBy != null && pdfGeneratorOptions.hiddenElements().contains(hiddenBy);
    final var shouldHideByValue = shouldHide != null && shouldHide.test(allElementData);

    if (shouldHideByCitizenChoice || shouldHideByValue) {
      return Optional.of(replacementValue);
    }

    return Optional.empty();
  }
}
