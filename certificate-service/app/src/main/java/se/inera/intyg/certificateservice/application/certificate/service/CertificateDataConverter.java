package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@Component
@RequiredArgsConstructor
public class CertificateDataConverter {

  private final CertificateDataValueConverter certificateDataValueConverter;
  private final CertificateDataConfigConverter certificateDataConfigConverter;
  private final CertificateDataValidationConverter certificateDataValidationConverter;

  public Map<String, CertificateDataElement> convert(CertificateModel certificateModel,
      List<ElementData> elementData) {
    final var index = new AtomicInteger(0);
    return elementData.stream()
        .map(data -> updateData(data, certificateModel.elementSpecification(data.id()),
            index.incrementAndGet()))
        .reduce(new HashMap<>(), (map1, map2) -> {
              map1.putAll(map2);
              return map1;
            }
        );
  }

  private Map<String, CertificateDataElement> updateData(ElementData elementData,
      ElementSpecification elementSpecification, int index) {

    final var questionId = elementData.id().id();
    return Map.of(
        questionId,
        CertificateDataElement.builder()
            .id(questionId)
            .index(index)
            .config(certificateDataConfigConverter.convert(elementSpecification))
            .value(certificateDataValueConverter.convert(elementData))
            .validation(certificateDataValidationConverter.convert(elementSpecification.rules()))
            .build()
    );
  }
}
