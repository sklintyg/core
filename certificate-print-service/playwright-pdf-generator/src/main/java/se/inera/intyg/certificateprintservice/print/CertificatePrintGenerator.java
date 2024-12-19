package se.inera.intyg.certificateprintservice.print;

import static se.inera.intyg.certificateprintservice.print.Constants.RIGHT_MARGIN_INFO_STYLE;
import static se.inera.intyg.certificateprintservice.print.Constants.STYLE;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.PdfOptions;
import com.microsoft.playwright.Playwright;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.text.html.HTML.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateprintservice.print.api.Category;
import se.inera.intyg.certificateprintservice.print.api.Certificate;
import se.inera.intyg.certificateprintservice.print.api.Metadata;
import se.inera.intyg.certificateprintservice.print.api.Question;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueList;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueTable;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueText;

@Service
@Slf4j
@RequiredArgsConstructor
public class CertificatePrintGenerator implements PrintCertificateGenerator {


  @Value("classpath:templates/certificateTemplate.html")
  private Resource template;
  @Value("classpath:templates/infoPageTemplate.html")
  private Resource infoPageTemplate;
  @Value("classpath:templates/tailwind_3.4.16.js")
  private Resource cssScript;
  private final Playwright playwright;

  private final ElementProvider elementProvider;

