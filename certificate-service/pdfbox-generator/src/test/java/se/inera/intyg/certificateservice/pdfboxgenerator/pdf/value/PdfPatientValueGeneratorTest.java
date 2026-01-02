package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_META_DATA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@ExtendWith(MockitoExtension.class)
class PdfPatientValueGeneratorTest {

  private static final String FIELD_ID = "FIELD_ID";
  private static final String FIELD_ID_2 = "FIELD_ID_2";

  @InjectMocks
  PdfPatientValueGenerator patientInformationHelper;


  @Test
  void shouldSetPatientIdToField() {
    final var certificate = buildCertificate();

    final var expected = List.of(PdfField.builder()
        .id(FIELD_ID)
        .value(CERTIFICATE_META_DATA.patient().id().idWithoutDash())
        .patientField(true)
        .build()
    );

    final var result = patientInformationHelper.generate(certificate,
        List.of(new PdfFieldId(FIELD_ID)));

    assertEquals(expected, result);
  }

  @Test
  void shouldSetPatientIdToFieldForSeveralFields() {
    final var certificate = buildCertificate();

    final var expected = List.of(
        PdfField.builder()
            .id(FIELD_ID)
            .value(CERTIFICATE_META_DATA.patient().id().idWithoutDash())
            .patientField(true)
            .build(),
        PdfField.builder()
            .id(FIELD_ID_2)
            .value(CERTIFICATE_META_DATA.patient().id().idWithoutDash())
            .patientField(true)
            .build()
    );

    final var result = patientInformationHelper.generate(certificate,
        List.of(new PdfFieldId(FIELD_ID), new PdfFieldId(FIELD_ID_2)));

    assertEquals(expected, result);
  }

  private Certificate buildCertificate() {
    return fk7210CertificateBuilder()
        .certificateMetaData(null)
        .metaDataFromSignInstance(CERTIFICATE_META_DATA)
        .build();
  }

}