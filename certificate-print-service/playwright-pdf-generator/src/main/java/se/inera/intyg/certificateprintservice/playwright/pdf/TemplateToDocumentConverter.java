package se.inera.intyg.certificateprintservice.playwright.pdf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TemplateToDocumentConverter {

//  @Value("classpath:templates/tailwindCSS.js")
//  private Resource tailwindScript;
//
//  private String tailwindCSS;
//
//  @Override
//  public void afterPropertiesSet() throws Exception {
//    tailwindCSS = new String(Base64.getEncoder().encode(tailwindScript.getContentAsByteArray()));
//  }

//  public Document convert(PrintInformation printInformation)
//      throws IOException {
//    final var document = Jsoup.parse(printInformation.getTemplate(), StandardCharsets.UTF_8.name(),
//        "",
//        Parser.xmlParser());
//
//    setDocumentTitle(document, printInformation.getcertificateMetadata());
//    setPageMargin(document, printInformation.getHeaderPage(), printInformation.getHeaderHtml());
//    return document;
//  }

//  public void setDocumentTitle(Document document, Metadata metadata) {
//    final var title = document.getElementById("title");
//    Objects.requireNonNull(title).appendText(TextFactory.title(metadata));
//  }

//  public String getScriptSource() {
//    return "data:text/javascript;base64, %s".formatted(tailwindCSS);
//  }

//  public void setPageMargin(Document doc, Page page, String header) {
//    final var styleElement = doc.getElementById(STYLE);
//
//    if (styleElement == null) {
//      throw new IllegalStateException("Style element is null");
//    }
//
//    final var headerHeight = calculateHeaderHeight(page, header);
//    styleElement.appendText("""
//        @page {
//          margin: calc(%spx + 15mm) 20mm 40mm 20mm;
//        }""".formatted(headerHeight));
//  }
//
//  public int calculateHeaderHeight(Page page, String header, String name) {
//    page.setContent(header);
//    return (int) Math.round(page.getByTitle(name).boundingBox().height);
//    //return (int) page.getByTitle("headerElement").evaluate("node => node.offsetHeight");
//  }
}
