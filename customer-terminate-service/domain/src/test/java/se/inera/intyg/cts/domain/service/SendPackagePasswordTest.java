package se.inera.intyg.cts.domain.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.domain.repository.TerminationRepository;

@ExtendWith(MockitoExtension.class)
class SendPackagePasswordTest {

  @Mock
  private SendPassword sendPassword;

  @Mock
  private TerminationRepository terminationRepository;

  @InjectMocks
  private SendPackagePasswordImpl sendPackagePassword;

  @Mock
  private Termination termination;

  @Nested
  class sendPasswordTest {
    @Test
    public void shouldUpdateTerminationWhenSuccessfulPassword() {
      when(sendPassword.sendPassword(termination)).thenReturn(true);

      sendPackagePassword.sendPassword(termination);

      verify(sendPassword, times(1)).sendPassword(termination);
      verify(termination, times(1)).passwordSent();
      verify(terminationRepository, times(1)).store(termination);
    }

    @Test
    public void shouldNotUpdateTerminationWhenFailedPassword() {
      when(sendPassword.sendPassword(termination)).thenReturn(false);

      sendPackagePassword.sendPassword(termination);

      verify(sendPassword, times(1)).sendPassword(termination);
      verify(termination, times(0)).passwordSent();
      verify(terminationRepository, times(0)).store(termination);
    }
  }

  @Nested
  class resendPasswordTest {

    @Test
    public void resendPassword() {
      when(termination.status())
          .thenReturn(TerminationStatus.PASSWORD_SENT)
          .thenReturn(TerminationStatus.PASSWORD_RESENT);
      when(sendPassword.sendPassword(termination)).thenReturn(true);

      sendPackagePassword.resendPassword(termination);
      sendPackagePassword.resendPassword(termination);

      verify(termination, times(3)).status();
      verify(sendPassword, times(2)).sendPassword(termination);
      verify(termination, times(2)).passwordResent();
      verify(terminationRepository, times(2)).store(termination);
    }

    @Test
    public void resendPasswordRuntimeException() {
      when(termination.status()).thenReturn(TerminationStatus.PASSWORD_RESENT);
      when(sendPassword.sendPassword(termination)).thenReturn(false);

      assertThrows(RuntimeException.class, () -> {
        sendPackagePassword.resendPassword(termination);
      });

      verify(termination, times(2)).status();
      verify(sendPassword, times(1)).sendPassword(termination);
      verify(termination, times(0)).passwordResent();
      verify(terminationRepository, times(0)).store(termination);
    }

    @Test
    public void resendPasswordIllegalArgumentException() {
      when(termination.status()).thenReturn(TerminationStatus.CREATED);

      assertThrows(IllegalArgumentException.class, () -> {
        sendPackagePassword.resendPassword(termination);
      });

      verify(termination, times(3)).status();
      verify(sendPassword, times(0)).sendPassword(termination);
      verify(termination, times(0)).passwordResent();
      verify(terminationRepository, times(0)).store(termination);
    }
  }
}
