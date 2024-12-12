package se.inera.intyg.certificateprintservice.print.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateprintservice.application.print.dto.value.ElementSimplifiedValue;
import se.inera.intyg.certificateprintservice.print.api.PrintCertificateQuestion.PrintCertificateQuestionBuilder;

@Value
@Builder
@JsonDeserialize(builder = PrintCertificateQuestionBuilder.class)
public class PrintCertificateQuestion {

  String id;
  String name;
  ElementSimplifiedValue value;
  List<PrintCertificateQuestion> subQuestions;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PrintCertificateQuestionBuilder {

  }
}