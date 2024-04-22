package se.inera.intyg.certificateservice.pdfboxgenerator.helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;
import se.inera.intyg.certificateservice.pdfboxgenerator.value.PdfPatientValueGenerator;

@ExtendWith(MockitoExtension.class)
class PdfPatientValueGeneratorTest {

  private static final String FIELD_ID = "FIELD_ID";

  @InjectMocks
  PdfPatientValueGenerator patientInformationHelper;


  @Test
  void shouldSetPatientIdToField() {
    final var certificate = buildCertificate();

    final var expected = List.of(PdfField.builder()
        .id(FIELD_ID)
        .value(certificate.certificateMetaData().patient().id().id())
        .build()
    );

    final var result = patientInformationHelper.generate(certificate, FIELD_ID);

    assertEquals(expected, result);
  }

  private Certificate buildCertificate() {
    return fk7211CertificateBuilder().build();
  }

}