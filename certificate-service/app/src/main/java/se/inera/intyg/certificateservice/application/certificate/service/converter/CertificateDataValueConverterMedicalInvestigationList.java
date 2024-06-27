package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.util.Collections;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCode;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueMedicalInvestigation;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;

@Component
public class CertificateDataValueConverterMedicalInvestigationList implements
    CertificateDataValueConverter {

  @Override
  public ElementType getType() {
    return ElementType.MEDICAL_INVESTIGATION_LIST;
  }

  @Override
  public CertificateDataValue convert(ElementSpecification elementSpecification,
      ElementValue elementValue) {
    if (elementValue != null && !(elementValue instanceof ElementValueMedicalInvestigationList)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(elementValue.getClass())
      );
    }

    if (!(elementSpecification.configuration() instanceof ElementConfigurationMedicalInvestigationList
        elementConfiguration)) {
      throw new IllegalStateException(
          "Invalid configuration type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataValueMedicalInvestigationList.builder()
        .id(elementConfiguration.id().value())
        .list(
            isValueDefined(elementValue)
                ? ((ElementValueMedicalInvestigationList) elementValue).list()
                .stream()
                .map(medicalInvestigation ->
                    CertificateDataValueMedicalInvestigation.builder()
                        .id(
                            getMedicalInvestigationConfig(
                                elementValue,
                                elementConfiguration,
                                medicalInvestigation
                            ).id().value())
                        .date(CertificateDataValueDate.builder()
                            .id(
                                getMedicalInvestigationConfig(
                                    elementValue,
                                    elementConfiguration,
                                    medicalInvestigation
                                ).dateId().value()
                            )
                            .date(medicalInvestigation.date().date())
                            .build())
                        .informationSource(CertificateDataValueText.builder()
                            .id(getMedicalInvestigationConfig(
                                elementValue,
                                elementConfiguration,
                                medicalInvestigation
                            ).informationSourceId().value())
                            .text(medicalInvestigation.informationSource().text())
                            .build())
                        .investigationType(CertificateDataValueCode.builder()
                            .id(getMedicalInvestigationConfig(
                                elementValue,
                                elementConfiguration,
                                medicalInvestigation
                            ).investigationTypeId().value())
                            .code(medicalInvestigation.investigationType().code())
                            .build())
                        .build()
                ).toList()
                : Collections.emptyList())
        .build();
  }

  private static MedicalInvestigationConfig getMedicalInvestigationConfig(
      ElementValue elementValue,
      ElementConfigurationMedicalInvestigationList elementConfiguration,
      MedicalInvestigation medicalInvestigation) {
    final var value = (ElementValueMedicalInvestigationList) elementValue;
    return elementConfiguration.list().get(value.list().indexOf(medicalInvestigation));
  }

  private static boolean isValueDefined(ElementValue elementValue) {
    if (elementValue == null) {
      return false;
    }

    final var value = (ElementValueMedicalInvestigationList) elementValue;

    return value.list() != null && !value.list().isEmpty();
  }
}
