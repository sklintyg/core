package se.inera.intyg.certificateservice.pdfboxgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7211_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.DATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;


@ExtendWith(MockitoExtension.class)
class CertificatePdfGeneratorTest {

  private static final LocalDateTime SIGNED_DATE = LocalDateTime.now();
  private static final String ADDITIONAL_INFO_TEXT = "additionalInfoText";

  @InjectMocks
  CertificatePdfGenerator certificatePdfGenerator;

  @Test
  void shouldSetCorrectFileName() {
    final var expected = "intyg_om_graviditet_" + LocalDateTime.now()
        .format((DateTimeFormatter.ofPattern("yy-MM-dd_HHmm")));
    final var pdfByteArray = certificatePdfGenerator.generate(
        buildCertificate(), ADDITIONAL_INFO_TEXT);

    assertEquals(expected, pdfByteArray.fileName());
  }

  private Certificate buildCertificate() {
    return fk7211CertificateBuilder()
        .certificateMetaData(CertificateMetaData.builder()
            .issuer(AJLA_DOKTOR)
            .patient(ATHENA_REACT_ANDERSSON)
            .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
            .careUnit(ALFA_MEDICINCENTRUM)
            .careProvider(ALFA_REGIONEN)
            .build())
        .status(Status.DRAFT)
        .elementData(List.of(DATE))
        .certificateModel(FK7211_CERTIFICATE_MODEL)
        .signed(SIGNED_DATE)
        .build();
  }
}