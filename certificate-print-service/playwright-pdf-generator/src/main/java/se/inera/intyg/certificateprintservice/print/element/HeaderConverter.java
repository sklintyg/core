package se.inera.intyg.certificateprintservice.print.element;

import static se.inera.intyg.certificateprintservice.print.Constants.STYLE;

import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.print.api.Metadata;
import se.inera.intyg.certificateprintservice.print.text.TextFactory;

public class HeaderConverter {

  private HeaderConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static String header(Metadata metadata, boolean includeInformation) {
    final var baseWrapper = ElementProvider.element(Tag.DIV);

    if (includeInformation && metadata.isSent()) {
      baseWrapper.appendChild(InformationElementFactory.rightMargin(
              TextFactory.certificateId(metadata.getCertificateId())
          )
      );
    }

    baseWrapper.appendChild(buildHeaderElement(metadata, includeInformation));
    baseWrapper.appendChild(
        InformationElementFactory.leftMargin(TextFactory.margin(metadata))
    );

    if (metadata.isDraft()) {
      baseWrapper.appendChild(InformationElementFactory.watermark(TextFactory.draft()));
    }

    return baseWrapper.html();
  }

  private static Element buildHeaderElement(Metadata metadata, boolean includeInformation) {
    final var headerElement = headerWrapper();
    final var certificateHeader = certificateHeader(TextFactory.title(metadata));
    final var pageHeader = pageHeader(metadata.getRecipientLogo());

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
        .attr(STYLE, "display: grid; width: 100%; font-size: 10pt;")
        .attr("title", "headerElement");

  }

  private static Element certificateHeader(String certificateTitle) {
    final var certificateHeader = new Element(Tag.DIV.toString()).attr(STYLE,
        "margin: 0 20mm 10mm 20mm;");
    certificateHeader.appendChild(InformationElementFactory.title(certificateTitle));
    return certificateHeader;
  }

  private static Element pageHeader(byte[] logo) {

    final var pageHeader = new Element(Tag.DIV.toString()).attr(STYLE, """
          margin: 10mm 20mm 10mm 20mm;
          display: flex;
          border: green solid 1px;
        """);
    pageHeader.appendChild(ElementProvider.logo(logo));

    return pageHeader;
  }
}
