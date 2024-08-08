package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class PdfValueGeneratorUtilTest {

  @Test
  void shouldSplitTextUsingLongInformationTextIfLimitIsOver25() {
    final var value = "This is the value if was planning on testing for splitting text.";
    final var expected = List.of("This is ... Se fortsättningsblad!",
        "... the value if was planning on testing for splitting text.\n");

    assertEquals(expected, PdfValueGeneratorUtil.splitByLimit(35, value));
  }

  @Test
  void shouldSplitTextUsingShortInformationTextIfLimitIsUnder25() {
    final var value = "This is the value if was planning on testing for splitting text.";
    final var expected = List.of("This is ...",
        "... the value if was planning on testing for splitting text.\n");

    assertEquals(expected, PdfValueGeneratorUtil.splitByLimit(10, value));
  }

}