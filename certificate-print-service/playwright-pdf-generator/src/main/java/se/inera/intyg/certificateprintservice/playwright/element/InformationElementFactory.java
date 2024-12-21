package se.inera.intyg.certificateprintservice.playwright.element;

import static se.inera.intyg.certificateprintservice.playwright.Constants.RIGHT_MARGIN_INFO_STYLE;
import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;

import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.playwright.Constants;

public class InformationElementFactory {

  private InformationElementFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static Element element(Tag tag) {
    return new Element(tag.toString());
  }

  public static Element alert(String info) {
    final var printInfoWrapper = new Element(Tag.DIV.toString())
        .attr(STYLE, """
            margin-top: 5mm;
            padding: 3mm 5mm;
            border: red solid 1px;
            """);

    final var printInfo = new Element(Tag.SPAN.toString()).appendText(info);
    printInfoWrapper.appendChild(printInfo);
    return printInfoWrapper;
  }

  public static Element title(String name) {
    final var titleWrapper = new Element(Tag.DIV.toString())
        .attr(STYLE, """
            font-size: 14pt;
            font-weight: bold;
            padding-bottom: 1mm;
            border-bottom: black solid 1px;
            """);

    final var title = element(Tag.SPAN).appendText(name);
    titleWrapper.appendChild(title);
    return titleWrapper;
  }

  public static Element personId(String personId) {
    final var personIdWrapper = element(Tag.DIV)
        .attr(STYLE, "width: 100%");

    final var div = element(Tag.DIV);
    div.attr(STYLE, "border: red solid 1px; float: right; text-align: right;");

    div.appendChild(element(Tag.SPAN)
        .attr(STYLE, "font-weight: bold;")
        .appendText("Person- /samordningsnr"));
    div.appendChild(element(Tag.BR));
    div.appendChild(element(Tag.SPAN).appendText(personId));

    personIdWrapper.appendChild(div);
    return personIdWrapper;
  }

  public static Element leftMargin(String info) {
    final var leftMarginInfoWrapper = element(Tag.DIV)
        .attr(STYLE, Constants.LEFT_MARGIN_INFO_STYLE);

    final var leftMarginInfo = new Element(Tag.SPAN.toString()).text(info);
    leftMarginInfoWrapper.appendChild(leftMarginInfo);
    return leftMarginInfoWrapper;
  }

  public static Element watermark(String text) {
    final var watermark = element(Tag.DIV)
        .attr(STYLE, """
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translateX(-50%) translateY(-50%) rotate(315deg);
            font-size: 100pt;
            color: rgb(128, 128, 128);
            opacity: 0.5;
            z-index: -1;
            """);

    watermark.text(text);
    return watermark;
  }

  public static Element rightMargin(String text) {
    final var rightMarginInfoWrapper = element(Tag.DIV)
        .attr(STYLE, RIGHT_MARGIN_INFO_STYLE);

    final var rightMarginInfo = element(Tag.SPAN)
        .text(text);

    rightMarginInfoWrapper.appendChild(rightMarginInfo);
    return rightMarginInfoWrapper;
  }
}
