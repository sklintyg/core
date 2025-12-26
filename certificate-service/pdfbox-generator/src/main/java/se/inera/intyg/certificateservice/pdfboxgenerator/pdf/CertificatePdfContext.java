package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import lombok.Builder;
import lombok.Value;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;

@Value
@Builder
public class CertificatePdfContext implements AutoCloseable {

  PDDocument document;
  AtomicInteger mcid;
  Certificate certificate;
  TemplatePdfSpecification templatePdfSpecification;
  String additionalInfoText;
  boolean citizenFormat;
  @Builder.Default
  List<PdfField> pdfFields = new ArrayList<>();

  PdfFontResolver fontResolver;
  PdfFieldSanitizer fieldSanitizer;

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

  public void sanitizePdfFields() {
    pdfFields.forEach(this::sanitizePdfField);
  }

  public void sanitizePdfField(PdfField field) {
    final var font = fontResolver.resolveFont(field);
    fieldSanitizer.sanitize(field, font);
  }

  public List<PdfField> getPdfFields(Function<PdfField, Boolean> findFieldPredicate) {
    return pdfFields.stream()
        .filter(findFieldPredicate::apply)
        .toList();
  }

  public PDAcroForm getAcroForm() {
    return document.getDocumentCatalog().getAcroForm();
  }

  public PDRectangle getPatientIdRectangleForOverflow() {
    final var fieldId = templatePdfSpecification.patientIdFieldIds().getLast().id();

    return getAcroForm().getField(fieldId).getWidgets().getFirst().getRectangle();
  }
}