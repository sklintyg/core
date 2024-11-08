package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.springframework.stereotype.Component;

@Component
public class TextUtil {

  public float calculateTextHeight(String text, float fontSize, PDFont font,
      float width) throws IOException {

    String[] lines = text.split("\n");
    int totalLines = 0;

    for (String line : lines) {
      totalLines += wrapLine(line, width, fontSize, font).size();
    }
    
    float lineHeight = fontSize * 1.5F;
    return totalLines * lineHeight;
  }

  public List<String> wrapLine(String line, float width, float fontSize, PDFont font)
      throws IOException {
    List<String> wrappedLines = new ArrayList<>();

    final float lineWidth = fontSize * font.getStringWidth(line) / 1000;

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
      float substringWidth = fontSize * font.getStringWidth(substring) / 1000;

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
}
