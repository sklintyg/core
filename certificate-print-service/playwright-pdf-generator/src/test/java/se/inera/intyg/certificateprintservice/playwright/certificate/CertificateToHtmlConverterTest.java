package se.inera.intyg.certificateprintservice.playwright.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.microsoft.playwright.Page;
import java.io.IOException;
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
import org.springframework.core.io.Resource;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Category;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Question;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValueText;
import se.inera.intyg.certificateprintservice.playwright.pdf.TemplateToDocumentConverter;

@ExtendWith(MockitoExtension.class)
class CertificateToHtmlConverterTest {

  private static final String HEADER = "header";
  private static final Metadata.MetadataBuilder METADATA_BUILDER = Metadata.builder()
      .issuingUnit("unit")
      .issuerName("issuerName")
      .issuingUnitInfo(List.of("adress"))
      .name("Certificate name")
      .description("Certificate description");
  private static final Metadata METADATA = METADATA_BUILDER.build();
  private static final List<Category> categories = List.of(Category.builder()
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
  Resource template;
  @Mock
  Page page;
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
      var metadata = METADATA_BUILDER.signingDate("2021-12-12").build();
      when(templateToDocumentConverter.convert(template, HEADER, page, metadata)).thenReturn(doc);
      var result = certificateToHtmlConverter.certificate(template, Certificate.builder()
              .metadata(metadata)
              .categories(categories)
              .build(),
          page,
          HEADER);

      assertTrue(result.contains("IntygsUtfärdare"));
    }

    @Test
    void shallSetSignDateIfSigned() throws IOException {
      var metadata = METADATA_BUILDER.signingDate("2021-12-12").build();
      when(templateToDocumentConverter.convert(template, HEADER, page, metadata)).thenReturn(doc);
      var result = certificateToHtmlConverter.certificate(template, Certificate.builder()
              .metadata(metadata)
              .categories(categories)
              .build(),
          page,
          HEADER);

      assertTrue(result.contains("Intyget signerades:"));
    }

    @Test
    void shallNotSetIssuerIfDraft() throws IOException {
      when(templateToDocumentConverter.convert(template, HEADER, page, METADATA)).thenReturn(doc);
      var result = defaultCertDetails();

      assertFalse(result.contains("IntygsUtfärdare"));
    }


    @Test
    void shallNotSetSignDateIfDraft() throws IOException {
      when(templateToDocumentConverter.convert(template, HEADER, page, METADATA)).thenReturn(doc);
      var result = defaultCertDetails();

      assertFalse(result.contains("Intyget signerades:"));
    }

    @Test
    void shallSetContactInfo() throws IOException {
      when(templateToDocumentConverter.convert(template, HEADER, page, METADATA)).thenReturn(doc);
      var result = defaultCertDetails();

      assertTrue(result.contains("Kontaktuppgifter:"));
    }


    @Test
    void shallSetCategories() throws IOException {
      when(templateToDocumentConverter.convert(template, HEADER, page, METADATA)).thenReturn(doc);
      var result = defaultCertDetails();

      assertTrue(result.contains("Name Category"));
    }

    @Test
    void shallSetScript() throws IOException {
      when(templateToDocumentConverter.convert(template, HEADER, page, METADATA)).thenReturn(doc);
      when(templateToDocumentConverter.getScriptSource()).thenReturn("this is the script");
      var result = defaultCertDetails();

      assertTrue(result.contains("this is the script"));
    }

    private String defaultCertDetails() throws IOException {
      return certificateToHtmlConverter.certificate(template, Certificate.builder()
              .metadata(METADATA)
              .categories(categories)
              .build(),
          page,
          HEADER);
    }
  }

  @Nested
  class CertificateInformation {


    @Test
    void shallSetCertName() throws IOException {
      when(templateToDocumentConverter.convert(template, HEADER, page, METADATA)).thenReturn(doc);

      var result = defaultCertInfo();
      assertTrue(result.contains("Certificate name"));
    }

    @Test
    void shallSetCertDescription() throws IOException {
      when(templateToDocumentConverter.convert(template, HEADER, page, METADATA)).thenReturn(doc);
      var result = defaultCertInfo();
      assertTrue(result.contains("Certificate description"));
    }

    @Test
    void shallSetCitizenInfo() throws IOException {
      when(templateToDocumentConverter.convert(template, HEADER, page, METADATA)).thenReturn(doc);
      var result = defaultCertInfo();

      assertAll(() -> assertTrue(result.contains("Skicka intyg till mottagare")),
          () -> assertTrue(result.contains(
              "Du kan hantera ditt intyg genom att logga in på 1177.se Där kan du till exempel skicka intyget till mottagaren")));
    }


    String defaultCertInfo() throws IOException {
      return certificateToHtmlConverter.certificateInformation(template,
          page,
          HEADER, METADATA);
    }

  }


}