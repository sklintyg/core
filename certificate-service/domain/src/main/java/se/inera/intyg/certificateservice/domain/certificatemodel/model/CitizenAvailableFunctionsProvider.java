package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public interface CitizenAvailableFunctionsProvider {

  List<CitizenAvailableFunction> of(Certificate certificate);

}
