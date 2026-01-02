package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;

@Slf4j
@EqualsAndHashCode
public class TextFieldAppearance {

  PDVariableText field;

  public TextFieldAppearance(PDVariableText field) {
    this.field = field;
  }

  public void adjustFieldHeight() {
    adjustFieldHeight(0);
  }

  public void adjustFieldHeight(Integer offset) {
    final var fontSize = getFontSize();
    for (PDAnnotationWidget widget : field.getWidgets()) {
      final var rec = widget.getRectangle();

      widget.setRectangle(new PDRectangle(rec.getLowerLeftX(),
          rec.getLowerLeftY(),
          rec.getWidth(),
          rec.getHeight() + Math.round(fontSize) - 1 + (offset != null ? offset : 0)));
    }
  }

  public float getFontSize() {
    return Float.parseFloat(this.getAppearanceParts()[1]);
  }

  private String[] getAppearanceParts() {
    final var appearance = field.getDefaultAppearance();
    return appearance.split("\\s+");
  }

  private String getFontName() {
    return getAppearanceParts()[0].substring(1);
  }

  public PDFont getFont(PDResources resources) {
    try {
      return resources.getFont(COSName.getPDFName(getFontName()));
    } catch (Exception e) {
      throw new IllegalStateException("Missing font resource in template", e);
    }
  }
}
