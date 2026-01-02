package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory;

import java.util.Optional;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.TextFieldAppearance;

public class TextFieldAppearanceFactory {

  public Optional<TextFieldAppearance> create(PDField field) {
    if (field instanceof PDVariableText variableText) {
      return Optional.of(new TextFieldAppearance(variableText));
    }
    return Optional.empty();
  }
}
