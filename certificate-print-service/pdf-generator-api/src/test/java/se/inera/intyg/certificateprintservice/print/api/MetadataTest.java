package se.inera.intyg.certificateprintservice.print.api;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class MetadataTest {

  @Test
  void shouldReturnIsNotDraftIfSigned() {
    assertFalse(
        Metadata.builder()
            .signingDate("2025-01-01")
            .build().isDraft()
    );
  }
}