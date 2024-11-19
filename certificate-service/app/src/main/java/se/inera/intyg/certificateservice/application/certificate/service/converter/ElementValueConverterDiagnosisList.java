package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType.DIAGNOSIS_LIST;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDiagnosisList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
public class ElementValueConverterDiagnosisList implements ElementValueConverter {

  @Override
  public CertificateDataValueType getType() {
    return DIAGNOSIS_LIST;
  }

  @Override
  public ElementValue convert(CertificateDataValue value) {
    if (!(value instanceof CertificateDataValueDiagnosisList diagnosis)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(value.getType())
      );
    }
    return ElementValueDiagnosisList.builder()
        .diagnoses(
            diagnosis.getList().stream()
                .map(valueDiagnosis -> ElementValueDiagnosis.builder()
                    .id(new FieldId(valueDiagnosis.getId()))
                    .code(valueDiagnosis.getCode())
                    .terminology(valueDiagnosis.getTerminology())
                    .description(valueDiagnosis.getDescription())
                    .build()
                )
                .toList()
        )
        .build();
  }
}
