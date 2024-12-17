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

      final var watermarkPath = ClassLoader.getSystemResource("transportstyrelsen-logo.png")
          .toURI();
      byte[] imageBytes = Files.readAllBytes(Paths.get(watermarkPath));
      final var base64 = Base64.getEncoder().encode(imageBytes);
      final var img = new Element(Tag.IMG.toString())
          .attr("src", "data:image/png;base64, " + new String(base64))
          .attr("alt", "test-image-alt")
          .attr("style", "height: 40px");

      final var logo = doc.getElementById("logo").appendChild(img);
      final var pnr = doc.getElementById("pnr").attr("style", "font-weight: bold;")
          .appendText("Person- /samordningsnr");

      log.info(doc.html());
      return doc.html();
    } catch (Exception e) {
      throw new RuntimeException("Could not build html from certificate config", e);
    }
  }

  private PdfOptions createtPdfOptions(Metadata certificateMetadata)
      throws IOException, URISyntaxException {
    final var pdfOptions = new PdfOptions();
    pdfOptions.setFormat("A4");
    pdfOptions.setPrintBackground(true);
    //pdfOptions.setDisplayHeaderFooter(true);
    pdfOptions.setTagged(true);
    //pdfOptions.setHeaderTemplate(createHeader(certificateMetadata));
//    pdfOptions.setFooterTemplate("""
//        <footer>
//          <p>Utskriften skapades med Webcert - en tjänst som drivs av Inera AB</p>
//        </footer>""");
    pdfOptions.setPath(Path.of("certificate.pdf"));

    return pdfOptions;
  }

  private String createHeader(Metadata certificateMetadata) throws IOException, URISyntaxException {
    final var headerDiv = new Element(Tag.DIV.toString()).attr("style",
        "display: block !important;");
    headerDiv.appendChild(new Element(Tag.P.toString()).attr("class", "title").attr("style",
        "font-size: 14px; width: 800px; height: 200px; margin: 20px;"));
    headerDiv.appendChild(new Element(Tag.P.toString()).attr(CLASS, "pageNumber").attr(STYLE,
        "font-size: 10px; width: 15px; height: 200px; margin-top: 20px;"));
    headerDiv.appendChild(new Element(Tag.P.toString()).attr(CLASS, "totalPages").attr(STYLE,
        "font-size: 10px; width: 15px; height: 200px; margin-top: 20px;"));

//    final var pnrDiv = new Element(Tag.DIV.toString());
//    pnrDiv.appendChild(img);
//    pnrDiv.appendChild(new Element(Tag.STRONG.toString()).appendText("Person- /samordningsnr"));
//    pnrDiv.appendChild(new Element(Tag.P.toString()).appendText(certificateMetadata.getPersonId()));
//    headerDiv.appendChild(pnrDiv);
//
//    final var logo = ClassLoader.getSystemResource("transportstyrelsens_logotyp_rgb.eps.pdf")
//        .getPath();
//    headerDiv.appendChild(
//        new Element(Tag.IMG.toString()).attr("src", logo));

//    final var watermark = new Element(Tag.DIV.toString());
//    watermark.text("UTKAST");
//    watermark.attr("style", "")

    return headerDiv.html();
  }

  private Node convertCategory(Category category, int index) {
    final var div = new Element(Tag.DIV.toString());
    div.attr(STYLE, "border: 1px solid black; margin-top 2cm;");
    final var title = new Element(Tag.H2.toString());
    title.attr(CLASS, "text-lg font-bold");
    title.attr(STYLE, "border-bottom: 1px solid black;");
    title.text("%d %s".formatted(index + 1, category.getName()));
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