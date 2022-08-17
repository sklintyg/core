package se.inera.intyg.cts.domain.service;

import se.inera.intyg.cts.domain.model.Termination;

public interface SendPackagePassword {

  void sendPassword(Termination termination);

  void resendPassword(Termination termination);
}
