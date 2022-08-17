package se.inera.intyg.cts.domain.service;

import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;

public class SendPackagePasswordImpl implements SendPackagePassword {

  private final SendPassword sendPassword;
  private final TerminationRepository terminationRepository;

  public SendPackagePasswordImpl(SendPassword sendPassword,
      TerminationRepository terminationRepository) {
    this.sendPassword = sendPassword;
    this.terminationRepository = terminationRepository;
  }

  @Override
  public void sendPassword(Termination termination) {
    final var sendPasswordSuccess = sendPassword.sendPassword(termination);

    if (sendPasswordSuccess) {
      termination.passwordSent();
      terminationRepository.store(termination);
    }
  }

  /**
   * Send the password again
   * @param termination
   */
  @Override
  public void resendPassword(Termination termination) {
    final boolean sendPasswordSuccess = sendPassword.sendPassword(termination);

    if (sendPasswordSuccess) {
      termination.passwordResent();
      terminationRepository.store(termination);
    }
  }
}
