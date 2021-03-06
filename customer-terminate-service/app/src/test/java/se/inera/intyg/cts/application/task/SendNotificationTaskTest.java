package se.inera.intyg.cts.application.task;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.cts.application.service.MessageService;

@ExtendWith(MockitoExtension.class)
class SendNotificationTaskTest {

    @Mock
    MessageService messageService;

    @InjectMocks
    SendNotificationTask sendNotificationTask;

    @Test
    void testSendExportedPackageNotification() {
        sendNotificationTask.sendNotification();
        verify(messageService, times(1)).sendNotification();
    }
}