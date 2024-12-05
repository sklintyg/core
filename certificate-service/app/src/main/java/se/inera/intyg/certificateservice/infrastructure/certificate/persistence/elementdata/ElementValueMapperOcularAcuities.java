package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.OcularAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class ElementValueMapperOcularAcuities implements ElementValueMapper {

  @Override
  public boolean supports(Class<?> c) {
    return c.equals(MappedElementValueOcularAcuities.class)
        || c.equals(ElementValueOcularAcuities.class);
  }

  @Override
  public ElementValue toDomain(MappedElementValue mappedValue) {
    if (mappedValue instanceof MappedElementValueOcularAcuities valueOcularAcuities) {

      return ElementValueOcularAcuities.builder()
          .rightEye(OcularAcuity.builder()
              .doubleId(new FieldId(valueOcularAcuities.getRightEye().getId()))
              .withCorrection(valueOcularAcuities.getRightEye().getWithCorrection())
              .withoutCorrection(valueOcularAcuities.getRightEye().getWithoutCorrection())
              .build()
          )
          .leftEye(OcularAcuity.builder()
              .doubleId(new FieldId(valueOcularAcuities.getLeftEye().getId()))
              .withCorrection(valueOcularAcuities.getLeftEye().getWithCorrection())
              .withoutCorrection(valueOcularAcuities.getLeftEye().getWithoutCorrection())
              .build()
          )
          .binocular(OcularAcuity.builder()
              .doubleId(new FieldId(valueOcularAcuities.getBinocular().getId()))
              .withCorrection(valueOcularAcuities.getBinocular().getWithCorrection())
              .withoutCorrection(valueOcularAcuities.getBinocular().getWithoutCorrection())
              .build()
          )
          .build();
    }
    throw new IllegalStateException("MappedElementValue not supported '%s'".formatted(mappedValue));
  }

  @Override
  public MappedElementValue toMapped(ElementValue value) {
    if (value instanceof ElementValueOcularAcuities elementValueOcularAcuities) {

      return MappedElementValueOcularAcuities.builder()
          .rightEye(MappedElementValueOcularAcuity.builder()
              .id(elementValueOcularAcuities.rightEye().doubleId().value())
              .withCorrection(elementValueOcularAcuities.rightEye().withCorrection())
              .withoutCorrection(elementValueOcularAcuities.rightEye().withoutCorrection())
              .build()
          )
          .leftEye(MappedElementValueOcularAcuity.builder()
              .id(elementValueOcularAcuities.leftEye().doubleId().value())
              .withCorrection(elementValueOcularAcuities.leftEye().withCorrection())
              .withoutCorrection(elementValueOcularAcuities.leftEye().withoutCorrection())
              .build()
          )
          .binocular(MappedElementValueOcularAcuity.builder()
              .id(elementValueOcularAcuities.binocular().doubleId().value())
              .withCorrection(elementValueOcularAcuities.binocular().withCorrection())
              .withoutCorrection(elementValueOcularAcuities.binocular().withoutCorrection())
              .build()
          )
          .build();
    }
    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
  }
}
