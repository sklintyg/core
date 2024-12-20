package se.inera.intyg.certificateprintservice.playwright.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.microsoft.playwright.Page;
import java.io.IOException;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.pdf.TemplateToDocumentConverter;

@ExtendWith(MockitoExtension.class)
class CertificateToHtmlConverterTest {


  @Mock
  TemplateToDocumentConverter templateToDocumentConverter;
  @Mock
  Resource template;
  @Mock
  Page page;

  @InjectMocks
  CertificateInformationToHtmlConverter certificateInformationToHtmlConverter;

  @Test
  void shallFillContent() throws IOException {
    var metadata = Metadata.builder()
        .name("Intygets namn")
        .description("Beskrivning av detta intyg")
        .build();
    final var doc = new Document("example");
    doc.appendChild(new Element(Tag.DIV.toString()).attr("id", "content"));

    when(templateToDocumentConverter.convert(template, "", page, metadata)).thenReturn(doc);

    var result = certificateInformationToHtmlConverter.convert(template, page, "", metadata);

    assertEquals("""
        <div id="content">
         <strong>Intygets namn</strong>
         <p>Beskrivning av detta intyg</p><strong>Skicka intyg till mottagare</strong>
         <p>Du kan hantera ditt intyg genom att logga in på 1177.se Där kan du till exempel skicka intyget till mottagaren</p>
        </div>""", result);
  }

}