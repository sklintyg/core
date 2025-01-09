package se.inera.intyg.certificateprintservice.playwright.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.microsoft.playwright.Page;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Category;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Question;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValueText;
import se.inera.intyg.certificateprintservice.playwright.pdf.TemplateToDocumentConverter;

@ExtendWith(MockitoExtension.class)
class CertificateToHtmlConverterTest {

  private static final Metadata.MetadataBuilder METADATA_BUILDER = Metadata.builder()
      .issuingUnit("unit")
      .issuerName("issuerName")
      .issuingUnitInfo(List.of("adress"))
      .name("Certificate name")
      .description("Certificate description");
  private static final Metadata METADATA = METADATA_BUILDER.build();
  private static final List<Category> CATEGORIES = List.of(Category.builder()
      .id("ID")
      .name("Name Category")
      .questions(List.of(
          Question.builder()
              .id("ID")
              .name("Name Question")
              .value(ElementValueText.builder()
                  .text("Example text for value")
                  .build()
              )
              .subQuestions(Collections.emptyList())
              .build()
      ))
      .build());

  @Mock
  TemplateToDocumentConverter templateToDocumentConverter;
  @Mock
  InputStream template;
  @Mock
  Page headerPage;
  @InjectMocks
  CertificateToHtmlConverter certificateToHtmlConverter;

  Document doc;


  @BeforeEach
  void init() {
    doc = (Document) new Document("example").appendChildren(List.of(
        new Element(Tag.DIV.toString()).attr("id", "content"), new Element(Tag.SCRIPT.toString())));
  }

  @Nested
  class CertificateDetails {


    @Test
    void shallSetIssuerIfSigned() throws IOException {
      var result = getSignedHtml();
      assertTrue(result.contains("IntygsUtfärdare"));
    }


    @Test
    void shallSetSignDateIfSigned() throws IOException {
      var result = getSignedHtml();
      assertTrue(result.contains("Intyget signerades:"));
    }


    @Test
    void shallNotSetIssuerIfDraft() throws IOException {
      var result = getUnsignedHtml();

      assertFalse(result.contains("IntygsUtfärdare"));
    }


    @Test
    void shallNotSetSignDateIfDraft() throws IOException {
      var result = getUnsignedHtml();

      assertFalse(result.contains("Intyget signerades:"));
    }

    @Test
    void shallSetContactInfo() throws IOException {
      var result = getUnsignedHtml();

      assertTrue(result.contains("Kontaktuppgifter:"));
    }


    @Test
    void shallSetCategories() throws IOException {
      var result = getUnsignedHtml();

      assertTrue(result.contains("Name Category"));
    }

    @Test
    void shallSetScript() throws IOException {
      when(templateToDocumentConverter.getScriptSource()).thenReturn("this is the script");
      var result = getUnsignedHtml();

      assertTrue(result.contains("this is the script"));
    }
  }

  @Nested
  class CertificateInformation {


    @Test
    void shallSetCertName() throws IOException {

      var result = getUnsignedHtml();
      assertTrue(result.contains("Certificate name"));
    }

    @Test
    void shallSetCertDescription() throws IOException {
      var result = getUnsignedHtml();
      assertTrue(result.contains("Certificate description"));
    }

    @Test
    void shallSetCitizenInfo() throws IOException {
      var result = getUnsignedHtml();

      assertAll(() -> assertTrue(result.contains("Skicka intyg till mottagare")),
          () -> assertTrue(result.contains(
              "Du kan hantera ditt intyg genom att logga in på 1177.se Där kan du till exempel skicka intyget till mottagaren")));
    }
  }

  private String getSignedHtml() throws IOException {
    var metadata = METADATA_BUILDER.signingDate("2021-12-12").build();

    var pi = new PrintInformation();
    pi.setCertificate(Certificate.builder()
        .metadata(metadata)
        .categories(CATEGORIES)
        .build());
    pi.setTemplate(template);
    pi.setHeaderPage(headerPage);

    when(templateToDocumentConverter.convert(pi)).thenReturn(doc);
    return certificateToHtmlConverter.certificate(pi);
  }

  private String getUnsignedHtml() throws IOException {
    var pi = new PrintInformation();
    pi.setCertificate(Certificate.builder()
        .metadata(METADATA)
        .categories(CATEGORIES)
        .build());
    pi.setTemplate(template);
    pi.setHeaderPage(headerPage);

    when(templateToDocumentConverter.convert(pi)).thenReturn(doc);
    return certificateToHtmlConverter.certificate(pi);
  }
}

