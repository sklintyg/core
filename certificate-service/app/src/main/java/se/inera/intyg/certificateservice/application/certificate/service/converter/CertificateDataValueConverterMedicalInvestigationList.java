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

  private static final String LEGACY_CODE = "SYNHABILITERING";
  private static final String CURRENT_CODE = "SYNHABILITERINGEN";

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

    final var valueForConversion = !isValueDefined(elementValue)
        || ((ElementValueMedicalInvestigationList) elementValue).list().isEmpty()
        ? elementConfiguration.emptyValue() : elementValue;

    return CertificateDataValueMedicalInvestigationList.builder()
        .id(elementConfiguration.id().value())
        .list(
            isValueDefined(valueForConversion)
                ? ((ElementValueMedicalInvestigationList) valueForConversion).list()
                .stream()
                .map(medicalInvestigation ->
                    CertificateDataValueMedicalInvestigation.builder()
                        .id(getMedicalInvestigationConfig(
                            valueForConversion,
                            elementConfiguration,
                            medicalInvestigation).id().value())
                        .date(CertificateDataValueDate.builder()
                            .id(getMedicalInvestigationConfig(
                                valueForConversion,
                                elementConfiguration,
                                medicalInvestigation).dateId().value()
                            )
                            .date(medicalInvestigation.date().date())
                            .build())
                        .informationSource(CertificateDataValueText.builder()
                            .id(getMedicalInvestigationConfig(
                                valueForConversion,
                                elementConfiguration,
                                medicalInvestigation
                            ).informationSourceId().value())
                            .text(medicalInvestigation.informationSource().text())
                            .build())
                        .investigationType(CertificateDataValueCode.builder()
                            .id(getMedicalInvestigationConfig(
                                valueForConversion,
                                elementConfiguration,
                                medicalInvestigation
                            ).investigationTypeId().value())
                            .code(mapLegacyCode(medicalInvestigation.investigationType().code()))
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
    final var config = elementConfiguration.list().get(value.list().indexOf(medicalInvestigation));

    if (config == null) {
      throw new IllegalStateException(
          "Could not find matching config for medical investigation value");
    }

    return config;
  }

  private static boolean isValueDefined(ElementValue elementValue) {
    if (elementValue == null) {
      return false;
    }

    final var value = (ElementValueMedicalInvestigationList) elementValue;

    return value.list() != null && !value.list().isEmpty();
  }

  private static String mapLegacyCode(String code) {
    if (LEGACY_CODE.equals(code)) {
      return CURRENT_CODE;
    }
    return code;
  }
}