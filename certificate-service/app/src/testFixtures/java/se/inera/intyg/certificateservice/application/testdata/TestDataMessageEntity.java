package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateEntity.CERTIFICATE_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.COMPLEMENT_QUESTION_ID_ONE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.COMPLEMENT_TEXT_ONE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTACT_INFO;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CREATED_AFTER_SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.LAST_DATE_TO_REPLY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REFERENCE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SUBJECT;

import java.util.List;
import se.inera.intyg.certificateservice.application.message.dto.SentByDTO;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageComplementEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageContactInfoEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEnum;

public class TestDataMessageEntity {

  public final static MessageEntity COMPLEMENT_MESSAGE_ENTITY = complementMessageEntityBuilder().build();

  public static MessageEntity.MessageEntityBuilder complementMessageEntityBuilder() {
    return MessageEntity.builder()
        .key(999)
        .id(MESSAGE_ID)
        .reference(REFERENCE_ID)
        .subject(SUBJECT)
        .content(CONTENT)
        .author(SentByDTO.FK.name())
        .created(CREATED_AFTER_SENT)
        .modified(CREATED_AFTER_SENT)
        .sent(SENT)
        .forwarded(false)
        .lastDateToReply(LAST_DATE_TO_REPLY)
        .status(
            MessageStatusEntity.builder()
                .key(MessageStatusEnum.SENT.getKey())
                .status(MessageStatusEnum.SENT.name())
                .build()
        )
        .messageType(
            MessageTypeEntity.builder()
                .key(MessageTypeEnum.COMPLEMENT.getKey())
                .type(MessageTypeEnum.COMPLEMENT.name())
                .build()
        )
        .contactInfo(
            CONTACT_INFO.stream()
                .map(info ->
                    MessageContactInfoEmbeddable.builder()
                        .info(info)
                        .build()
                )
                .toList()
        )
        .complements(
            List.of(
                MessageComplementEmbeddable.builder()
                    .elementId(COMPLEMENT_QUESTION_ID_ONE)
                    .content(COMPLEMENT_TEXT_ONE)
                    .build()
            )
        )
        .certificate(CERTIFICATE_ENTITY);
  }
}
