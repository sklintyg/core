package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class ElementValueMapperCodeList implements ElementValueMapper {

  @Override
  public boolean supports(Class<?> c) {
    return c.equals(MappedElementValueCodeList.class)
           || c.equals(ElementValueCodeList.class);
  }

  @Override
  public ElementValue toDomain(MappedElementValue mappedValue) {
    if (mappedValue instanceof MappedElementValueCodeList mappedElementValueCodeList) {
      return ElementValueCodeList.builder()
          .id(new FieldId(mappedElementValueCodeList.getId()))
          .list(
              mappedElementValueCodeList.getList().stream()
                  .map(code ->
                      ElementValueCode.builder()
                          .codeId(new FieldId(code.getId()))
                          .code(code.getCode())
                          .build()
                  ).toList()
          )
          .build();

    }
    throw new IllegalStateException("MappedElementValue not supported '%s'".formatted(mappedValue));
  }

  @Override
  public MappedElementValue toMapped(ElementValue value) {
    if (value instanceof ElementValueCodeList elementValueCodeList) {
      return MappedElementValueCodeList.builder()
          .id(elementValueCodeList.id().value())
          .list(
              elementValueCodeList.list().stream()
                  .map(code -> MappedCode.builder()
                      .id(code.codeId().value())
                      .code(code.code())
                      .build())
                  .toList()
          )
          .build();
    }
    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
  }
}
