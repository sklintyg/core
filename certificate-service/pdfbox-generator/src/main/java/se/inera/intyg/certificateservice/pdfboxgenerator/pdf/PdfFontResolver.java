package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;

@RequiredArgsConstructor
public class PdfFontResolver {

  private final PDAcroForm acroForm;

  public PDFont resolveFont(PdfField field) {
    final var extractedField = acroForm.getField(field.getId());

    if (extractedField instanceof PDTextField textField) {
      return new TextFieldAppearance(textField)
          .getFont(acroForm.getDefaultResources());
    }

    if (extractedField instanceof PDVariableText variableText) {
      return new TextFieldAppearance(variableText)
          .getFont(acroForm.getDefaultResources());
    }

    return extractFallbackFont();
  }

  private PDFont extractFallbackFont() {
    try {
      var resources = acroForm.getDefaultResources();
      var fontName = resources.getFontNames().iterator().next();
      return resources.getFont(fontName);
    } catch (IOException e) {
      throw new IllegalStateException("Could not resolve fallback font", e);
    }
  }
}
