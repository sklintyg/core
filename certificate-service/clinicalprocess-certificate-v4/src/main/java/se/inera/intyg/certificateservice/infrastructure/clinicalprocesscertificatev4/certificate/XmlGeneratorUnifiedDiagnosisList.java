package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.UnifiedDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class XmlGeneratorUnifiedDiagnosisList implements XmlGeneratorElementData {

  @Override
  public Class<? extends ElementValue> supports() {
    return UnifiedDiagnosisList.class;
  }

  public List<Svar> generate(ElementData data, ElementSpecification specification) {
    if (!(data.value() instanceof ElementValueDiagnosisList diagnosisList)) {
      return Collections.emptyList();
    }

    if (!(specification.configuration() instanceof ElementConfigurationDiagnosis configuration)) {
      throw new IllegalArgumentException(
          "Cannot generate xml for configuration of type '%s'"
              .formatted(specification.configuration().getClass())
      );
    }

    if (diagnosisList.diagnoses() == null || diagnosisList.diagnoses().isEmpty()) {
      return Collections.emptyList();
    }

    final var objectFactory = new ObjectFactory();
    final var atomicInteger = new AtomicInteger(1);
    final var diagnosisAnswer = new Svar();
    diagnosisAnswer.setId(data.id().id());

    diagnosisList.diagnoses().forEach(
        diagnosis -> {
          final var subAnswerDiagnosisDescription = new Delsvar();
          subAnswerDiagnosisDescription.setId(
              getIdDescription(data, atomicInteger.getAndIncrement())
          );
          subAnswerDiagnosisDescription.getContent().add(diagnosis.description());

          final var subAnswerDiagnosisCode = new Delsvar();
          subAnswerDiagnosisCode.setId(getIdCode(data, atomicInteger.getAndIncrement()));

          final var cvType = new CVType();
          cvType.setCode(diagnosis.code());
          cvType.setDisplayName(diagnosis.description());
          cvType.setCodeSystem(configuration.codeSystem(diagnosis.terminology()));

          final var convertedCvType = objectFactory.createCv(cvType);
          subAnswerDiagnosisCode.getContent().add(convertedCvType);

          diagnosisAnswer.getDelsvar().add(subAnswerDiagnosisDescription);
          diagnosisAnswer.getDelsvar().add(subAnswerDiagnosisCode);
        }
    );

    return List.of(diagnosisAnswer);
  }

  private static String getIdDescription(ElementData data, int id) {
    return data.id().id() + ".%s".formatted(id);
  }

  private static String getIdCode(ElementData data, int id) {
    return data.id().id() + ".%s".formatted(id);
  }
}