package se.inera.intyg.certificateservice.certificate.converter;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateCategoryDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateQuestionDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Component
public class PrintCertificateCategoryConverter {

  private PrintCertificateQuestionConverter printCertificateQuestionConverter;

  public PrintCertificateCategoryDTO convert(Certificate certificate) {
    return PrintCertificateCategoryDTO.builder()
        .name(certificate.certificateModel().name())
        .id(certificate.id().id())
        .children(convertChildren(certificate))
        .build();
  }

  private List<PrintCertificateQuestionDTO> convertChildren(Certificate certificate) {
    return certificate.certificateModel().elementSpecifications()
        .stream()
        .map(elementSpecification -> printCertificateQuestionConverter.convert(elementSpecification,
            certificate))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }

}
