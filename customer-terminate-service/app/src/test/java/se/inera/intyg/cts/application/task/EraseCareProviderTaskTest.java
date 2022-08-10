package se.inera.intyg.cts.application.task;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.cts.application.service.EraseServiceImpl;

@ExtendWith(MockitoExtension.class)
class EraseCareProviderTaskTest {

  @Mock
  private EraseServiceImpl eraseService;

  @InjectMocks
  private EraseCareProviderTask eraseCareProviderTask;

  @Test
  void eraseCareProvider() {
    eraseCareProviderTask.eraseCareProvider();

    verify(eraseService, times(1)).erase();
  }
}