package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import se.inera.intyg.certificateservice.domain.certificate.model.CustomMapperId;

public interface XmlGeneratorCustomMapper extends XmlGenerator {

  CustomMapperId id();

}