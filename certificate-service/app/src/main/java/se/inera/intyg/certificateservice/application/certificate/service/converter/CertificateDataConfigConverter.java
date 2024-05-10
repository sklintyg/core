package se.inera.intyg.certificateservice.application.certificate.service.converter;

import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

public interface CertificateDataConfigConverter {

  ElementType getType();

  CertificateDataConfig convert(ElementSpecification elementSpecification, Certificate certificate);
}
