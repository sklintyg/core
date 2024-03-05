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

public class PrintCertificateService {

  @SneakyThrows
  public void get() {
    final var pathTemplate = "C:\\Users\\mhoernfeldt\\Documents\\intyg\\core\\certificate-service\\app\\src\\main\\java\\se\\inera\\intyg\\certificateservice\\application\\certificate\\service\\fk7804.pdf";

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

}
