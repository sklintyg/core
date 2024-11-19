package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigMedicalInvestigation;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CodeItem;
import se.inera.intyg.certificateservice.application.certificate.dto.config.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataMedicalInvestigationConfigConverter implements
    CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.MEDICAL_INVESTIGATION_LIST;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification,
      Certificate certificate) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationMedicalInvestigationList configuration)) {
      throw new IllegalStateException(
          "Invalid config type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataConfigMedicalInvestigation.builder()
        .text(configuration.name())
        .description(configuration.description())
        .header(configuration.header())
        .label(configuration.label())
        .informationSourceText(configuration.informationSourceText())
        .informationSourceDescription(configuration.informationSourceDescription())
        .typeText(configuration.typeText())
        .dateText(configuration.dateText())
        .list(configuration.list()
            .stream()
            .map(
                medicalInvestigation -> MedicalInvestigation.builder()
                    .investigationTypeId(medicalInvestigation.investigationTypeId().value())
                    .informationSourceId(medicalInvestigation.informationSourceId().value())
                    .dateId(medicalInvestigation.dateId().value())
                    .maxDate(date(medicalInvestigation.max()))
                    .minDate(date(medicalInvestigation.min()))
                    .typeOptions(medicalInvestigation.typeOptions()
                        .stream()
                        .map(
                            typeOption -> CodeItem.builder()
                                .code(typeOption.code())
                                .id(typeOption.code())
                                .label(typeOption.displayName())
                                .build()
                        ).toList()
                    )
                    .build()
            ).toList()
        )
        .build();
  }

  private static LocalDate date(Period period) {
    return period == null ? null : LocalDate.now(ZoneId.systemDefault()).plus(period);
  }
}
