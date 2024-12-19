package se.inera.intyg.certificateprintservice.print;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;


public class PdfMerger {

  public static byte[] mergePdfs(byte[] pdf, byte[] otherPdf) throws IOException {
    final var merger = new PDFMergerUtility();
    final var doc = Loader.loadPDF(pdf);
    final var infoDoc = Loader.loadPDF(otherPdf);

    merger.appendDocument(doc, infoDoc);
    addPageNumber(doc);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    doc.save(byteArrayOutputStream);
    doc.close();
    return byteArrayOutputStream.toByteArray();
  }

  private static void addPageNumber(PDDocument doc) throws IOException {
    int totalPages = doc.getNumberOfPages();
    for (int i = 0; i < totalPages; i++) {
      PDPage page = doc.getPage(i);

      PDPageContentStream contentStream = new PDPageContentStream(doc, page,
          PDPageContentStream.AppendMode.APPEND, true, true);
      contentStream.setFont(new PDType1Font(FontName.HELVETICA), 10);
      contentStream.beginText();
      contentStream.newLineAtOffset(page.getMediaBox().getWidth() - 70, 15);
      contentStream.showText("Sida %s (%s)".formatted(i + 1, totalPages));
      contentStream.endText();
      contentStream.close();
    }
  }

}
