package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.StaffRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageContactInfoEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEnum;

@Component
@RequiredArgsConstructor
public class AnswerToMessageEntityMapper {

  private final StaffRepository staffEntityRepository;

  public MessageEntity toEntity(MessageEntity messageEntity, Answer answer, Integer key) {
    return MessageEntity.builder()
        .key(key)
        .id(answer.id() != null ? answer.id().id() : UUID.randomUUID().toString())
        .reference(answer.reference() != null ? answer.reference().reference() : null)
        .subject(answer.subject().subject())
        .content(answer.content().content())
        .author(answer.author().author())
        .created(answer.created() == null ? LocalDateTime.now(ZoneId.systemDefault())
            : answer.created())
        .modified(answer.modified() == null ? LocalDateTime.now(ZoneId.systemDefault())
            : answer.modified())
        .sent(answer.sent())
        .status(
            MessageStatusEntity.builder()
                .key(MessageStatusEnum.valueOf(answer.status().name()).getKey())
                .status(MessageStatusEnum.valueOf(answer.status().name()).name())
                .build()
        )
        .messageType(
            MessageTypeEntity.builder()
                .key(MessageTypeEnum.ANSWER.getKey())
                .type(MessageTypeEnum.ANSWER.name())
                .build()
        )
        .authoredByStaff(
            answer.authoredStaff() != null
                ? staffEntityRepository.staff(answer.authoredStaff())
                : null
        )
        .contactInfo(
            answer.contactInfo() != null ? answer.contactInfo().lines().stream()
                .map(info ->
                    MessageContactInfoEmbeddable.builder()
                        .info(info)
                        .build()
                )
                .toList()
                : null
        )
        .certificate(messageEntity.getCertificate())
        .forwarded(messageEntity.isForwarded())
        .build();
  }
}
