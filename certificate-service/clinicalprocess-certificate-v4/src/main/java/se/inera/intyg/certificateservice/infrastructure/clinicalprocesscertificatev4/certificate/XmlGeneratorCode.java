package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDropdownCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class XmlGeneratorCode implements XmlGeneratorElementValue {

  @Override
  public Class<? extends ElementValue> supports() {
    return ElementValueCode.class;
  }

  public List<Svar> generate(ElementData data, ElementSpecification specification) {
    if (!(data.value() instanceof ElementValueCode elementValueCode)) {
      return Collections.emptyList();
    }

    if (!StringUtils.hasLength(elementValueCode.code())) {
      return Collections.emptyList();
    }

    final var codeAnswer = new Svar();
    codeAnswer.setId(data.id().id());

    final var subAnswerCode = new Delsvar();
    final var cvType = new CVType();

    final var code = getCode(specification.configuration(), elementValueCode);
    subAnswerCode.setId(specification.configuration().id().value());
    cvType.setCode(code.code());
    cvType.setCodeSystem(code.codeSystem());
    cvType.setDisplayName(code.displayName());

    final var objectFactory = new ObjectFactory();
    final var convertedCvType = objectFactory.createCv(cvType);
    subAnswerCode.getContent().add(convertedCvType);

    codeAnswer.getDelsvar().add(subAnswerCode);

    return List.of(codeAnswer);
  }

  private Code getCode(ElementConfiguration configuration, ElementValueCode elementValueCode) {
    return switch (configuration) {
      case ElementConfigurationRadioMultipleCode radioConfig -> radioConfig.code(elementValueCode);
      case ElementConfigurationDropdownCode dropdownConfig -> dropdownConfig.code(elementValueCode);
      case null, default -> throw new IllegalArgumentException(
          "Cannot generate xml for configuration of type '%s'"
              .formatted(configuration.getClass())
      );
    };
  }
}