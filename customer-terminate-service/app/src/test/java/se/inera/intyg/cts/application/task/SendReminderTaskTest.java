package se.inera.intyg.cts.application.task;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.cts.application.service.MessageService;
import se.inera.intyg.cts.logging.MdcHelper;

@ExtendWith(MockitoExtension.class)
class SendReminderTaskTest {

  @Mock
  private MdcHelper mdcHelper;

  @Mock
  MessageService messageService;

  @InjectMocks
  SendReminderTask sendReminderTask;

  @Test
  void testSendExportedPackageReminder() {
    sendReminderTask.sendReminder();
    verify(messageService, times(1)).sendReminder();
  }
}