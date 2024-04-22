package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.text.PdfAdditionalInformationTextGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.text.PdfTextGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfPatientValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfSignatureValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfUnitValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfValueGenerator;

public class CertificatePdfGenerator implements PdfGenerator {

    private final List<CertificateTypePdfFillService> certificateTypePdfFillServices = List.of(
        new FK7211PdfFillService(),
        new FK7443PdfFillService()
    );

    public Pdf generate(Certificate certificate, String additionalInfoText) {
        final var pdfGeneratorValueToolkit = new PdfValueGenerator();
        final var pdfGeneratorTextToolkit = new PdfTextGenerator();
        final var certificatePdfFillService = new CertificatePdfFillService(
            new PdfUnitValueGenerator(pdfGeneratorValueToolkit),
            new PdfPatientValueGenerator(pdfGeneratorValueToolkit),
            new PdfSignatureValueGenerator(pdfGeneratorValueToolkit, pdfGeneratorTextToolkit),
            new PdfAdditionalInformationTextGenerator(pdfGeneratorTextToolkit));

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            final var certificateTypeSpecificFillService = certificateTypePdfFillServices.stream()
                .filter(generator -> isCertificateTypeEqual(certificate, generator))
                .findFirst().orElseThrow(() -> throwPdfFillServiceDoesNotExistForType(certificate));

            final var filledPdf = certificatePdfFillService.fillDocument(
                certificate,
                additionalInfoText,
                certificateTypeSpecificFillService
            );

            filledPdf.getDocumentInformation().setTitle(getFileName(certificate));
            filledPdf.getDocumentCatalog().getAcroForm().flatten();
            filledPdf.save(byteArrayOutputStream);
            filledPdf.close();

            return new Pdf(byteArrayOutputStream.toByteArray(), getFileName(certificate));

        } catch (Exception e) {
            throw new IllegalStateException("Could not create Pdf", e);
        }
    }

    private static IllegalStateException throwPdfFillServiceDoesNotExistForType(
        Certificate certificate) {
        return new IllegalStateException(
            String.format(
                "Could not find pdf fill service for certificate type: '%s'",
                certificate.certificateModel().id().type().type()
            )
        );
    }

    private static boolean isCertificateTypeEqual(Certificate certificate,
        CertificateTypePdfFillService generator) {
        return generator.getType().equals(certificate.certificateModel().id().type());
    }

    private String getFileName(Certificate certificate) {
        final var certificateName = certificate.certificateModel().name();
        final var timestamp = LocalDateTime.now()
            .format((DateTimeFormatter.ofPattern("yy-MM-dd_HHmm")));

        return String.format("%s_%s", certificateName, timestamp)
            .replace("å", "a")
            .replace("ä", "a")
            .replace("ö", "o")
            .replace(" ", "_")
            .replace("–", "")
            .replace("__", "_")
            .toLowerCase();
    }
}
