package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;

public class TextFieldAppearance {

  private final PDVariableText field;

  public TextFieldAppearance(PDVariableText field) {
    this.field = field;
  }

  public void adjustFieldHeight() {
    final var fontSize = getFontSize();
    for (PDAnnotationWidget widget : field.getWidgets()) {
      final var rec = widget.getRectangle();

      widget.setRectangle(new PDRectangle(rec.getLowerLeftX(),
          rec.getLowerLeftY(),
          rec.getWidth(),
          rec.getHeight() + Math.round(fontSize) - 1));
    }
  }

  public float getFontSize() {
    return Float.parseFloat(this.getAppearanceParts()[1]);
  }

  private String[] getAppearanceParts() {
    final var appearance = field.getDefaultAppearance();
    return appearance.split("\\s+");
  }
}
