package se.inera.intyg.certificateprintservice.playwright.text;

public class TextFactory {

  private TextFactory() {
    throw new IllegalStateException("Utility class");
  }

//  public static String alert(String recipientName, boolean isDraft, boolean isSent) {
//    if (isDraft) {
//      return "Detta är en utskrift av ett elektroniskt intygsutkast och ska INTE skickas till %s.".formatted(
//          recipientName);
//    }
//
//    if (isSent) {
//      return
//          "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren. "
//              + "Notera att intyget redan har skickats till %s.".formatted(
//              recipientName);
//    }
//
//    return "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren.";
//  }

//  public static String margin(Metadata metadata) {
//    return "%s - Fastställd av %s".formatted(
//        metadata.getTypeId(),
//        metadata.getRecipientName()
//    );
//  }

//  public static String applicationOrigin(String origin) {
//    return "Utskriften skapades med %s - en tjänst som drivs av Inera AB".formatted(origin);
//  }

//  public static String citizenInformation() {
//    return "Du kan hantera ditt intyg genom att logga in på 1177.se Där kan du till exempel skicka intyget till mottagaren";
//  }

//  public static String title(String name, String type, String version) {
//    return "%s (%s v%s)".formatted(name, type, version);
//  }

//  public static String draft() {
//    return "UTKAST";
//  }

//  public static String certificateId(String certificateId) {
//    return "Intygs-ID: %s".formatted(certificateId);
//  }
}
