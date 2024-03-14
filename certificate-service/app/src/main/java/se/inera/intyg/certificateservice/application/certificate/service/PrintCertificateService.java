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
import java.util.HashMap;
import lombok.SneakyThrows;
import org.apache.pdfbox.multipdf.Overlay;
import org.apache.pdfbox.multipdf.Overlay.Position;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PrintCertificateService {

  public static final String FIELD_PATIENT_ID = "form1[0].#subform[0].flt_txtPersonNr[0]";
  public static final String FIELD_CHECKBOX = "form1[0].#subform[0].ksr_UndersokningPatient[0]";
  public static final String FIELD_DATE = "form1[0].#subform[0].flt_datumPatient[0]";
  public static final String FIELD_TEXT_2 = "form1[0].#subform[0].flt_txtYrkeArbetsuppgifter[0]";
  public static final String FIELD_TEXT_5 = "form1[0].Sida2[0].flt_txtBeskrivUndersokningsfynd[0]";
  public static final String FIELD_TEXT_VALUE =
      "Lorem ipsum dolor sit amet, consectetur adipiscing "
          + "elit. Vivamus pellentesque, arcu sit amet dictum malesuada, dolor nibh rutrum ligula, "
          + "ut interdum ipsum nibh iaculis magna. Pellentesque habitant morbi tristique senectus "
          + "et netus et malesuada fames ac turpis egestas. Curabitur ullamcorper congue purus eu "
          + "condimentum. Suspendisse id consectetur dui. Donec in posuere nisl, at tincidunt "
          + "felis. Ut aliquet ex nec turpis ultricies faucibus. Sed enim mauris, feugiat a risus "
          + "ut, ultricies cursus purus. Sed ultricies commodo lacus, nec dictum ante egestas sit "
          + "amet.";

  public static final String NEW_PATIENT_ID = "20121212-1212";

  public ClassLoader classLoader = getClass().getClassLoader();


  @SneakyThrows
  public void get() {
    File file = new File(classLoader.getResource("fk7804_ifylld_utan_hjalptext.pdf").getFile());
    final var pdfDocument = PDDocument.load(new File(String.valueOf(file)));

    fillPDF(pdfDocument);
    pdfDocument.save("lisjp.pdf");
    pdfDocument.close();
  }

  @SneakyThrows
  public void setField(String name, String value, PDDocument pdfDocument) {
    final var docCatalog = pdfDocument.getDocumentCatalog();
    final var acroForm = docCatalog.getAcroForm();
    final var field = acroForm.getField(name);
    if (field != null) {
      System.out.println("\n" + "Modifying field with name " + name);
      System.out.println("Old value is: " + field.getValueAsString());
      field.setValue(value);
      System.out.println("New value is: " + field.getValueAsString());
    } else {
      System.err.println("No field found with name:" + name);
    }
  }

  @SneakyThrows
  public void fillPDF(PDDocument pdfDocument) {
    final var docCatalog = pdfDocument.getDocumentCatalog();
    final var acroForm = docCatalog.getAcroForm();

    setField(FIELD_PATIENT_ID, NEW_PATIENT_ID, pdfDocument);
    setField(FIELD_CHECKBOX, "Off", pdfDocument);
    setField(FIELD_CHECKBOX, "1", pdfDocument);
    setField(FIELD_DATE, "2024-02-02", pdfDocument);
    setField(FIELD_TEXT_2, FIELD_TEXT_VALUE, pdfDocument);
    setField(FIELD_TEXT_5, FIELD_TEXT_VALUE, pdfDocument);

    fillFooter(pdfDocument);

    System.out.println("\n" + "Adding watermark on PDF...");
    addWatermark(pdfDocument);

    System.out.println("\n" + "Setting the PDF as read-only...");
    acroForm.flatten();

  }

  @SneakyThrows
  private void addWatermark(PDDocument pdfDocument) {
    File file = new File(classLoader.getResource("UTKAST.pdf").getFile());
    final var overlay = new Overlay();
    overlay.setInputPDF(pdfDocument);
    overlay.setOverlayPosition(Position.BACKGROUND);
    overlay.setAllPagesOverlayFile(String.valueOf(file));
    overlay.overlay(new HashMap<>()).save("lisjp.pdf");
  }

  private void fillFooter(PDDocument pdfDocument) throws IOException {
    final var text = "Det här intyget är utksrivet via Webcert";
    final var fontSize = 9;
    final var page = pdfDocument.getPage(0);
    final var font = PDType1Font.HELVETICA;
    final var contentStream = new PDPageContentStream(pdfDocument, page,
        AppendMode.APPEND, true, true);

    System.out.println("\n" + "Adding footer text on the first page...");

    float titleWidth = font.getStringWidth(text) / 1000 * fontSize;
    float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * fontSize;
    contentStream.beginText();
    contentStream.newLineAtOffset((
            page.getMediaBox().getWidth() - titleWidth) / 2,
        titleHeight + 25
    );
    contentStream.setFont(font, fontSize);
    contentStream.drawString(text);
    contentStream.endText();
    contentStream.close();
  }
}
