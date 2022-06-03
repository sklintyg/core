package se.inera.intyg.cts.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.terminationWithCreatedDate;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;
import se.inera.intyg.cts.domain.service.SendNotification;
import se.inera.intyg.cts.domain.service.SendPassword;
import se.inera.intyg.cts.testutil.TerminationTestDataBuilder;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private TerminationRepository terminationRepositoryMock;
    @Mock
    private SendPassword sendPasswordMock;
    @Mock
    private SendNotification sendNotification;
    @InjectMocks
    private MessageServiceImpl messageService;

    private final Termination termination1 = defaultTermination();
    private final Termination termination2 = defaultTermination();
    private final List<Termination> terminations = List.of(termination1, termination2);

    @Nested
    class TestSendPassword {

        @Test
        void sendPassword() {
            ReflectionTestUtils.setField(messageService, "sendPasswordActive", true);
            when(terminationRepositoryMock.findByStatuses(anyList())).thenReturn(terminations);

            messageService.sendPassword();

            verify(terminationRepositoryMock, times(1)).findByStatuses(anyList());
            verify(sendPasswordMock, times(2)).sendPassword(any(Termination.class));
        }

        @Test
        void sendPasswordForAllEvenIfOneFail() {
            ReflectionTestUtils.setField(messageService, "sendPasswordActive", true);
            when(terminationRepositoryMock.findByStatuses(anyList())).thenReturn(terminations);
            doThrow(new RuntimeException()).when(sendPasswordMock).sendPassword(termination1);

            messageService.sendPassword();

            verify(terminationRepositoryMock, times(1)).findByStatuses(anyList());
            verify(sendPasswordMock, times(2)).sendPassword(any(Termination.class));
        }

        @Test
        void shouldNotSendPasswordWhenSendPasswordInactive() {
            ReflectionTestUtils.setField(messageService, "sendPasswordActive", false);
            when(terminationRepositoryMock.findByStatuses(anyList())).thenReturn(terminations);

            messageService.sendPassword();

            verifyNoInteractions(sendPasswordMock);
        }
    }

    @Nested
    class TestSendNotification {

        @Test
        void sendNotification() {
            ReflectionTestUtils.setField(messageService, "sendNotificationsActive", true);
            when(terminationRepositoryMock.findByStatuses(anyList())).thenReturn(terminations);

            messageService.sendNotification();

            verify(terminationRepositoryMock, times(1)).findByStatuses(anyList());
            verify(sendNotification, times(2)).sendNotification(any(Termination.class));
        }

        @Test
        void shouldNotSendNotificationWhenSendNotificationInactive() {
            ReflectionTestUtils.setField(messageService, "sendNotificationsActive", false);
            when(terminationRepositoryMock.findByStatuses(anyList())).thenReturn(terminations);

            messageService.sendNotification();

            verifyNoInteractions(sendNotification);
        }
    }

    @Nested
    class TestSendReminder {

        @Test
        void shouldSendReminderWhenPassedReminderTime() {
            final var createdDate = LocalDateTime.now().minusDays(15L);
            final var termination = terminationWithCreatedDate(createdDate);
            ReflectionTestUtils.setField(messageService, "sendNotificationsActive", true);
            ReflectionTestUtils.setField(messageService, "reminderDelayInDays", 14);
            when(terminationRepositoryMock.findByStatuses(anyList())).thenReturn(List.of(termination));

            messageService.sendReminder();

            verify(terminationRepositoryMock, times(1)).findByStatuses(anyList());
            verify(sendNotification, times(1)).sendReminder(any(Termination.class));
        }

        @Test
        void shouldNotSendReminderWhenNotPassedReminderTime() {
            final var createdDate = LocalDateTime.now().minusDays(13L);
            final var termination = terminationWithCreatedDate(createdDate);
            ReflectionTestUtils.setField(messageService, "sendNotificationsActive", true);
            ReflectionTestUtils.setField(messageService, "reminderDelayInDays", 14);
            when(terminationRepositoryMock.findByStatuses(anyList())).thenReturn(List.of(termination));

            messageService.sendReminder();

            verify(terminationRepositoryMock, times(1)).findByStatuses(anyList());
            verifyNoInteractions(sendNotification);
        }

        @Test
        void shouldNotSendReminderWhenSendNotificationInactive() {
            ReflectionTestUtils.setField(messageService, "sendNotificationsActive", false);
            when(terminationRepositoryMock.findByStatuses(anyList())).thenReturn(terminations);

            messageService.sendReminder();

            verifyNoInteractions(sendNotification);
        }
    }
}