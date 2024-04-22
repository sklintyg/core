package se.inera.intyg.certificateservice.pdfboxgenerator.helpers;

import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.contactInfoElementDataBuilder;

import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfUnitValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfValueGenerator;

@ExtendWith(MockitoExtension.class)
class PdfUnitValueGeneratorTest {

    @Mock
    PdfValueGenerator pdfValueGenerator;

    @InjectMocks
    PdfUnitValueGenerator pdfUnitValueGenerator;

    @Mock
    PDAcroForm pdAcroForm;

    @Test
    void shouldSetContactInformationOfUnitToField() throws IOException {
        final var certificate = buildCertificate();
        final var unit = certificate.certificateMetaData().issuingUnit();
        final var elementData = certificate.getElementDataById(
            new ElementId("UNIT_CONTACT_INFORMATION")
        );
        final var unitValue = (ElementValueUnitContactInformation) elementData.get().value();
        final var expectedAddress =
            unit.name().name() + "\n" + unitValue.address() + "\n" + unitValue.zipCode() + " "
                + unitValue.city() + "\nTelefon: " + unitValue.phoneNumber();

        pdfUnitValueGenerator.setContactInformation(pdAcroForm, certificate);

        verify(pdfValueGenerator).setValue(
            pdAcroForm,
            "form1[0].#subform[0].flt_txtVardgivarensNamnAdressTelefon[0]",
            expectedAddress
        );
    }

    private Certificate buildCertificate() {
        return fk7211CertificateBuilder()
            .elementData(List.of(contactInfoElementDataBuilder().build()))
            .build();
    }

}