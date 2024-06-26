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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

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
        elementConfigurationMedicalInvestigationList)) {
      throw new IllegalStateException(
          "Invalid configuration type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataValueMedicalInvestigationList.builder() //TODO kolla om vi vill lägga till id
        .list(
            isValueDefined(elementValue)
                ? ((ElementValueMedicalInvestigationList) elementValue).list()
                .stream()
                .map(medicalInvestigation ->
                    CertificateDataValueMedicalInvestigation.builder()
                        .date(CertificateDataValueDate.builder()
                            .id(medicalInvestigation.date().dateId().value())
                            .date(medicalInvestigation.date().date())
                            .build())
                        .informationSource(CertificateDataValueText.builder()
                            .id(medicalInvestigation.informationSource().textId().value())
                            .text(medicalInvestigation.informationSource().text())
                            .build())
                        .investigationType(CertificateDataValueCode.builder()
                            .id(medicalInvestigation.investigationType().codeId().value())
                            .code(medicalInvestigation.investigationType().code())
                            .build())
                        .build()
                ).toList()
                : Collections.emptyList())
        .build();
  }

  private static boolean isValueDefined(ElementValue elementValue) {
    if (elementValue == null) {
      return false;
    }

    final var value = (ElementValueMedicalInvestigationList) elementValue;

    return value.list() != null && !value.list().isEmpty();
  }
}
