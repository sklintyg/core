package se.inera.intyg.certificateprintservice.print;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.EmulateMediaOptions;
import com.microsoft.playwright.Page.PdfOptions;
import com.microsoft.playwright.options.Media;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.swing.text.html.HTML.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
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
  private static final String CLASS = "class";
  @Value("classpath:templates/certificateTemplate.html")
  private Resource template;
  private final Browser browser;


  @Override
  public byte[] generate(Certificate certificate) {
    String jsoupContent = convertToHtml(certificate);

    try (
        BrowserContext context = browser.newContext();
        Page page = context.newPage()
    ) {
      final var emulateOptions = new EmulateMediaOptions();
      final var pdfOptions = createtPdfOptions(certificate.getMetadata());
      emulateOptions.setMedia(Media.PRINT);
      page.setContent(jsoupContent);
      page.emulateMedia(emulateOptions);

      return page.pdf(pdfOptions);
    } catch (IOException e) {
      throw new RuntimeException("Could not get logo", e);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }


  private String getLogo() throws IOException, URISyntaxException {
    final var logoPath = ClassLoader.getSystemResource("transportstyrelsen-logo.png")
        .toURI();
    final var logoBytes = Files.readAllBytes(Paths.get(logoPath));
    return new String(Base64.getEncoder().encode(logoBytes));
  }

  private String convertToHtml(Certificate certificate) {
    try {
      //final var metadata = certificate.getMetadata();

      final var doc = Jsoup.parse(template.getFile(), "UTF-8", "", Parser.xmlParser());

      Element title = doc.getElementById("title");
      title.appendText(
          "%s (%s %s)".formatted(certificate.getMetadata().getName(),
              certificate.getMetadata().getTypeId(),
              certificate.getMetadata().getVersion()));
      Element content = doc.getElementById("content");

//      doc.getElementById("recipient-logo")
//          .attr("src", "data:image/png;base64, " + getLogo())
//          .attr("alt", "recipient-logo");
//      doc.getElementById("person-id-title").text("Person- /samordningsnr");
//      doc.getElementById("person-id-value").text(metadata.getPersonId());
//
//      doc.getElementById("certificate-header-title").text(certificateTitle);

      certificate.getCategories().forEach(category -> content.appendChild(
          convertCategory(category, certificate.getCategories().indexOf(category))));

      log.info(doc.html());
      return doc.html();
    } catch (Exception e) {
      throw new RuntimeException("Could not build html from certificate config", e);
    }
  }

  private PdfOptions createtPdfOptions(Metadata certificateMetadata)
      throws IOException, URISyntaxException {
    final var pdfOptions = new PdfOptions();
    //pdfOptions.setFormat("A4");
    pdfOptions.setPrintBackground(true);
    pdfOptions.setDisplayHeaderFooter(true);
    pdfOptions.setTagged(true);
    pdfOptions.setPreferCSSPageSize(true);
    pdfOptions.setHeaderTemplate(createHeader(certificateMetadata));
//    final var margin = new Margin();
//    margin.setLeft("20mm");
//    margin.setTop("15mm");
//    pdfOptions.setMargin(margin);

    pdfOptions.setPath(Path.of("certificate.pdf"));

    return pdfOptions;
  }


  private Element getTitle(String title) {
    return new Element(Tag.P.toString()).addClass("title")
        .attr("style", "font-size: 10px;");
  }

  private Element getLeftMarginInfo() {
    final var leftMarginInfo = new Element(Tag.DIV.toString()).addClass(
        "fixed top-0 bottom 0 left-0 leading-4 text-sm");
    leftMarginInfo.appendChild(
        new Element(Tag.DIV.toString()).addClass("rotate-180")
            .attr("style",
                "font-size: 10px; writingMode: vertical-rl; textOrientation: sideways; height: 100%;")
            .appendChild(new Element(Tag.P.toString()).text("HEJSANHOPPSANHEJSAN"))
    );
    return leftMarginInfo;
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

  private Element getQRCode(byte[] qrCodeBytes) {
    final var qrCodeWrapper = new Element(Tag.DIV.toString());//.attr("style", "width: 50%");
    final var base64 = Base64.getEncoder().encode(qrCodeBytes);
    final var qrCode = new Element((Tag.IMG.toString()))
        .attr("src", "data:image/png;base64, " + new String(base64))
        .attr("alt", "qr-code")
        .attr("style",
            "size: 100%; max-height: 15mm; max-width: 15mm; margin-right: 5mm; border: purple solid 1px;");
    qrCodeWrapper.appendChild(qrCode);
    return qrCodeWrapper;
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

  private String createHeader(Metadata certificateMetadata) throws IOException, URISyntaxException {
    final var logoPath = ClassLoader.getSystemResource("transportstyrelsen-logo.png")
        .toURI();
    final var qrPath = ClassLoader.getSystemResource("qrcode.png")
        .toURI();

    final var pageHeaderWrapper = new Element(Tag.DIV.toString());

    final var pageHeader = new Element(Tag.DIV.toString()).attr("style", """
        margin: 10mm 20mm 10mm 20mm;
        width: 100%;
        font-size: 10pt;
        display: flex;
        border: green solid 1px;
        """);

    pageHeader.appendChild(getLogo(Files.readAllBytes(Paths.get(logoPath))));
    //pageHeader.appendChild(getQRCode(Files.readAllBytes(Paths.get(qrPath))));
    pageHeader.appendChild(getPersonId(certificateMetadata.getPersonId()));
    pageHeaderWrapper.appendChild(pageHeader);

    return pageHeaderWrapper.html();
  }

  private String getFooter() {
    final var pageNrDiv = new Element(Tag.DIV.toString());

    pageNrDiv.appendChild(
        new Element(Tag.P.toString()).addClass("pageNumber").attr("style", "font-size: 10px;"));
    pageNrDiv.appendChild(
        new Element(Tag.P.toString()).addClass("totalPages").attr("style", "font-size: 10px;"));
    //header.appendChild(pageNrDiv);
    return pageNrDiv.html();
  }

  private Node convertCategory(Category category, int index) {
    final var div = new Element(Tag.DIV.toString());
    div.attr(STYLE, "border: 1px solid black;");

    final var title = new Element(Tag.H2.toString());
    title.attr(CLASS, "text-lg font-bold");
    title.attr(STYLE, "border-bottom: 1px solid black;");
    title.text("%s".formatted(category.getName()));

    div.appendChild(title);
    category.getQuestions().forEach(question -> div.appendChildren(convertQuestion(question)));
    return div;
  }

  private List<Node> convertQuestion(Question question) {
    final var list = new ArrayList<Node>();
    final var name = new Element(Tag.H3.toString());
    name.attr(CLASS, "p-1");
    name.text("%s".formatted(question.getName()));
    list.add(name);
    final var value = new Element(Tag.P.toString());
    value.attr(CLASS, "text-sm p-1");
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
}