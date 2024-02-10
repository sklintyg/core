package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
    if (elementData == null) {
      return Collections.emptyMap();
    }
    final var childParentMap = certificateModel.elementSpecifications().stream()
        .flatMap(this::mapChildrenToParents)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    final var index = new AtomicInteger(0);
    final Map<String, CertificateDataElement> convertedData = new HashMap<>();

    elementData.forEach(element -> {
          final var certificateDataElementMap = updateData(
              element,
              certificateModel.elementSpecification(element.id()),
              index.incrementAndGet(),
              childParentMap
          );
          convertedData.putAll(certificateDataElementMap);
        }
    );

    return convertedData;
  }

  private Stream<Map.Entry<String, String>> mapChildrenToParents(ElementSpecification parent) {
    return Optional.ofNullable(parent.children())
        .orElse(Collections.emptyList())
        .stream()
        .flatMap(child -> {
              final var childToParent = Stream.of(
                  new AbstractMap.SimpleEntry<>(child.id().id(), parent.id().id())
              );
              final var subChildren = mapChildrenToParents(child);
              return Stream.concat(childToParent, subChildren);
            }
        );
  }

  private Map<String, CertificateDataElement> updateData(ElementData elementData,
      ElementSpecification elementSpecification, int index,
      Map<String, String> childParentMap) {
    final var questionId = elementData.id().id();
    return Map.of(
        questionId,
        CertificateDataElement.builder()
            .id(questionId)
            .parent(childParentMap.getOrDefault(questionId, null))
            .index(index)
            .config(certificateDataConfigConverter.convert(elementSpecification))
            .value(certificateDataValueConverter.convert(elementData))
            .validation(certificateDataValidationConverter.convert(elementSpecification.rules()))
            .build()
    );
  }
}
