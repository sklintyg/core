package se.inera.intyg.cts.infrastructure.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.infrastructure.integration.SendSMS;
import se.inera.intyg.cts.infrastructure.integration.tellustalk.dto.SMSResponseDTO;

@ExtendWith(MockitoExtension.class)
class SendPasswordImplTest {

    @Mock
    private SendSMS sendSMS;

    @InjectMocks
    private SendPasswordImpl smsService;

    @Test
    void sendPassword() {
        Termination termination = defaultTermination();
        when(sendSMS.sendSMS("1212", "Hej")).thenReturn(new SMSResponseDTO("ID", "URL"));

        smsService.sendPassword(termination);

        verify(sendSMS, times(1)).sendSMS("1212", "Hej");
    }
}