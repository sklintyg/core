package se.inera.intyg.certificateservice.pdfboxgenerator.value;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.toolkits.PdfGeneratorValueToolkit;

public class PdfDateValueGenerator implements PdfElementValueGenerator {

  @Override
  public void generate(PDAcroForm acroForm, Certificate certificate, ElementId questionId,
      String fieldName) throws IOException {
    PdfGeneratorValueToolkit pdfGeneratorValueToolkit = new PdfGeneratorValueToolkit();

    if (certificate.elementData() == null || certificate.elementData().isEmpty()) {
      return;
    }

    final var question = certificate.getElementDataById(questionId);

    if (question.isEmpty()) {
      throw new IllegalStateException(
          "Could not find question with id: %s".formatted(questionId));
    }

    if (!(question.get().value() instanceof ElementValueDate elementValueDate)) {
      throw new IllegalStateException(
          String.format(
              "Expected class ElementValueDate but was: '%s'",
              question.get().value().getClass()
          )
      );
    }

    pdfGeneratorValueToolkit.setValue(acroForm, fieldName, elementValueDate.date().toString());
  }
}
