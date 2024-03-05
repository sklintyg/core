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
    final var pathTemplate = "C:\\Intyg\\core\\certificate-service\\app\\src\\main\\resources\\fk7804-005-f-001_olast_211122.pdf";

    final var pdfDocument = PDDocument.load(new File(pathTemplate));

    setField("SomeFieldName", "SomeFieldValue", pdfDocument);
    pdfDocument.save("lisjp.pdf");
    pdfDocument.close();

  }

  @SneakyThrows
  public static void setField(String name, String value, PDDocument pdfDocument) {
    final var docCatalog = pdfDocument.getDocumentCatalog();
    final var acroForm = docCatalog.getAcroForm();
    final var fields = acroForm.getFields();
    final var tree = fields.get(0).getAcroForm().getFieldTree();

    final var values = fields.get(0).getAcroForm().getCOSObject().getValues();
    final var field = acroForm.getField(name);
    if (field != null) {
      field.setValue(value);
    } else {
      System.err.println("No field found with name:" + name);
    }
  }

  @SneakyThrows
  public static void getField() {
    final var pathTemplate = "C:\\Intyg\\core\\certificate-service\\app\\src\\main\\resources\\fk7804-005-f-001_olast_2111222.pdf";
    final var pdfDocument = PDDocument.load(new File(pathTemplate));

    final var docCatalog = pdfDocument.getDocumentCatalog();
    final var acroForm = docCatalog.getAcroForm();

    //SET PATIENT_ID VALUE (String)
    System.out.println("Modifying patient id...");
    final var patientIdField = acroForm.getField(FIELD_PATIENT_ID);

    System.out.println("Old value is: " + patientIdField.getValueAsString());

    patientIdField.setValue(NEW_PATIENT_ID);
    System.out.println("New value is: " + patientIdField.getValueAsString());

    //SET CHECKBOX VALUE
    System.out.println("\n" + "Modifying checkbox...");
    final var checkboxField = acroForm.getField(FIELD_CHECKBOX);

    System.out.println("Old value is: " + checkboxField.getValueAsString());

    checkboxField.setValue("Off");
    System.out.println("New value is: " + checkboxField.getValueAsString());

    //SET NEW DATE VALUE
    System.out.println("\n" + "Modifying date...");
    final var dateField = acroForm.getField(FIELD_DATE);

    System.out.println("Old value is: " + dateField.getValueAsString());

    dateField.setValue("2024-02-02");
    System.out.println("New value is: " + dateField.getValueAsString());

    //ADD FOOTER TEXT
    System.out.println("\n" + "Adding footer text on the first page...");

    final var page = pdfDocument.getPage(0);
    PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page,
        AppendMode.APPEND, true, true);

    contentStream.beginText();
    final var text = "Footer text";
    contentStream.newLineAtOffset(250, 25);
    contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
    contentStream.showText(text);
    contentStream.endText();
    contentStream.close();

    //ADD WATERMARK ON PDF
    System.out.println("\n" + "Adding watermark on PDF...NOT DONE");

    //MAKE PDF READ-ONLY
    acroForm.flatten();
    System.out.println("\n" + "The PDF has been set to read-only");

    // SAVE AND CLOSE PDF
    pdfDocument.save("lisjp.pdf");
    pdfDocument.close();
    System.out.println("\n" + "The PDF has been saved");

    //Ta bort hjälprutorna
    //Kolla upp om det är möjligt att signera PDFer med PdfBox

  }

}
