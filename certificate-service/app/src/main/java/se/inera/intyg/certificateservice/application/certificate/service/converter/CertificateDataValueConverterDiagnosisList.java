package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.util.Collections;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDiagnosis;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataValueConverterDiagnosisList implements CertificateDataValueConverter {

  @Override
  public ElementType getType() {
    return ElementType.DIAGNOSIS;
  }

  @Override
  public CertificateDataValue convert(ElementSpecification elementSpecification,
      ElementValue elementValue) {
    if (elementValue != null && !(elementValue instanceof ElementValueDiagnosisList)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementValue.getClass())
      );
    }

    return CertificateDataValueDiagnosisList.builder()
        .list(
            elementValue != null ? ((ElementValueDiagnosisList) elementValue).diagnoses().stream()
                .map(
                    elementValueDiagnosis -> CertificateDataValueDiagnosis.builder()
                        .id(elementValueDiagnosis.id().value())
                        .code(elementValueDiagnosis.code())
                        .description(elementValueDiagnosis.description())
                        .terminology(elementValueDiagnosis.terminology())
                        .build()
                )
                .toList() : Collections.emptyList()
        )
        .build();
  }
}
