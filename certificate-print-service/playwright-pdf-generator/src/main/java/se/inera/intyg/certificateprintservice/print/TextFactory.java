package se.inera.intyg.certificateprintservice.print;

import se.inera.intyg.certificateprintservice.print.api.Metadata;

public class TextFactory {

  public static String information(Metadata metadata) {
    if (metadata.isDraft()) {
      "Detta är en utskrift av ett elektroniskt intygsutkast och ska INTE skickas till %s.".formatted(
          metadata.getRecipientName());
    }

    if (metadata.isSent()) {
      return
          "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren. "
              + "Notera att intyget redan har skickats till %s.".formatted(
              metadata.getRecipientName());
    }

    return "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren.";
  }

  public static String margin(Metadata metadata) {
    return "%s - Fastställd av %s".formatted(
        metadata.getTypeId(),
        metadata.getRecipientName()
    );
  }

  public static String applicationOrigin(Metadata metadata) {
    return "Utskriften skapades med %s - en tjänst som drivs av Inera AB".formatted(
        metadata.getApplicationOrigin()
    );
  }

  public static String citizenInformation() {
    return "Du kan hantera ditt intyg genom att logga in på 1177.se Där kan du till exempel skicka intyget till mottagaren";
  }
}
