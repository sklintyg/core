package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public interface CertificateAvailableFunctionsProvider {

  List<AvailableFunction> of(Certificate certificate);

}
