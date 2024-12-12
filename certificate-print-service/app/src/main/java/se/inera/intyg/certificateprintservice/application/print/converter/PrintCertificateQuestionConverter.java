package se.inera.intyg.certificateprintservice.application.print.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateQuestionDTO;
import se.inera.intyg.certificateprintservice.application.print.dto.value.ElementSimplifiedValueList;
import se.inera.intyg.certificateprintservice.application.print.dto.value.ElementSimplifiedValueText;
import se.inera.intyg.certificateprintservice.print.api.PrintCertificateQuestion;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValue;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueList;
import se.inera.intyg.certificateprintservice.print.api.value.ElementValueText;

@Component
public class PrintCertificateQuestionConverter {

  public PrintCertificateQuestion convert(PrintCertificateQuestionDTO question) {
    return PrintCertificateQuestion.builder()
        .id(question.getId())
        .name(question.getName())
        .value(getElementValue(question))
        .subQuestions(question.getSubQuestions().stream()
            .map(this::convert)
            .toList())
        .build();
  }

  private static ElementValue getElementValue(PrintCertificateQuestionDTO question) {
    if (question.getValue() instanceof ElementSimplifiedValueText text) {
      return ElementValueText.builder()
          .text(text.getText())
          .build();
    } else if (question.getValue() instanceof ElementSimplifiedValueList list) {
      return ElementValueList.builder()
          .list(list.getList())
          .build();
    }

    throw new IllegalArgumentException("Illegal value type");
  }
}
