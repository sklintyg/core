package se.inera.intyg.certificateservice.application.message.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.REMINDER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.AUTHOR_INCOMING_MESSAGE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REMINDER_CONTENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REMINDER_MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SENT;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReminderConverterTest {

  @InjectMocks
  private ReminderConverter reminderConverter;

  @Test
  void shallIncludeId() {
    assertEquals(REMINDER_MESSAGE_ID,
        reminderConverter.convert(REMINDER).getId()
    );
  }

  @Test
  void shallIncludeAuthor() {
    assertEquals(AUTHOR_INCOMING_MESSAGE_NAME,
        reminderConverter.convert(REMINDER).getAuthor()
    );
  }

  @Test
  void shallIncludeSent() {
    assertEquals(SENT,
        reminderConverter.convert(REMINDER).getSent()
    );
  }

  @Test
  void shallIncludeMessage() {
    assertEquals(REMINDER_CONTENT,
        reminderConverter.convert(REMINDER).getMessage()
    );
  }
}