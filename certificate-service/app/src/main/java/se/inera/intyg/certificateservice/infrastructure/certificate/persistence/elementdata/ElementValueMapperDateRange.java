package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class ElementValueMapperDateRange implements ElementValueMapper {

  @Override
  public boolean supports(Class<?> c) {
    return c.equals(MappedElementValueDateRange.class)
        || c.equals(ElementValueDateRange.class);
  }

  @Override
  public ElementValue toDomain(MappedElementValue mappedValue) {
    if (mappedValue instanceof MappedElementValueDateRange valueDateRange) {
      return ElementValueDateRange.builder()
          .id(new FieldId(valueDateRange.getDateId()))
          .fromDate(valueDateRange.getFromDate())
          .toDate(valueDateRange.getToDate())
          .build();
    }
    throw new IllegalStateException("MappedElementValue not supported '%s'".formatted(mappedValue));
  }

  @Override
  public MappedElementValue toMapped(ElementValue value) {
    if (value instanceof ElementValueDateRange elementValueDateRange) {
      return MappedElementValueDateRange.builder()
          .dateId(elementValueDateRange.id().value())
          .fromDate(elementValueDateRange.fromDate())
          .toDate(elementValueDateRange.toDate())
          .build();
    }
    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
  }
}
