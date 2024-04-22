package se.inera.intyg.certificateservice.pdfboxgenerator.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_DATE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_FULL_NAME_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_HSA_ID_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_PA_TITLE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_SPECIALITY_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.PdfConstants.SIGNATURE_WORKPLACE_CODE_FIELD_ID;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;
import se.inera.intyg.certificateservice.pdfboxgenerator.text.PdfTextGenerator;

public class PdfSignatureValueGenerator {

    private final PdfValueGenerator pdfValueGenerator;
    private final PdfTextGenerator pdfTextGenerator;

    public PdfSignatureValueGenerator(PdfValueGenerator pdfValueGenerator,
        PdfTextGenerator pdfTextGenerator) {
        this.pdfValueGenerator = pdfValueGenerator;
        this.pdfTextGenerator = pdfTextGenerator;
    }

    public void setSignedValues(PDDocument document, PDAcroForm acroForm,
        Certificate certificate)
        throws IOException {
        pdfTextGenerator.addDigitalSignatureText(document, acroForm);
        setSignedDate(acroForm, certificate);
        setIssuerFullName(acroForm, certificate);
        setPaTitles(acroForm, certificate);
        setSpeciality(acroForm, certificate);
        setHsaId(acroForm, certificate);
        setWorkplaceCode(acroForm, certificate);
    }

    private void setSignedDate(PDAcroForm acroForm, Certificate certificate)
        throws IOException {
        pdfValueGenerator.setValue(acroForm,
            SIGNATURE_DATE_FIELD_ID,
            certificate.signed().format(DateTimeFormatter.ISO_DATE)
        );
    }

    private void setIssuerFullName(PDAcroForm acroForm, Certificate certificate)
        throws IOException {
        pdfValueGenerator.setValue(acroForm, SIGNATURE_FULL_NAME_FIELD_ID,
            certificate.certificateMetaData().issuer().name().fullName()
        );
    }

    private void setPaTitles(PDAcroForm acroForm, Certificate certificate) throws IOException {
        final var paTitles = certificate.certificateMetaData().issuer().paTitles();
        if (paTitles != null) {
            final var paTitleCodes = paTitles.stream()
                .map(PaTitle::code)
                .collect(Collectors.joining(", "));

            pdfValueGenerator.setValue(acroForm, SIGNATURE_PA_TITLE_FIELD_ID, paTitleCodes);
        }
    }

    private void setSpeciality(PDAcroForm acroForm, Certificate certificate)
        throws IOException {
        final var specialities = certificate.certificateMetaData().issuer().specialities();
        if (specialities != null) {
            final var mappedSpecialities = specialities.stream()
                .map(Speciality::value)
                .collect(Collectors.joining(", "));

            pdfValueGenerator.setValue(
                acroForm,
                SIGNATURE_SPECIALITY_FIELD_ID,
                mappedSpecialities);
        }
    }

    private void setHsaId(PDAcroForm acroForm, Certificate certificate) throws IOException {
        final var hsaId = certificate.certificateMetaData().issuer().hsaId().id();
        pdfValueGenerator.setValue(
            acroForm,
            SIGNATURE_HSA_ID_FIELD_ID,
            hsaId
        );
    }

    private void setWorkplaceCode(PDAcroForm acroForm, Certificate certificate)
        throws IOException {
        final var workplaceCode = certificate.certificateMetaData().issuingUnit().workplaceCode();
        if (workplaceCode != null) {
            pdfValueGenerator.setValue(
                acroForm,
                SIGNATURE_WORKPLACE_CODE_FIELD_ID,
                workplaceCode.code()
            );
        }
    }
}
