package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class XmlGeneratorCodeList implements XmlGeneratorElementValue {

  @Override
  public Class<? extends ElementValue> supports() {
    return ElementValueCodeList.class;
  }

  public List<Svar> generate(ElementData data, ElementSpecification specification) {
    if (!(data.value() instanceof ElementValueCodeList codeListValue)) {
      return Collections.emptyList();
    }

    if (codeListValue.list() == null || codeListValue.list().isEmpty()) {
      return Collections.emptyList();
    }

    if (!(specification.configuration() instanceof ElementConfigurationCheckboxMultipleCode configuration)) {
      throw new IllegalArgumentException(
          "Cannot generate xml for configuration of type '%s'"
              .formatted(specification.configuration().getClass())
      );
    }

    final var objectFactory = new ObjectFactory();

    return codeListValue.list().stream()
        .map(codeValue -> {

              final var codeAnswer = new Svar();
              codeAnswer.setId(data.id().id());
              codeAnswer.setInstans(codeListValue.list().indexOf(codeValue) + 1);

              final var subAnswerCode = new Delsvar();
              final var cvType = new CVType();

              final var code = configuration.code(codeValue);
              subAnswerCode.setId(codeListValue.id().value());
              cvType.setCode(code.code());
              cvType.setCodeSystem(code.codeSystem());
              cvType.setDisplayName(code.displayName());

              final var convertedCvType = objectFactory.createCv(cvType);
              subAnswerCode.getContent().add(convertedCvType);

              codeAnswer.getDelsvar().add(subAnswerCode);

              return codeAnswer;
            }
        )
        .toList();
  }
}