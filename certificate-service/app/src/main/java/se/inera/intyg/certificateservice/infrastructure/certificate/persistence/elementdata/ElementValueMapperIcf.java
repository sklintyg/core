package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class ElementValueMapperIcf implements ElementValueMapper {

  @Override
  public boolean supports(Class<?> c) {
    return c.equals(MappedElementValueIcf.class)
        || c.equals(ElementValueIcf.class);
  }

  @Override
  public ElementValue toDomain(MappedElementValue mappedValue) {
    if (mappedValue instanceof MappedElementValueIcf valueIcf) {
      return ElementValueIcf.builder()
          .id(new FieldId(valueIcf.getId()))
          .text(valueIcf.getText())
          .icfCodes(valueIcf.getIcfCodes())
          .build();

    }
    throw new IllegalStateException("MappedElementValue not supported '%s'".formatted(mappedValue));
  }

  @Override
  public MappedElementValue toMapped(ElementValue value) {
    if (value instanceof ElementValueIcf elementValueIcf) {
      return MappedElementValueIcf.builder()
          .id(elementValueIcf.id().value())
          .text(elementValueIcf.text())
          .icfCodes(elementValueIcf.icfCodes())
          .build();
    }
    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
  }
}