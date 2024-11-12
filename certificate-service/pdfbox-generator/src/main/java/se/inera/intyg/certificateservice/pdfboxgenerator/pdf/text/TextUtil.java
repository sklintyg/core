package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class TextUtil {

  public float calculateTextHeight(String text, float fontSize, PDFont font,
      float width) {

    String[] lines = text.split("\n");
    int totalLines = 0;

    for (String line : lines) {
      totalLines += wrapLine(line, width, fontSize, font).size();
    }

    float lineHeight = fontSize * 1.5F;
    return totalLines * lineHeight;
  }


  public List<String> wrapLine(String line, float width, float fontSize, PDFont font) {
    List<String> wrappedLines = new ArrayList<>();

    final float lineWidth = fontSize * getWidthOfString(line, font) / 1000;

    // If the line fits, no need to wrap it
    if (lineWidth <= width) {
      wrappedLines.add(line);
      return wrappedLines;
    }

    int lastSpaceIndex = -1;
    while (!line.isEmpty()) {
      int spaceIndex = line.indexOf(' ', lastSpaceIndex + 1);

      if (spaceIndex == -1) {
        // If no space found, take the entire remaining text
        spaceIndex = line.length();
      }

      String substring = line.substring(0, spaceIndex);
      float substringWidth = fontSize * getWidthOfString(substring, font) / 1000;

      // If the substring is too long, add the previous part to the line
      if (substringWidth > width) {
        // If the substring is one long word without spaces it will not wrap correctly
        if (lastSpaceIndex != -1) {
          substring = line.substring(0, lastSpaceIndex);
        }

        wrappedLines.add(substring);
        line = line.substring(lastSpaceIndex == -1 ? spaceIndex : lastSpaceIndex).trim();
        lastSpaceIndex = -1; // Reset to process the next part
      } else if (spaceIndex == line.length()) {
        // If we've reached the end of the line, add the last portion and break
        wrappedLines.add(line);
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

  public boolean isTextOverflowing(List<PdfField> currentFields, PdfField newTextField,
      PDRectangle rectangle, float fontSize, PDFont font) {
    final var currentText = currentFields.stream()
        .map(PdfField::getValue)
        .collect(Collectors.joining("\n"));
    var currentTextHeight = calculateTextHeight(currentText + (currentText.isEmpty() ? "" : "\n"),
        fontSize,
        font, rectangle.getWidth());

    String[] lines = newTextField.getValue().split("\n");
    var wrappedLines = new ArrayList<String>();
    float lineHeight = fontSize * 1.5F;

    var availableLineSpaces = (int) Math.floor(
        rectangle.getHeight() - currentTextHeight / lineHeight);

    for (String line : lines) {
      wrappedLines.addAll(wrapLine(line, rectangle.getWidth(), fontSize, font));
    }

    if (wrappedLines.size() > availableLineSpaces) {
      wrappedLines.subList(0, availableLineSpaces);
    }

    return 3 > rectangle.getHeight();
  }

  public OverFlowLineSplit getOverflowingLines(List<PdfField> currentFields, PdfField newTextField,
      PDRectangle rectangle, float fontSize, PDFont font) {
    final var currentText = currentFields.stream()
        .map(PdfField::getValue)
        .collect(Collectors.joining("\n"));
    var currentTextHeight = calculateTextHeight(currentText + (currentText.isEmpty() ? "" : "\n"),
        fontSize,
        font, rectangle.getWidth());

    String[] lines = newTextField.getValue().split("\n");
    var wrappedLines = new ArrayList<String>();
    float lineHeight = fontSize * 1.5F;

    var availableLineSpaces = (int) Math.max(Math.floor(
        (rectangle.getHeight() - currentTextHeight) / lineHeight), 0);

    for (String line : lines) {
      wrappedLines.addAll(wrapLine(line, rectangle.getWidth(), fontSize, font));
    }

    var overFlowInfo = new OverFlowLineSplit();
    if (wrappedLines.size() > availableLineSpaces) {
      overFlowInfo.start = wrappedLines.subList(0, availableLineSpaces).stream()
          .collect(Collectors.joining(" "));
      overFlowInfo.end = wrappedLines.subList(availableLineSpaces, wrappedLines.size()).stream()
          .collect(
              Collectors.joining(" ")) + "\n";
    }

    return overFlowInfo;
  }

  @Getter
  public class OverFlowLineSplit {

    String start;
    String end;
  }
}
