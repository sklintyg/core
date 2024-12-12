package se.inera.intyg.certificateprintservice.print;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;

@Service
public class CertificatePrintGenerator implements PrintCertificateGenerator {

  @Override
  public byte[] generate() {
    try (
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(true));
        BrowserContext context = browser.newContext();
        Page page = context.newPage()
    ) {
      page.setContent(
          """
              <!DOCTYPE html>
                      <html lang="sv">
                      <head>
                          <meta charset="UTF-8">
                          <title>Läkarintyg för högre körkortsbehörighet</title>
                      </head>
                      <body>
                          <h2>Transportstyrelsens läkarintyg högre körkortsbehörighet (TSTRK1007 v7.0)</h1>
                          <p><strong>Detta är en utskrift av ett elektroniskt intygsutkast och ska INTE skickas till Transportstyrelsen.</strong></p>
                          <h3>Hörsel och balanssinne</h3>
                          <h4>Har patienten överraskande anfall av balansrubbningar eller yrsel?</h4> 
                          <p>Ej angivet</p>
                          <h4>Har patienten svårt att uppfatta vanlig samtalsstämma på fyra meters avstånd (hörapparat får användas)?</h4>
                          <p>Ej angivet</p>
                          <h3>Rörelseorganens funktioner</h2>
                          <h4>Har patienten någon sjukdom eller funktionsnedsättning som påverkar rörligheten och som medför att fordon inte kan köras på ett trafiksäkert sätt?</h4> 
                          <p>Ej angivet</p>
                          <h4>Ange vilken typ av nedsättning eller sjukdom:</h3>
                          <p>Ej angivet</p>
                          <h4>Finns en nedsättning av rörelseförmågan som gör att patienten inte kan hjälpa passagerare in och ut ur fordonet samt med bilbälte?</h4> 
                          <p>Ej angivet</p>
                          <h3>Hjärt- och kärlsjukdomar</h3>
                          <h4>Föreligger hjärt- eller kärlsjukdom som kan medföra en påtaglig risk för att hjärnans funktioner akut försämras eller som i övrigt innebär en trafiksäkerhetsrisk?</h4>
                          <p>Ej angivet</p>
                          <h4>Finns tecken på hjärnskada efter trauma, stroke eller annan sjukdom i centrala nervsystemet?</h4> 
                          <p>Ej angivet</p>
                          <h4>Föreligger viktiga riskfaktorer för stroke (tidigare stroke eller TIA, förhöjt blodtryck, förmaksflimmer eller kärlmissbildning)?</h4>
                          <p>Ej angivet</p>
                          <h3>Diabetes</h3>
                          <h4>Har patienten diabetes?</h4> 
                          <p>Ej angivet</p>
                          <footer>
                              <p>Utskriften skapades med Webcert - en tjänst som drivs av Inera AB</p>
                          </footer>
                      </body>
                      </html>
              """
      );

      Path outputPath = Paths.get("C:/output.pdf");
      page.pdf(new Page.PdfOptions().setPath(outputPath).setScale(1));

      return page.pdf();
    }
  }
}