package se.inera.intyg.certificateprintservice.application.print.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateQuestionDTO;
import se.inera.intyg.certificateprintservice.print.api.PrintCertificateQuestion;

@Component
public class PrintCertificateQuestionConverter {

  public PrintCertificateQuestion convert(PrintCertificateQuestionDTO question) {
    return PrintCertificateQuestion.builder()
        .build();
  }
}