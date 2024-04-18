package se.inera.intyg.certificateservice.pdfboxgenerator.toolkits;

import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.CHECKED_BOX_VALUE;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

public class PdfGeneratorValueToolkit {

  public void setValue(PDAcroForm acroForm, String fieldId, String value)
      throws IOException {
    if (value != null) {
      final var field = acroForm.getField(fieldId);

      if (field == null) {
        throw new IllegalStateException(
            String.format("Field is null when getting field of id '%s'", fieldId)
        );
      }

      field.setValue(value);
    }
  }

  public void setCheckedBoxValue(PDAcroForm acroForm, String fieldId)
      throws IOException {
    final var field = acroForm.getField(fieldId);
    field.setValue(CHECKED_BOX_VALUE);
  }
}
