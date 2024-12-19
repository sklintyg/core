package se.inera.intyg.certificateprintservice.print.converter;

import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.print.ElementProvider;
import se.inera.intyg.certificateprintservice.print.TextFactory;
import se.inera.intyg.certificateprintservice.print.api.Metadata;

public class HeaderConverter {

  private HeaderConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static String header(Metadata metadata, boolean includeInformation) {
    final var baseWrapper = ElementProvider.element(Tag.DIV);

    if (includeInformation && metadata.isSent()) {
      baseWrapper.appendChild(ElementProvider.sent(metadata.getCertificateId()));
    }

    baseWrapper.appendChild(buildHeaderElement(metadata, includeInformation));
    baseWrapper.appendChild(
        ElementProvider.leftMarginInfo(TextFactory.margin(metadata))
    );

    if (metadata.isDraft()) {
      baseWrapper.appendChild(ElementProvider.draftWatermark());
    }

    return baseWrapper.html();
  }

  private static Element buildHeaderElement(Metadata metadata, boolean includeInformation) {
    final var headerElement = ElementProvider.headerWrapper();
    final var certificateHeader = ElementProvider.certificateHeader(TextFactory.title(metadata));
    final var pageHeader = ElementProvider.pageHeader(metadata.getRecipientLogo());

    if (includeInformation) {
      pageHeader.appendChild(ElementProvider.personId(metadata.getPersonId()));
      certificateHeader.appendChild(ElementProvider.printInfo(TextFactory.information(metadata)));
    }

    headerElement.appendChild(pageHeader);
    headerElement.appendChild(certificateHeader);

    return headerElement;
  }
}
