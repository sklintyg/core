package se.inera.intyg.certificateservice.application.certificate.service.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDiagnoses;
import se.inera.intyg.certificateservice.application.certificate.dto.config.DiagnosesListItem;
import se.inera.intyg.certificateservice.application.certificate.dto.config.DiagnosesTerminology;
import se.inera.intyg.certificateservice.application.certificate.dto.config.Message;
import se.inera.intyg.certificateservice.application.certificate.dto.config.MessageLevel;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@Component
public class CertificateDataDiagnosisListConfigConverter implements CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.DIAGNOSIS;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification,
      Certificate certificate) {
    if (!(elementSpecification.configuration() instanceof ElementConfigurationDiagnosis elementConfigurationDiagnosis)) {
      throw new IllegalStateException(
          "Invalid config type. Type was '%s'".formatted(
              elementSpecification.configuration().type())
      );
    }

    return CertificateDataConfigDiagnoses.builder()
        .message(
            shouldIncludeMessage(certificate, elementConfigurationDiagnosis) ?
                Message.builder()
                    .content(elementConfigurationDiagnosis.message().content())
                    .level(MessageLevel.toMessageLevel(
                        elementConfigurationDiagnosis.message().level()))
                    .build()
                : null
        )
        .list(
            elementConfigurationDiagnosis.list().stream()
                .map(elementDiagnosisListItem -> DiagnosesListItem.builder()
                    .id(elementDiagnosisListItem.id().value())
                    .build()
                )
                .toList()
        )
        .terminology(
            elementConfigurationDiagnosis.terminology().stream()
                .map(
                    elementDiagnosisTerminology -> DiagnosesTerminology.builder()
                        .id(elementDiagnosisTerminology.id())
                        .label(elementDiagnosisTerminology.label())
                        .build()
                )
                .toList()
        )
        .text(elementConfigurationDiagnosis.name())
        .description(elementConfigurationDiagnosis.description())
        .build();
  }

  private static boolean shouldIncludeMessage(Certificate certificate,
      ElementConfigurationDiagnosis elementConfigurationDiagnosis) {
    return elementConfigurationDiagnosis.message() != null
        && elementConfigurationDiagnosis.message().include(certificate);
  }
}
