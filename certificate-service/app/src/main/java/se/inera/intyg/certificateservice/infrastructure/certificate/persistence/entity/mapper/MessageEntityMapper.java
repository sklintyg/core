package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Complement;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageContactInfo;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Reminder;
import se.inera.intyg.certificateservice.domain.message.model.SenderReference;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.StaffRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageComplementEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageContactInfoEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageRelationEntityRepository;

@Component
@RequiredArgsConstructor
public class MessageEntityMapper {

  private final CertificateEntityRepository certificateEntityRepository;
  private final MessageRelationEntityRepository messageRelationEntityRepository;
  private final StaffRepository staffEntityRepository;


  public MessageEntity toEntity(Message message, Integer key) {
    final var messageStatusEnum = MessageStatusEnum.valueOf(message.status().name());
    final var messageTypeEnum = MessageTypeEnum.valueOf(message.type().name());
    return MessageEntity.builder()
        .key(key)
        .id(message.id().id())
        .reference(message.reference() != null ? message.reference().reference() : null)
        .subject(message.subject() != null ? message.subject().subject() : null)
        .content(message.content().content())
        .author(message.author().author())
        .created(message.created() == null ? LocalDateTime.now(ZoneId.systemDefault())
            : message.created())
        .modified(message.modified() == null ? LocalDateTime.now(ZoneId.systemDefault())
            : message.modified())
        .sent(message.sent())
        .forwarded(message.forwarded().value())
        .lastDateToReply(message.lastDateToReply())
        .authoredByStaff(
            message.authoredStaff() != null ? staffEntityRepository.staff(message.authoredStaff())
                : null)
        .messageType(
            MessageTypeEntity.builder()
                .key(messageTypeEnum.getKey())
                .type(messageTypeEnum.name())
                .build()
        )
        .status(
            MessageStatusEntity.builder()
                .key(messageStatusEnum.getKey())
                .status(messageStatusEnum.name())
                .build()
        )
        .contactInfo(
            message.contactInfo() != null ? message.contactInfo().lines().stream()
                .map(info ->
                    MessageContactInfoEmbeddable.builder()
                        .info(info)
                        .build()
                )
                .toList()
                : null
        )
        .complements(
            message.complements().stream()
                .map(complement ->
                    MessageComplementEmbeddable.builder()
                        .elementId(complement.elementId().id())
                        .fieldId(complement.fieldId() == null ? null : complement.fieldId().value())
                        .content(complement.content().content())
                        .build()
                )
                .toList()
        )
        .certificate(
            certificateEntityRepository.findByCertificateId(message.certificateId().id())
                .orElseThrow(() -> new IllegalStateException(
                        "Certificate with id '%s'not found!".formatted(message.certificateId().id())
                    )
                )
        )
        .build();
  }

  public Message toDomain(MessageEntity messageEntity) {
    final var messageRelations = messageRelationEntityRepository.findByParentMessage(
        messageEntity
    );

    return Message.builder()
        .id(new MessageId(messageEntity.getId()))
        .reference(new SenderReference(messageEntity.getReference()))
        .certificateId(new CertificateId(messageEntity.getCertificate().getCertificateId()))
        .personId(
            PersonId.builder()
                .id(messageEntity.getCertificate().getPatient().getId())
                .type(
                    PersonIdType.valueOf(
                        messageEntity.getCertificate().getPatient().getType().getType()
                    )
                )
                .build()
        )
        .type(MessageType.valueOf(messageEntity.getMessageType().getType()))
        .status(MessageStatus.valueOf(messageEntity.getStatus().getStatus()))
        .subject(new Subject(messageEntity.getSubject()))
        .content(new Content(messageEntity.getContent()))
        .author(new Author(messageEntity.getAuthor()))
        .created(messageEntity.getCreated())
        .modified(messageEntity.getModified())
        .sent(messageEntity.getSent())
        .forwarded(new Forwarded(messageEntity.isForwarded()))
        .lastDateToReply(messageEntity.getLastDateToReply())
        .authoredStaff(messageEntity.getAuthoredByStaff() != null ? StaffEntityMapper.toDomain(
            messageEntity.getAuthoredByStaff()) : null)
        .contactInfo(convertContactInfo(messageEntity))
        .complements(
            messageEntity.getComplements().stream()
                .map(complement ->
                    Complement.builder()
                        .elementId(new ElementId(complement.getElementId()))
                        .fieldId(complement.getFieldId() == null ? null
                            : new FieldId(complement.getFieldId()))
                        .content(new Content(complement.getContent()))
                        .build()
                )
                .toList()
        )
        .answer(
            messageRelations.stream()
                .filter(relation -> relation.getMessageRelationType().getType()
                    .equals(MessageRelationType.ANSWER.name()))
                .findFirst()
                .map(MessageRelationEntity::getChildMessage)
                .map(childMessage ->
                    Answer.builder()
                        .id(new MessageId(childMessage.getId()))
                        .reference(
                            childMessage.getReference() != null
                                ? new SenderReference(childMessage.getReference())
                                : null)
                        .type(MessageType.valueOf(childMessage.getMessageType().getType()))
                        .created(childMessage.getCreated())
                        .subject(
                            childMessage.getSubject() != null
                                ? new Subject(childMessage.getSubject())
                                : null)
                        .content(new Content(childMessage.getContent()))
                        .modified(childMessage.getModified())
                        .sent(childMessage.getSent())
                        .status(MessageStatus.valueOf(childMessage.getStatus().getStatus()))
                        .author(new Author(childMessage.getAuthor()))
                        .authoredStaff(
                            childMessage.getAuthoredByStaff() != null
                                ? StaffEntityMapper.toDomain(childMessage.getAuthoredByStaff())
                                : null)
                        .contactInfo(convertContactInfo(childMessage))
                        .build()
                )
                .orElse(null)
        )
        .reminders(
            messageRelations.stream()
                .filter(relation -> relation.getMessageRelationType().getType()
                    .equals(MessageRelationType.REMINDER.name()))
                .map(MessageRelationEntity::getChildMessage)
                .map(childMessage ->
                    Reminder.builder()
                        .id(new MessageId(childMessage.getId()))
                        .reference(
                            childMessage.getReference() != null
                                ? new SenderReference(childMessage.getReference())
                                : null)
                        .created(childMessage.getCreated())
                        .subject(
                            childMessage.getSubject() != null
                                ? new Subject(childMessage.getSubject())
                                : null)
                        .content(new Content(childMessage.getContent()))
                        .sent(childMessage.getSent())
                        .author(new Author(childMessage.getAuthor()))
                        .build()
                )
                .toList()
        )
        .build();
  }

  private static MessageContactInfo convertContactInfo(MessageEntity messageEntity) {
    return new MessageContactInfo(
        messageEntity.getContactInfo() != null ? messageEntity.getContactInfo().stream()
            .map(MessageContactInfoEmbeddable::getInfo)
            .toList()
            : Collections.emptyList()
    );
  }
}
