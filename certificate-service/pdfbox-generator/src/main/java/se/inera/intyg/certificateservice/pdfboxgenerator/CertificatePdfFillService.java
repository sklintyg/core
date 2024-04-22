package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.io.IOException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.pdfboxgenerator.text.PdfAdditionalInformationTextGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfPatientValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfSignatureValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfUnitValueGenerator;


public class CertificatePdfFillService {

    private final PdfUnitValueGenerator pdfUnitValueGenerator;
    private final PdfPatientValueGenerator pdfPatientValueGenerator;
    private final PdfSignatureValueGenerator pdfSignatureValueGenerator;
    private final PdfAdditionalInformationTextGenerator pdfAdditionalInformationTextGenerator;

    public CertificatePdfFillService(PdfUnitValueGenerator pdfUnitValueGenerator,
        PdfPatientValueGenerator pdfPatientValueGenerator,
        PdfSignatureValueGenerator pdfSignatureValueGenerator,
        PdfAdditionalInformationTextGenerator pdfAdditionalInformationTextGenerator) {
        this.pdfUnitValueGenerator = pdfUnitValueGenerator;
        this.pdfPatientValueGenerator = pdfPatientValueGenerator;
        this.pdfSignatureValueGenerator = pdfSignatureValueGenerator;
        this.pdfAdditionalInformationTextGenerator = pdfAdditionalInformationTextGenerator;
    }

    public PDDocument fillDocument(Certificate certificate, String additionalInfoText,
        CertificateTypePdfFillService certificateValueGenerator) {
        final var template = certificate.certificateModel().pdfTemplatePath();
        try (final var inputStream = getClass().getClassLoader().getResourceAsStream(template)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Template not found: " + template);
            }
            final var document = Loader.loadPDF(inputStream.readAllBytes());
            final var acroForm = document.getDocumentCatalog().getAcroForm();

            pdfPatientValueGenerator.setPatientInformation(
                acroForm,
                certificate,
                certificateValueGenerator.getPatientIdFieldId()
            );

            pdfUnitValueGenerator.setContactInformation(acroForm, certificate);
            certificateValueGenerator.fillDocument(acroForm, certificate);

            setMarginText(document, certificate, additionalInfoText);
            setSentText(document, certificate);
            setDraftWatermark(document, certificate);
            setSignatureAndSignedValues(certificate, document, acroForm);

            return document;
        } catch (Exception e) {
            throw new IllegalStateException("Could not create Pdf", e);
        }
    }

    private void setSignatureAndSignedValues(Certificate certificate, PDDocument document,
        PDAcroForm acroForm)
        throws IOException {
        if (certificate.status() == Status.SIGNED) {
            pdfSignatureValueGenerator.setSignedValues(document, acroForm, certificate);
        }
    }

    private void setSentText(PDDocument document, Certificate certificate)
        throws IOException {
        if (certificate.sent() != null && certificate.sent().sentAt() != null) {
            pdfAdditionalInformationTextGenerator.addSentText(document, certificate);
            pdfAdditionalInformationTextGenerator.addSentVisibilityText(document);
        }
    }

    private void setMarginText(PDDocument document, Certificate certificate,
        String additionalInfoText)
        throws IOException {
        if (certificate.status() == Status.SIGNED) {
            pdfAdditionalInformationTextGenerator.addMarginAdditionalInfoText(
                document,
                certificate.id().id(),
                additionalInfoText
            );
        }
    }

    private void setDraftWatermark(PDDocument document, Certificate certificate)
        throws IOException {
        if (certificate.status() == Status.DRAFT) {
            pdfAdditionalInformationTextGenerator.addDraftWatermark(document);
        }
    }
}
