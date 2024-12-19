package se.inera.intyg.certificateprintservice.print;

import static se.inera.intyg.certificateprintservice.print.Constants.RIGHT_MARGIN_INFO_STYLE;
import static se.inera.intyg.certificateprintservice.print.Constants.STYLE;

import java.util.Base64;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;

public class ElementProvider {

  public static Element element(Tag tag) {
    return new Element(tag.toString());
  }

  public static Element recipientLogo(byte[] logoBytes) {
    final var logoWrapper = element(Tag.DIV);
    final var base64 = Base64.getEncoder().encode(logoBytes);
    final var logo = element(Tag.IMG)
        .attr("src", "data:image/png;base64, " + new String(base64))
        .attr("alt", "recipient-logo")
        .attr("style",
            "max-height: 15mm; max-width: 35mm; border: blue solid 1px;");
    logoWrapper.appendChild(logo);
    return logoWrapper;
  }

  public static Element printInfo(String info) {
    final var printInfoWrapper = new Element(Tag.DIV.toString()).attr("style", """
        margin-top: 5mm;
        padding: 3mm 5mm;
        border: red solid 1px;
        """);

    final var printInfo = new Element(Tag.SPAN.toString()).appendText(info);
    printInfoWrapper.appendChild(printInfo);
    return printInfoWrapper;
  }

  public static Element title(String name) {
    final var titleWrapper = new Element(Tag.DIV.toString()).attr("style", """
        font-size: 14pt;
        font-weight: bold;
        border-bottom: black solid 0.5px;
        padding-bottom: 1mm;
        """);

    final var title = element(Tag.SPAN).appendText(name);

    titleWrapper.appendChild(title);
    return titleWrapper;
  }

  public static Element personId(String personId) {
    final var personIdWrapper = element(Tag.DIV).attr("style", "width: 100%");

    final var div = element(Tag.DIV);
    div.attr("style", "border: red solid 1px; float: right; text-align: right;");

    div.appendChild(element(Tag.SPAN).attr("style", "font-weight: bold;")
        .appendText("Person- /samordningsnr"));
    div.appendChild(element(Tag.BR));
    div.appendChild(element(Tag.SPAN).appendText(personId));

    personIdWrapper.appendChild(div);
    return personIdWrapper;
  }

  public static Element leftMarginInfo(String info) {
    final var leftMarginInfoWrapper = element(Tag.DIV)
        .attr(STYLE, Constants.LEFT_MARGIN_INFO_STYLE);

    final var leftMarginInfo = new Element(Tag.SPAN.toString())
        .text(info);

    leftMarginInfoWrapper.appendChild(leftMarginInfo);
    return leftMarginInfoWrapper;
  }

  public static Element pageHeader(byte[] logo) {

    final var pageHeader = new Element(Tag.DIV.toString()).attr("style", """
          margin: 10mm 20mm 10mm 20mm;
          display: flex;
          border: green solid 1px;
        """);
    pageHeader.appendChild(recipientLogo(logo));

    return pageHeader;
  }

  public static Element headerWrapper() {
    return element(Tag.DIV)
        .attr("style", "display: grid; width: 100%; font-size: 10pt;")
        .attr("title", "headerElement");

  }

  public static Element certificateHeader(String certificateTitle) {
    final var certificateHeader = new Element(Tag.DIV.toString()).attr("style",
        "margin: 0 20mm 10mm 20mm;");
    certificateHeader.appendChild(title(certificateTitle));
    return certificateHeader;
  }

  public static Element draftWatermark() {
    final var watermark = element(Tag.DIV).attr("style", """
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translateX(-50%) translateY(-50%) rotate(315deg);
        font-size: 100pt;
        color: rgb(128, 128, 128);
        opacity: 0.5;
        z-index: -1;
        """
    );

    watermark.text("UTKAST");
    return watermark;
  }

  public static Element sent(String certificateId) {
    final var rightMarginInfoWrapper = element(Tag.DIV)
        .attr(STYLE, RIGHT_MARGIN_INFO_STYLE);

    final var rightMarginInfo = element(Tag.SPAN)
        .text("Intygs-ID: %s".formatted(certificateId));

    rightMarginInfoWrapper.appendChild(rightMarginInfo);
    return rightMarginInfoWrapper;
  }
}
