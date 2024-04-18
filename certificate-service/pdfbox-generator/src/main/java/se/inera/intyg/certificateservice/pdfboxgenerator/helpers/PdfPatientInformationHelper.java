package se.inera.intyg.certificateservice.pdfboxgenerator.helpers;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.pdfboxgenerator.toolkits.PdfGeneratorValueToolkit;

public class PdfPatientInformationHelper {

  private final PdfGeneratorValueToolkit pdfGeneratorValueToolkit;

  public PdfPatientInformationHelper(PdfGeneratorValueToolkit pdfGeneratorValueToolkit) {
    this.pdfGeneratorValueToolkit = pdfGeneratorValueToolkit;
  }

  public void setPatientInformation(PDAcroForm acroForm, Certificate certificate, String id)
      throws IOException {
    final var patientId = certificate.certificateMetaData().patient().id().id();
    pdfGeneratorValueToolkit.setValue(acroForm, id, patientId);
  }
}
