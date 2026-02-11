package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.TEXT_FIELD_LINE_HEIGHT;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.Y_MARGIN_APPENDIX_PAGE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
@Slf4j
public class TextUtil {

  private static final Map<String, String> PROBLEM_CHARACTERS_MAPPING = Map.of(
      "\u2010", "-", // hyphen
      "\u2011", "-", // non-breaking hyphen
      "\u2012", "-", // figure dash
      "\u2013", "-", // en dash
      "\u2014", "-", // em dash
      "\u2015", "-",  // horizontal bar
      "\u2212", "-",  // minus
      "\u2192", "->",  // right arrow
      "\u2190", "<-",   // left arrow
      "\u2194", "<->"   // left right arrow
  );

  public float calculateTextHeight(String text, float fontSize, PDFont font,
      float width) {

    String[] lines = text.split("\n");
    int totalLines = 0;

    for (String line : lines) {
      totalLines += wrapLine(line, width, fontSize, font).size();
    }

    float lineHeight = fontSize * TEXT_FIELD_LINE_HEIGHT;
    return totalLines * lineHeight;
  }


  public List<String> wrapLine(String line, float width, float fontSize, PDFont font) {
    List<String> wrappedLines = new ArrayList<>();

    var sanitizedLine = sanitizeText(line, font);
    final float lineWidth = fontSize * getWidthOfString(sanitizedLine, font) / 1000;

    // If the line fits, no need to wrap it
    if (lineWidth <= width) {
      wrappedLines.add(sanitizedLine);
      return wrappedLines;
    }

    int lastSpaceIndex = -1;
    while (!sanitizedLine.isEmpty()) {
      int spaceIndex = sanitizedLine.indexOf(' ', lastSpaceIndex + 1);

      if (spaceIndex == -1) {
        // If no space found, take the entire remaining text
        spaceIndex = sanitizedLine.length();
      }

      String substring = sanitizedLine.substring(0, spaceIndex);
      float substringWidth = fontSize * getWidthOfString(substring, font) / 1000;

      // If the substring is too long, add the previous part to the line
      if (substringWidth > width) {
        // If the substring is one long word without spaces it will not wrap correctly
        if (lastSpaceIndex != -1) {
          substring = sanitizedLine.substring(0, lastSpaceIndex);
        }

        wrappedLines.add(substring);
        sanitizedLine = sanitizedLine.substring(lastSpaceIndex == -1 ? spaceIndex : lastSpaceIndex)
            .trim();
        lastSpaceIndex = -1; // Reset to process the next part
      } else if (spaceIndex == sanitizedLine.length()) {
        // If we've reached the end of the line, add the last portion and break
        wrappedLines.add(sanitizedLine);
        break;
      } else {
        // Update lastSpaceIndex to continue wrapping
        lastSpaceIndex = spaceIndex;
      }
    }

    return wrappedLines;
  }

  private static float getWidthOfString(String line, PDFont font) {
    try {
      return font.getStringWidth(line);
    } catch (IOException exception) {
      throw new IllegalStateException("Could not process text", exception);
    }
  }

  public static String sanitizeText(String text, PDFont font) {
    if (text == null) {
      return "";
    }

    final var sanitizedText = text.replaceAll(
            "[\\t\\r\\v\\x00-\\x08\\x0B-\\x0C\\x0E-\\x1F\\x7F]+", " ")
        .trim();

    return normalizePrintableCharacters(sanitizedText, font);
  }

  public static String normalizePrintableCharacters(String sanitizedText, PDFont font) {
    final var normalizedChars = PROBLEM_CHARACTERS_MAPPING.entrySet().stream()
        .reduce(sanitizedText,
            (result, entry) -> result.replace(entry.getKey(), entry.getValue()),
            (s1, s2) -> s2);

    return filterUnsupportedFontCharacters(font, normalizedChars);
  }

  private static String filterUnsupportedFontCharacters(PDFont font, String text) {
    return text.codePoints()
        .mapToObj(cp -> getSupportedString(font, cp))
        .collect(Collectors.joining());
  }

  private static boolean isAllowedControl(int cp) {
    return cp == '\n' || cp == '\r' || cp == '\t';
  }

  private static String getSupportedString(PDFont font, int cp) {
    if (Character.isISOControl(cp)) {
      return isAllowedControl(cp) ? new String(Character.toChars(cp)) : "";
    }

    final var s = new String(Character.toChars(cp));
    try {
      font.encode(s);
      return s;
    } catch (IOException | IllegalArgumentException e) {
      log.warn(
          "Character '%s' with unicode 'U+%s' cannot be encoded in font '%s', replacing with space.".formatted(
              s, Integer.toHexString(cp).toUpperCase(),
              font.getName())
      );
      return " ";
    }
  }

  public Optional<OverFlowLineSplit> getOverflowingLines(List<PdfField> currentFields,
      PdfField newTextField,
      PDRectangle rectangle, float fontSize, PDFont font) {
    final var currentText = currentFields.stream()
        .map(PdfField::getValue)
        .collect(Collectors.joining("\n"));
    var currentTextHeight = calculateTextHeight(currentText + (currentText.isEmpty() ? "" : "\n"),
        fontSize,
        font, rectangle.getWidth());

    String[] lines = newTextField.getValue().split("\n");
    var wrappedLines = new ArrayList<String>();
    float lineHeight = fontSize * TEXT_FIELD_LINE_HEIGHT;

    var availableLineSpaces = (int) Math.max(Math.floor(
        (rectangle.getHeight() - Y_MARGIN_APPENDIX_PAGE - currentTextHeight) / lineHeight), 0);

    final var avaiableLinesEmptyPage = (int) Math.max(Math.floor(
        (rectangle.getHeight() - Y_MARGIN_APPENDIX_PAGE) / lineHeight), 0);

    if (avaiableLinesEmptyPage <= 0) {
      throw new IllegalStateException(
          "Not enough space to add any text to the page. Available lines on empty page: %s".formatted(
              avaiableLinesEmptyPage));
    }
    for (String line : lines) {
      wrappedLines.addAll(wrapLine(line, rectangle.getWidth(), fontSize, font));
    }

    if (wrappedLines.size() > availableLineSpaces) {
      var overFlowInfo = new OverFlowLineSplit(
          String.join("\n", wrappedLines.subList(0, availableLineSpaces)),
          getOverflowPages(availableLineSpaces, wrappedLines, avaiableLinesEmptyPage));
      return Optional.of(overFlowInfo);
    } else {
      return Optional.empty();
    }
  }

  private List<String> getOverflowPages(int availableLineSpaces,
      List<String> wrappedLines, int avaiableLinesEmptyPage) {
    return IntStream
        .iterate(availableLineSpaces, i -> i < wrappedLines.size(),
            i -> i + avaiableLinesEmptyPage)
        .mapToObj(start -> {
          final var end = Math.min(start + avaiableLinesEmptyPage, wrappedLines.size());
          return String.join("\n", wrappedLines.subList(start, end)) + "\n";
        })
        .toList();
  }
}