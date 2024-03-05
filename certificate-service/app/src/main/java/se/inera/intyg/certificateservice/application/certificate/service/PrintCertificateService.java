/*
 * Copyright (C) 2024 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.inera.intyg.certificateservice.application.certificate.service;

import java.io.File;
import java.io.IOException;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PrintCertificateService {

  public static final String FIELD_PATIENT_ID = "form1[0].#subform[0].flt_txtPersonNr[0]";
  public static final String FIELD_CHECKBOX = "form1[0].#subform[0].ksr_UndersokningPatient[0]";
  public static final String FIELD_DATE = "form1[0].#subform[0].flt_datumPatient[0]";

  public static final String NEW_PATIENT_ID = "20121212-1212";

  @SneakyThrows
  public void get() {
    final var pathTemplate = "C:\\Users\\mhoernfeldt\\Documents\\intyg\\core\\certificate-service\\app\\src\\main\\java\\se\\inera\\intyg\\certificateservice\\application\\certificate\\service\\fk7804.pdf";

    final var pdfDocument = PDDocument.load(new File(pathTemplate));

    fillPDF(pdfDocument);
    pdfDocument.save("lisjp.pdf");
    pdfDocument.close();
  }

  @SneakyThrows
  public static void setField(String name, String value, PDDocument pdfDocument) {
    final var docCatalog = pdfDocument.getDocumentCatalog();
    final var acroForm = docCatalog.getAcroForm();
    final var field = acroForm.getField(name);
    if (field != null) {
      System.out.println("Modifying field with name " + name);
      System.out.println("Old value is: " + field.getValueAsString());
      field.setValue(value);
      System.out.println("New value is: " + field.getValueAsString());
    } else {
      System.err.println("No field found with name:" + name);
    }
  }

  @SneakyThrows
  public static void fillPDF(PDDocument pdfDocument) {
    final var docCatalog = pdfDocument.getDocumentCatalog();
    final var acroForm = docCatalog.getAcroForm();

    setField(FIELD_PATIENT_ID, NEW_PATIENT_ID, pdfDocument);
    setField(FIELD_CHECKBOX, "Off", pdfDocument);
    setField(FIELD_DATE, "2024-02-02", pdfDocument);

    fillFooter(pdfDocument);

    System.out.println("\n" + "Adding watermark on PDF...NOT DONE");

    //MAKE PDF READ-ONLY
    acroForm.flatten();
    System.out.println("\n" + "The PDF has been set to read-only");

    //Ta bort hjälprutorna
    //Kolla upp om det är möjligt att signera PDFer med PdfBox

  }

  private static void fillFooter(PDDocument pdfDocument) throws IOException {
    final var text = "Footer text";
    final var page = pdfDocument.getPage(0);
    final var contentStream = new PDPageContentStream(pdfDocument, page,
        AppendMode.APPEND, true, true);

    System.out.println("\n" + "Adding footer text on the first page...");

    contentStream.beginText();
    contentStream.newLineAtOffset(250, 25);
    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
    contentStream.showText(text);
    contentStream.endText();
    contentStream.close();
  }
}
