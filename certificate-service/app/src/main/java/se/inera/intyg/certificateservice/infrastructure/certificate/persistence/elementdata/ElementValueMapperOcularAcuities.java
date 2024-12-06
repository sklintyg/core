package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.OcularAcuity;
import se.inera.intyg.certificateservice.domain.certificate.model.WithCorrection;
import se.inera.intyg.certificateservice.domain.certificate.model.WithoutCorrection;
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
          .rightEye(
              OcularAcuity.builder()
                  .withCorrection(
                      WithCorrection.builder()
                          .id(new FieldId(
                              valueOcularAcuities.getRightEye().getWithCorrection().getId()))
                          .value(valueOcularAcuities.getRightEye().getWithCorrection().getValue())
                          .build()
                  )
                  .withoutCorrection(
                      WithoutCorrection.builder()
                          .id(new FieldId(
                              valueOcularAcuities.getRightEye().getWithoutCorrection().getId())
                          )
                          .value(
                              valueOcularAcuities.getRightEye().getWithoutCorrection().getValue()
                          )
                          .build()
                  )
                  .build()
          )
          .leftEye(
              OcularAcuity.builder()
                  .withCorrection(
                      WithCorrection.builder()
                          .id(new FieldId(
                              valueOcularAcuities.getLeftEye().getWithCorrection().getId()))
                          .value(valueOcularAcuities.getLeftEye().getWithCorrection().getValue())
                          .build()
                  )
                  .withoutCorrection(
                      WithoutCorrection.builder()
                          .id(new FieldId(
                              valueOcularAcuities.getLeftEye().getWithoutCorrection().getId())
                          )
                          .value(
                              valueOcularAcuities.getLeftEye().getWithoutCorrection().getValue()
                          )
                          .build()
                  )
                  .build()
          )
          .binocular(
              OcularAcuity.builder()
                  .withCorrection(
                      WithCorrection.builder()
                          .id(new FieldId(
                              valueOcularAcuities.getBinocular().getWithCorrection().getId()))
                          .value(valueOcularAcuities.getBinocular().getWithCorrection().getValue())
                          .build()
                  )
                  .withoutCorrection(
                      WithoutCorrection.builder()
                          .id(new FieldId(
                              valueOcularAcuities.getBinocular().getWithoutCorrection().getId())
                          )
                          .value(
                              valueOcularAcuities.getBinocular().getWithoutCorrection().getValue()
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
    if (value instanceof ElementValueOcularAcuities elementValueOcularAcuities) {

      return MappedElementValueOcularAcuities.builder()
          .rightEye(
              MappedElementValueOcularAcuity.builder()
                  .withCorrection(
                      MappedElementValueDouble.builder()
                          .id(elementValueOcularAcuities.rightEye().withCorrection().id().value())
                          .value(elementValueOcularAcuities.rightEye().withCorrection().value())
                          .build()
                  )
                  .withoutCorrection(
                      MappedElementValueDouble.builder()
                          .id(elementValueOcularAcuities.rightEye().withoutCorrection().id()
                              .value())
                          .value(elementValueOcularAcuities.rightEye().withoutCorrection().value())
                          .build()
                  )
                  .build()
          )
          .leftEye(
              MappedElementValueOcularAcuity.builder()
                  .withCorrection(
                      MappedElementValueDouble.builder()
                          .id(elementValueOcularAcuities.leftEye().withCorrection().id().value())
                          .value(elementValueOcularAcuities.leftEye().withCorrection().value())
                          .build()
                  )
                  .withoutCorrection(
                      MappedElementValueDouble.builder()
                          .id(elementValueOcularAcuities.leftEye().withoutCorrection().id()
                              .value())
                          .value(elementValueOcularAcuities.leftEye().withoutCorrection().value())
                          .build()
                  )
                  .build()
          )
          .binocular(
              MappedElementValueOcularAcuity.builder()
                  .withCorrection(
                      MappedElementValueDouble.builder()
                          .id(elementValueOcularAcuities.binocular().withCorrection().id().value())
                          .value(elementValueOcularAcuities.binocular().withCorrection().value())
                          .build()
                  )
                  .withoutCorrection(
                      MappedElementValueDouble.builder()
                          .id(elementValueOcularAcuities.binocular().withoutCorrection().id()
                              .value())
                          .value(elementValueOcularAcuities.binocular().withoutCorrection().value())
                          .build()
                  )
                  .build()
          )
          .build();
    }
    throw new IllegalStateException("ElementValue not supported '%s'".formatted(value));
  }
}