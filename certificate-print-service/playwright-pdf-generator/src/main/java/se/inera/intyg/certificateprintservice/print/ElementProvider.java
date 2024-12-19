package se.inera.intyg.certificateprintservice.print;

import static se.inera.intyg.certificateprintservice.print.Constants.STYLE;

import java.util.Base64;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

@Component
public class ElementProvider {


  public Element element(Tag tag) {
    return new Element(tag.toString());
  }

  public Element recipientLogo(byte[] logoBytes) {
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

  public Element printInfo(String info) {
    final var printInfoWrapper = new Element(Tag.DIV.toString()).attr("style", """
        margin-top: 5mm;
        padding: 3mm 5mm;
        border: red solid 1px;
        """);

    final var printInfo = new Element(Tag.SPAN.toString()).appendText(info);
    printInfoWrapper.appendChild(printInfo);
    return printInfoWrapper;
  }

  public Element title(String name) {
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

  public Element personId(String personId) {
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


  public Element leftMarginInfo(String info) {
    final var leftMarginInfoWrapper = element(Tag.DIV)
        .attr(STYLE, Constants.LEFT_MARGIN_INFO_STYLE);

    final var leftMarginInfo = new Element(Tag.SPAN.toString())
        .text(info);

    leftMarginInfoWrapper.appendChild(leftMarginInfo);
    return leftMarginInfoWrapper;
  }
}
