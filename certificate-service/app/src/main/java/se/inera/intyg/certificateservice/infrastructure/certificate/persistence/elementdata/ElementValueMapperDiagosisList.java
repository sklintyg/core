package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class ElementValueMapperDiagosisList implements ElementValueMapper {

  @Override
  public boolean supports(Class<?> c) {
    return c.equals(MappedElementValueDiagnosisList.class)
        || c.equals(ElementValueDiagnosisList.class);
  }

  @Override
  public ElementValue toDomain(MappedElementValue mappedValue) {
    if (mappedValue instanceof MappedElementValueDiagnosisList value) {
      return ElementValueDiagnosisList.builder()
          .diagnoses(
              value.getMappedDiagnoses().stream()
                  .map(mappedDiagnosis -> ElementValueDiagnosis.builder()
                      .id(new FieldId(mappedDiagnosis.id))
                      .code(mappedDiagnosis.code)
                      .terminology(mappedDiagnosis.terminology)
                      .description(mappedDiagnosis.description)
                      .build())
                  .toList()
          )
          .build();

    }
    throw new IllegalStateException("MappedElementValue not supported '%s'".formatted(mappedValue));
  }

  @Override
  public MappedElementValue toMapped(ElementValue value) {
    if (value instanceof ElementValueDiagnosisList valueDiagnosisList) {
      return MappedElementValueDiagnosisList.builder()
          .mappedDiagnoses(
              valueDiagnosisList.diagnoses().stream()
                  .map(elementValueDiagnosis -> MappedDiagnosis.builder()
                      .id(elementValueDiagnosis.id().value())
                      .code(elementValueDiagnosis.code())
                      .terminology(elementValueDiagnosis.terminology())
                      .description(elementValueDiagnosis.description())
                      .build())
                  .toList()
          )
          .build();
    }
    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
  }
}
