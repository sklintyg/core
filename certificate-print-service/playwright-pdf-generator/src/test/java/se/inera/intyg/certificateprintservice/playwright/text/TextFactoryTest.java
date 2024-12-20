package se.inera.intyg.certificateprintservice.playwright.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;

class TextFactoryTest {

  @Test
  void shouldReturnDraftText() {
    final var metadata =
        Metadata.builder()
            .recipientName("Transportstyrelsen")
            .build();

    assertEquals(
        "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren.",
        TextFactory.alert(metadata)
    );
  }

  @Test
  void shouldReturnDraft() {

    assertEquals(
        "UTKAST",
        TextFactory.draft()
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
        TextFactory.alert(metadata)
    );
  }

  @Test
  void shouldReturnCertificateId() {
    final var metadata =
        Metadata.builder()
            .certificateId("1234ABC")
            .recipientName("Transportstyrelsen")
            .signingDate("2025-01-01")
            .build();

    assertEquals(
        "Intygs-ID: 1234ABC",
        TextFactory.certificateId(metadata.getCertificateId())
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
        TextFactory.alert(metadata)
    );
  }

  @Test
  void shouldReturnMargin() {
    final var metadata =
        Metadata.builder()
            .recipientName("Transportstyrelsen")
            .typeId("TS8071")
            .build();

    assertEquals(
        "TS8071 - Fastställd av Transportstyrelsen",
        TextFactory.margin(metadata)
    );
  }

  @Test
  void shouldReturnApplicationOrigin() {
    final var metadata =
        Metadata.builder()
            .recipientName("Transportstyrelsen")
            .typeId("TS8071")
            .applicationOrigin("Webcert")
            .build();

    assertEquals(
        "Utskriften skapades med Webcert - en tjänst som drivs av Inera AB",
        TextFactory.applicationOrigin(metadata)
    );
  }

  @Test
  void shouldReturnCitizenInformation() {
    assertEquals(
        "Du kan hantera ditt intyg genom att logga in på 1177.se Där kan du till exempel skicka intyget till mottagaren",
        TextFactory.citizenInformation()
    );
  }

  @Test
  void shouldReturnTitle() {
    final var metadata = Metadata.builder()
        .name("Name")
        .typeId("TypeId")
        .version("1.0")
        .build();

    assertEquals(
        "Name (TypeId v1.0)",
        TextFactory.title(metadata)
    );
  }
}