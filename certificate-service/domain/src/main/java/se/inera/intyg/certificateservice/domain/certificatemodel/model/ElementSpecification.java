package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ElementSpecification {

  ElementId id;
  ElementConfiguration configuration;
  @Builder.Default
  List<ElementRule> rules = List.of();
  @Builder.Default
  List<ElementValidation> validations = List.of();
  @Builder.Default
  List<ElementSpecification> children = List.of();

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
}
