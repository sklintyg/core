package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationHidden;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Service
public class PdfElementValueGenerator {

  private final Map<Class<? extends ElementValue>, PdfElementValue> pdfElementValues;

  public PdfElementValueGenerator(List<PdfElementValue> pdfElementValues) {
    this.pdfElementValues = pdfElementValues.stream()
        .collect(
            Collectors.toMap(
                PdfElementValue::getType,
                Function.identity()
            )
        );
  }

  public List<PdfField> generate(Certificate certificate) {
    final var elementSpecifications = certificate.certificateModel().elementSpecifications()
        .stream()
        .flatMap(ElementSpecification::flatten)
        .toList();

    return elementSpecifications.stream()
        .filter(elementSpecification -> !elementSpecification.id().equals(UNIT_CONTACT_INFORMATION))
        .map(elementSpecification -> getFieldsForElementSpecification(certificate,
            elementSpecification)
        )
        .flatMap(List::stream)
        .toList();
  }

  private List<PdfField> getFieldsForElementSpecification(Certificate certificate,
      ElementSpecification elementSpecification) {
    final var elementData = certificate.getElementDataById(elementSpecification.id());

    if (elementData.isEmpty()) {
      return Collections.emptyList();
    }

    return getFields(
        elementSpecification,
        elementData.get().value()
    );
  }

  private List<PdfField> getFields(ElementSpecification elementSpecification,
      ElementValue elementValue) {
    if (elementSpecification.pdfConfiguration() instanceof PdfConfigurationHidden) {
      return Collections.emptyList();
    }

    final var pdfElementValue = pdfElementValues.get(elementValue.getClass());

    if (pdfElementValue == null) {
      throw new IllegalStateException(
          String.format(
              "Could not find value generator for pdf value type: '%s'", elementValue.getClass()
          )
      );
    }

    return pdfElementValue.generate(elementSpecification, elementValue);
  }
}
