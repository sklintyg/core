package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.TextSplitRenderSpec;

class PdfValueGeneratorUtilTest {

  @Test
  void shouldSplitTextUsingLongInformationTextIfLimitIsOver25() {
    final var value = "This is the value if was planning on testing for splitting text.";
    final var expected = List.of("This is ... Se fortsättningsblad!",
        "... the value if was planning on testing for splitting text.");

    assertEquals(expected, PdfValueGeneratorUtil.splitByLimit(
        TextSplitRenderSpec.builder()
            .limit(35)
            .fieldText(value)
            .build()
    ));
  }

  @Test
  void shouldSplitTextUsingShortInformationTextIfLimitIsUnder25() {
    final var value = "This is the value if was planning on testing for splitting text.";
    final var expected = List.of("This is ...",
        "... the value if was planning on testing for splitting text.");

    assertEquals(expected, PdfValueGeneratorUtil.splitByLimit(
        TextSplitRenderSpec.builder()
            .limit(10)
            .fieldText(value)
            .build()
    ));
  }

  @Test
  void shouldSplitTextUsingMessageIfProvided() {
    final var value = "This is the value if was planning on testing for splitting text.";
    final var message = "xxx";
    final var expected = List.of("This is xxx",
        "... the value if was planning on testing for splitting text.");

    assertEquals(expected, PdfValueGeneratorUtil.splitByLimit(
        TextSplitRenderSpec.builder()
            .limit(10)
            .fieldText(value)
            .informationMessage(message)
            .build()
    ));
  }
}
