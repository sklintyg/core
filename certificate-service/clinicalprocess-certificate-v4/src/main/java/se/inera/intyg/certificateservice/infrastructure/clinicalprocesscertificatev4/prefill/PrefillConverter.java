package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.util.Collection;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

public interface PrefillConverter {

  Class<? extends ElementConfiguration> supports();

  PrefillAnswer prefillSubAnswer(List<Delsvar> subAnswers,
      ElementSpecification specification);

  PrefillAnswer prefillAnswer(List<Svar> answers, ElementSpecification specification);

  Collection<PrefillAnswer> unknownIds(Svar answer, CertificateModel model);
}
