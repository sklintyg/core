package se.inera.intyg.certificateservice.pdfboxgenerator.value;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfTextValueGenerator implements PdfElementValueGenerator {

  @Override
  public List<PdfField> generate(Certificate certificate, ElementId questionId, String fieldId) {
    if (certificate.elementData() == null || certificate.elementData().isEmpty()) {
      return Collections.emptyList();
    }

    final var question = certificate.getElementDataById(questionId);

    if (question.isEmpty()) {
      throw new IllegalStateException(
          "Could not find question with id: %s".formatted(questionId));
    }

    if (!(question.get().value() instanceof ElementValueText elementValueText)) {
      throw new IllegalStateException(
          String.format(
              "Expected class ElementValueText but was: '%s'",
              question.get().value().getClass()
          )
      );
    }

    return List.of(
        PdfField.builder()
            .id(fieldId)
            .value(elementValueText.text())
            .build()
    );
  }
}