package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.junit.jupiter.api.Test;

class CertificatePdfContextTest {

  @Test
  void shouldIncrementMcidCorrectly() {
    final var context = CertificatePdfContext.builder()
        .mcid(new AtomicInteger(0))
        .document(new PDDocument())
        .additionalInfoText("")
        .citizenFormat(false)
        .build();

    assertAll(
        () -> assertEquals(1, context.nextMcid()),
        () -> assertEquals(2, context.nextMcid())
    );
  }

  @Test
  void shouldSanatizePdfFieldsCorrectly() {
    final var context = CertificatePdfContext.builder()
        .mcid(new AtomicInteger(0))
        .document(new PDDocument())
        .additionalInfoText("")
        .font(new PDType1Font(FontName.HELVETICA))
        .citizenFormat(false)
        .build();

    context.getPdfFields().add(PdfField.builder()
        .id("field1")
        .value("text\u2013with\u2014dashes")
        .build());

    context.sanatizePdfFields();

    assertEquals("text-with-dashes", context.getPdfFields().getFirst().getValue());
  }

  @Test
  void shouldAddDefaultAppearanceToPdfFields() {
    final var context = CertificatePdfContext.builder()
        .mcid(new AtomicInteger(0))
        .document(new PDDocument())
        .additionalInfoText("")
        .defaultAppearance("defaultAppearance")
        .citizenFormat(false)
        .build();

    context.getPdfFields().add(PdfField.builder()
        .id("field1")
        .value("value1")
        .build());
    context.getPdfFields().add(PdfField.builder()
        .id("field2")
        .value("value2")
        .appearance("customAppearance")
        .build());

    context.addDefaultAppearanceToPdfFields();

    assertAll(
        () -> assertEquals("defaultAppearance", context.getPdfFields().getFirst().getAppearance()),
        () -> assertEquals("customAppearance", context.getPdfFields().get(1).getAppearance())
    );
  }
}