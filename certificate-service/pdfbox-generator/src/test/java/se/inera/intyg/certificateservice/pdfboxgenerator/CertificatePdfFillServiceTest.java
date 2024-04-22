package se.inera.intyg.certificateservice.pdfboxgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7443CertificateBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.pdfboxgenerator.text.PdfAdditionalInformationTextGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfPatientValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfSignatureValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfUnitValueGenerator;

@ExtendWith(MockitoExtension.class)
class CertificatePdfFillServiceTest {

    private static final String TEXT = "TEXT";
    private static final String PATIENT_ID = "PATIENT_ID";

    @Mock
    PdfUnitValueGenerator pdfUnitValueGenerator;

    @Mock
    PdfPatientValueGenerator pdfPatientValueGenerator;

    @Mock
    PdfSignatureValueGenerator pdfSignatureValueGenerator;

    @Mock
    PdfAdditionalInformationTextGenerator pdfAdditionalInformationTextGenerator;

    @Mock
    FK7443PdfFillService fk7443PdfFillService;

    @InjectMocks
    CertificatePdfFillService certificatePdfFillService;

    private Certificate certificate;

    @BeforeEach
    void setup() {
        when(fk7443PdfFillService.getPatientIdFieldId())
            .thenReturn(PATIENT_ID);
    }

    @Nested
    class NotSignedCertificate {

        @BeforeEach
        void setup() {
            certificate = getDraft();
        }

        @Test
        void shouldNotSetMarginText() throws IOException {
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfAdditionalInformationTextGenerator, times(0))
                .addMarginAdditionalInfoText(any(), anyString(), anyString());
        }

