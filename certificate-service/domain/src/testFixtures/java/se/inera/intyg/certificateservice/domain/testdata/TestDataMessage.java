package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;

import se.inera.intyg.certificateservice.domain.message.model.Message;

public class TestDataMessage {

  public static Message COMPLEMENT_MESSAGE = messageBuilder().build();

  public static Message.MessageBuilder messageBuilder() {
    return Message.builder()
        .certificateId(CERTIFICATE_ID);
  }
}
