package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import java.util.List;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.TextSplitRenderSpec;

public class PdfValueGeneratorUtil {

  private static final String OVERFLOW_MESSAGE = "... Se fortsättningsblad!";
  private static final String SMALL_OVERFLOW_MESSAGE = "...";

  private PdfValueGeneratorUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static List<String> splitByLimit(TextSplitRenderSpec textSplitRenderSpec) {
    final var informationText = textSplitRenderSpec.getInformationMessage() != null
        ? textSplitRenderSpec.getInformationMessage()
        : getInformationText(textSplitRenderSpec.getLimit());

    final var updatedLimit = textSplitRenderSpec.getLimit() - informationText.length();
    final var noLineBreak =
        textSplitRenderSpec.isShouldRemoveLineBreaks() ? textSplitRenderSpec.getFieldText()
            .replace("\n", "")
            : textSplitRenderSpec.getFieldText();
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
