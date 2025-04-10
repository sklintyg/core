package se.inera.intyg.certificateprintservice.playwright.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;

class LeftMarginInfoConverterTest {

  private final LeftMarginInfoConverter leftMarginInfoConverter = new LeftMarginInfoConverter();

  private static final String TYPE_ID = "typeId";
  private static final String RECIPIENT_NAME = "recipientName";
  private static final Metadata METADATA = Metadata.builder()
      .typeId(TYPE_ID)
      .recipientName(RECIPIENT_NAME)
      .build();

  @Test
  void shouldSetType() {
    final var response = leftMarginInfoConverter.convert(METADATA);
    assertEquals(TYPE_ID, response.getCertificateType());
  }

  @Test
  void shouldSetRecipientName() {
    final var response = leftMarginInfoConverter.convert(METADATA);
    assertEquals(RECIPIENT_NAME, response.getRecipientName());
  }

}
