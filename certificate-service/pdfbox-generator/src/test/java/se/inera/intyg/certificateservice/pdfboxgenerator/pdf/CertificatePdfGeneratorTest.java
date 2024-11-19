package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7472CertificateBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;


@ExtendWith(MockitoExtension.class)
class CertificatePdfGeneratorTest {

  private static final String ADDITIONAL_INFO_TEXT = "additionalInfoText";

  @Mock
  CertificatePdfFillService certificatePdfFillService;

  private PDDocument document;

  CertificatePdfGenerator certificatePdfGenerator;

  @BeforeEach
  void setup() {
    final var classloader = getClass().getClassLoader();
    final var inputStream = classloader.getResourceAsStream("fk7210/pdf/fk7210_v1.pdf");

    try {
      document = Loader.loadPDF(inputStream.readAllBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    certificatePdfGenerator = new CertificatePdfGenerator(certificatePdfFillService);
  }

  @Test
  void shouldThrowErrorIfNoPdfGeneratorForCertificateType() {
    final var certificate = Certificate.builder()
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

    assertThrows(
        IllegalStateException.class,
        () -> certificatePdfGenerator.generate(certificate, ADDITIONAL_INFO_TEXT, false)
    );
  }

  @Nested
  class PdfData {

    @BeforeEach
    void setup() {
      when(
          certificatePdfFillService.fillDocument(any(Certificate.class), anyString(), anyBoolean()))
          .thenReturn(document);
    }

    @Nested
    class Fk7210 {

      @Test
      void shouldReturnPDF() {
        final var pdf = certificatePdfGenerator.generate(
            buildFK7210Certificate(),
            ADDITIONAL_INFO_TEXT,
            false
        );

        assertNotEquals(0, pdf.pdfData().length);
      }

      @Test
      void shouldSetCorrectFileName() {
        final var expected = "intyg_om_graviditet_" + LocalDateTime.now()
            .format((DateTimeFormatter.ofPattern("yy-MM-dd_HHmm")));

        final var pdf = certificatePdfGenerator.generate(
            buildFK7210Certificate(),
            ADDITIONAL_INFO_TEXT,
            false
        );

        assertEquals(expected, pdf.fileName());
      }
    }

    @Nested
    class Fk7472 {

      @Test
      void shouldReturnPDF() {
        final var pdf = certificatePdfGenerator.generate(
            buildFK7472Certificate(),
            ADDITIONAL_INFO_TEXT,
            false
        );

        assertNotEquals(0, pdf.pdfData().length);
      }

      @Test
      void shouldSetCorrectFileName() {
        final var expected = "intyg_om_tillfallig_foraldrapenning_" + LocalDateTime.now()
            .format((DateTimeFormatter.ofPattern("yy-MM-dd_HHmm")));

        final var pdf = certificatePdfGenerator.generate(
            buildFK7472Certificate(),
            ADDITIONAL_INFO_TEXT,
            false
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
