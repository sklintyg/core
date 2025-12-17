package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Builder;
import lombok.Getter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;

@Getter
@Builder
public class CertificatePdfContext implements AutoCloseable {

  private final PDDocument document;
  private final PDFont font;
  private final String defaultAppearance;
  private final AtomicInteger mcid;
  private final Certificate certificate;
  private final TemplatePdfSpecification templatePdfSpecification;
  private final String additionalInfoText;
  private final boolean citizenFormat;
  @Builder.Default
  private final List<PdfField> pdfFields = new ArrayList<>();

  public int nextMcid() {
    return mcid.incrementAndGet();
  }

  @Override
  public void close() throws IOException {
    document.close();
  }

  public byte[] toByteArray() throws IOException {
    try (var outputStream = new java.io.ByteArrayOutputStream()) {
      document.save(outputStream);
      return outputStream.toByteArray();
    }
  }

  public void addDefaultAppearanceToPdfFields() {

    pdfFields.replaceAll(pdfField -> {
      if (pdfField.getAppearance() == null || pdfField.getAppearance().isEmpty()) {
        return pdfField.withAppearance(defaultAppearance);
      }
      return pdfField;
    });
  }

  public void sanatizePdfFields() {
    pdfFields.forEach(field -> field.setValue(field.sanitizedValue(font)));
  }

  public float getFontSize() {
    return Float.parseFloat(getDefaultAppearanceParts()[1]);
  }

  private String[] getDefaultAppearanceParts() {
    return defaultAppearance.split("\\s+");
  }

}