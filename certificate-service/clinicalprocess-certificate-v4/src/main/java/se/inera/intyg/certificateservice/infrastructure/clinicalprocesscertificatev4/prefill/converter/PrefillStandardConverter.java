package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfiguration;

public interface PrefillStandardConverter extends PrefillConverter {

  Class<? extends ElementConfiguration> supports();
}