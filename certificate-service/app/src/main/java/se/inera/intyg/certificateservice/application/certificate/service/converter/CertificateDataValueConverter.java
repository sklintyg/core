package se.inera.intyg.certificateservice.application.certificate.service.converter;

import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

public interface CertificateDataValueConverter {


  ElementType getType();

  CertificateDataValue convert(ElementSpecification elementSpecification,
      ElementValue elementValue);


}
