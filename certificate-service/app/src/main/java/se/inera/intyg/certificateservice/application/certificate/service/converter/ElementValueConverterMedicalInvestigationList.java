package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType.MEDICAL_INVESTIGATION_LIST;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Component
@RequiredArgsConstructor
public class ElementValueConverterMedicalInvestigationList implements ElementValueConverter {

  @Override
  public CertificateDataValueType getType() {
    return MEDICAL_INVESTIGATION_LIST;
  }

  @Override
  public ElementValue convert(CertificateDataValue value) {
    if (!(value instanceof CertificateDataValueMedicalInvestigationList dataValueMedicalInvestigationList)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(value.getType())
      );
    }

    return ElementValueMedicalInvestigationList.builder()
        .id(new FieldId(dataValueMedicalInvestigationList.getId()))
        .list(
            dataValueMedicalInvestigationList.getList().stream()
                .map(medicalInvestigation ->
                    MedicalInvestigation.builder()
                        .id(new FieldId(medicalInvestigation.getId()))
                        .date(ElementValueDate.builder()
                            .dateId(new FieldId(medicalInvestigation.getDate().getId()))
                            .date(medicalInvestigation.getDate().getDate())
                            .build())
                        .investigationType(ElementValueCode.builder()
                            .codeId(
                                new FieldId(medicalInvestigation.getInvestigationType().getId()))
                            .code(medicalInvestigation.getInvestigationType().getCode())
                            .build())
                        .informationSource(ElementValueText.builder()
                            .textId(
                                new FieldId(medicalInvestigation.getInformationSource().getId()))
                            .text(medicalInvestigation.getInformationSource().getText())
                            .build())
                        .build()
                )
                .toList()
        )
        .build();
  }
}
