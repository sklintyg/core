package se.inera.intyg.cts.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.cts.domain.util.TerminationTestDataFactory.terminationWithStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.domain.repository.InMemoryTerminationRepository;

@ExtendWith(MockitoExtension.class)
class SendPackagePasswordTest {

  @Mock
  private SendPassword sendPassword;

  private SendPackagePassword sendPackagePassword;
  private InMemoryTerminationRepository terminationRepository;

  @BeforeEach
  void setUp() {
    terminationRepository = new InMemoryTerminationRepository();
    sendPackagePassword = new SendPackagePasswordImpl(sendPassword, terminationRepository);
  }

  @Test
  public void shouldUpdateTerminationWhenSuccessfulPassword() {
    final var termination = createTermination();
    doReturn(true).when(sendPassword).sendPassword(termination);

    sendPackagePassword.sendPassword(termination);

    assertEquals(TerminationStatus.PASSWORD_SENT,
        termination(termination.terminationId()).status());
  }

  @Test
  public void shouldNotUpdateTerminationWhenFailedPassword() {
    final var termination = createTermination();
    doReturn(false).when(sendPassword).sendPassword(termination);

    sendPackagePassword.sendPassword(termination);

    assertEquals(TerminationStatus.RECEIPT_RECEIVED,
        termination(termination.terminationId()).status());
  }

  private Termination createTermination() {
    final var termination = terminationWithStatus(TerminationStatus.RECEIPT_RECEIVED);
    terminationRepository.store(termination);
    return termination;
  }

  private Termination termination(TerminationId terminationId) {
    return terminationRepository.findByTerminationId(terminationId).orElseThrow();
  }

}
