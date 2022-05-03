package se.inera.intyg.cts.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.SendPassword;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private TerminationRepository terminationRepositoryMock;
    @Mock
    private SendPassword sendPasswordMock;
    @InjectMocks
    private MessageServiceImpl messageService;

    private final Termination termination1 = defaultTermination();
    private final Termination termination2 = defaultTermination();
    private final List<Termination> terminations = List.of(termination1, termination2);

    @Test
    void sendPassword() {
        when(terminationRepositoryMock.findByStatuses(anyList())).thenReturn(terminations);

        messageService.sendPassword();

        verify(terminationRepositoryMock, times(1)).findByStatuses(anyList());
        verify(sendPasswordMock, times(2)).sendPassword(any(Termination.class));
    }

    @Test
    void sendPasswordForAllEvenIfOneFail() {
        when(terminationRepositoryMock.findByStatuses(anyList())).thenReturn(terminations);
        doThrow(new RuntimeException()).when(sendPasswordMock).sendPassword(termination1);

        messageService.sendPassword();

        verify(terminationRepositoryMock, times(1)).findByStatuses(anyList());
        verify(sendPasswordMock, times(2)).sendPassword(any(Termination.class));
    }
}