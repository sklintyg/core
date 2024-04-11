package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class ElementValueMapperDateRangeList implements ElementValueMapper {

  @Override
  public boolean supports(Class<?> c) {
    return c.equals(MappedElementValueDateRangeList.class)
        || c.equals(ElementValueDateRangeList.class);
  }

  @Override
  public ElementValue toDomain(MappedElementValue mappedValue) {
    if (mappedValue instanceof MappedElementValueDateRangeList valueDateRangeList) {
      return ElementValueDateRangeList.builder()
          .dateRangeListId(new FieldId(valueDateRangeList.getDateRangeListId()))
          .dateRangeList(
              valueDateRangeList.getDateRangeList().stream()
                  .map(dateRange ->
                      DateRange.builder()
                          .dateRangeId(new FieldId(dateRange.getId()))
                          .to(dateRange.getTo())
                          .from(dateRange.getFrom())
                          .build()
                  ).toList()
          )
          .build();

    }
    throw new IllegalStateException("MappedElementValue not supported '%s'".formatted(mappedValue));
  }

  @Override
  public MappedElementValue toMapped(ElementValue value) {
    if (value instanceof ElementValueDateRangeList elementValueDateRangeList) {
      return MappedElementValueDateRangeList.builder()
          .dateRangeListId(elementValueDateRangeList.dateRangeListId().value())
          .dateRangeList(
              elementValueDateRangeList.dateRangeList().stream()
                  .map(dateRange -> MappedDateRange.builder()
                      .id(dateRange.dateRangeId().value())
                      .to(dateRange.to())
                      .from(dateRange.from())
                      .build())
                  .toList()
          )
          .build();
    }
    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
  }
}
