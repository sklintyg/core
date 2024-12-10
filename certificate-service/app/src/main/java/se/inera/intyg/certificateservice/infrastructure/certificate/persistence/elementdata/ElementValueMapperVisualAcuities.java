package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.VisualAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class ElementValueMapperVisualAcuities implements ElementValueMapper {

  @Override
  public boolean supports(Class<?> c) {
    return c.equals(MappedElementValueVisualAcuities.class)
        || c.equals(ElementValueVisualAcuities.class);
  }

  @Override
  public ElementValue toDomain(MappedElementValue mappedValue) {
    if (mappedValue instanceof MappedElementValueVisualAcuities valueVisualAcuities) {

      return ElementValueVisualAcuities.builder()
          .rightEye(
              VisualAcuity.builder()
                  .withCorrection(
                      Correction.builder()
                          .id(new FieldId(
                              valueVisualAcuities.getRightEye().getWithCorrection().getId()))
                          .value(valueVisualAcuities.getRightEye().getWithCorrection().getValue())
                          .build()
                  )
                  .withoutCorrection(
                      Correction.builder()
                          .id(new FieldId(
                              valueVisualAcuities.getRightEye().getWithoutCorrection().getId())
                          )
                          .value(
                              valueVisualAcuities.getRightEye().getWithoutCorrection().getValue()
                          )
                          .build()
                  )
                  .build()
          )
          .leftEye(
              VisualAcuity.builder()
                  .withCorrection(
                      Correction.builder()
                          .id(new FieldId(
                              valueVisualAcuities.getLeftEye().getWithCorrection().getId()))
                          .value(valueVisualAcuities.getLeftEye().getWithCorrection().getValue())
                          .build()
                  )
                  .withoutCorrection(
                      Correction.builder()
                          .id(new FieldId(
                              valueVisualAcuities.getLeftEye().getWithoutCorrection().getId())
                          )
                          .value(
                              valueVisualAcuities.getLeftEye().getWithoutCorrection().getValue()
                          )
                          .build()
                  )
                  .build()
          )
          .binocular(
              VisualAcuity.builder()
                  .withCorrection(
                      Correction.builder()
                          .id(new FieldId(
                              valueVisualAcuities.getBinocular().getWithCorrection().getId()))
                          .value(valueVisualAcuities.getBinocular().getWithCorrection().getValue())
                          .build()
                  )
                  .withoutCorrection(
                      Correction.builder()
                          .id(new FieldId(
                              valueVisualAcuities.getBinocular().getWithoutCorrection().getId())
                          )
                          .value(
                              valueVisualAcuities.getBinocular().getWithoutCorrection().getValue()
                          )
                          .build()
                  )
                  .build()
          )
          .build();
    }
    throw new IllegalStateException("MappedElementValue not supported '%s'".formatted(mappedValue));
  }

  @Override
  public MappedElementValue toMapped(ElementValue value) {
    if (value instanceof ElementValueVisualAcuities elementValueVisualAcuities) {

      return MappedElementValueVisualAcuities.builder()
          .rightEye(
              MappedElementValueVisualAcuity.builder()
                  .withCorrection(
                      MappedElementValueDouble.builder()
                          .id(elementValueVisualAcuities.rightEye().withCorrection().id().value())
                          .value(elementValueVisualAcuities.rightEye().withCorrection().value())
                          .build()
                  )
                  .withoutCorrection(
                      MappedElementValueDouble.builder()
                          .id(elementValueVisualAcuities.rightEye().withoutCorrection().id()
                              .value())
                          .value(elementValueVisualAcuities.rightEye().withoutCorrection().value())
                          .build()
                  )
                  .build()
          )
          .leftEye(
              MappedElementValueVisualAcuity.builder()
                  .withCorrection(
                      MappedElementValueDouble.builder()
                          .id(elementValueVisualAcuities.leftEye().withCorrection().id().value())
                          .value(elementValueVisualAcuities.leftEye().withCorrection().value())
                          .build()
                  )
                  .withoutCorrection(
                      MappedElementValueDouble.builder()
                          .id(elementValueVisualAcuities.leftEye().withoutCorrection().id()
                              .value())
                          .value(elementValueVisualAcuities.leftEye().withoutCorrection().value())
                          .build()
                  )
                  .build()
          )
          .binocular(
              MappedElementValueVisualAcuity.builder()
                  .withCorrection(
                      MappedElementValueDouble.builder()
                          .id(elementValueVisualAcuities.binocular().withCorrection().id().value())
                          .value(elementValueVisualAcuities.binocular().withCorrection().value())
                          .build()
                  )
                  .withoutCorrection(
                      MappedElementValueDouble.builder()
                          .id(elementValueVisualAcuities.binocular().withoutCorrection().id()
                              .value())
                          .value(elementValueVisualAcuities.binocular().withoutCorrection().value())
                          .build()
                  )
                  .build()
          )
          .build();
    }
    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
  }
}