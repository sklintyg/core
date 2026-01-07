package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
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
    try (var outputStream = new ByteArrayOutputStream()) {
      document.save(outputStream);
      return outputStream.toByteArray();
    }
  }

  public List<PdfField> sanitizePdfFields(List<PdfField> pdfFields) {
    return pdfFields.stream().map(this::sanitizePdfField).toList();
  }

  private PdfField sanitizePdfField(PdfField field) {
    final var font = fontResolver.resolveFont(field);
    return fieldSanitizer.sanitize(field, font);
  }

  public List<PdfField> getPdfFields(Predicate<PdfField> findFieldPredicate) {
    return pdfFields.stream()
        .filter(findFieldPredicate)
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