package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Complement;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageContactInfo;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.SenderReference;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageComplementEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageContactInfoEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.CertificateEntityRepository;

@Component
@RequiredArgsConstructor
public class MessageEntityMapper {

  private final CertificateEntityRepository certificateEntityRepository;

  public MessageEntity toEntity(Message message) {
    final var messageStatusEnum = MessageStatusEnum.valueOf(message.status().name());
    return MessageEntity.builder()
        .id(message.id().id())
        .reference(message.reference().reference())
        .subject(message.subject().subject())
        .content(message.content().content())
        .author(message.author().author())
        .created(message.created() == null ? LocalDateTime.now(ZoneId.systemDefault())
            : message.created())
        .modified(message.modified() == null ? LocalDateTime.now(ZoneId.systemDefault())
            : message.modified())
        .sent(message.sent())
        .forwarded(message.forwarded().value())
        .lastDateToReply(message.lastDateToReply())
        .messageType(
            MessageTypeEntity.builder()
                .key(MessageTypeEnum.COMPLEMENT.getKey())
                .type(MessageTypeEnum.COMPLEMENT.name())
                .build()
        )
        .status(
            MessageStatusEntity.builder()
                .key(messageStatusEnum.getKey())
                .status(messageStatusEnum.name())
                .build()
        )
        .contactInfo(
            message.contactInfo().lines().stream()
                .map(info ->
                    MessageContactInfoEmbeddable.builder()
                        .info(info)
                        .build()
                )
                .toList()
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
        .contactInfo(
            new MessageContactInfo(
                messageEntity.getContactInfo().stream()
                    .map(MessageContactInfoEmbeddable::getInfo)
                    .toList()
            )
        )
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
        .build();
  }
}
