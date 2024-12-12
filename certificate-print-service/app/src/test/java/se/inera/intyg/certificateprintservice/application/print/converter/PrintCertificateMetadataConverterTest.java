package se.inera.intyg.certificateprintservice.application.print.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateMetadataDTO;

class PrintCertificateMetadataConverterTest {

  private static final String NAME = "name";
  private static final String VERSION = "version";
  private static final String TYPE_ID = "typeId";
  private static final String CERTIFICATE_ID = "certificateId";
  private static final String SIGNING_DATE = "signingDate";
  private static final byte[] RECIPIENT_LOGO = "logo".getBytes(StandardCharsets.UTF_8);
  private static final String RECIPIENT_NAME = "recipientName";
  private static final String APPLICATION_ORIGIN = "applicationOrigin";
  private static final String PERSON_ID = "personId";
  private static final String DESCRIPTION = "description";
  private static PrintCertificateMetadataDTO METADATA_DTO = PrintCertificateMetadataDTO.builder()
      .name(NAME)
      .version(VERSION)
      .typeId(TYPE_ID)
      .certificateId(CERTIFICATE_ID)
      .signingDate(SIGNING_DATE)
      .recipientLogo(RECIPIENT_LOGO)
      .recipientName(RECIPIENT_NAME)
      .applicationOrigin(APPLICATION_ORIGIN)
      .personId(PERSON_ID)
      .description(DESCRIPTION)
      .build();

  PrintCertificateMetadataConverter printCertificateMetadataConverter;

  @BeforeEach
  void setUp() {
    printCertificateMetadataConverter = new PrintCertificateMetadataConverter();
  }

  @Test
  void shallConvertName() {
    assertEquals(NAME, printCertificateMetadataConverter.convert(METADATA_DTO).getName());
  }

  @Test
  void shallConvertVersion() {
    assertEquals(VERSION, printCertificateMetadataConverter.convert(METADATA_DTO).getVersion());
  }

  @Test
  void shallConvertTypeId() {
    assertEquals(TYPE_ID, printCertificateMetadataConverter.convert(METADATA_DTO).getTypeId());
  }

  @Test
  void shallConvertCertificateId() {
    assertEquals(CERTIFICATE_ID,
        printCertificateMetadataConverter.convert(METADATA_DTO).getCertificateId());
  }

  @Test
  void shallConvertSigningDate() {
    assertEquals(SIGNING_DATE,
        printCertificateMetadataConverter.convert(METADATA_DTO).getSigningDate());
  }

  @Test
  void shallConvertRecipientLogo() {
    assertEquals(RECIPIENT_LOGO,
        printCertificateMetadataConverter.convert(METADATA_DTO).getRecipientLogo());
  }

  @Test
  void shallConvertRecipientName() {
    assertEquals(RECIPIENT_NAME,
        printCertificateMetadataConverter.convert(METADATA_DTO).getRecipientName());
  }

  @Test
  void shallConvertApplicationOrigin() {
    assertEquals(APPLICATION_ORIGIN,
        printCertificateMetadataConverter.convert(METADATA_DTO).getApplicationOrigin());
  }

  @Test
  void shallConvertPersonId() {
    assertEquals(PERSON_ID, printCertificateMetadataConverter.convert(METADATA_DTO).getPersonId());
  }

  @Test
  void shallConvertDescription() {
    assertEquals(DESCRIPTION,
        printCertificateMetadataConverter.convert(METADATA_DTO).getDescription());
  }
}