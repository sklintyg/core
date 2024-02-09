package se.inera.intyg.certificateservice.application.certificate.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValue;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;

@Component
public class CertificateDataValueConverter {

  public CertificateDataValue convert(ElementData elementValue) {
    if (elementValue.value() == null) {
      return null;
    }
    return switch (elementValue.value().type()) {
      case DATE -> {
        final var value = (ElementValueDate) elementValue.value();
        yield CertificateDataValueDate.builder()
            .id(elementValue.id().id())
            .date(value.date())
            .build();
      }
    };
  }
}
