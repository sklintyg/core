package se.inera.intyg.cts.infrastructure.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.terminationWithPhoneNumber;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.infrastructure.integration.SendSMS;
import se.inera.intyg.cts.infrastructure.integration.tellustalk.dto.TellusTalkResponseDTO;

@ExtendWith(MockitoExtension.class)
class SendPasswordWithSMSTest {

    @Mock
    private SendSMS sendSMS;
    @Mock
    private TerminationRepository terminationRepository;
    @Mock
    private SmsPhoneNumberFormatter smsPhoneNumberFormatter;

    @InjectMocks
    private SendPasswordWithSMS smsService;

    private static final String COMPLIANT_PHONE_NUMBER = "sms:+46701234567";

    @Test
    void sendPassword() {
        Termination termination = terminationWithPhoneNumber(COMPLIANT_PHONE_NUMBER);
        when(terminationRepository.findByTerminationId(any(TerminationId.class))).thenReturn(Optional.of(termination));
        setMocks();

        smsService.sendPassword(termination);

        verify(terminationRepository, times(1)).store(termination);
        verify(smsPhoneNumberFormatter, times(1)).formatPhoneNumber(termination.export()
            .organizationRepresentative().phoneNumber().number());
        verify(terminationRepository, times(1)).findByTerminationId(termination.terminationId());
        verify(sendSMS, times(1)).sendSMS(termination.export().organizationRepresentative()
            .phoneNumber().number(), termination.export().password().password());
    }

    @Test
    void shouldThrowExceptionIfStatusUpdateFailure() {
        Termination termination = defaultTermination();
        when(terminationRepository.findByTerminationId(any(TerminationId.class))).thenReturn(Optional.empty());
        setMocks();

        assertThrows(IllegalStateException.class, () -> smsService.sendPassword(termination));
    }

    private void setMocks() {
        when(sendSMS.sendSMS(any(String.class), any(String.class))).thenReturn(new TellusTalkResponseDTO("ID", "URL"));
        when(smsPhoneNumberFormatter.formatPhoneNumber(any(String.class))).thenReturn(COMPLIANT_PHONE_NUMBER);
    }
}
