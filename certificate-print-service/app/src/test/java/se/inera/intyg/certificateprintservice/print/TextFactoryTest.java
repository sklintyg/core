package se.inera.intyg.certificateprintservice.print;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.print.api.Metadata;

class TextFactoryTest {

  @Test
  void shouldReturnDraftText() {
    final var metadata =
        Metadata.builder()
            .recipientName("Transportstyrelsen")
            .build();

    assertEquals(
        "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren.",
        TextFactory.information(metadata)
    );
  }

  @Test
  void shouldReturnSignedText() {
    final var metadata =
        Metadata.builder()
            .recipientName("Transportstyrelsen")
            .signingDate("2025-01-01")
            .build();

    assertEquals(
        "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren.",
        TextFactory.information(metadata)
    );
  }

  @Test
  void shouldReturnSentText() {
    final var metadata =
        Metadata.builder()
            .recipientName("Transportstyrelsen")
            .signingDate("2025-01-01")
            .isSent(true)
            .build();

    assertEquals(
        "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren. Notera att intyget redan har skickats till Transportstyrelsen.",
        TextFactory.information(metadata)
    );
  }

}