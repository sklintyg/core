package se.inera.intyg.certificateprintservice.print.converter;

import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.print.ElementProvider;
import se.inera.intyg.certificateprintservice.print.TextFactory;
import se.inera.intyg.certificateprintservice.print.api.Metadata;
import se.inera.intyg.certificateprintservice.print.element.InformationElementFactory;

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
    final var headerElement = ElementProvider.headerWrapper();
    final var certificateHeader = ElementProvider.certificateHeader(TextFactory.title(metadata));
    final var pageHeader = ElementProvider.pageHeader(metadata.getRecipientLogo());

    if (includeInformation) {
      pageHeader.appendChild(InformationElementFactory.personId(metadata.getPersonId()));
      certificateHeader.appendChild(
          InformationElementFactory.alert(TextFactory.alert(metadata)));
    }

    headerElement.appendChild(pageHeader);
    headerElement.appendChild(certificateHeader);

    return headerElement;
  }
}
