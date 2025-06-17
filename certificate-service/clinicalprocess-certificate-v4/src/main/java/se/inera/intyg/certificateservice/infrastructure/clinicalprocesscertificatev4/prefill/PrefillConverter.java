package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.Collection;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

public interface PrefillConverter {

  Class<? extends ElementConfiguration> supports();

  PrefillAnswer prefillSubAnswer(Collection<Delsvar> subAnswers,
      ElementSpecification specification);

  PrefillAnswer prefillAnswer(Collection<Svar> answers, ElementSpecification specification);

  Collection<PrefillAnswer> unknownIds(Svar answer, CertificateModel model);
}
