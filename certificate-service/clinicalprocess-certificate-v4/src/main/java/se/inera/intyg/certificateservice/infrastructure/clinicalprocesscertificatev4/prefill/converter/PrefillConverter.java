package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.PrefillAnswer;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

public interface PrefillConverter {

  Class<? extends ElementConfiguration> supports();

  PrefillAnswer prefillAnswer(ElementSpecification elementSpecification, Forifyllnad prefill);
}