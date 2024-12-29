package se.inera.intyg.certificateprintservice.playwright.certificate;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Question;

public class QuestionConverter {

  private QuestionConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static List<Element> question(Question question) {
    final var name = new Element(Tag.H3.toString())
        .addClass("p-1")
        .text(question.getName());

    final var list = new ArrayList<Element>();
    list.add(name);
    list.add(ElementValueConverter.html(question.getValue()));

    question.getSubQuestions()
        .forEach(subQuestion -> list.addAll(question(subQuestion)));
    return list;
  }
}
