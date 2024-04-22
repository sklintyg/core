package se.inera.intyg.certificateservice.pdfboxgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7443CertificateBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificatePdfGenerator;


@ExtendWith(MockitoExtension.class)
class CertificatePdfGeneratorTest {

  private static final String ADDITIONAL_INFO_TEXT = "additionalInfoText";

  @InjectMocks
  CertificatePdfGenerator certificatePdfGenerator;

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

  private Certificate buildFK7211Certificate() {
    return fk7211CertificateBuilder()
        .build();
  }

  private Certificate buildFK7443Certificate() {
    return fk7443CertificateBuilder()
        .build();
  }
}