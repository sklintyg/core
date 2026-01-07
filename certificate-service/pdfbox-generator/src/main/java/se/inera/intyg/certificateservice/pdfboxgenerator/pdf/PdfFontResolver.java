package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory.TextFieldAppearanceFactory;

@RequiredArgsConstructor
public class PdfFontResolver {

  private final PDAcroForm acroForm;
  private final TextFieldAppearanceFactory textFieldAppearanceFactory;

  public PDFont resolveFont(PdfField field) {

    return textFieldAppearanceFactory.create(acroForm.getField(field.getId()))
        .map(appearance -> appearance.getFont(acroForm.getDefaultResources()))
        .orElseGet(this::extractFallbackFont);
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
