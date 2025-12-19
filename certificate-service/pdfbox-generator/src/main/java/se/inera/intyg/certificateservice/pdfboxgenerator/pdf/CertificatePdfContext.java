package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
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
    pdfFields.forEach(this::getSanatizedField);
  }

  private void getSanatizedField(PdfField field) {
    if (field.getAppend()) {
      System.out.println(field.getValue());
      field.setValue(field.normalizedValue(font));
    } else {
      field.setValue(field.sanitizedValue(font));
    }
  }

  public float getFontSize() {
    return Float.parseFloat(getDefaultAppearanceParts()[1]);
  }

  public PdfField getPdfField(Function<PdfField, Boolean> findFieldPredicate) {
    return pdfFields.stream()
        .filter(findFieldPredicate::apply)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("PdfField not found"));
  }

  private String[] getDefaultAppearanceParts() {
    return defaultAppearance.split("\\s+");
  }

  public PDAcroForm getAcroForm() {
    return document.getDocumentCatalog().getAcroForm();
  }

  public PDRectangle getPatientIdRectangleForOverflow() {
    final var fieldId = templatePdfSpecification.patientIdFieldIds().getLast().id();

    return getAcroForm().getField(fieldId).getWidgets().getFirst().getRectangle();
  }
}