package se.inera.intyg.certificateservice.pdfboxgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7443CertificateBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificatePdfFillService;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificatePdfGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificateTypePdfFillService;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7211.FK7211PdfFillService;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7443.FK7443PdfFillService;


@ExtendWith(MockitoExtension.class)
class CertificatePdfGeneratorTest {

  private static final String ADDITIONAL_INFO_TEXT = "additionalInfoText";

  @Mock
  FK7443PdfFillService fk7443PdfFillService;

  @Mock
  FK7211PdfFillService fk7211PdfFillService;

  @Mock
  CertificatePdfFillService certificatePdfFillService;

  private PDDocument document;

  List<CertificateTypePdfFillService> certificateTypePdfFillServiceList;

  CertificatePdfGenerator certificatePdfGenerator;

  @BeforeEach
  void setup() {
    final var classloader = getClass().getClassLoader();
    final var inputStream = classloader.getResourceAsStream("fk7211/pdf/fk7211_v1.pdf");

    try {
      document = Loader.loadPDF(inputStream.readAllBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    when(fk7211PdfFillService.getType())
        .thenReturn(new CertificateType("fk7211"));

    certificateTypePdfFillServiceList = List.of(fk7211PdfFillService, fk7443PdfFillService);
    certificatePdfGenerator = new CertificatePdfGenerator(certificateTypePdfFillServiceList,
        certificatePdfFillService);
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
        () -> certificatePdfGenerator.generate(certificate, ADDITIONAL_INFO_TEXT)
    );
  }

  @Nested
  class PdfData {

    @BeforeEach
    void setup() {
      when(certificatePdfFillService.fillDocument(any(Certificate.class), anyString(),
          any(CertificateTypePdfFillService.class)))
          .thenReturn(document);
    }

    @Nested
    class Fk7211 {

      @Test
      void shouldReturnPDF() {
        final var pdf = certificatePdfGenerator.generate(
            buildFK7211Certificate(),
            ADDITIONAL_INFO_TEXT
        );

        assertNotEquals(0, pdf.pdfData().length);
      }

      @Test
      void shouldSetCorrectFileName() {
        final var expected = "intyg_om_graviditet_" + LocalDateTime.now()
            .format((DateTimeFormatter.ofPattern("yy-MM-dd_HHmm")));

        final var pdf = certificatePdfGenerator.generate(
            buildFK7211Certificate(),
            ADDITIONAL_INFO_TEXT
        );

        assertEquals(expected, pdf.fileName());
      }
    }

    @Nested
    class Fk7443 {

      @BeforeEach
      void setup() {
        when(fk7443PdfFillService.getType())
            .thenReturn(new CertificateType("fk7443"));
      }

      @Test
      void shouldReturnPDF() {
        final var pdf = certificatePdfGenerator.generate(
            buildFK7443Certificate(),
            ADDITIONAL_INFO_TEXT
        );

        assertNotEquals(0, pdf.pdfData().length);
      }

      @Test
      void shouldSetCorrectFileName() {
        final var expected = "intyg_om_tillfallig_foraldrapenning_" + LocalDateTime.now()
            .format((DateTimeFormatter.ofPattern("yy-MM-dd_HHmm")));

        final var pdf = certificatePdfGenerator.generate(
            buildFK7443Certificate(),
            ADDITIONAL_INFO_TEXT
        );

        assertEquals(expected, pdf.fileName());
      }
    }
  }

  private Certificate buildFK7211Certificate() {
    return fk7211CertificateBuilder()
        .build();
  }

  private Certificate buildFK7443Certificate() {
    return fk7443CertificateBuilder()
        .build();
  }
}