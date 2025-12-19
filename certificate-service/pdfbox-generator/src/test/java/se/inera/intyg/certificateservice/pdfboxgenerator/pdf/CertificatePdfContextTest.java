package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.pdfbox.pdmodel.PDDocument;
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
}