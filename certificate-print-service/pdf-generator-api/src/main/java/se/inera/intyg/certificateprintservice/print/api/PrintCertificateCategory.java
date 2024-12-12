package se.inera.intyg.certificateprintservice.print.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateprintservice.print.api.PrintCertificateCategory.PrintCertificateCategoryBuilder;

@Value
@Builder
@JsonDeserialize(builder = PrintCertificateCategoryBuilder.class)
public class PrintCertificateCategory {

  String id;
  String name;
  List<PrintCertificateQuestion> questions;

  public String asHtml() {
    var sb = new StringBuilder();
    sb.append("<h1>").append(name).append("</h1>\n");
    questions.forEach(question -> sb.append(question.asHtml()));
    return sb.toString();
  }

  @JsonPOJOBuilder(withPrefix = "")
  public static class PrintCertificateCategoryBuilder {

  }
}