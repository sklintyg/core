package se.inera.intyg.certificateprintservice.playwright;

import static org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode.APPEND;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;


public class PdfMerger {

  private PdfMerger() {
    throw new IllegalStateException("Utility class");
  }

  private static final int PPI = 72;
  private static final int FONT_SIZE = 10;
  private static final int MARGIN_MILLIMETERS = 20;
  private static final float MILLIMETERS_PER_INCH = 25.4f;

  public static byte[] mergePdfs(byte[] detailsPdf, byte[] infoPdf) {
    try {
      final var merger = new PDFMergerUtility();
      final var detailsDoc = Loader.loadPDF(detailsPdf);
      final var infoDoc = Loader.loadPDF(infoPdf);
      final var byteArrayOutputStream = new ByteArrayOutputStream();

      merger.appendDocument(detailsDoc, infoDoc);
      addPageNumber(detailsDoc);
      detailsDoc.save(byteArrayOutputStream);
      detailsDoc.close();
      return byteArrayOutputStream.toByteArray();

    } catch (Exception e) {
      throw new IllegalStateException("Failure merging detailsPdf and infoPdf.", e);
    }
  }

  private static void addPageNumber(PDDocument doc) throws IOException {
    final var totalPages = doc.getNumberOfPages();
    for (int i = 0; i < totalPages; i++) {
      final var page = doc.getPage(i);
      final var contentStream = new PDPageContentStream(doc, page, APPEND, true, true);

      final var font = new PDType1Font(FontName.HELVETICA);
      final var pageNumber = "Sida %s (%s)".formatted(i + 1, totalPages);
      final var textWidth = (font.getStringWidth(pageNumber) / 1000) * FONT_SIZE;
      final var textHeight = (font.getFontDescriptor().getCapHeight()) / 1000 * FONT_SIZE;
      final var marginWidth = PPI * MARGIN_MILLIMETERS / MILLIMETERS_PER_INCH;
      final var positionX = page.getMediaBox().getWidth() - textWidth - marginWidth;
      final var positionY = marginWidth - textHeight / 2;

      contentStream.setFont(font, FONT_SIZE);
      contentStream.beginText();
      contentStream.newLineAtOffset(positionX, positionY);
      contentStream.showText(pageNumber);
      contentStream.endText();
      contentStream.close();
    }
  }

}
