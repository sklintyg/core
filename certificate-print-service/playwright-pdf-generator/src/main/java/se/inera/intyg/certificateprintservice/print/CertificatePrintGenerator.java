package se.inera.intyg.certificateprintservice.print;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.EmulateMediaOptions;
import com.microsoft.playwright.Page.PdfOptions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
        Page page = context.newPage();
        Page infoPage = context.newPage()
    ) {

      infoPage.setContent(convertInfoToHtml(certificate));
      var infoPdf = infoPage.pdf(createtGeneralPdfOptions(certificate.getMetadata()));

      String jsoupContent = convertCertificateToHtml(certificate);
      final var emulateOptions = new EmulateMediaOptions();
      final var pdfOptions = createtPdfOptions(certificate.getMetadata());
      page.setContent(jsoupContent);
      page.emulateMedia(emulateOptions);

      final var certPdf = page.pdf(pdfOptions);

      return mergePdfs(certPdf, infoPdf);
    } catch (IOException e) {
      throw new RuntimeException("Could not get logo", e);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }


  private String convertInfoToHtml(Certificate certificate) throws IOException {
    final var doc = Jsoup.parse(infoPageTemplate.getFile(), "UTF-8", "", Parser.xmlParser());
    setTitle(doc, certificate);
    setPageMargin(doc);

    Element content = doc.getElementById("content");
    content.appendChild(
        new Element(Tag.STRONG.toString()).text(certificate.getMetadata().getName()));
    content.appendChild(
        new Element(Tag.P.toString()).text(certificate.getMetadata().getDescription()));

    content.appendChild(
        new Element(Tag.STRONG.toString()).text("Skicka intyg till mottagare"));
    content.appendChild(
        new Element(Tag.P.toString()).text(
            "Du kan hantera ditt intyg genom att logga in på 1177.se Där kan du till exempel skicka intyget till mottagaren"));

    return doc.html();
  }

  private String convertCertificateToHtml(Certificate certificate) {
    try {

      final var doc = Jsoup.parse(template.getFile(), "UTF-8", "", Parser.xmlParser());

      setTitle(doc, certificate);
      setPageMargin(doc);
      Element content = doc.getElementById("content");
      certificate.getCategories()
          .forEach(category -> content.appendChild(convertCategory(category)));

      log.info(doc.html());
      return doc.html();
    } catch (Exception e) {
      throw new RuntimeException("Could not build html from certificate config", e);
    }
  }

  private void setPageMargin(Document doc) {
    doc.getElementById("style").appendText("""
        @page {
              margin: %dmm 20mm 15mm 20mm;
            }""".formatted(calculateTopMargin()));
  }

  private void setTitle(Document doc, Certificate certificate) {
    Element title = doc.getElementById("title");
    title.appendText(
        "%s (%s %s)".formatted(certificate.getMetadata().getName(),
            certificate.getMetadata().getTypeId(),
            certificate.getMetadata().getVersion()));
  }

  private int calculateTopMargin() {
    return 50;
  }

  private PdfOptions createtPdfOptions(Metadata certificateMetadata)
      throws IOException, URISyntaxException {
    final var pdfOptions = createtGeneralPdfOptions(certificateMetadata);
    pdfOptions.setHeaderTemplate(createHeader(certificateMetadata, false));
    return pdfOptions;
  }

  private PdfOptions createtGeneralPdfOptions(Metadata certificateMetadata)
      throws IOException, URISyntaxException {
    final var pdfOptions = new PdfOptions();
    pdfOptions.setFormat("A4");
    pdfOptions.setPrintBackground(true);
    pdfOptions.setDisplayHeaderFooter(true);
    pdfOptions.setTagged(true);
    pdfOptions.setHeaderTemplate(createHeader(certificateMetadata, true));
    pdfOptions.setFooterTemplate(createFooterTemplate(certificateMetadata));

    return pdfOptions;
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


  private Element getLeftMarginInfo(Metadata certificateMetadata) {

    final var leftPageInfo = new Element(Tag.DIV.toString()).attr("style",
        """
            display: flex;
            justify-content: start;
            align-items: end;
            position: fixed;
            border: red solid 1px;
            transform: translateX(-50%);
            font-size; 10pt;
            writingMode: vertical-rl;
            textOrientation: sideways;
            height: 100%;
            bottom: 40mm;
            width: 20mm;
            text-wrap: nowrap;
            """);
    final var child = new Element(Tag.SPAN.toString());
    child.attr("style",
        "font-size: 10px; transform: rotate(-90deg);");
    child.text("%s utfärdat av %s".formatted(certificateMetadata.getTypeId(),
        certificateMetadata.getApplicationOrigin()));
    leftPageInfo.appendChild(child);
    return leftPageInfo;
  }

  private Element getLogo(byte[] logoBytes) {
    final var logoWrapper = new Element(Tag.DIV.toString());//.attr("style", "width: 50%");
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

  private Element getCertificateTitle(Metadata metadata) {
    final var certificateTitle = "%s (%s v%s)".formatted(metadata.getName(),
        metadata.getTypeId(), metadata.getVersion());

    final var titleWrapper = new Element(Tag.DIV.toString())
        .attr("style", "font-size: 14pt; border-bottom: black solid 0.5px; padding-bottom: 1mm;");

    final var title = new Element(Tag.SPAN.toString())
        .appendText(certificateTitle);

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

  private String createHeader(Metadata certificateMetadata, boolean isGeneralInfo)
      throws IOException, URISyntaxException {
    final var logoPath = ClassLoader.getSystemResource("transportstyrelsen-logo.png")
        .toURI();

    final var baseWrapper = new Element(Tag.DIV.toString()).attr("style",
        "display: flex; flex-direction: column;");
    final var headerWrapper = new Element(Tag.DIV.toString())
        .attr("style", "display: grid; width: 100%; font-size: 10pt;");

    final var pageHeader = new Element(Tag.DIV.toString()).attr("style", """
        margin: 10mm 20mm 10mm 20mm;
        display: flex;
        border: green solid 1px;
        """);
    pageHeader.appendChild(getLogo(Files.readAllBytes(Paths.get(logoPath))));
    pageHeader.appendChild(getPersonId(certificateMetadata.getPersonId()));

    if (!isGeneralInfo) {
      pageHeader.appendChild(getPersonId(certificateMetadata.getPersonId()));
    }

    final var certificateHeader = new Element(Tag.DIV.toString()).attr("style", """
        margin: 0 20mm 10mm 20mm;
        font-weight: bold;
        """);
    certificateHeader.appendChild(getCertificateTitle(certificateMetadata));

    headerWrapper.appendChild(pageHeader);
    headerWrapper.appendChild(certificateHeader);
    baseWrapper.appendChild(headerWrapper);
    baseWrapper.appendChild(getLeftMarginInfo(certificateMetadata));

    appendWatermarkIfDraft(baseWrapper, certificateMetadata.getSigningDate());

    return baseWrapper.html();
  }

  private Node createCertificateHeader(Metadata certificateMetadata) {
    final var certHeader = new Element(Tag.DIV.toString()).attr("style", """
        margin: 10mm 20mm 10mm 20mm;
        width: 100%;
        display: flex;
        font-size: 10pt;
        border: blue solid 1px;
        """);

    certHeader.appendChild(new Element(Tag.STRONG.toString()).text(certificateMetadata.getName()));
    certHeader.appendText(
        "(%s %s)".formatted(certificateMetadata.getTypeId(), certificateMetadata.getVersion()));
    return certHeader;
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