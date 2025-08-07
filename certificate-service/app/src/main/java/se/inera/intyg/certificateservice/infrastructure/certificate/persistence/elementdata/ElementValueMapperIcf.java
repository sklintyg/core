package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIcfValue;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class ElementValueMapperIcf implements ElementValueMapper {

  @Override
  public boolean supports(Class<?> c) {
    return c.equals(MappedElementValueIcfValue.class)
        || c.equals(ElementValueIcfValue.class);
  }

  @Override
  public ElementValue toDomain(MappedElementValue mappedValue) {
    if (mappedValue instanceof MappedElementValueIcfValue valueIcf) {
      return ElementValueIcfValue.builder()
          .id(new FieldId(valueIcf.getId()))
          .icfCodes(valueIcf.getIcfCodes())
          .build();

    }
    throw new IllegalStateException("MappedElementValue not supported '%s'".formatted(mappedValue));
  }

  @Override
  public MappedElementValue toMapped(ElementValue value) {
    if (value instanceof ElementValueIcfValue elementValueIcfValue) {
      return MappedElementValueIcfValue.builder()
          .id(elementValueIcfValue.id().value())
          .icfCodes(elementValueIcfValue.icfCodes())
          .build();
    }
    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
  }
}
