package se.inera.intyg.cts.domain.service;

import se.inera.intyg.cts.domain.model.ServiceId;
import se.inera.intyg.cts.domain.model.Termination;

public interface EraseDataInService {

  void erase(Termination termination) throws EraseException;

  ServiceId serviceId();
}
