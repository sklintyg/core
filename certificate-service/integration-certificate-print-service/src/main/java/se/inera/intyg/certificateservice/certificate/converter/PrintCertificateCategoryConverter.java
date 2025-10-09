package se.inera.intyg.certificateservice.certificate.converter;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateCategoryDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateQuestionDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@Component
@RequiredArgsConstructor
public class PrintCertificateCategoryConverter {

  private final PrintCertificateQuestionConverter printCertificateQuestionConverter;

  public PrintCertificateCategoryDTO convert(Certificate certificate,
      ElementSpecification category, List<ElementId> hiddenElements) {
    if (!(category.configuration() instanceof ElementConfigurationCategory)) {
      throw new IllegalStateException(
          "Only category can be at top level of element specifications");
    }

    return PrintCertificateCategoryDTO.builder()
        .name(category.configuration().name())
        .id(category.id().id())
        .questions(convertChildren(certificate, category, hiddenElements))
        .build();
  }

  private List<PrintCertificateQuestionDTO> convertChildren(Certificate certificate,
      ElementSpecification category, List<ElementId> hiddenElementIds) {
    return category.children().stream()
        .map(elementSpecification -> printCertificateQuestionConverter.convert(elementSpecification,
            certificate,
            hiddenElementIds)
        )
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }
}
