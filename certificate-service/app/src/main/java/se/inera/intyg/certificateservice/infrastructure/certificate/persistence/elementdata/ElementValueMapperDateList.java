package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class ElementValueMapperDateList implements ElementValueMapper {

  @Override
  public boolean supports(Class<?> c) {
    return c.equals(MappedElementValueDateList.class)
        || c.equals(ElementValueDateList.class);
  }

  @Override
  public ElementValue toDomain(MappedElementValue mappedValue) {
    if (mappedValue instanceof MappedElementValueDateList mappedElementValueDateList) {
      return ElementValueDateList.builder()
          .dateListId(new FieldId(mappedElementValueDateList.getDateListId()))
          .dateList(
              mappedElementValueDateList.getDateList().stream()
                  .map(date ->
                      ElementValueDate.builder()
                          .dateId(new FieldId(date.getId()))
                          .date(date.getDate())
                          .build()
                  ).toList()
          )
          .build();

    }
    throw new IllegalStateException("MappedElementValue not supported '%s'".formatted(mappedValue));
  }

  @Override
  public MappedElementValue toMapped(ElementValue value) {
    if (value instanceof ElementValueDateList elementValueDateList) {
      return MappedElementValueDateList.builder()
          .dateListId(elementValueDateList.dateListId().value())
          .dateList(
              elementValueDateList.dateList().stream()
                  .map(date -> MappedDate.builder()
                      .id(date.dateId().value())
                      .date(date.date())
                      .build())
                  .toList()
          )
          .build();
    }
    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
  }
}
