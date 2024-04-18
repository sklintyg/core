package se.inera.intyg.certificateservice.pdfboxgenerator.value;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.toolkits.PdfGeneratorValueToolkit;

public class PdfTextValueGenerator implements PdfElementValueGenerator {

  @Override
  public void generate(PDAcroForm acroForm, Certificate certificate, ElementId questionId,
      String fieldName) throws IOException {
    final var pdfGeneratorValueToolkit = new PdfGeneratorValueToolkit();
    final var question = certificate.getElementDataById(questionId);

    if (question.isEmpty()) {
      throw new IllegalStateException(
          "Could not find question with id: " + certificate.elementData().get(0).id().id());
    }

    if (!(question.get().value() instanceof ElementValueText elementValueText)) {
      throw new IllegalStateException(
          String.format(
              "Expected class ElementValueText but was: '%s'",
              question.get().value().getClass()
          )
      );
    }

    pdfGeneratorValueToolkit.setValue(acroForm, fieldName, elementValueText.text());
  }
}
