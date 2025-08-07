package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;

public interface XmlGeneratorElementValue extends XmlGenerator {

  Class<? extends ElementValue> supports();

}