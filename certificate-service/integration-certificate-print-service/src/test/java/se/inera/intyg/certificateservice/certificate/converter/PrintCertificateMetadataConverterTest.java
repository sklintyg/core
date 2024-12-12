package se.inera.intyg.certificateservice.certificate.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

class PrintCertificateMetadataConverterTest {

  private final PrintCertificateMetadataConverter printCertificateMetadataConverter = new PrintCertificateMetadataConverter();

  public static final Certificate CERTIFICATE = fk7210CertificateBuilder()
      .signed(LocalDateTime.now())
      .build();

  public static final String APPLICATION_ORIGIN_WEBCERT = "Webcert";
  public static final String APPLICATION_ORIGIN_1177_INTYG = "1177 intyg";

  @Test
  void shouldSetName() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false);
    assertEquals(CERTIFICATE.certificateModel().name(), result.getName());
  }

  @Test
  void shouldSetVersion() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false);
    assertEquals(CERTIFICATE.certificateModel().id().version().version(),
        result.getVersion());
  }

  @Test
  void shouldSetTypeId() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false);
    assertEquals(CERTIFICATE.certificateModel().type().code(), result.getTypeId());
  }

  @Test
  void shouldSetCertificateId() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false);
    assertEquals(CERTIFICATE.id().id(), result.getCertificateId());
  }

  @Test
  void shouldSetSigningDate() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false);
    assertEquals(CERTIFICATE.signed().toString(), result.getSigningDate());
  }

  //  @Test
//  void shouldSetRecipientLogo() {
//    final var result = printCertificateMetadataConverter.convert(CERTIFICATE);
//    assertEquals(CERTIFICATE.certificateModel().recipient().logo, result.getRecipientLogo());
//  }

  @Test
  void shouldSetRecipientName() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false);
    assertEquals(CERTIFICATE.certificateModel().recipient().name(),
        result.getRecipientName());
  }

  @Test
  void shouldSetApplicationOriginFor1177intyg() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, true);
    assertEquals(APPLICATION_ORIGIN_1177_INTYG, result.getApplicationOrigin());
  }

  @Test
  void shouldSetApplicationOriginForWebcert() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false);
    assertEquals(APPLICATION_ORIGIN_WEBCERT, result.getApplicationOrigin());
  }

  @Test
  void shouldSetSPersonId() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false);
    assertEquals(CERTIFICATE.certificateMetaData().patient().id().idWithDash(),
        result.getPersonId());
  }

  @Test
  void shouldSetDescription() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false);
    assertEquals(CERTIFICATE.certificateModel().detailedDescription(),
        result.getDescription());
  }

}
