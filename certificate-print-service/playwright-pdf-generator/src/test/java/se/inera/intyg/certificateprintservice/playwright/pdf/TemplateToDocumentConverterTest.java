package se.inera.intyg.certificateprintservice.playwright.pdf;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateprintservice.playwright.certificate.PrintInformation;

@ExtendWith(MockitoExtension.class)
class TemplateToDocumentConverterTest {


  @InjectMocks
  TemplateToDocumentConverter templateToDocumentConverter;

  @Test
  void test() throws IOException {
    final var printInformation = new PrintInformation();
    final var result = templateToDocumentConverter.convert(printInformation);
    assertNotNull(result);
  }

}