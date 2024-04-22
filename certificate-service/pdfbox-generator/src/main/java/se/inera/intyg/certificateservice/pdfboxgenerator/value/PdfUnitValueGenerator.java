package se.inera.intyg.certificateservice.pdfboxgenerator.value;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;

public class PdfUnitValueGenerator {

    private final PdfValueGenerator pdfValueGenerator;

    public PdfUnitValueGenerator(PdfValueGenerator pdfValueGenerator) {
        this.pdfValueGenerator = pdfValueGenerator;
    }

    public void setContactInformation(PDAcroForm acroForm, Certificate certificate)
        throws IOException {
        final var elementData = certificate.getElementDataById(UNIT_CONTACT_INFORMATION);

        final var contactInfo = elementData
            .map(data -> buildAddress(certificate, data.value()))
            .orElse("");

        pdfValueGenerator.setValue(
            acroForm,
            SIGNATURE_CARE_UNIT_CONTACT_INFORMATION_FIELD_ID,
            contactInfo
        );
    }

    private static String buildAddress(Certificate certificate,
        ElementValue elementValue) {
        if (!(elementValue instanceof ElementValueUnitContactInformation unitValue)) {
            throw new IllegalStateException(
                String.format("Wrong value type: '%s'", elementValue.getClass())
            );
        }

        return String.join("\n",
            certificate.certificateMetaData().issuingUnit().name().name(),
            unitValue.address(),
            String.join(" ", unitValue.zipCode(), unitValue.city()),
            String.join(" ", "Telefon:", unitValue.phoneNumber()));
    }
}
