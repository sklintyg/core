package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidation;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationError;

@Value
@Builder
public class ElementSpecification {

  ElementId id;
  @Builder.Default
  boolean includeWhenRenewing = Boolean.TRUE;
  ElementConfiguration configuration;
  @Builder.Default
  List<ElementRule> rules = Collections.emptyList();
  @Builder.Default
  List<ElementValidation> validations = Collections.emptyList();
  ElementMapping mapping;
  PdfConfiguration pdfConfiguration;
  @Builder.Default
  List<ElementSpecification> children = Collections.emptyList();
  Predicate<List<ElementData>> shouldValidate;
  @Builder.Default
  boolean includeInXml = Boolean.TRUE;
  @Builder.Default
  boolean includeForCitizen = Boolean.TRUE;
  ElementVisibilityConfiguration visibilityConfiguration;

  public boolean exists(ElementId id) {
    if (id().equals(id)) {
      return true;
    }
    if (children == null) {
      return false;
    }
    return children.stream()
        .anyMatch(elementSpecification -> elementSpecification.exists(id));
  }

  public ElementSpecification elementSpecification(ElementId id) {
    if (id().equals(id)) {
      return this;
    }
    if (children == null) {
      throw new IllegalArgumentException(
          "No element with id '%s' exists within '%s'".formatted(id, id())
      );
    }
    return children.stream()
        .filter(elementSpecification -> elementSpecification.exists(id))
        .map(elementSpecification -> elementSpecification.elementSpecification(id))
        .findAny()
        .orElseThrow(
            () -> new IllegalArgumentException(
                "No element with id '%s' exists within '%s'".formatted(id, id())
            )
        );
  }

  public Stream<ElementSpecification> flatten() {
    return Stream.concat(
        Stream.of(this),
        children.stream()
            .flatMap(ElementSpecification::flatten)
    );
  }

  public List<ValidationError> validate(List<ElementData> elementData,
      Optional<ElementId> categoryId) {
    if (shouldValidate != null && shouldValidate.negate().test(elementData)) {
      return Collections.emptyList();
    }

    final var validationErrors = validations.stream()
        .map(
            validation -> validation.validate(dataForElement(elementData), categoryId, elementData))
        .flatMap(List::stream);

    final var childrenValidationErrors = children.stream()
        .map(child -> child.validate(elementData, categoryForElement(categoryId)))
        .flatMap(List::stream);

    return Stream.concat(validationErrors, childrenValidationErrors).toList();
  }


  private Optional<ElementId> categoryForElement(Optional<ElementId> categoryId) {
    if (ElementType.CATEGORY.equals(configuration.type())) {
      return Optional.of(id);
    }
    return categoryId;
  }

  private ElementData dataForElement(List<ElementData> elementData) {
    return elementData.stream()
        .filter(data -> id.equals(data.id()))
        .findAny()
        .map(this::elementData)
        .orElse(
            ElementData.builder()
                .id(id)
                .value(configuration().emptyValue())
                .build()
        );
  }

  private ElementData elementData(ElementData data) {
    if (data.value() == null) {
      return data.withValue(
          configuration().emptyValue()
      );
    }
    return data;
  }

  public Optional<ElementMapping> getMapping() {
    return Optional.ofNullable(mapping);
  }

  public Optional<ElementVisibilityConfiguration> getVisibilityConfiguration() {
    return Optional.ofNullable(visibilityConfiguration);
  }

  public Optional<ElementSimplifiedValue> simplifiedValue(Optional<ElementData> elementData,
      List<ElementData> allElementData, PdfGeneratorOptions pdfGeneratorOptions) {
    if ((pdfConfiguration instanceof CitizenPdfConfiguration hiddenConfig)) {
      final var replacementElementValue = hiddenConfig.replacementElementValue(
          pdfGeneratorOptions,
          allElementData
      );

      if (replacementElementValue.isPresent()) {
        return replacementElementValue;
      }
    }

    if (elementData.isEmpty()) {
      return configuration.simplified(configuration.emptyValue());
    }

    return configuration.simplified(elementData.get().value());
  }
}