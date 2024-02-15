package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

public class ElementDataMapper {

  private ElementDataMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementData toDomain(MappedElementData mappedElement) {
    return ElementData.builder()
        .id(new ElementId(mappedElement.getId()))
        .value(ElementValueMapper.toDomain(mappedElement.getValue()))
        .build();
  }

  public static MappedElementData toMapped(ElementData element) {
    return MappedElementData.builder()
        .id(element.id().id())
        .value(ElementValueMapper.toMapped(element.value()))
        .build();
  }
}
