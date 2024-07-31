package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Service
@RequiredArgsConstructor
public class PdfElementValueGenerator {

  private final List<PdfElementValue> pdfElementValues;

  public List<PdfField> generate(Certificate certificate) {
    return certificate.elementData().stream()
        .filter(elementData -> !elementData.id().equals(UNIT_CONTACT_INFORMATION))
        .map(elementData -> {
              final var elementSpecification = certificate.certificateModel()
                  .elementSpecification(elementData.id());
              return getFields(
                  certificate,
                  elementData.id(),
                  elementSpecification.printMapping().pdfFieldId().id(),
                  elementData.value().getClass()
              );
            }
        )
        .flatMap(List::stream)
        .toList();
  }

  private List<PdfField> getFields(Certificate certificate, ElementId questionId, String pdfFieldId,
      Class<? extends ElementValue> pdfValueType) {
    return pdfElementValues.stream()
        .filter(types -> types.getType().equals(pdfValueType))
        .findFirst()
        .map(pdfValue -> pdfValue.generate(certificate, questionId, pdfFieldId))
        .orElseThrow(() -> new IllegalStateException(
            String.format(
                "Could not find value generator for pdf value type: '%s'", pdfValueType
            ))
        );
  }
}
