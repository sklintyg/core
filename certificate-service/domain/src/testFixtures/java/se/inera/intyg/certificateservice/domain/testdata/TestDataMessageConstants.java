package se.inera.intyg.certificateservice.domain.testdata;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class TestDataMessageConstants {

  public static final String MESSAGE_ID = "MESSAGE_ID";
  public static final String REFERENCE_ID = "REFERENCE_ID";

  public static final String ANSWER_ID = "ANSWER_ID";
  public static final String ANSWER_REFERENCE_ID = "ANSWER_REFERENCE_ID";

  public static final String REMINDER_MESSAGE_ID = "REMINDER_MESSAGE_ID";
  public static final String ANSWER_MESSAGE_ID = "ANSWER_MESSAGE_ID";
  public static final String REMINDER_REFERENCE_ID = "REMINDER_REFERENCE_ID";

  public static final String SUBJECT = "This is the subject";
  public static final String CONTENT = "This is the content of the message";
  public static final String REMINDER_CONTENT = "This is a reminder";
  public static final LocalDateTime SENT = LocalDateTime.now(ZoneId.systemDefault());
  public static final LocalDateTime CREATED_AFTER_SENT = SENT.plusDays(1);
  public static final List<String> CONTACT_INFO = List.of(
      "Contact info 1", "Contact info 2", "Contact info 3"
  );
  public static final LocalDate LAST_DATE_TO_REPLY = LocalDate.now(ZoneId.systemDefault())
      .plusDays(10);
  public static final String COMPLEMENT_QUESTION_ID_ONE = "55";
  public static final String COMPLEMENT_FIELD_ID_ONE = "55.1";
  public static final Integer INSTANCE_ONE = 0;
  public static final String COMPLEMENT_TEXT_ONE = "This is text for the first question";
  public static final String AUTHOR_INCOMING_MESSAGE = "FK";
  public static final String AUTHOR_INCOMING_MESSAGE_NAME = "Försäkringskassan";
}
