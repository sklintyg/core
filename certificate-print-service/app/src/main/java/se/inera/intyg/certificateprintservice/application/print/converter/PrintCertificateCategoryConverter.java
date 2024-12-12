package se.inera.intyg.certificateprintservice.application.print.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateCategoryDTO;
import se.inera.intyg.certificateprintservice.print.api.PrintCertificateCategory;

@Component
@RequiredArgsConstructor
public class PrintCertificateCategoryConverter {

  private final PrintCertificateQuestionConverter printCertificateQuestionConverter;

  public PrintCertificateCategory convert(PrintCertificateCategoryDTO category) {
    return PrintCertificateCategory.builder()
        .id(category.getId())
        .name(category.getName())
        .questions(category.getQuestions().stream().map(printCertificateQuestionConverter::convert)
            .toList())
        .build();
  }
}