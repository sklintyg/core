package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType.ISSUING_UNIT;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@Component
@RequiredArgsConstructor
public class CertificateDataConverter {

  private final List<CertificateDataConfigConverter> certificateDataConfigConverters;
  private final List<CertificateDataValidationConverter> certificateDataValidationConverters;

  private final CertificateDataValueConverter certificateDataValueConverter;

  public Map<String, CertificateDataElement> convert(CertificateModel certificateModel,
      List<ElementData> elementData) {
    final var childParentMap = certificateModel.elementSpecifications().stream()
        .flatMap(this::mapChildrenToParents)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    final var elementIdElementValueMap = elementData.stream()
        .collect(Collectors.toMap(ElementData::id, ElementData::value));

    final var elementSpecifications = certificateModel.elementSpecifications().stream()
        .filter(removeIssuingUnitSpecifications())
        .toList();

    final var atomicInteger = new AtomicInteger(0);

    final var certificateDataElementStream = toCertificateDataElementMap(
        elementSpecifications,
        elementIdElementValueMap,
        atomicInteger,
        childParentMap
    );

    return collectStreamOfCertificateDataElementsToMap(certificateDataElementStream);
  }


  private Map<String, CertificateDataElement> convertData(
      Map<ElementId, ElementValue> elementIdElementValueMap,
      ElementSpecification elementSpecification, AtomicInteger atomicInteger,
      Map<String, String> childParentMap) {
    final var certificateDataElementHashMap = new HashMap<String, CertificateDataElement>();
    final var questionId = elementSpecification.id().id();
    final var value = elementIdElementValueMap.getOrDefault(elementSpecification.id(), null);
    final var index = atomicInteger.incrementAndGet();

    final var children = toCertificateDataElementMap(
        elementSpecification.children(),
        elementIdElementValueMap,
        atomicInteger,
        childParentMap
    );

    final var certificateDataElement = CertificateDataElement.builder()
        .id(questionId)
        .parent(childParentMap.getOrDefault(questionId, null))
        .index(index)
        .config(getConfig(elementSpecification))
        .value(certificateDataValueConverter.convert(elementSpecification, value))
        .validation(getValidation(elementSpecification.rules()))
        .build();

    certificateDataElementHashMap.put(questionId, certificateDataElement);
    certificateDataElementHashMap.putAll(collectStreamOfCertificateDataElementsToMap(children));

    return certificateDataElementHashMap;
  }

  private CertificateDataValidation[] getValidation(List<ElementRule> rules) {
    if (rules == null) {
      return new CertificateDataValidation[0];
    }

    return rules.stream()
        .map(this::getConvertedValidation)
        .toArray(CertificateDataValidation[]::new);
  }

  private CertificateDataValidation getConvertedValidation(ElementRule rule) {
    return certificateDataValidationConverters.stream()
        .filter(
            converter -> converter.getType().equals(rule.type())
        )
        .findFirst()
        .map(converter -> converter.convert(rule))
        .orElseThrow(() -> new IllegalStateException(
                "Could not find converter for rule type '%s'".formatted(
                    rule.type()
                )
            )
        );
  }

  private CertificateDataConfig getConfig(ElementSpecification elementSpecification) {
    if (elementSpecification.configuration() == null) {
      return null;
    }

    return certificateDataConfigConverters.stream()
        .filter(
            converter -> converter.getType().equals(elementSpecification.configuration().type())
        )
        .findFirst()
        .map(converter -> converter.convert(elementSpecification))
        .orElseThrow(() -> new IllegalStateException(
                "Could not find converter for type '%s'".formatted(
                    elementSpecification.configuration().type()
                )
            )
        );
  }

  private static Map<String, CertificateDataElement> collectStreamOfCertificateDataElementsToMap(
      Stream<Set<Entry<String, CertificateDataElement>>> stream) {
    return stream.flatMap(Set::stream).collect(Collectors.toMap(
        Entry::getKey,
        Entry::getValue
    ));
  }

  private Stream<Set<Entry<String, CertificateDataElement>>> toCertificateDataElementMap(
      List<ElementSpecification> elementSpecification,
      Map<ElementId, ElementValue> elementIdElementValueMap, AtomicInteger atomicInteger,
      Map<String, String> childParentMap) {
    return elementSpecification.stream()
        .flatMap(
            specification -> {
              final var dataElementMap = convertData(
                  elementIdElementValueMap,
                  specification,
                  atomicInteger,
                  childParentMap
              );
              return Stream.of(dataElementMap.entrySet());
            }
        );
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

  private static Predicate<ElementSpecification> removeIssuingUnitSpecifications() {
    return specification -> !(specification.configuration().type().equals(ISSUING_UNIT));
  }
}
