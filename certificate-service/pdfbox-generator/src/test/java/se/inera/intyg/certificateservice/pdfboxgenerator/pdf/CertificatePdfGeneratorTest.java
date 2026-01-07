package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7472CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.fk7210certificateModelBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.GeneralPdfSpecification;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory.CertificatePdfContextFactory;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.service.CertificatePdfFillService;


@ExtendWith(MockitoExtension.class)
class CertificatePdfGeneratorTest {

  private static final String ADDITIONAL_INFO_TEXT = "additionalInfoText";

  @Mock
  CertificatePdfFillService certificatePdfFillService;
  @Mock
  CertificatePdfContextFactory certificatePdfContextFactory;

  private PDDocument document;

  CertificatePdfGenerator certificatePdfGenerator;

  @BeforeEach
  void setup() throws IOException {
    final var classloader = getClass().getClassLoader();
    try (final var inputStream = classloader.getResourceAsStream("fk7210/pdf/fk7210_v1.pdf")) {
      if (inputStream == null) {
        throw new IllegalStateException("Could not load test PDF");
      }
      document = Loader.loadPDF(inputStream.readAllBytes());
    }

    certificatePdfGenerator = new CertificatePdfGenerator(certificatePdfFillService,
        certificatePdfContextFactory);
  }

  @Nested
  class PdfExceptions {

    @Test
    void shouldThrowErrorIfNoPdfGeneratorForCertificateType() {
      final var certificate = MedicalCertificate.builder()
          .certificateModel(
              CertificateModel.builder()
                  .id(
                      CertificateModelId.builder()
                          .type(new CertificateType("NOT_IT"))
                          .build()
                  )
                  .build()
          )
          .build();

      final var options = PdfGeneratorOptions.builder()
          .additionalInfoText(ADDITIONAL_INFO_TEXT)
          .citizenFormat(false)
          .hiddenElements(Collections.emptyList())
          .build();

      assertThrows(
          IllegalArgumentException.class,
          () -> certificatePdfGenerator.generate(certificate, options)
      );
    }

    @Test
    void shouldThrowErrorIfPdfSpecificationIsWrong() {
      final var certificateModel = fk7210certificateModelBuilder()
          .pdfSpecification(GeneralPdfSpecification.builder().build()).build();

      final var certificate = fk7210CertificateBuilder()
          .certificateModel(certificateModel)
          .build();

      final var options = PdfGeneratorOptions.builder()
          .additionalInfoText(ADDITIONAL_INFO_TEXT)
          .citizenFormat(false)
          .hiddenElements(Collections.emptyList())
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> certificatePdfGenerator.generate(certificate, options));
    }

    @Test
    void shouldThrowErrorIfHidingElementsInTemplatePdf() {
      final var options = PdfGeneratorOptions.builder()
          .additionalInfoText(ADDITIONAL_INFO_TEXT)
          .citizenFormat(false)
          .hiddenElements(
              Collections.singletonList(new ElementId("1234"))
          )
          .build();
      final var certificate = buildFK7210Certificate();

      assertThrows(IllegalArgumentException.class,
          () -> certificatePdfGenerator.generate(
              certificate,
              options
          ));
    }

    @Test
    void shouldThrowErrorIfPdfFillingFails() {
      when(certificatePdfContextFactory.create(any(), any(), any())).thenThrow(
          new IllegalStateException("Creation failed"));

      final var options = PdfGeneratorOptions.builder()
          .additionalInfoText(ADDITIONAL_INFO_TEXT)
          .citizenFormat(false)
          .hiddenElements(Collections.emptyList())
          .build();

      final var certificate = buildFK7210Certificate();

      assertThrows(IllegalStateException.class,
          () -> certificatePdfGenerator.generate(
              certificate,
              options
          ));
    }
  }

  @Nested
  class PdfData {

    @BeforeEach
    void setup() throws IOException {
      final var mockContext = mock(CertificatePdfContext.class);
      when(mockContext.getDocument()).thenReturn(document);
      when(mockContext.toByteArray()).thenAnswer(invocation -> {
        try (var outputStream = new java.io.ByteArrayOutputStream()) {
          document.save(outputStream);
          return outputStream.toByteArray();
        }
      });
      doNothing().when(mockContext).close();
      when(certificatePdfContextFactory.create(any(), any(), any())).thenReturn(mockContext);
      when(certificatePdfFillService.fillDocument(any(CertificatePdfContext.class)))
          .thenReturn(document);
    }

    @Nested
    class Fk7210 {

      @Test
      void shouldReturnPDF() {
        final var options = PdfGeneratorOptions.builder()
            .additionalInfoText(ADDITIONAL_INFO_TEXT)
            .citizenFormat(false)
            .hiddenElements(Collections.emptyList())
            .build();

        final var pdf = certificatePdfGenerator.generate(
            buildFK7210Certificate(),
            options
        );

        assertNotEquals(0, pdf.pdfData().length);
      }

      @Test
      void shouldSetCorrectFileName() {
        final var expected = "intyg_om_graviditet_" + LocalDateTime.now()
            .format((DateTimeFormatter.ofPattern("yy-MM-dd_HHmm")));

        final var options = PdfGeneratorOptions.builder()
            .additionalInfoText(ADDITIONAL_INFO_TEXT)
            .citizenFormat(false)
            .hiddenElements(Collections.emptyList())
            .build();

        final var pdf = certificatePdfGenerator.generate(
            buildFK7210Certificate(),
            options
        );

        assertEquals(expected, pdf.fileName());
      }
    }

    @Nested
    class Fk7472 {

      @Test
      void shouldReturnPDF() {
        final var options = PdfGeneratorOptions.builder()
            .additionalInfoText(ADDITIONAL_INFO_TEXT)
            .citizenFormat(false)
            .hiddenElements(Collections.emptyList())
            .build();

        final var pdf = certificatePdfGenerator.generate(
            buildFK7472Certificate(),
            options
        );

        assertNotEquals(0, pdf.pdfData().length);
      }

      @Test
      void shouldSetCorrectFileName() {
        final var expected = "intyg_om_tillfallig_foraldrapenning_" + LocalDateTime.now()
            .format((DateTimeFormatter.ofPattern("yy-MM-dd_HHmm")));

        final var options = PdfGeneratorOptions.builder()
            .additionalInfoText(ADDITIONAL_INFO_TEXT)
            .citizenFormat(false)
            .hiddenElements(Collections.emptyList())
            .build();

        final var pdf = certificatePdfGenerator.generate(
            buildFK7472Certificate(),
            options
        );

        assertEquals(expected, pdf.fileName());
      }
    }
  }

  private Certificate buildFK7210Certificate() {
    return fk7210CertificateBuilder()
        .build();
  }

  private Certificate buildFK7472Certificate() {
    return fk7472CertificateBuilder()
        .build();
  }
}

