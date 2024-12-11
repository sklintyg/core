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

  @Test
  void shouldSetName() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE);
    assertEquals(CERTIFICATE.certificateModel().name(), result.get().getName());
  }

  @Test
  void shouldSetVersion() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE);
    assertEquals(CERTIFICATE.certificateModel().id().version().version(),
        result.get().getVersion());
  }

  @Test
  void shouldSetTypeId() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE);
    assertEquals(CERTIFICATE.certificateModel().type().code(), result.get().getTypeId());
  }

  @Test
  void shouldSetCertificateId() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE);
    assertEquals(CERTIFICATE.id().id(), result.get().getCertificateId());
  }

  @Test
  void shouldSetSigningDate() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE);
    assertEquals(CERTIFICATE.signed().toString(), result.get().getSigningDate());
  }

  //  @Test
//  void shouldSetRecipientLogo() {
//    final var result = printCertificateMetadataConverter.convert(CERTIFICATE);
//    assertEquals(CERTIFICATE.certificateModel().recipient().logo, result.get().getRecipientLogo());
//  }

  @Test
  void shouldSetRecipientName() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE);
    assertEquals(CERTIFICATE.certificateModel().recipient().name(),
        result.get().getRecipientName());
  }

//  @Test
//  void shouldSetApplicationOrigin() {
//    final var result = printCertificateMetadataConverter.convert(CERTIFICATE);
//    assertEquals(CERTIFICATE.signed().toString(), result.get().getApplicationOrigin());
//  }

  @Test
  void shouldSetSPersonId() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE);
    assertEquals(CERTIFICATE.certificateMetaData().patient().id().idWithDash(),
        result.get().getPersonId());
  }

  @Test
  void shouldSetDescription() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE);
    assertEquals(CERTIFICATE.certificateModel().detailedDescription(),
        result.get().getDescription());
  }

}
