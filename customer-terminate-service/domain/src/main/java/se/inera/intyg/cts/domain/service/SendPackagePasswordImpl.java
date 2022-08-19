package se.inera.intyg.cts.domain.service;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import javax.management.OperationsException;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationStatus;
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
   * Resend the password. This can only be done if the password has been sent at least once.
   * @param termination Id of the termination.
   */
  @Override
  public void resendPassword(Termination termination) {

    if(termination.status().equals(TerminationStatus.PASSWORD_SENT) || termination.status().equals(TerminationStatus.PASSWORD_RESENT)){
      if (sendPassword.sendPassword(termination)) {
        termination.passwordResent();
        terminationRepository.store(termination);
        return;
      }
      throw new RuntimeException(String.format("Could not store status %s for %t", TerminationStatus.PASSWORD_RESENT, termination.terminationId().id()));
     }
    throw new IllegalArgumentException(String.format("Invalid status: %s to resend password.", termination.status()));
  }
}
