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
}
