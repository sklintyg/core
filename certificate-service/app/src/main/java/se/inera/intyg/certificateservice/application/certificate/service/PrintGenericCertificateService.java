package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PrintGenericCertificateService {

  @SneakyThrows
  public void get() {

    final var pdfDocument = new PDDocument();
    PDPage page = new PDPage(PDRectangle.A4);
    pdfDocument.addPage(page);
    PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);

    contentStream.setNonStrokingColor(235, 235, 235);
    contentStream.addRect(50, 200, 500, 100);
    contentStream.fill();
    contentStream.setNonStrokingColor(0, 0, 0);
    contentStream.beginText();
    contentStream.setFont(PDType1Font.HELVETICA, 10);
    contentStream.newLineAtOffset(260, 240);
    contentStream.showText("Text on light gray rectangle");

    contentStream.newLineAtOffset(100, 100);
    contentStream.showText("____________________________");

    contentStream.endText();
    PDImageXObject pdImage = PDImageXObject.createFromFile(
        "C:\\Intyg\\core\\certificate-service\\app\\src\\main\\resources\\transportstyrelsen_logga.png",
        pdfDocument);

    contentStream.drawImage(pdImage, 10, 700, 160, 100);

    contentStream.close();
    pdfDocument.save("generic.pdf");

    pdfDocument.close();
  }
}
