package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;

@Component
public class ElementValueMapperMedicalInvestigationList implements ElementValueMapper {

  @Override
  public boolean supports(Class<?> c) {
    return c.equals(MappedElementValueMedicalInvestigationList.class)
        || c.equals(ElementValueMedicalInvestigationList.class);
  }

  @Override
  public ElementValue toDomain(MappedElementValue mappedValue) {
    if (mappedValue instanceof MappedElementValueMedicalInvestigationList mappedElementValueMedicalInvestigationList) {
      return ElementValueMedicalInvestigationList.builder()
          .build();

    }
    throw new IllegalStateException("MappedElementValue not supported '%s'".formatted(mappedValue));
  }

  @Override
  public MappedElementValue toMapped(ElementValue value) {
    if (value instanceof ElementValueMedicalInvestigationList elementValueMedicalInvestigationList) {
      return MappedElementValueMedicalInvestigationList.builder()
          .build();
    }
    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
  }
}
