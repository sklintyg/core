package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7472CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7809CertificateBuilder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Revoked;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Complement;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;

class GetCertificateEventsOfTypeDomainServiceTest {

  private static final LocalDateTime NOW = LocalDateTime.now();
  private static final CertificateId CERTIFICATE_ID = new CertificateId("C_ID");
  private final GetCertificateEventsOfTypeDomainService getCertificateEventsOfTypeDomainService = new GetCertificateEventsOfTypeDomainService();

  @ParameterizedTest
  @EnumSource(value = CertificateEventType.class, names = {"DELETED", "LOCKED", "READY_FOR_SIGN",
      "INCOMING_MESSAGE_HANDLED", "INCOMING_MESSAGE_REMINDER", "OUTGOING_MESSAGE_HANDLED",
      "COPIED_BY", "COPIED_FROM", "CREATED_FROM", "RELATED_CERTIFICATE_REVOKED"})
  void shouldReturnEmptyListForNonHandledEvents(CertificateEventType type) {
    assertEquals(Collections.emptyList(),
        getCertificateEventsOfTypeDomainService.events(fk7809CertificateBuilder().build(), type));
  }

  @Nested
  class Created {

    private final Certificate certificate = fk7809CertificateBuilder().build();

    @Test
    void shouldReturnEvent() {
      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(certificate.created())
          .type(CertificateEventType.CREATED)
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate, CertificateEventType.CREATED)
      );
    }

    @Test
    void shouldNotReturnCreatedEventIfCertificateHasParentRelationReplace() {
      final var parent = fk7809CertificateBuilder()
          .id(CERTIFICATE_ID)
          .build();
      final var certificate = fk7809CertificateBuilder()
          .created(LocalDateTime.now())
          .parent(
              Relation.builder()
                  .certificate(parent)
                  .created(NOW)
                  .type(RelationType.REPLACE)
                  .build()

          )
          .build();

      final var createdEvent = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(certificate.created())
          .type(CertificateEventType.CREATED)
          .build();

      assertFalse(
          getCertificateEventsOfTypeDomainService.events(certificate, CertificateEventType.CREATED)
              .contains(createdEvent));
    }

    @Test
    void shouldNotReturnCreatedEventIfCertificateHasParentRelationComplement() {
      final var parent = fk7809CertificateBuilder()
          .id(CERTIFICATE_ID)
          .build();
      final var certificate = fk7809CertificateBuilder()
          .created(LocalDateTime.now())
          .parent(
              Relation.builder()
                  .certificate(parent)
                  .created(NOW)
                  .type(RelationType.COMPLEMENT)
                  .build()

          )
          .build();

      final var createdEvent = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(certificate.created())
          .type(CertificateEventType.CREATED)
          .build();

      assertFalse(
          getCertificateEventsOfTypeDomainService.events(certificate, CertificateEventType.CREATED)
              .contains(createdEvent));
    }

    @Test
    void shouldNotReturnCreatedEventIfCertificateHasParentRelationRenew() {
      final var parent = fk7809CertificateBuilder()
          .id(CERTIFICATE_ID)
          .build();
      final var certificate = fk7809CertificateBuilder()
          .created(LocalDateTime.now())
          .parent(
              Relation.builder()
                  .certificate(parent)
                  .created(NOW)
                  .type(RelationType.RENEW)
                  .build()

          )
          .build();

      final var createdEvent = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(certificate.created())
          .type(CertificateEventType.CREATED)
          .build();

      assertFalse(
          getCertificateEventsOfTypeDomainService.events(certificate, CertificateEventType.CREATED)
              .contains(createdEvent));
    }
  }

  @Nested
  class AvailableForPatient {

    @Test
    void shouldReturnEventIfSignedAndAvailableForPatient() {
      final var certificate = fk7809CertificateBuilder()
          .signed(NOW)
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(certificate.signed())
          .type(CertificateEventType.AVAILABLE_FOR_PATIENT)
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.AVAILABLE_FOR_PATIENT)
      );
    }

    @Test
    void shouldReturnEmptyListIfSignedAndNotAvailableForPatient() {
      final var certificate = fk7472CertificateBuilder()
          .signed(NOW)
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.AVAILABLE_FOR_PATIENT)
      );
    }

    @Test
    void shouldReturnEmptyEventIfNotSigned() {
      final var certificate = fk7809CertificateBuilder()
          .build();

      final var expected = CertificateEvent.builder()
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.AVAILABLE_FOR_PATIENT)
      );
    }
  }

  @Nested
  class Signed {

    @Test
    void shouldReturnEventIfSigned() {
      final var certificate = fk7809CertificateBuilder()
          .signed(NOW)
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(certificate.signed())
          .type(CertificateEventType.SIGNED)
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.SIGNED)
      );
    }

    @Test
    void shouldReturnEmptyEventIfNotSigned() {
      final var certificate = fk7809CertificateBuilder()
          .build();

      final var expected = CertificateEvent.builder()
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.SIGNED)
      );
    }
  }

  @Nested
  class SentTest {

    @Test
    void shouldReturnEventIfSent() {
      final var certificate = fk7809CertificateBuilder()
          .sent(
              Sent.builder()
                  .sentAt(NOW)
                  .build()
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.SENT)
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.SENT)
      );
    }

    @Test
    void shouldReturnEmptyListIfNotSent() {
      final var certificate = fk7809CertificateBuilder()
          .sent(null)
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.SENT)
      );
    }

    @Test
    void shouldReturnEmptyEventIfNotSentTimestamp() {
      final var certificate = fk7809CertificateBuilder()
          .sent(
              Sent.builder()
                  .build()
          )
          .build();

      final var expected = CertificateEvent.builder()
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.SENT)
      );
    }
  }

  @Nested
  class RequestForComplement {

    @Test
    void shouldReturnEventIfComplementRequestFromFK() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .complements(List.of(Complement.builder().build()))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.REQUEST_FOR_COMPLEMENT)
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.REQUEST_FOR_COMPLEMENT)
      );
    }

    @Test
    void shouldReturnSeveralEventsIfComplementRequestFromFKOnSeveralMessages() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .complements(List.of(Complement.builder().build()))
                      .sent(NOW)
                      .build(),
                  Message.builder()
                      .complements(List.of(Complement.builder().build()))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.REQUEST_FOR_COMPLEMENT)
          .build();

      assertEquals(
          List.of(expected, expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.REQUEST_FOR_COMPLEMENT)
      );
    }

    @Test
    void shouldReturnEmptyListIfNoComplementRequests() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.REQUEST_FOR_COMPLEMENT)
      );
    }
  }

  @Nested
  class RevokedTest {

    @Test
    void shouldReturnEventIfRevoked() {
      final var certificate = fk7809CertificateBuilder()
          .revoked(
              Revoked.builder()
                  .revokedAt(NOW)
                  .build()
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.REVOKED)
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.REVOKED)
      );
    }

    @Test
    void shouldReturnEmptyListIfNotSent() {
      final var certificate = fk7809CertificateBuilder()
          .revoked(null)
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.REVOKED)
      );
    }

    @Test
    void shouldReturnEmptyEventIfNotRevokedTimestamp() {
      final var certificate = fk7809CertificateBuilder()
          .revoked(
              Revoked.builder()
                  .build()
          )
          .build();

      final var expected = CertificateEvent.builder()
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.REVOKED)
      );
    }
  }

  @Nested
  class ExtendedTest {

    // Opposite behaviour from other relations to match behaviour in frontend

    @Test
    void shouldReturnEventIfParentRelationExists() {
      final var parent = fk7809CertificateBuilder()
          .id(CERTIFICATE_ID)
          .build();
      final var certificate = fk7809CertificateBuilder()
          .parent(
              Relation.builder()
                  .certificate(parent)
                  .created(NOW)
                  .type(RelationType.RENEW)
                  .build()
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.EXTENDED)
          .relatedCertificateId(parent.id())
          .relatedCertificateStatus(parent.status())
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate, CertificateEventType.EXTENDED)
      );
    }

    @Test
    void shouldReturnEmptyListIfNoParentRelation() {
      final var certificate = fk7809CertificateBuilder()
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.EXTENDED)
      );
    }
  }

  @Nested
  class ComplementedTest {

    @Test
    void shouldReturnEventIfChildRelationExists() {
      final var child = fk7809CertificateBuilder()
          .id(CERTIFICATE_ID)
          .build();
      final var certificate = fk7809CertificateBuilder()
          .children(
              List.of(
                  Relation.builder()
                      .certificate(child)
                      .created(NOW)
                      .type(RelationType.COMPLEMENT)
                      .build()
              )
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.COMPLEMENTED)
          .relatedCertificateId(child.id())
          .relatedCertificateStatus(child.status())
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.COMPLEMENTED)
      );
    }

    @Test
    void shouldReturnEventIfSeveralChildRelationsExists() {
      final var child = fk7809CertificateBuilder()
          .id(CERTIFICATE_ID)
          .build();
      final var certificate = fk7809CertificateBuilder()
          .children(
              List.of(
                  Relation.builder()
                      .certificate(child)
                      .created(NOW)
                      .type(RelationType.COMPLEMENT)
                      .build(),
                  Relation.builder()
                      .certificate(child)
                      .created(NOW)
                      .type(RelationType.RENEW)
                      .build(),
                  Relation.builder()
                      .certificate(child)
                      .created(NOW)
                      .type(RelationType.COMPLEMENT)
                      .build()
              )
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.COMPLEMENTED)
          .relatedCertificateId(child.id())
          .relatedCertificateStatus(child.status())
          .build();

      assertEquals(
          List.of(expected, expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.COMPLEMENTED)
      );
    }

    @Test
    void shouldReturnEmptyListIfNoChildRelation() {
      final var certificate = fk7809CertificateBuilder()
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.COMPLEMENTED)
      );
    }
  }

  @Nested
  class ReplacedTest {

    @Test
    void shouldReturnEventIfChildRelationExists() {
      final var child = fk7809CertificateBuilder()
          .id(CERTIFICATE_ID)
          .build();
      final var certificate = fk7809CertificateBuilder()
          .children(
              List.of(
                  Relation.builder()
                      .certificate(child)
                      .created(NOW)
                      .type(RelationType.REPLACE)
                      .build()
              )
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.REPLACED)
          .relatedCertificateId(child.id())
          .relatedCertificateStatus(child.status())
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate, CertificateEventType.REPLACED)
      );
    }

    @Test
    void shouldReturnEventIfSeveralChildRelationsExists() {
      final var child = fk7809CertificateBuilder()
          .id(CERTIFICATE_ID)
          .build();
      final var certificate = fk7809CertificateBuilder()
          .children(
              List.of(
                  Relation.builder()
                      .certificate(child)
                      .created(NOW)
                      .type(RelationType.REPLACE)
                      .build(),
                  Relation.builder()
                      .certificate(child)
                      .created(NOW)
                      .type(RelationType.REPLACE)
                      .build(),
                  Relation.builder()
                      .certificate(child)
                      .created(NOW)
                      .type(RelationType.COMPLEMENT)
                      .build()
              )
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.REPLACED)
          .relatedCertificateId(child.id())
          .relatedCertificateStatus(child.status())
          .build();

      assertEquals(
          List.of(expected, expected),
          getCertificateEventsOfTypeDomainService.events(certificate, CertificateEventType.REPLACED)
      );
    }

    @Test
    void shouldReturnEmptyListIfNoChildRelation() {
      final var certificate = fk7809CertificateBuilder()
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.REPLACED)
      );
    }
  }

  @Nested
  class ReplacesTest {

    @Test
    void shouldReturnEventIfParentRelationExists() {
      final var parent = fk7809CertificateBuilder()
          .id(CERTIFICATE_ID)
          .build();
      final var certificate = fk7809CertificateBuilder()
          .parent(
              Relation.builder()
                  .certificate(parent)
                  .created(NOW)
                  .type(RelationType.REPLACE)
                  .build()
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.REPLACES)
          .relatedCertificateId(parent.id())
          .relatedCertificateStatus(parent.status())
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate, CertificateEventType.REPLACES)
      );
    }

    @Test
    void shouldReturnEmptyListIfWrongParentRelation() {
      final var parent = fk7809CertificateBuilder()
          .id(CERTIFICATE_ID)
          .build();
      final var certificate = fk7809CertificateBuilder()
          .parent(
              Relation.builder()
                  .certificate(parent)
                  .created(NOW)
                  .type(RelationType.COMPLEMENT)
                  .build()
          )
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.REPLACES)
      );
    }

    @Test
    void shouldReturnEmptyListIfNoParentRelation() {
      final var certificate = fk7809CertificateBuilder()
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.REPLACES)
      );
    }
  }

  @Nested
  class ComplementsTest {

    @Test
    void shouldReturnEventIfParentRelationExists() {
      final var parent = fk7809CertificateBuilder()
          .id(CERTIFICATE_ID)
          .build();
      final var certificate = fk7809CertificateBuilder()
          .parent(
              Relation.builder()
                  .certificate(parent)
                  .created(NOW)
                  .type(RelationType.COMPLEMENT)
                  .build()
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.COMPLEMENTS)
          .relatedCertificateId(parent.id())
          .relatedCertificateStatus(parent.status())
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.COMPLEMENTS)
      );
    }

    @Test
    void shouldReturnEmptyListIfWrongParentRelation() {
      final var parent = fk7809CertificateBuilder()
          .id(CERTIFICATE_ID)
          .build();
      final var certificate = fk7809CertificateBuilder()
          .parent(
              Relation.builder()
                  .certificate(parent)
                  .created(NOW)
                  .type(RelationType.REPLACE)
                  .build()
          )
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.COMPLEMENTS)
      );
    }

    @Test
    void shouldReturnEmptyListIfNoParentRelation() {
      final var certificate = fk7809CertificateBuilder()
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.COMPLEMENTS)
      );
    }
  }

  @Nested
  class IncomingMessage {

    @Test
    void shouldReturnEventIfMessageFromFK() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .type(MessageType.CONTACT)
                      .author(new Author("FK"))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.INCOMING_MESSAGE)
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.INCOMING_MESSAGE)
      );
    }

    @Test
    void shouldReturnSeveralEventsIfMessagesFromFK() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .type(MessageType.CONTACT)
                      .author(new Author("FK"))
                      .sent(NOW)
                      .build(),
                  Message.builder()
                      .type(MessageType.OTHER)
                      .author(new Author("FK"))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.INCOMING_MESSAGE)
          .build();

      assertEquals(
          List.of(expected, expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.INCOMING_MESSAGE)
      );
    }

    @Test
    void shouldReturnEmptyListIfNoMessageFromFK() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .type(MessageType.OTHER)
                      .author(new Author("WC"))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.INCOMING_MESSAGE)
      );
    }

    @Test
    void shouldReturnEmptyListIfNoMessageOfCorrectType() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .type(MessageType.COMPLEMENT)
                      .author(new Author("FK"))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.INCOMING_MESSAGE)
      );
    }
  }

  @Nested
  class OutgoingMessageTest {

    @Test
    void shouldReturnEventIfMessageFromWC() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .type(MessageType.CONTACT)
                      .author(new Author("WC"))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.OUTGOING_MESSAGE)
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.OUTGOING_MESSAGE)
      );
    }

    @Test
    void shouldReturnSeveralEventsIfMessagesFromWC() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .type(MessageType.CONTACT)
                      .author(new Author("WC"))
                      .sent(NOW)
                      .build(),
                  Message.builder()
                      .type(MessageType.OTHER)
                      .author(new Author("WC"))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.OUTGOING_MESSAGE)
          .build();

      assertEquals(
          List.of(expected, expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.OUTGOING_MESSAGE)
      );
    }

    @Test
    void shouldReturnEmptyListIfNoMessageFromWC() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .type(MessageType.OTHER)
                      .author(new Author("FK"))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.OUTGOING_MESSAGE)
      );
    }

    @Test
    void shouldReturnEmptyListIfNoMessageOfCorrectType() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .type(MessageType.COMPLEMENT)
                      .author(new Author("WC"))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.OUTGOING_MESSAGE)
      );
    }
  }

  @Nested
  class IncomingAnswerTest {

    @Test
    void shouldReturnEventIfAnswerFromFK() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .type(MessageType.ANSWER)
                      .author(new Author("FK"))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.INCOMING_ANSWER)
          .build();

      assertEquals(
          List.of(expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.INCOMING_ANSWER)
      );
    }

    @Test
    void shouldReturnSeveralEventsIfAnswersFromFK() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .type(MessageType.ANSWER)
                      .author(new Author("FK"))
                      .sent(NOW)
                      .build(),
                  Message.builder()
                      .type(MessageType.ANSWER)
                      .author(new Author("FK"))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      final var expected = CertificateEvent.builder()
          .certificateId(certificate.id())
          .timestamp(NOW)
          .type(CertificateEventType.INCOMING_ANSWER)
          .build();

      assertEquals(
          List.of(expected, expected),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.INCOMING_ANSWER)
      );
    }

    @Test
    void shouldReturnEmptyListIfNoMessageFromFK() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .type(MessageType.ANSWER)
                      .author(new Author("WC"))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.INCOMING_ANSWER)
      );
    }

    @Test
    void shouldReturnEmptyListIfNoMessageOfCorrectType() {
      final var certificate = fk7809CertificateBuilder()
          .messages(
              List.of(
                  Message.builder()
                      .type(MessageType.COMPLEMENT)
                      .author(new Author("FK"))
                      .sent(NOW)
                      .build()
              )
          )
          .build();

      assertEquals(
          Collections.emptyList(),
          getCertificateEventsOfTypeDomainService.events(certificate,
              CertificateEventType.INCOMING_ANSWER)
      );
    }
  }
}
