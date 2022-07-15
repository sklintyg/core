package se.inera.intyg.cts.domain.service;

import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;

public class SendPackagePassword {

  private final SendPassword sendPassword;
  private final TerminationRepository terminationRepository;

  public SendPackagePassword(SendPassword sendPassword,
      TerminationRepository terminationRepository) {
    this.sendPassword = sendPassword;
    this.terminationRepository = terminationRepository;
  }

  public void sendPassword(Termination termination) {
    final var sendPasswordSuccess = sendPassword.sendPassword(termination);

    if (sendPasswordSuccess) {
      termination.passwordSent();
      terminationRepository.store(termination);
    }
  }
}
