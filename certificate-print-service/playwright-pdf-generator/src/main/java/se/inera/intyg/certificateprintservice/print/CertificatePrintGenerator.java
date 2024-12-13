package se.inera.intyg.certificateprintservice.print;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.PdfOptions;
import com.microsoft.playwright.Playwright;
import java.util.ArrayList;
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
import se.inera.intyg.certificateprintservice.print.api.Question;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueList;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueText;

@Service
@Slf4j
@RequiredArgsConstructor
public class CertificatePrintGenerator implements PrintCertificateGenerator {

  @Value("classpath:templates/certificateTemplate.html")
  private Resource template;

  @Override
  public byte[] generate(Certificate request) {
    String jsoupContent = convertToHtml(request);

    try (
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(true));
        BrowserContext context = browser.newContext();
        Page page = context.newPage()
    ) {
      final var pdfOptions = createtPdfOptions();
      page.setContent(jsoupContent);
      return page.pdf(pdfOptions);
    }
  }

  private PdfOptions createtPdfOptions() {
    var pdfOptions = new PdfOptions();
    pdfOptions.setFormat("A4");
    pdfOptions.setPrintBackground(true);
    pdfOptions.setDisplayHeaderFooter(true);
    pdfOptions.setTagged(true);

    pdfOptions.setHeaderTemplate("<title>Läkarintyg för högre körkortsbehörighet</title>");
    pdfOptions.setFooterTemplate("""
        <footer>
          <p>Utskriften skapades med Webcert - en tjänst som drivs av Inera AB</p>
        </footer>""");
    return pdfOptions;
  }

  private String convertToHtml(Certificate request) {
    try {
      var doc = Jsoup.parse(template.getFile(), "UTF-8", "", Parser.xmlParser());
      Element title = doc.getElementById("title");
      title.appendText(request.getMetadata().getName());
      Element content = doc.getElementById("content");

      request.getCategories().forEach(category -> content.appendChild(
          convertCategory(category, request.getCategories().indexOf(category))));

      log.info(doc.html());
      return doc.html();
    } catch (Exception e) {
      throw new RuntimeException("Could not build html from certificate config", e);
    }
  }

  private Node convertCategory(Category category, int index) {
    final var div = new Element(Tag.DIV.toString());
    div.attr("style", "border: 2px solid black;");
    final var title = new Element(Tag.H2.toString());
    title.attr("class", "text-lg font-bold");
    title.attr("style", "border-bottom: 2px solid black;");
    title.text("%d %s".formatted(index + 1, category.getName()));
    div.appendChild(title);
    category.getQuestions().forEach(question -> div.appendChildren(convertQuestion(question)));
    return div;
  }

  private List<Node> convertQuestion(Question question) {
    var list = new ArrayList<Node>();
    final var name = new Element(Tag.H3.toString());
    name.attr("class", "p-1");
    name.text("%s".formatted(question.getName()));
    list.add(name);
    final var value = new Element(Tag.P.toString());
    value.attr("class", "text-sm p-1");
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