        @Test
        void shouldSetDraftWatermark() throws IOException {
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfAdditionalInformationTextGenerator, times(1))
                .addDraftWatermark(any());
        }

        @Test
        void shouldNotSetSentText() throws IOException {
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfAdditionalInformationTextGenerator, times(0))
                .addSentText(any(), any());
        }

        @Test
        void shouldSetSentVisibilityText() throws IOException {
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfAdditionalInformationTextGenerator, times(0))
                .addSentVisibilityText(any());
        }

        @Test
        void shouldNotSetSignedValues() throws IOException {
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfSignatureValueGenerator, times(0))
                .setSignedValues(any(PDDocument.class), any(PDAcroForm.class), any(Certificate.class));
        }

        @Test
        void shouldSetPatientValues() throws IOException {
            final var captor = ArgumentCaptor.forClass(String.class);
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfPatientValueGenerator, times(1))
                .setPatientInformation(any(PDAcroForm.class), any(Certificate.class), captor.capture());
            assertEquals(PATIENT_ID, captor.getValue());
        }

        @Test
        void shouldSetUnitContactInformation() throws IOException {
            final var captor = ArgumentCaptor.forClass(Certificate.class);
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfUnitValueGenerator, times(1))
                .setContactInformation(any(PDAcroForm.class), captor.capture());
            assertEquals(certificate, captor.getValue());
        }

        @Test
        void shouldFillPdfWithCertificateTypeSpecificValues() throws IOException {
            final var captor = ArgumentCaptor.forClass(Certificate.class);
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(fk7443PdfFillService, times(1))
                .fillDocument(any(PDAcroForm.class), captor.capture());
            assertEquals(certificate, captor.getValue());
        }
    }

    @Nested
    class SignedCertificate {

        @BeforeEach
        void setup() {
            certificate = getCertificate();
        }

        @Test
        void shouldSetMarginText() throws IOException {
            final var captor = ArgumentCaptor.forClass(String.class);
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfAdditionalInformationTextGenerator, times(1))
                .addMarginAdditionalInfoText(any(), anyString(), captor.capture());

            assertEquals(TEXT, captor.getValue());
        }

        @Test
        void shouldNotSetDraftWatermark() throws IOException {
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfAdditionalInformationTextGenerator, times(0))
                .addDraftWatermark(any());
        }

        @Test
        void shouldNotSetSentText() throws IOException {
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfAdditionalInformationTextGenerator, times(0))
                .addSentText(any(), any());
        }

        @Test
        void shouldNotSetSentVisibilityText() throws IOException {
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfAdditionalInformationTextGenerator, times(0))
                .addSentVisibilityText(any());
        }

        @Test
        void shouldSetSignedValues() throws IOException {
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfSignatureValueGenerator, times(1))
                .setSignedValues(any(PDDocument.class), any(PDAcroForm.class), any(Certificate.class));
        }

        @Test
        void shouldSetPatientValues() throws IOException {
            final var captor = ArgumentCaptor.forClass(String.class);
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfPatientValueGenerator, times(1))
                .setPatientInformation(any(PDAcroForm.class), any(Certificate.class), captor.capture());
            assertEquals(PATIENT_ID, captor.getValue());
        }

        @Test
        void shouldSetUnitContactInformation() throws IOException {
            final var captor = ArgumentCaptor.forClass(Certificate.class);
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfUnitValueGenerator, times(1))
                .setContactInformation(any(PDAcroForm.class), captor.capture());
            assertEquals(certificate, captor.getValue());
        }

        @Test
        void shouldFillPdfWithCertificateTypeSpecificValues() throws IOException {
            final var captor = ArgumentCaptor.forClass(Certificate.class);
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(fk7443PdfFillService, times(1))
                .fillDocument(any(PDAcroForm.class), captor.capture());
            assertEquals(certificate, captor.getValue());
        }
    }

    @Nested
    class SentCertificate {

        @BeforeEach
        void setup() {
            certificate = getSentCertificate();
        }

        @Test
        void shouldSetMarginText() throws IOException {
            final var captor = ArgumentCaptor.forClass(String.class);
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfAdditionalInformationTextGenerator, times(1))
                .addMarginAdditionalInfoText(any(), anyString(), captor.capture());

            assertEquals(TEXT, captor.getValue());
        }

        @Test
        void shouldNotSetDraftWatermark() throws IOException {
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfAdditionalInformationTextGenerator, times(0))
                .addDraftWatermark(any());
        }

        @Test
        void shouldSetSentText() throws IOException {
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfAdditionalInformationTextGenerator, times(1))
                .addSentText(any(), any());
        }

        @Test
        void shouldSetSentVisibilityText() throws IOException {
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfAdditionalInformationTextGenerator, times(1))
                .addSentVisibilityText(any());
        }

        @Test
        void shouldSetSignedValues() throws IOException {
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfSignatureValueGenerator, times(1))
                .setSignedValues(any(PDDocument.class), any(PDAcroForm.class), any(Certificate.class));
        }

        @Test
        void shouldSetPatientValues() throws IOException {
            final var captor = ArgumentCaptor.forClass(String.class);
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfPatientValueGenerator, times(1))
                .setPatientInformation(any(PDAcroForm.class), any(Certificate.class), captor.capture());
            assertEquals(PATIENT_ID, captor.getValue());
        }

        @Test
        void shouldSetUnitContactInformation() throws IOException {
            final var captor = ArgumentCaptor.forClass(Certificate.class);
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(pdfUnitValueGenerator, times(1))
                .setContactInformation(any(PDAcroForm.class), captor.capture());
            assertEquals(certificate, captor.getValue());
        }

        @Test
        void shouldFillPdfWithCertificateTypeSpecificValues() throws IOException {
            final var captor = ArgumentCaptor.forClass(Certificate.class);
            certificatePdfFillService.fillDocument(certificate, TEXT, fk7443PdfFillService);

            verify(fk7443PdfFillService, times(1))
                .fillDocument(any(PDAcroForm.class), captor.capture());
            assertEquals(certificate, captor.getValue());
        }
    }

    private static Certificate getSentCertificate() {
        return fk7443CertificateBuilder()
            .signed(LocalDateTime.now())
            .status(Status.SIGNED)
            .sent(
                Sent.builder()
                    .sentAt(LocalDateTime.now())
                    .build()
            )
            .build();
    }

    private static Certificate getCertificate() {
        return fk7443CertificateBuilder()
            .signed(LocalDateTime.now())
            .status(Status.SIGNED)
            .sent(null)
            .build();
    }

    private static Certificate getDraft() {
        return fk7443CertificateBuilder()
            .status(Status.DRAFT)
            .sent(null)
            .signed(null)
            .build();
    }
}