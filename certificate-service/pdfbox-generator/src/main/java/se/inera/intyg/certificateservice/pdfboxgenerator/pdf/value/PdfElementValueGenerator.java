package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
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
    return certificate.elementData().stream()
        .filter(elementData -> !elementData.id().equals(UNIT_CONTACT_INFORMATION))
        .map(elementData -> {
              final var elementSpecification = certificate.certificateModel()
                  .elementSpecification(elementData.id());
              return getFields(
                  elementSpecification,
                  elementData.value()
              );
            }
        )
        .flatMap(List::stream)
        .toList();
  }

  private List<PdfField> getFields(ElementSpecification elementSpecification,
      ElementValue elementValue) {
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
