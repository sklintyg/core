package se.inera.intyg.certificateservice.pdfboxgenerator.value;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.toolkits.PdfGeneratorValueToolkit;

public class PdfDateValueGenerator implements PdfElementValueGenerator {

  private final PdfGeneratorValueToolkit pdfGeneratorValueToolkit = new PdfGeneratorValueToolkit();

  @Override
  public void generate(PDAcroForm acroForm, Certificate certificate, ElementId questionId,
      String fieldName) throws IOException {
    final var question = certificate.getElementDataById(questionId);

    if (question.isEmpty()) {
      throw new IllegalStateException(
          "Could not find question with id: " + certificate.elementData().get(0).id().id());
    }

    if (!(question.get().value() instanceof ElementValueDate elementValueDate)) {
      throw new IllegalStateException(
          String.format(
              "Expected class ElementValueText but was: '%s'",
              question.get().value().getClass()
          )
      );
    }

    pdfGeneratorValueToolkit.setValue(acroForm, fieldName, elementValueDate.date().toString());
  }
}