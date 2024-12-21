package se.inera.intyg.certificateprintservice.playwright.element;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;

import java.io.IOException;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

public class HeaderConverter {

  private HeaderConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static String header(Metadata metadata, boolean includeInformation) {
    final var baseWrapper = ElementProvider.element(Tag.DIV);

    if (includeInformation && metadata.getSigningDate() != null) {
      baseWrapper.appendChild(InformationElementFactory.rightMargin(
              TextFactory.certificateId(metadata.getCertificateId())
          )
      );
    }

    baseWrapper.appendChild(buildHeaderElement(metadata, includeInformation));
    baseWrapper.appendChild(
        InformationElementFactory.leftMargin(TextFactory.margin(metadata)));

    if (metadata.isDraft()) {
      baseWrapper.appendChild(InformationElementFactory.watermark(TextFactory.draft()));
    }

    return baseWrapper.html();
  }

  private static Element buildHeaderElement(Metadata metadata, boolean includeInformation) {
    final var headerElement = headerWrapper();
    final var certificateHeader = certificateHeader(TextFactory.title(metadata));

    final byte[] logoBytes;
    try {
      logoBytes = ClassLoader.getSystemResourceAsStream("transportstyrelsen-logo.png")
          .readAllBytes();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    final var pageHeader = pageHeader(logoBytes);
    //final var pageHeader = pageHeader(metadata.getRecipientLogo());

    if (includeInformation) {
      pageHeader.appendChild(InformationElementFactory.personId(metadata.getPersonId()));
      certificateHeader.appendChild(
          InformationElementFactory.alert(TextFactory.alert(metadata)));
    }

    headerElement.appendChild(pageHeader);
    headerElement.appendChild(certificateHeader);

    return headerElement;
  }

  private static Element headerWrapper() {
    return ElementProvider.element(Tag.DIV)
        .attr("title", "headerElement")
        .attr(STYLE, """
            display: grid;
            width: 17cm;
            font-size: 10pt;
            margin: 10mm 20mm 0 20mm;
            border: blue solid 2px;
            """);
  }

  private static Element certificateHeader(String certificateTitle) {
    final var certificateHeader = new Element(Tag.DIV.toString())
        .attr(STYLE, "margin-bottom: 5mm;");
    certificateHeader.appendChild(InformationElementFactory.title(certificateTitle));
    return certificateHeader;
  }

  private static Element pageHeader(byte[] logo) {

    final var pageHeader = new Element(Tag.DIV.toString()).attr(STYLE, """
          margin: 0 0 10mm 0;
          display: flex;
          border: green solid 1px;
        """);

    pageHeader.appendChild(ElementProvider.logo(logo));

    return pageHeader;
  }
}
