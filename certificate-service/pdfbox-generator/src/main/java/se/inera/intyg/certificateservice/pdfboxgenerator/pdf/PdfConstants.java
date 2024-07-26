package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

public class PdfConstants {

  private PdfConstants() {
    throw new IllegalStateException("Utility class");
  }

  // TEXT
  public static final String WATERMARK_DRAFT = "UTKAST";
  public static final String DIGITALLY_SIGNED_TEXT =
      "Detta är en utskrift av ett elektroniskt intyg. "
          + "Intyget har signerats elektroniskt av intygsutfärdaren.";

  // VALUES
  public static final String CHECKED_BOX_VALUE = "1";
  public static final String UNCHECKED_BOX_VALUE = "Off";
}
