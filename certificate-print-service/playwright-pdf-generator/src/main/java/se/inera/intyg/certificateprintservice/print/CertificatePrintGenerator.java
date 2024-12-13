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
import se.inera.intyg.certificateprintservice.print.api.PrintCertificateCategory;
import se.inera.intyg.certificateprintservice.print.api.PrintCertificateQuestion;
import se.inera.intyg.certificateprintservice.print.api.PrintCertificateRequest;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueText;

@Service
@Slf4j
@RequiredArgsConstructor
public class CertificatePrintGenerator implements PrintCertificateGenerator {

  private final GeneratorService generatorService;

  @Value("classpath:templates/certificateTemplate.html")
  private Resource template;

  @Override
  public byte[] generate(PrintCertificateRequest request) {
    String jsoupContent = null;
    try {
      var doc = Jsoup.parse(template.getFile(), "UTF-8", "", Parser.xmlParser());
      Element title = doc.getElementById("title");
      title.appendText(request.getMetadata().getName());
      Element content = doc.getElementById("content");
      request.getCategories().forEach(category -> content.appendChild(
          convertCategory(category, request.getCategories().indexOf(category))));

      log.info(doc.html());
      jsoupContent = doc.html();
    } catch (Exception e) {
      throw new RuntimeException("Could not build html from certificate config", e);
    }

    try (
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(true));
        BrowserContext context = browser.newContext();
        Page page = context.newPage()
    ) {
      final var pdfOptions = new PdfOptions();
      pdfOptions.setFormat("A4");
      pdfOptions.setPrintBackground(true);
      pdfOptions.setDisplayHeaderFooter(true);
      pdfOptions.setTagged(true);
      page.setContent(jsoupContent);
      return page.pdf(pdfOptions);
    }

//      page.setContent(
//          """
//              <!DOCTYPE html>
//                      <html lang="sv">
//                      <head>
//                          <meta charset="UTF-8">
//                          <title>Läkarintyg för högre körkortsbehörighet</title>
//                      </head>
//                      <body>
//                          <h2>Transportstyrelsens läkarintyg högre körkortsbehörighet (TSTRK1007 v7.0)</h1>
//                          <p><strong>Detta är en utskrift av ett elektroniskt intygsutkast och ska INTE skickas till Transportstyrelsen.</strong></p>
//                          <h3>Hörsel och balanssinne</h3>
//                          <h4>Har patienten överraskande anfall av balansrubbningar eller yrsel?</h4>
//                          <p>Ej angivet</p>
//                          <h4>Har patienten svårt att uppfatta vanlig samtalsstämma på fyra meters avstånd (hörapparat får användas)?</h4>
//                          <p>Ej angivet</p>
//                          <h3>Rörelseorganens funktioner</h2>
//                          <h4>Har patienten någon sjukdom eller funktionsnedsättning som påverkar rörligheten och som medför att fordon inte kan köras på ett trafiksäkert sätt?</h4>
//                          <p>Ej angivet</p>
//                          <h4>Ange vilken typ av nedsättning eller sjukdom:</h3>
//                          <p>Ej angivet</p>
//                          <h4>Finns en nedsättning av rörelseförmågan som gör att patienten inte kan hjälpa passagerare in och ut ur fordonet samt med bilbälte?</h4>
//                          <p>Ej angivet</p>
//                          <h3>Hjärt- och kärlsjukdomar</h3>
//                          <h4>Föreligger hjärt- eller kärlsjukdom som kan medföra en påtaglig risk för att hjärnans funktioner akut försämras eller som i övrigt innebär en trafiksäkerhetsrisk?</h4>
//                          <p>Ej angivet</p>
//                          <h4>Finns tecken på hjärnskada efter trauma, stroke eller annan sjukdom i centrala nervsystemet?</h4>
//                          <p>Ej angivet</p>
//                          <h4>Föreligger viktiga riskfaktorer för stroke (tidigare stroke eller TIA, förhöjt blodtryck, förmaksflimmer eller kärlmissbildning)?</h4>
//                          <p>Ej angivet</p>
//                          <h3>Diabetes</h3>
//                          <h4>Har patienten diabetes?</h4>
//                          <p>Ej angivet</p>
//                          <footer>
//                              <p>Utskriften skapades med Webcert - en tjänst som drivs av Inera AB</p>
//                          </footer>
//                      </body>
//                      </html>
//              """
//      );
//
//      return page.pdf();
//    }
//    }
  }

  private Node convertCategory(PrintCertificateCategory category, int index) {
    final var child = new Element(Tag.H2.toString());
    child.text("%d %s".formatted(index + 1, category.getName()));
    category.getQuestions().forEach(question -> child.appendChildren(convertQuestion(question)));
    return child;
  }

  private List<Node> convertQuestion(PrintCertificateQuestion question) {
    var list = new ArrayList<Node>();
    final var name = new Element(Tag.H3.toString());
    name.text("%s".formatted(question.getName()));
    list.add(name);
    final var value = new Element(Tag.P.toString());
    value.appendText(((ElementValueText) question.getValue()).getText());
    question.getSubQuestions()
        .forEach(subQuestion -> list.addAll(convertQuestion(subQuestion)));
    return list;
  }
}