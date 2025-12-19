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
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;

@Getter
@Builder
public class CertificatePdfContext implements AutoCloseable {

  private final PDDocument document;
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

  public void sanitizePdfFields(PdfFontResolver fontResolver, PdfFieldSanitizer fieldSanitizer) {
    pdfFields.forEach(field -> {
      final var font = fontResolver.resolveFont(field);
      fieldSanitizer.sanitize(field, font);
    });
  }

  public PdfField getPdfField(Function<PdfField, Boolean> findFieldPredicate) {
    return pdfFields.stream()
        .filter(findFieldPredicate::apply)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("PdfField not found"));
  }

  public PDAcroForm getAcroForm() {
    return document.getDocumentCatalog().getAcroForm();
  }

  public PDRectangle getPatientIdRectangleForOverflow() {
    final var fieldId = templatePdfSpecification.patientIdFieldIds().getLast().id();

    return getAcroForm().getField(fieldId).getWidgets().getFirst().getRectangle();
  }
}