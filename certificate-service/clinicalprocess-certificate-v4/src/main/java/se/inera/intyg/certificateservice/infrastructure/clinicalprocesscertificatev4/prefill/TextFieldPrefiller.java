package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@Component
public class TextFieldPrefiller implements PrefillElementData {

  @Override
  public Class<? extends ElementConfiguration> supports() {
    return ElementConfigurationTextField.class;
  }


  public PrefillResult prefillAnswer(Collection<Svar> data, ElementSpecification specification) {
    return null;
  }

  @Override
  public PrefillResult prefillSubAnswer(Delsvar delsvar, ElementSpecification specification) {
    final var content = delsvar.getContent();
    if (content != null && !content.isEmpty()) {
      var text = (String) content.getFirst();
      return PrefillResult.builder()
          .elementData(ElementData.builder()
              .id(specification.id())
              .value(ElementValueText.builder()
                  .textId(specification.configuration().id())
                  .text(text)
                  .build())
              .build())
          .build();
    }

    return PrefillResult.builder()
        .error(List.of(new PrefillError(
            "Empty content in Delsvar for element: " + specification.id().id())))
        .build();
  }

}
