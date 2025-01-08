package se.inera.intyg.certificateprintservice.playwright.certificate;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;

import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Category;

public class CategoryConverter {

  private CategoryConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static Element category(Category category) {
    final var div = new Element(Tag.DIV.toString())
        .addClass("box-decoration-clone")
        .attr(STYLE, "border: 1px solid black; margin-bottom: 5mm; padding-bottom: 3mm;");

    final var title = new Element(Tag.H2.toString())
        .addClass("text-base font-medium uppercase")
        .attr(STYLE,
            "border-bottom: 1px solid black; padding-left: 5mm; padding-right: 5mm;")
        .text(category.getName());

    div.appendChild(title);
    category.getQuestions()
        .forEach(
            question -> div.appendChildren(QuestionConverter.question(question, false))
        );
    return div;
  }
}
