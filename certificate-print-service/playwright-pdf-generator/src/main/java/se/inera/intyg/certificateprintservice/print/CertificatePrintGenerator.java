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
  @Value("classpath:templates/certificateTemplate.html")
  private Resource template;
  @Value("classpath:templates/tailwind_3.4.16.js")
  private Resource cssScript;
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


  private String convertToHtml(Certificate certificate) {
    try {

      final var doc = Jsoup.parse(template.getFile(), "UTF-8", "", Parser.xmlParser());

      Element title = doc.getElementById("title");
      title.appendText(
          "%s (%s %s)".formatted(certificate.getMetadata().getName(),
              certificate.getMetadata().getTypeId(),
              certificate.getMetadata().getVersion()));
      Element content = doc.getElementById("content");

      certificate.getCategories().forEach(category -> content.appendChild(
          convertCategory(category, certificate.getCategories().indexOf(category))));

      doc.getElementById("style").appendText("""
          @page {
                margin: %dmm 20mm 15mm 20mm;
              }""".formatted(calculateTopMargin()));

      log.info(doc.html());
      return doc.html();
    } catch (Exception e) {
      throw new RuntimeException("Could not build html from certificate config", e);
    }
  }

  private int calculateTopMargin() {
    return 30;
  }

  private PdfOptions createtPdfOptions(Metadata certificateMetadata)
      throws IOException, URISyntaxException {
    final var pdfOptions = new PdfOptions();
    pdfOptions.setFormat("A4");
    pdfOptions.setPrintBackground(true);
    pdfOptions.setDisplayHeaderFooter(true);
    pdfOptions.setTagged(true);
    pdfOptions.setHeaderTemplate(createHeader(certificateMetadata));
    pdfOptions.setFooterTemplate(createFooterTemplate(certificateMetadata));
    pdfOptions.setPath(Path.of("certificate.pdf"));

    return pdfOptions;
  }

  private String createFooterTemplate(Metadata certificateMetadata) {

    final var footerDiv = new Element(Tag.DIV.toString());
    footerDiv.attr("style", "width: 100%; font-size: 10px;");
    final var pageNrSpan = new Element(Tag.SPAN.toString()).addClass("pageNumber");
    final var totalPageNrSpan = new Element(Tag.SPAN.toString()).addClass("totalPageNumber");

    footerDiv.appendText("Sida ");
    footerDiv.appendChild(pageNrSpan);
    footerDiv.appendText("(");
    footerDiv.appendChild(totalPageNrSpan);
    footerDiv.appendText(")").html();
//    return footerDiv.html();
    return """
            <div style="width: 100%; text-align: right; font-size: 10px; margin-right: 30mm">
                Sida <span class="pageNumber"></span>
                (<span class="totalPages"></span>)
            </div>
        """;
  }

  private String createHeader(Metadata certificateMetadata) throws IOException, URISyntaxException {

    final var headerDiv = new Element(Tag.DIV.toString()).attr("style",
        "display: flex; font-size: 10px; height: 100%; justify-content: center; align-items: center;");
    headerDiv.appendChild(
        new Element(Tag.P.toString()).addClass("title").attr("style", "font-size: 10px;"));

    final var pnrDiv = new Element(Tag.DIV.toString());
    pnrDiv.appendChild(new Element(Tag.STRONG.toString()).appendText("Person- /samordningsnr"))
        .attr("style", "font-size: 10px;");
    pnrDiv.appendChild(new Element(Tag.P.toString()).appendText(certificateMetadata.getPersonId()))
        .attr("style", "font-size: 10px;");
    headerDiv.appendChild(pnrDiv);

    final var logoPath = ClassLoader.getSystemResource("transportstyrelsen-logo.png")
        .toURI();
    byte[] imageBytes = Files.readAllBytes(Paths.get(logoPath));
    final var base64 = Base64.getEncoder().encode(imageBytes);
    final var img = new Element(Tag.IMG.toString())
        .attr("src", "data:image/png;base64, " + new String(base64))
        .attr("alt", "test-image-alt")
        .attr("style", "height: 40px");

    headerDiv.appendChild(img);

    final var leftPageInfo = new Element(Tag.DIV.toString()).attr("style",
        """
            display: flex;
            justify-content: start;
            align-items: end;
            position: fixed;
            border: red solid 1px;
            transform: translateX(-50%);
            font-size; 10px;
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
    headerDiv.appendChild(leftPageInfo);

    final var watermark = new Element(Tag.DIV.toString()).attr("style", """
        position: absolute;
        top: 50%;
        left: 50%;
        width: 100%;
        height: 100%;
        transform: translate(-50%, -50%) rotate(45deg);
        font-family: 'Helvetica', sans-serif;
        font-size: 100pt;
        color: rgb(128, 128, 128);
        opacity: 0.5;
        z-index: -1;
        """
    );
    watermark.text("UTKAST");
    headerDiv.appendChild(watermark);

    return headerDiv.html();
  }

  private Node convertCategory(Category category, int index) {
    final var div = new Element(Tag.DIV.toString());
    div.attr(STYLE, "border: 1px solid black; margin-top 2cm;");
    div.addClass("box-decoration-clone");
    final var title = new Element(Tag.H2.toString());
    title.addClass("text-lg font-bold");
    title.attr(STYLE, "border-bottom: 1px solid black;");
    title.text("%d %s".formatted(index + 1, category.getName()));
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
}