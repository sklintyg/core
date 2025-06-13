package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.Collection;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

public interface PrefillElementData {

  Class<? extends ElementConfiguration> supports();

  PrefillResult prefillSubAnswer(Delsvar data, ElementSpecification specification);

  PrefillResult prefillAnswer(Collection<Svar> data, ElementSpecification specification);


}
