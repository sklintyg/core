package se.inera.intyg.certificateprintservice.playwright.certificate;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Question;

public class QuestionConverter {

  private QuestionConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static List<Node> question(Question question) {
    final var list = new ArrayList<Node>();
    final var name = new Element(Tag.H3.toString());
    name.addClass("p-1");
    name.text("%s".formatted(question.getName()));
    list.add(name);
    list.add(ElementValueConverter.html(question.getValue()));

    question.getSubQuestions()
        .forEach(subQuestion -> list.addAll(question(subQuestion)));
    return list;
  }
}
