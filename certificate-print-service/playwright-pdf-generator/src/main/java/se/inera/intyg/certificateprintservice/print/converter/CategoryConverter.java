package se.inera.intyg.certificateprintservice.print.converter;

import static se.inera.intyg.certificateprintservice.print.Constants.STYLE;

import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import se.inera.intyg.certificateprintservice.print.api.Category;

public class CategoryConverter {

  private CategoryConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static Node category(Category category) {
    final var div = new Element(Tag.DIV.toString());
    div.attr(STYLE, "border: 1px solid black;");
    div.addClass("box-decoration-clone");
    final var title = new Element(Tag.H2.toString());
    title.addClass("text-lg font-bold");
    title.attr(STYLE, "border-bottom: 1px solid black;");
    title.text("%s".formatted(category.getName()));

    div.appendChild(title);
    category.getQuestions()
        .forEach(
            question -> div.appendChildren(QuestionConverter.question(question))
        );
    return div;
  }
}
