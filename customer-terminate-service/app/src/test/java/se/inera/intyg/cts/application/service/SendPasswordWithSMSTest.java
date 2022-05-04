package se.inera.intyg.cts.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.terminationWithPhoneNumber;

import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.infrastructure.integration.SendSMS;
import se.inera.intyg.cts.infrastructure.integration.tellustalk.dto.SMSResponseDTO;
import se.inera.intyg.cts.infrastructure.service.SendPasswordWithSMS;
import se.inera.intyg.cts.testutil.TerminationTestDataBuilder;

@ExtendWith(MockitoExtension.class)
class SendPasswordWithSMSTest {

    @Mock
    private SendSMS sendSMS;
    @Mock
    private TerminationRepository terminationRepository;

    @Captor
    private ArgumentCaptor<String> capturePhoneNumber = ArgumentCaptor.forClass(String.class);

    @InjectMocks
    private SendPasswordWithSMS smsService;

    private static final String COMPLIANT_PHONE_NUMBER = "sms:+46701234567";

    @Test
    void sendPassword() {
        Termination termination = terminationWithPhoneNumber(COMPLIANT_PHONE_NUMBER);
        setMocks(termination);

        smsService.sendPassword(termination);

        verify(terminationRepository, times(1)).store(termination);
        verify(terminationRepository, times(1)).findByTerminationId(termination.terminationId());
        verify(sendSMS, times(1)).sendSMS(termination.export().organizationRepresentative()
            .phoneNumber().number(), termination.export().password().password());
    }

    @Test
    void shouldThrowExceptionIfStatusUpdateFailure() {
        Termination termination = defaultTermination();
        when(sendSMS.sendSMS(any(String.class), any(String.class))).thenReturn(new SMSResponseDTO("ID", "URL"));
        when(terminationRepository.findByTerminationId(any(TerminationId.class))).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> smsService.sendPassword(termination));
    }

    @Nested
    class testPhoneNumberFormatting {

        @Test
        void shouldHandleCompliantPhoneNumber() {
            final var termination = TerminationTestDataBuilder.terminationWithPhoneNumber(COMPLIANT_PHONE_NUMBER);
            setMocks(termination);

            smsService.sendPassword(termination);

            verify(sendSMS).sendSMS(capturePhoneNumber.capture(), any(String.class));
            assertEquals(COMPLIANT_PHONE_NUMBER, capturePhoneNumber.getValue());
        }

        @Test
        void shouldHandlePhoneNumberWithCountryCode() {
            final var termination = TerminationTestDataBuilder.terminationWithPhoneNumber("+46701234567");
            setMocks(termination);

            smsService.sendPassword(termination);

            verify(sendSMS).sendSMS(capturePhoneNumber.capture(), any(String.class));
            assertEquals(COMPLIANT_PHONE_NUMBER, capturePhoneNumber.getValue());
        }

        @Test
        void shouldHandleStandardPhoneNumberFormat() {
            final var termination = TerminationTestDataBuilder.terminationWithPhoneNumber("070-1234567");
            setMocks(termination);

            smsService.sendPassword(termination);

            verify(sendSMS).sendSMS(capturePhoneNumber.capture(), any(String.class));
            assertEquals(COMPLIANT_PHONE_NUMBER, capturePhoneNumber.getValue());
        }

        @Test
        void shouldHandleSomeNonStandardPhoneNumberFormat() {
            final var termination = TerminationTestDataBuilder.terminationWithPhoneNumber("+70-123R4 5-67");
            setMocks(termination);

            smsService.sendPassword(termination);

            verify(sendSMS).sendSMS(capturePhoneNumber.capture(), any(String.class));
            assertEquals(COMPLIANT_PHONE_NUMBER, capturePhoneNumber.getValue());
        }
    }

    private void setMocks(Termination termination) {
        when(sendSMS.sendSMS(any(String.class), any(String.class))).thenReturn(new SMSResponseDTO("ID", "URL"));
        when(terminationRepository.findByTerminationId(any(TerminationId.class))).thenReturn(Optional.of(termination));
    }
}
