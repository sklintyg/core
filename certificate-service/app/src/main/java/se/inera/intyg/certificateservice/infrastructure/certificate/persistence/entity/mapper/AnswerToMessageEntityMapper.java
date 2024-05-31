package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.util.UUID;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEnum;

@Component
public class AnswerToMessageEntityMapper {

  public MessageEntity toEntity(MessageEntity messageEntity, Answer answer) {
    return MessageEntity.builder()
        .id(UUID.randomUUID().toString())
        .reference(answer.reference().reference())
        .subject(answer.subject().subject())
        .content(answer.content().content())
        .author(answer.author().author())
        .created(answer.created())
        .modified(answer.modified())
        .sent(answer.sent())
        .status(
            MessageStatusEntity.builder()
                .key(MessageStatusEnum.valueOf(answer.status().name()).getKey())
                .status(MessageStatusEnum.valueOf(answer.status().name()).name())
                .build()
        )
        .messageType(
            MessageTypeEntity.builder()
                .key(MessageTypeEnum.valueOf(answer.type().name()).getKey())
                .type(MessageTypeEnum.valueOf(answer.type().name()).name())
                .build()
        )
        .certificate(messageEntity.getCertificate())
        .forwarded(messageEntity.isForwarded())
        .build();
  }
}
