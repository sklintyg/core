package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class XmlGeneratorCode implements XmlGeneratorElementData {

  @Override
  public Class<? extends ElementValue> supports() {
    return ElementValueDate.class;
  }

  public List<Svar> generate(ElementData data, ElementSpecification specification) {
    if (!(data.value() instanceof ElementValueCode elementValueCode)) {
      return Collections.emptyList();
    }

    if (elementValueCode.code() == null) {
      return Collections.emptyList();
    }

    if (!(specification.configuration() instanceof ElementConfigurationRadioMultipleCode configuration)) {
      throw new IllegalArgumentException(
          "Cannot generate xml for configuration of type '%s'"
              .formatted(specification.configuration().getClass())
      );
    }

    final var codeAnswer = new Svar();
    codeAnswer.setId(data.id().id());

    final var subAnswerCode = new Delsvar();
    final var cvType = new CVType();

    final var code = configuration.code(elementValueCode);
    subAnswerCode.setId(configuration.id().value());
    cvType.setCode(code.code());
    cvType.setCodeSystem(code.codeSystem());
    cvType.setDisplayName(code.displayName());

    final var objectFactory = new ObjectFactory();
    final var convertedCvType = objectFactory.createCv(cvType);
    subAnswerCode.getContent().add(convertedCvType);

    codeAnswer.getDelsvar().add(subAnswerCode);

    return List.of(codeAnswer);
  }
}
