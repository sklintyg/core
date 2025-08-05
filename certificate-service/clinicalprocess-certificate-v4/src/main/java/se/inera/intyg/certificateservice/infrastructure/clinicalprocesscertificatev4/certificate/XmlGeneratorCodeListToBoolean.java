package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static se.inera.intyg.certificateservice.domain.certificate.model.CustomMapperId.CODE_LIST_TO_BOOLEAN;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.CustomMapperId;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class XmlGeneratorCodeListToBoolean implements XmlGeneratorCustomMapper {

  @Override
  public CustomMapperId id() {
    return CODE_LIST_TO_BOOLEAN;
  }

  public List<Svar> generate(ElementData data, ElementSpecification specification) {
    if (!(data.value() instanceof ElementValueCodeList codeListValue)) {
      return Collections.emptyList();
    }

    if (!(specification.configuration() instanceof ElementConfigurationCheckboxMultipleCode configuration)) {
      throw new IllegalArgumentException(
          "Cannot generate xml for configuration of type '%s'"
              .formatted(specification.configuration().getClass())
      );
    }

    final var answer = new Svar();
    answer.setId(data.id().id());

    configuration.list().forEach(codeValue -> {
          final var subAnswer = new Delsvar();
          subAnswer.setId(codeValue.id().value());
          final var codeHasValue = codeListValue.list().stream()
              .anyMatch(
                  elementConfigurationCode -> elementConfigurationCode.code()
                      .equals(codeValue.code().code())
              );
          subAnswer.getContent().add(codeHasValue ? "true" : "false");
          answer.getDelsvar().add(subAnswer);
        }
    );

    return List.of(answer);
  }
}