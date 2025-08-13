package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.List;

public class PdfValueGeneratorUtil {

  private static final String OVERFLOW_MESSAGE = "... Se fortsättningsblad!";
  private static final String SMALL_OVERFLOW_MESSAGE = "...";

  private PdfValueGeneratorUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static List<String> splitByLimit(Integer limit, String s) {
    return splitByLimit(limit, s, null, true);
  }

  public static List<String> splitByLimit(Integer limit, String s, String message) {
    return splitByLimit(limit, s, message, true);
  }

  public static List<String> splitByLimit(Integer limit, String s, String message,
      boolean shouldRemoveLineBreaks) {
    final var informationText = message != null
        ? message
        : getInformationText(limit);

    final var updatedLimit = limit - informationText.length();
    final var noLineBreak = shouldRemoveLineBreaks ? s.replace("\n", "") : s;
    final var words = noLineBreak.split(" ");

    final var firstPart = new StringBuilder();
    final var secondPart = new StringBuilder();
    boolean limitReached = false;

    for (String word : words) {
      if (!limitReached && firstPart.length() + word.length() + 1 <= updatedLimit) {
        if (!firstPart.isEmpty()) {
          firstPart.append(" ");
        }
        firstPart.append(word);
      } else {
        if (!secondPart.isEmpty()) {
          secondPart.append(" ");
        }
        secondPart.append(word);
        limitReached = true;
      }
    }
    return List.of(
        firstPart + " " + informationText,
        SMALL_OVERFLOW_MESSAGE + " " + secondPart
    );
  }

  private static String getInformationText(Integer limit) {
    return limit <= OVERFLOW_MESSAGE.length()
        ? SMALL_OVERFLOW_MESSAGE
        : OVERFLOW_MESSAGE;
  }
}
