package se.inera.intyg.certificateprintservice.application.print.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateCategoryDTO;
import se.inera.intyg.certificateprintservice.print.api.PrintCertificateCategory;

@Component
public class PrintCertificateCategoryConverter {

  PrintCertificateQuestionConverter printCertificateQuestionConverter;

  public PrintCertificateCategory convert(PrintCertificateCategoryDTO category) {
    return PrintCertificateCategory.builder()
        .id(category.getId())
        .name(category.getName())
        .children(category.getChildren().stream().map(printCertificateQuestionConverter::convert)
            .toList())
        .build();
  }
}