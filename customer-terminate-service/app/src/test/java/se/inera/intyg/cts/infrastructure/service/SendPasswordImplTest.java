package se.inera.intyg.cts.infrastructure.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.infrastructure.integration.SendSMS;
import se.inera.intyg.cts.infrastructure.integration.tellustalk.dto.SMSResponseDTO;

@ExtendWith(MockitoExtension.class)
class SendPasswordImplTest {

    @Mock
    private SendSMS sendSMS;
    @Mock
    private TerminationRepository terminationRepository;
    private SendPasswordImpl smsService;

    @Test
    void sendPasswordSmsActive() {
        smsService = new SendPasswordImpl(sendSMS, terminationRepository, "true");

        Termination termination = defaultTermination();
        when(sendSMS.sendSMS(termination.export().organizationRepresentative().phoneNumber().number(), termination.export().password().password())).thenReturn(new SMSResponseDTO("ID", "URL"));
        when(terminationRepository.findByTerminationId(termination.terminationId())).thenReturn(Optional.of(termination));

        smsService.sendPassword(termination);

        verify(sendSMS, times(1)).sendSMS(termination.export().organizationRepresentative().phoneNumber().number(), termination.export().password().password());
        verify(terminationRepository, times(1)).findByTerminationId(termination.terminationId());
        verify(terminationRepository, times(1)).store(termination);
    }

    @Test
    void sendPasswordSmsinactive() {
        smsService = new SendPasswordImpl(sendSMS, terminationRepository, "false");

        Termination termination = defaultTermination();
        when(terminationRepository.findByTerminationId(termination.terminationId())).thenReturn(Optional.of(termination));

        smsService.sendPassword(termination);

        verify(sendSMS, times(0)).sendSMS(anyString(), any());
        verify(terminationRepository, times(1)).findByTerminationId(termination.terminationId());
        verify(terminationRepository, times(1)).store(termination);
    }
}