package se.inera.intyg.certificateservice.application.certificate.service.converter;

import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;

public interface ElementValueConverter {

  CertificateDataValueType getType();

  ElementValue convert(CertificateDataValue value);
}
