package se.inera.intyg.certificateprintservice.application.print.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateCategory.PrintCertificateCategoryBuilder;

@Value
@Builder
@JsonDeserialize(builder = PrintCertificateCategoryBuilder.class)
public class PrintCertificateCategory {

  String id;
  String name;
  List<PrintCertificateQuestion> children;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PrintCertificateCategoryBuilder {

  }
}