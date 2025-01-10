package se.inera.intyg.certificateprintservice.playwright.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PrintInformationTest {

  @Test
  void getPdfOptions() {
    String header = "header";
    String footer = "footer";
    final var printInformation = new PrintInformation();
    printInformation.setHeaderHtml(header);
    printInformation.setFooterHtml(footer);

    final var pdfOptions = printInformation.getPdfOptions();
    assertAll(
        () -> assertEquals("A4", pdfOptions.format),
        () -> assertTrue(pdfOptions.printBackground),
        () -> assertTrue(pdfOptions.displayHeaderFooter),
        () -> assertTrue(pdfOptions.tagged),
        () -> assertEquals(header, pdfOptions.headerTemplate),
        () -> assertEquals(footer, pdfOptions.footerTemplate));
  }
}