  @Override
  public byte[] generate(Certificate certificate) {

    try (
        Browser browser = playwright.chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(true));
        BrowserContext context = browser.newContext();
        Page certificatePage = context.newPage();
        Page certificateInfoPage = context.newPage();
        Page headerPage = context.newPage();
        Page infoHeaderPage = context.newPage()
    ) {
      final var metadata = certificate.getMetadata();
      final var certificateHeader = createHeader(metadata, false);
      final var certificateContent = certificateToHtml(certificate, headerPage, certificateHeader);
      final var pdfOptions = getPdfOptions(metadata, certificateHeader);
      certificatePage.setContent(certificateContent);

      final var certificateInfoHeader = createHeader(metadata, true);
      final var certificateInfo = certificateInfoToHtml(metadata, infoHeaderPage,
          certificateInfoHeader);
      final var certificateInfoPdfOptions = getPdfOptions(metadata, certificateInfoHeader);
      certificateInfoPage.setContent(certificateInfo);

      final var certificateContentPdf = certificatePage.pdf(pdfOptions);
      final var certificateInfoPdf = certificateInfoPage.pdf(certificateInfoPdfOptions);

      return mergePdfs(certificateContentPdf, certificateInfoPdf);

    } catch (Exception e) {
      throw new IllegalStateException("Failure creating pdf from certificate '%s'"
          .formatted(certificate.getMetadata().getCertificateId()), e);
    }
  }

  private String certificateToHtml(Certificate certificate, Page headerPage, String certificateHtml)
      throws IOException {
    var doc = createDocFromTemplate(template, certificateHtml, headerPage,
        certificate.getMetadata());

    final var content = doc.getElementById("content");
    certificate.getCategories().forEach(category ->
        Objects.requireNonNull(content).appendChild(convertCategory(category)));

    log.debug(doc.html());
    return doc.html();
  }

  private String certificateInfoToHtml(Metadata metadata, Page infoHeaderPage,
      String certificateInfoHtml) throws IOException {

    var doc = createDocFromTemplate(infoPageTemplate, certificateInfoHtml, infoHeaderPage,
        metadata);

    final var content = doc.getElementById("content");

    assert content != null;
    content.appendChild(elementProvider.element(Tag.STRONG).text(metadata.getName()));
    content.appendChild(elementProvider.element(Tag.P).text(metadata.getDescription()));
    content.appendChild(elementProvider.element(Tag.STRONG).text("Skicka intyg till mottagare"));
    content.appendChild(
        elementProvider.element(Tag.P)
            .text("Du kan hantera ditt intyg genom att logga in på 1177.se "
                + "Där kan du till exempel skicka intyget till mottagaren"));

    return doc.html();
  }

  private Document createDocFromTemplate(Resource template, String html, Page page,
      Metadata metadata)
      throws IOException {
    final var headerHeight = getHeaderHeight(page, html);
    final var doc = Jsoup.parse(template.getFile(), StandardCharsets.UTF_8.name(), "",
        Parser.xmlParser());

    setPageMargin(doc, headerHeight);
    final var title = doc.getElementById("title");
    Objects.requireNonNull(title).appendText(getCertificateTitle(metadata));
    return doc;
  }

  private String getCertificateTitle(Metadata metadata) {
    return "%s (%s v%s)".formatted(metadata.getName(), metadata.getTypeId(),
        metadata.getVersion());
  }


  private PdfOptions getPdfOptions(Metadata metadata, String header) {
    return new PdfOptions()
        .setFormat("A4")
        .setPrintBackground(true)
        .setDisplayHeaderFooter(true)
        .setTagged(true)
        .setHeaderTemplate(header)
        .setFooterTemplate(createFooterTemplate(metadata));
  }

  private double getHeaderHeight(Page page, String header) {
    page.setContent(header);
    return page.getByTitle("headerElement").boundingBox().height;
  }

  private void setPageMargin(Document doc, Double headerHeight) {
    doc.getElementById("style").appendText("""
        @page {
              margin: calc(%spx + 6mm) 20mm 15mm 20mm;
            }""".formatted(Math.round(headerHeight)));
  }


  private String createFooterTemplate(Metadata certificateMetadata) {

    var origin = "Utskriften skapades med %s - en tjänst som drivs av Inera AB".formatted(
        certificateMetadata.getApplicationOrigin());
    return """
            <div style="width: 100%%; text-align: left; font-size: 10px; margin-left: 30mm">
                <span>%s</span>
            </div>
        """.formatted(origin);
  }


  private void appendWatermarkIfDraft(Element baseWrapper, String signingDate) {
    if (signingDate != null) {
      return;
    }

    final var watermark = new Element(Tag.DIV.toString()).attr("style", """
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translateX(-50%) translateY(-50%) rotate(315deg);
        font-size: 100pt;
        color: rgb(128, 128, 128);
        opacity: 0.5;
        z-index: -1;
        """
    );

    watermark.text("UTKAST");
    baseWrapper.appendChild(watermark);
  }


  private static String getPrintInfoText(Metadata metadata) {
    final var draftInfo = "Detta är en utskrift av ett elektroniskt intygsutkast och ska INTE skickas till %s.".formatted(
        metadata.getRecipientName());
    final var signedInfo = "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren.";
    final var signedAndSentInfo =
        "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren. "
            + "Notera att intyget redan har skickats till %s.".formatted(
            metadata.getRecipientName());

    if (metadata.getSigningDate() == null) {
      return draftInfo;
    } else {
      return metadata.isSent() ? signedAndSentInfo : signedInfo;
    }

  }

  private void appendRightMarginInfoIfSigned(Element baseWrapper, Metadata metadata) {
    if (metadata.getSigningDate() == null) {
      return;
    }

    final var rightMarginInfoWrapper = new Element(Tag.DIV.toString())
        .attr(STYLE, RIGHT_MARGIN_INFO_STYLE);

    final var rightMarginInfo = new Element(Tag.SPAN.toString())
        .text("Intygs-ID: %s".formatted(metadata.getCertificateId()));

    rightMarginInfoWrapper.appendChild(rightMarginInfo);
    baseWrapper.appendChild(rightMarginInfoWrapper);
  }

  private String createHeader(Metadata certificateMetadata, boolean isGeneralInfo)
      throws IOException, URISyntaxException {
    final var logoPath = ClassLoader.getSystemResource("transportstyrelsen-logo.png")
        .toURI();

    final var baseWrapper = new Element(Tag.DIV.toString());
    final var headerWrapper = new Element(Tag.DIV.toString())
        .attr("style", "display: grid; width: 100%; font-size: 10pt;")
        .attr("title", "headerElement");

    final var pageHeader = new Element(Tag.DIV.toString()).attr("style", """
          margin: 10mm 20mm 10mm 20mm;
          display: flex;
          border: green solid 1px;
        """);
    pageHeader.appendChild(elementProvider.recipientLogo(Files.readAllBytes(Paths.get(logoPath))));

    final var certificateHeader = new Element(Tag.DIV.toString()).attr("style",
        "margin: 0 20mm 10mm 20mm;");
    certificateHeader.appendChild(elementProvider.title(getCertificateTitle(certificateMetadata)));

    if (!isGeneralInfo) {
      pageHeader.appendChild(elementProvider.personId(certificateMetadata.getPersonId()));
      certificateHeader.appendChild(
          elementProvider.printInfo(getPrintInfoText(certificateMetadata)));
      appendRightMarginInfoIfSigned(baseWrapper, certificateMetadata);
    }

    headerWrapper.appendChild(pageHeader);
    headerWrapper.appendChild(certificateHeader);
    baseWrapper.appendChild(headerWrapper);
    baseWrapper.appendChild(elementProvider.leftMarginInfo(
        "%s - Fastställd av %s".formatted(certificateMetadata.getTypeId(),
            certificateMetadata.getRecipientName())));

    appendWatermarkIfDraft(baseWrapper, certificateMetadata.getSigningDate());

    return baseWrapper.html();
  }

  private Node convertCategory(Category category) {
    final var div = new Element(Tag.DIV.toString());
    div.attr(STYLE, "border: 1px solid black;");
    div.addClass("box-decoration-clone");
    final var title = new Element(Tag.H2.toString());
    title.addClass("text-lg font-bold");
    title.attr(STYLE, "border-bottom: 1px solid black;");
    title.text("%s".formatted(category.getName()));

    div.appendChild(title);
    category.getQuestions().forEach(question -> div.appendChildren(convertQuestion(question)));
    return div;
  }

  private List<Node> convertQuestion(Question question) {
    final var list = new ArrayList<Node>();
    final var name = new Element(Tag.H3.toString());
    name.addClass("p-1");
    name.text("%s".formatted(question.getName()));
    list.add(name);

    Element value = null;
    if (question.getValue() instanceof ElementValueText textValue) {
      value = getTextValue(textValue.getText());
    } else if (question.getValue() instanceof ElementValueList listValue) {
      value = getTextValue(String.join(", ", listValue.getList()));
    } else if (question.getValue() instanceof ElementValueTable tableValue) {
      value = HTMLFactory.getTableValue(tableValue);
    }

    list.add(value);

    question.getSubQuestions()
        .forEach(subQuestion -> list.addAll(convertQuestion(subQuestion)));
    return list;
  }

  private Element getTextValue(String text) {
    final var value = new Element(Tag.P.toString());
    value.addClass("text-sm p-1");
    value.appendText(text);
    return value;
  }


  private byte[] mergePdfs(byte[] certPdf, byte[] infoPdf) throws IOException {
    PDFMergerUtility merger = new PDFMergerUtility();
    final var doc = Loader.loadPDF(certPdf);
    final var infoDoc = Loader.loadPDF(infoPdf);

    merger.appendDocument(doc, infoDoc);
    addPagenNr(doc);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    doc.save(byteArrayOutputStream);
    doc.close();
    return byteArrayOutputStream.toByteArray();
  }

  private void addPagenNr(PDDocument doc) throws IOException {
    int totalPages = doc.getNumberOfPages();
    for (int i = 0; i < totalPages; i++) {
      PDPage page = doc.getPage(i);

      PDPageContentStream contentStream = new PDPageContentStream(doc, page,
          PDPageContentStream.AppendMode.APPEND, true, true);
      contentStream.setFont(new PDType1Font(FontName.HELVETICA), 10);
      contentStream.beginText();
      contentStream.newLineAtOffset(page.getMediaBox().getWidth() - 70, 15);
      contentStream.showText("Sida %s (%s)".formatted(i + 1, totalPages));
      contentStream.endText();
      contentStream.close();
    }
  }
}