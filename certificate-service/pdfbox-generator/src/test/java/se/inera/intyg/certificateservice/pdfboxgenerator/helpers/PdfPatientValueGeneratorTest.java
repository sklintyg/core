package se.inera.intyg.certificateservice.pdfboxgenerator.helpers;

import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfPatientValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfValueGenerator;

@ExtendWith(MockitoExtension.class)
class PdfPatientValueGeneratorTest {

    private static final String FIELD_ID = "FIELD_ID";

    @Mock
    PdfValueGenerator pdfValueGenerator;

    @InjectMocks
    PdfPatientValueGenerator patientInformationHelper;

    PDAcroForm pdAcroForm;

    @Test
    void shouldSetPatientIdToField() throws IOException {
        final var certificate = buildCertificate();
        patientInformationHelper.setPatientInformation(pdAcroForm, certificate, FIELD_ID);

        verify(pdfValueGenerator).setValue(
            pdAcroForm,
            FIELD_ID,
            certificate.certificateMetaData().patient().id().id()
        );
    }

    private Certificate buildCertificate() {
        return fk7211CertificateBuilder().build();
    }

}