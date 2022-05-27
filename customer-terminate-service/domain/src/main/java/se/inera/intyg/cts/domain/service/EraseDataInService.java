package se.inera.intyg.cts.domain.service;

import se.inera.intyg.cts.domain.model.CareProvider;
import se.inera.intyg.cts.domain.model.ServiceId;

public interface EraseDataInService {

  void erase(CareProvider careProvider) throws EraseException;

  ServiceId serviceId();
}
