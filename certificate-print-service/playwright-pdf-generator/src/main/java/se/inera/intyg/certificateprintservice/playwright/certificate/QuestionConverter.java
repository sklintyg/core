package se.inera.intyg.certificateprintservice.playwright.certificate;

import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.html.HTML.Tag;
import org.jsoup.nodes.Element;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Question;

public class QuestionConverter {

  private QuestionConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static List<Element> question(Question question, boolean isSubQuestion) {
    final var name = new Element(Tag.H3.toString())
        .addClass("text-sm font-medium")
        .text(question.getName());

    if (isSubQuestion) {
      name.attr(STYLE, "padding-top: 1mm; padding-left: 5mm; padding-right: 5mm; color: #6A6A6A");
    } else {
      name.attr(STYLE, "padding-top: 1mm; padding-left: 5mm; padding-right: 5mm;");
    }

    final var list = new ArrayList<Element>();
    list.add(name);
    list.add(ElementValueConverter.html(question.getValue()));

    question.getSubQuestions()
        .forEach(subQuestion -> list.addAll(question(subQuestion, true)));
    return list;
  }
}
