package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateConstants.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REFERENCE_ID;

import java.util.List;
import se.inera.intyg.certificateservice.application.message.dto.IncomingComplementDTO;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest;
import se.inera.intyg.certificateservice.application.message.dto.IncomingMessageRequest.IncomingMessageRequestBuilder;
import se.inera.intyg.certificateservice.application.message.dto.MessageTypeDTO;
import se.inera.intyg.certificateservice.application.message.dto.SentByDTO;
import se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants;

public class TestDataIncomingMessage {

  public final static IncomingMessageRequest INCOMING_COMPLEMENT_MESSAGE = incomingComplementMessageBuilder().build();

  public static IncomingMessageRequestBuilder incomingComplementMessageBuilder() {
    return IncomingMessageRequest.builder()
        .id(MESSAGE_ID)
        .referenceId(REFERENCE_ID)
        .certificateId(CERTIFICATE_ID)
        .subject(TestDataMessageConstants.SUBJECT)
        .content(TestDataMessageConstants.CONTENT)
        .sent(TestDataMessageConstants.SENT)
        .type(MessageTypeDTO.KOMPLT)
        .contactInfo(TestDataMessageConstants.CONTACT_INFO)
        .sentBy(SentByDTO.FK)
        .lastDateToAnswer(TestDataMessageConstants.LAST_DATE_TO_ANSWER)
        .personId(TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_PERSON_ID_DTO)
        .complements(
            List.of(
                IncomingComplementDTO.builder()
                    .questionId(TestDataMessageConstants.COMPLEMENT_QUESTION_ID_ONE)
                    .instance(TestDataMessageConstants.INSTANCE_ONE)
                    .content(TestDataMessageConstants.COMPLEMENT_TEXT_ONE)
                    .build()
            )
        );
  }
}
