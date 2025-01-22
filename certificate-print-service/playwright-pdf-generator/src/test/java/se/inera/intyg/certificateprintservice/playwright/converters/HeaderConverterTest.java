package se.inera.intyg.certificateprintservice.playwright.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;

@ExtendWith(MockitoExtension.class)
class HeaderConverterTest {

  @Mock
  private LeftMarginInfoConverter leftMarginInfoConverter;
  @Mock
  private RightMarginInfoConverter rightMarginInfoConverter;

  @InjectMocks
  private HeaderConverter headerConverter;

  private static final String CERTIFICATE_NAME = "certificateName";
  private static final String TYPE_ID = "typeId";
  private static final String VERSION = "version";
  private static final String PERSON_ID = "personId";
  private static final String RECIPIENT_NAME = "recipientName";
  private static final String SIGNING_DATE = "2025-01-18";
  private static final String SENT_DATE = "2025-01-18";
  private static final byte[] RECIPIENT_LOGO = "recipientLogo".getBytes();
  private static final Metadata METADATA = Metadata.builder()
      .name(CERTIFICATE_NAME)
      .version(VERSION)
      .typeId(TYPE_ID)
      .personId(PERSON_ID)
      .recipientLogo(RECIPIENT_LOGO)
      .recipientName(RECIPIENT_NAME)
      .signingDate(SIGNING_DATE)
      .sentDate(SENT_DATE)
      .build();

  @Test
  void shouldSetName() {
    final var response = headerConverter.convert(METADATA);
    assertEquals(CERTIFICATE_NAME, response.getCertificateName());
  }

  @Test
  void shouldSetVersion() {
    final var response = headerConverter.convert(METADATA);
    assertEquals(VERSION, response.getCertificateVersion());
  }

  @Test
  void shouldSetTypeId() {
    final var response = headerConverter.convert(METADATA);
    assertEquals(TYPE_ID, response.getCertificateType());
  }

  @Test
  void shouldSetPersonId() {
    final var response = headerConverter.convert(METADATA);
    assertEquals(PERSON_ID, response.getPersonId());
  }

  @Test
  void shouldSetRecipientLogo() {
    final var response = headerConverter.convert(METADATA);
    assertEquals(RECIPIENT_LOGO, response.getRecipientLogo());
  }

  @Test
  void shouldSetRecipientName() {
    final var response = headerConverter.convert(METADATA);
    assertEquals(RECIPIENT_NAME, response.getRecipientName());
  }

  @Test
  void shouldSetIsDraft() {
    final var response = headerConverter.convert(METADATA);
    assertFalse(response.isDraft());
  }

  @Test
  void shouldSetIsSent() {
    final var response = headerConverter.convert(METADATA);
    assertTrue(response.isSent());
  }

}
