package se.inera.intyg.certificateservice.certificate.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.fk7210certificateModelBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;

@ExtendWith(MockitoExtension.class)
class PrintCertificateMetadataConverterTest {

  private static final String FILE_NAME = "fileName";
  public static final List<String> CONTACT_INFO = List.of("TEST 123", "070 070");

  @Mock
  private PrintCertificateUnitInformationConverter printCertificateUnitInformationConverter;

  @InjectMocks
  private PrintCertificateMetadataConverter printCertificateMetadataConverter;

  public static final Certificate CERTIFICATE = fk7210CertificateBuilder()
      .certificateModel(fk7210certificateModelBuilder()
          .recipient(new Recipient(new RecipientId("ts"), "ts", "ts", "transportstyrelsen-logo.png",
              "Läkarintyg Transportstyrelsen")
          ).build()
      )
      .status(Status.SIGNED)
      .signed(LocalDateTime.now())
      .build();

  public static final Certificate DRAFT = fk7210CertificateBuilder()
      .certificateModel(fk7210certificateModelBuilder()
          .recipient(new Recipient(new RecipientId("ts"), "ts", "ts", "transportstyrelsen-logo.png",
              "Läkarintyg Transportstyrelsen")
          ).build()
      )
      .status(Status.DRAFT)
      .build();

  public static final String APPLICATION_ORIGIN_WEBCERT = "Webcert";
  public static final String APPLICATION_ORIGIN_1177_INTYG = "1177 intyg";

  @BeforeEach
  void setUp() {
    when(printCertificateUnitInformationConverter.convert(any()))
        .thenReturn(CONTACT_INFO);
  }

  @Test
  void shouldSetName() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertEquals(CERTIFICATE.certificateModel().name(), result.getName());
  }


  @Test
  void shouldSetContactInfo() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertEquals(CONTACT_INFO, result.getUnitInformation());
  }

  @Test
  void shouldSetVersion() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertEquals(CERTIFICATE.certificateModel().id().version().version(),
        result.getVersion());
  }

  @Test
  void shouldSetTypeId() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertEquals(CERTIFICATE.certificateModel().type().code(), result.getTypeId());
  }

  @Test
  void shouldSetCertificateId() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertEquals(CERTIFICATE.id().id(), result.getCertificateId());
  }

  @Test
  void shouldSetSigningDate() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertEquals(CERTIFICATE.signed().format(DateTimeFormatter.ISO_DATE), result.getSigningDate());
  }

  @Test
  void shouldSetSigningDateToNullIfNotSigned() {
    final var result = printCertificateMetadataConverter.convert(DRAFT, false, FILE_NAME);
    assertNull(result.getSigningDate());
  }

  @Test
  void shouldSetRecipientLogo() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertNotNull(result.getRecipientLogo());
  }

  @Test
  void shouldSetRecipientName() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertEquals(CERTIFICATE.certificateModel().recipient().name(),
        result.getRecipientName());
  }

  @Test
  void shouldSetApplicationOriginFor1177intyg() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, true, FILE_NAME);
    assertEquals(APPLICATION_ORIGIN_1177_INTYG, result.getApplicationOrigin());
  }

  @Test
  void shouldSetApplicationOriginForWebcert() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertEquals(APPLICATION_ORIGIN_WEBCERT, result.getApplicationOrigin());
  }

  @Test
  void shouldSetSPersonId() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertEquals(CERTIFICATE.certificateMetaData().patient().id().idWithDash(),
        result.getPersonId());
  }

  @Test
  void shouldSetDescription() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertEquals(CERTIFICATE.certificateModel().detailedDescription(),
        result.getDescription());
  }

  @Test
  void shouldSetFileName() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertEquals(FILE_NAME, result.getFileName());
  }

  @Test
  void shouldSetIssuerName() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertEquals(CERTIFICATE.certificateMetaData().issuer().name().fullName(),
        result.getIssuerName());
  }

  @Test
  void shouldSetIssuingUnit() {
    final var result = printCertificateMetadataConverter.convert(CERTIFICATE, false, FILE_NAME);
    assertEquals(CERTIFICATE.certificateMetaData().issuingUnit().name().name(),
        result.getIssuingUnit());
  }
}
