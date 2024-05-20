package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
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
    return MessageEntity.builder()
        .id(message.id().id())
        .reference(message.reference().reference())
        .subject(message.subject().subject())
        .content(message.content().content())
        .author(message.author().author())
        .created(message.created())
        .modified(message.modified())
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
                .key(MessageStatusEnum.SENT.getKey())
                .status(MessageStatusEnum.SENT.name())
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

  public Message toDomain(MessageEntity savedEntity) {
    return Message.builder()
        .id(new MessageId(savedEntity.getId()))
        .reference(new SenderReference(savedEntity.getReference()))
        .certificateId(new CertificateId(savedEntity.getCertificate().getCertificateId()))
        .type(MessageType.valueOf(savedEntity.getMessageType().getType()))
        .status(MessageStatus.valueOf(savedEntity.getStatus().getStatus()))
        .subject(new Subject(savedEntity.getSubject()))
        .content(new Content(savedEntity.getContent()))
        .author(new Author(savedEntity.getAuthor()))
        .created(savedEntity.getCreated())
        .modified(savedEntity.getModified())
        .sent(savedEntity.getSent())
        .forwarded(new Forwarded(savedEntity.isForwarded()))
        .lastDateToReply(savedEntity.getLastDateToReply())
        .contactInfo(
            new MessageContactInfo(
                savedEntity.getContactInfo().stream()
                    .map(MessageContactInfoEmbeddable::getInfo)
                    .toList()
            )
        )
        .complements(
            savedEntity.getComplements().stream()
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
