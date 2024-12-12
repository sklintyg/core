package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common;

import java.util.stream.Collectors;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

public class CheckboxMultipleCodeSummaryValue {

  private CheckboxMultipleCodeSummaryValue() {
    throw new IllegalStateException("Utility class");
  }

  public static String value(ElementId elementId, Certificate certificate) {
    final var elementDataCode = certificate.getElementDataById(elementId);
    final var elementSpecification = certificate.certificateModel().elementSpecification(elementId);

    if (elementDataCode.isEmpty()) {
      return "";
    }

    if (!(elementDataCode.get()
        .value() instanceof ElementValueCodeList elementValueCodeList)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementDataCode.get().value())
      );
    }

    if (!(elementSpecification.configuration() instanceof ElementConfigurationCheckboxMultipleCode configuration)) {
      throw new IllegalStateException(
          "Invalid configuration type. Type was '%s'".formatted(
              elementSpecification.configuration().getClass())
      );
    }

    return elementValueCodeList.list()
        .stream()
        .map(value -> configuration.code(value).displayName())
        .collect(Collectors.joining(", "));
  }
}
