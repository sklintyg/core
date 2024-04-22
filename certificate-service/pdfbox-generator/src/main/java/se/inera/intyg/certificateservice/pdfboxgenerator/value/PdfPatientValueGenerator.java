package se.inera.intyg.certificateservice.pdfboxgenerator.value;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class PdfPatientValueGenerator {

    private final PdfValueGenerator pdfValueGenerator;

    public PdfPatientValueGenerator(PdfValueGenerator pdfValueGenerator) {
        this.pdfValueGenerator = pdfValueGenerator;
    }

    public void setPatientInformation(PDAcroForm acroForm, Certificate certificate, String id)
        throws IOException {
        final var patientId = certificate.certificateMetaData().patient().id().id();
        pdfValueGenerator.setValue(acroForm, id, patientId);
    }
}
