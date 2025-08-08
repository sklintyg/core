package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter;

import se.inera.intyg.certificateservice.domain.certificate.model.CustomMapperId;

public interface PrefillCustomConverter extends PrefillConverter {

  CustomMapperId id();
}