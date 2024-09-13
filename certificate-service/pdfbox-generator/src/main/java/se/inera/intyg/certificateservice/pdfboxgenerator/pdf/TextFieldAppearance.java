package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

public class TextFieldAppearance {

  private final PDTextField field;

  public TextFieldAppearance(PDTextField field) {
    this.field = field;
  }

  public void adjustMultilineFieldHeight() {
    final var fontSize = getFontSize();
    for (PDAnnotationWidget widget : field.getWidgets()) {
      final var rec = widget.getRectangle();

      widget.setRectangle(new PDRectangle(rec.getLowerLeftX(),
          rec.getLowerLeftY(),
          rec.getWidth(),
          rec.getHeight() + (int) Math.round(fontSize) - 1));
    }
  }

  public double getFontSize() {
    return Double.parseDouble(this.getAppearanceParts()[1]);
  }

  private String[] getAppearanceParts() {
    final var appearance = field.getDefaultAppearance();
    return appearance.split("\\s+");
  }
}
