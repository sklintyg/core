package se.inera.intyg.certificateprintservice.print;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.PdfOptions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
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
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueText;

@Service
@Slf4j
@RequiredArgsConstructor
public class CertificatePrintGenerator implements PrintCertificateGenerator {

  private static final String STYLE = "style";
  @Value("classpath:templates/certificateTemplate.html")
  private Resource template;
  @Value("classpath:templates/infoPageTemplate.html")
  private Resource infoPageTemplate;
  @Value("classpath:templates/tailwind_3.4.16.js")
  private Resource cssScript;
  private final Browser browser;


  @Override
  public byte[] generate(Certificate certificate) {

    try (
        BrowserContext context = browser.newContext();
        Page certificatePage = context.newPage();
        Page certificateInfoPage = context.newPage();
        Page headerPage = context.newPage();
        Page infoHeaderPage = context.newPage();
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
    final var headerHeight = getHeaderHeight(headerPage, certificateHtml);
    final var doc = Jsoup.parse(template.getFile(), StandardCharsets.UTF_8.name(), "",
        Parser.xmlParser());

    setPageMargin(doc, headerHeight);
    final var title = doc.getElementById("title");
    Objects.requireNonNull(title).appendText(getCertificateTitle(certificate.getMetadata()));

    final var content = doc.getElementById("content");
    certificate.getCategories().forEach(category ->
        Objects.requireNonNull(content).appendChild(convertCategory(category)));

    log.debug(doc.html());
    return doc.html();
  }

  private String certificateInfoToHtml(Metadata metadata, Page infoHeaderPage,
      String certificateInfoHtml) throws IOException {
    final var headerHeight = getHeaderHeight(infoHeaderPage, certificateInfoHtml);
    final var doc = Jsoup.parse(infoPageTemplate.getFile(), StandardCharsets.UTF_8.name(), "",
        Parser.xmlParser());

    setPageMargin(doc, headerHeight);
    final var title = doc.getElementById("title");
    Objects.requireNonNull(title).appendText(getCertificateTitle(metadata));

    final var content = doc.getElementById("content");

    assert content != null;
    content.appendChild(element(Tag.STRONG).text(metadata.getName()));
    content.appendChild(element(Tag.P).text(metadata.getDescription()));
    content.appendChild(element(Tag.STRONG).text("Skicka intyg till mottagare"));
    content.appendChild(
        element(Tag.P).text("Du kan hantera ditt intyg genom att logga in på 1177.se "
            + "Där kan du till exempel skicka intyget till mottagaren"));

    return doc.html();
  }

  private String getCertificateTitle(Metadata metadata) {
    return "%s (%s v%s)".formatted(metadata.getName(), metadata.getTypeId(),
        metadata.getVersion());
  }

  private Element element(Tag tag) {
    return new Element(tag.toString());
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


  private Element getLeftMarginInfo(Metadata metadata) {
    final var leftMarginInfoWrapper = new Element(Tag.DIV.toString())
        .attr("style", """
            position: absolute;
            left: 1cm;
            bottom: 15mm;
            border: red solid 1px;
            font-size: 10pt;
            transform: rotate(-90deg) translateY(-50%);
            transform-origin: top left;
            """);

    final var leftMarginInfo = new Element(Tag.SPAN.toString())
        .text("%s - Fastställd av Transportstyrelsen".formatted(metadata.getTypeId()));

    leftMarginInfoWrapper.appendChild(leftMarginInfo);
    return leftMarginInfoWrapper;
  }

  private Element getLogo(byte[] logoBytes) {
    final var logoWrapper = new Element(Tag.DIV.toString());
    final var base64 = Base64.getEncoder().encode(logoBytes);
    final var logo = new Element(Tag.IMG.toString())
        .attr("src", "data:image/png;base64, " + new String(base64))
        .attr("alt", "recipient-logo")
        .attr("style",
            "max-height: 15mm; max-width: 35mm; border: blue solid 1px;");
    logoWrapper.appendChild(logo);
    return logoWrapper;
  }

  private Element getPersonId(String personId) {
    final var personIdWrapper = new Element(Tag.DIV.toString()).attr("style", "width: 100%");

    final var div = new Element(Tag.DIV.toString());
    div.attr("style", "border: red solid 1px; float: right; text-align: right;");

    div.appendChild(new Element(Tag.SPAN.toString()).attr("style", "font-weight: bold;")
        .appendText("Person- /samordningsnr"));
    div.appendChild(new Element(Tag.BR.toString()));
    div.appendChild(new Element(Tag.SPAN.toString()).appendText(personId));

    personIdWrapper.appendChild(div);
    return personIdWrapper;
  }

  private Element getTitle(Metadata metadata) {
    final var titleWrapper = new Element(Tag.DIV.toString()).attr("style", """
        font-size: 14pt;
        font-weight: bold;
        border-bottom: black solid 0.5px;
        padding-bottom: 1mm;
        """);

    final var title = element(Tag.SPAN).appendText(getCertificateTitle(metadata));

    titleWrapper.appendChild(title);
    return titleWrapper;
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

  private Element getPrintInfo(Metadata metadata) {
    final var printInfoWrapper = new Element(Tag.DIV.toString()).attr("style", """
        margin-top: 5mm;
        padding: 3mm 5mm;
        border: red solid 1px;
        """);

    final var printInfo = new Element(Tag.SPAN.toString()).appendText(getPrintInfoText(metadata));
    printInfoWrapper.appendChild(printInfo);
    return printInfoWrapper;
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
      // TODO Metadata from cs should be updted to include sent info
      // return metadata.getSendDate() == null ? signedInfo : signedAndSentInfo;

      return signedInfo;
    }
  }

  private void appendRightMarginInfoIfSigned(Element baseWrapper, Metadata metadata) {
    if (metadata.getSigningDate() == null) {
      return;
    }

    final var rightMarginInfoWrapper = new Element(Tag.DIV.toString())
        .attr("style", """
            position: absolute;
            width: 100%;
            left: 20cm;
            bottom: 15mm;
            border: red solid 1px;
            font-size: 10pt;
            transform: rotate(-90deg) translateY(-50%);
            transform-origin: top left;
            """);

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
    pageHeader.appendChild(getLogo(Files.readAllBytes(Paths.get(logoPath))));

    final var certificateHeader = new Element(Tag.DIV.toString()).attr("style",
        "margin: 0 20mm 10mm 20mm;");
    certificateHeader.appendChild(getTitle(certificateMetadata));

    if (!isGeneralInfo) {
      pageHeader.appendChild(getPersonId(certificateMetadata.getPersonId()));
      certificateHeader.appendChild(getPrintInfo(certificateMetadata));
      appendRightMarginInfoIfSigned(baseWrapper, certificateMetadata);
    }

    headerWrapper.appendChild(pageHeader);
    headerWrapper.appendChild(certificateHeader);
    baseWrapper.appendChild(headerWrapper);
    baseWrapper.appendChild(getLeftMarginInfo(certificateMetadata));

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
    final var value = new Element(Tag.P.toString());
    value.addClass("text-sm p-1");
    if (question.getValue() instanceof ElementValueText textValue) {
      value.appendText(textValue.getText());
    } else if (question.getValue() instanceof ElementValueList listValue) {
      value.appendText(listValue.getList().toString());
    }
    list.add(value);

    question.getSubQuestions()
        .forEach(subQuestion -> list.addAll(convertQuestion(subQuestion)));
    return list;
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