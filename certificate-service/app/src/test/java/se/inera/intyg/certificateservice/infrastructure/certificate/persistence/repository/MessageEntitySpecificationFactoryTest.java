package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.data.jpa.domain.Specification;
import se.inera.intyg.certificateservice.domain.common.model.MessagesRequest;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;

class MessageEntitySpecificationFactoryTest {

  private MessageEntitySpecificationFactory specificationFactory;

  @BeforeEach
  void setUp() {
    specificationFactory = new MessageEntitySpecificationFactory();
  }

  @Test
  void shallIncludePatientId() {
    final var request = MessagesRequest.builder()
        .personId(ATHENA_REACT_ANDERSSON.id())
        .build();
    try (
        MockedStatic<PatientEntitySpecification> specification = mockStatic(
            PatientEntitySpecification.class)
    ) {
      specification.when(
              () -> PatientEntitySpecification.equalsPatientForMessage(request.personId()))
          .thenReturn(mock(Specification.class));

      assertNotNull(specificationFactory.create(request));

      specification.verify(
          () -> PatientEntitySpecification.equalsPatientForMessage(request.personId())
      );
    }
  }

  @Test
  void shallNotIncludePatientId() {
    final var messagesRequest = MessagesRequest.builder()
        .build();
    try (
        MockedStatic<PatientEntitySpecification> specification = mockStatic(
            PatientEntitySpecification.class)
    ) {
      assertNotNull(specificationFactory.create(messagesRequest));

      specification.verifyNoInteractions();
    }
  }

  @Test
  void shallIncludeIssuedUnitIds() {
    final var messagesRequest = MessagesRequest.builder()
        .issuedOnUnitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN.hsaId()))
        .build();
    try (
        MockedStatic<UnitEntitySpecification> specification = mockStatic(
            UnitEntitySpecification.class)
    ) {
      specification.when(
              () -> UnitEntitySpecification.inIssuedOnUnitIds(messagesRequest.issuedOnUnitIds()))
          .thenReturn(mock(Specification.class));

      assertNotNull(specificationFactory.create(messagesRequest));

      specification.verify(
          () -> UnitEntitySpecification.inIssuedOnUnitIds(messagesRequest.issuedOnUnitIds())
      );
    }
  }

  @Test
  void shallNotIncludeIssuedUnitId() {
    final var messagesRequest = MessagesRequest.builder()
        .build();
    try (
        MockedStatic<UnitEntitySpecification> specification = mockStatic(
            UnitEntitySpecification.class)
    ) {
      assertNotNull(specificationFactory.create(messagesRequest));

      specification.verifyNoInteractions();
    }
  }

  @Test
  void shallIncludeIssuedByStaffId() {
    final var messagesRequest = MessagesRequest.builder()
        .issuedByStaffId(AJLA_DOKTOR.hsaId())
        .build();
    try (
        MockedStatic<StaffEntitySpecification> specification = mockStatic(
            StaffEntitySpecification.class)
    ) {
      specification.when(
              () -> StaffEntitySpecification.equalsIssuedByStaffForMessage(
                  messagesRequest.issuedByStaffId()))
          .thenReturn(mock(Specification.class));

      assertNotNull(specificationFactory.create(messagesRequest));

      specification.verify(
          () -> StaffEntitySpecification.equalsIssuedByStaffForMessage(
              messagesRequest.issuedByStaffId())
      );
    }
  }

  @Test
  void shallNotIncludeIssuedByStaffId() {
    final var messagesRequest = MessagesRequest.builder()
        .build();
    try (
        MockedStatic<StaffEntitySpecification> specification = mockStatic(
            StaffEntitySpecification.class)
    ) {
      assertNotNull(specificationFactory.create(messagesRequest));

      specification.verifyNoInteractions();
    }
  }

  @Test
  void shallIncludeModifiedFrom() {
    final var messagesRequest = MessagesRequest.builder()
        .sentDateFrom(LocalDateTime.now(ZoneId.systemDefault()))
        .build();
    try (
        MockedStatic<MessageEntitySpecification> specification = mockStatic(
            MessageEntitySpecification.class)
    ) {
      specification.when(
              () -> MessageEntitySpecification.sentEqualsAndGreaterThan(
                  messagesRequest.sentDateFrom())
          )
          .thenReturn(mock(Specification.class));

      assertNotNull(specificationFactory.create(messagesRequest));

      specification.verify(
          () -> MessageEntitySpecification.sentEqualsAndGreaterThan(
              messagesRequest.sentDateFrom())
      );
    }
  }

  @Test
  void shallNotIncludeModifiedFrom() {
    final var messagesRequest = MessagesRequest.builder()
        .build();
    try (
        MockedStatic<MessageEntitySpecification> specification = mockStatic(
            MessageEntitySpecification.class)
    ) {
      assertNotNull(specificationFactory.create(messagesRequest));

      specification.verifyNoInteractions();
    }
  }

  @Test
  void shallIncludeModifiedTo() {
    final var messagesRequest = MessagesRequest.builder()
        .sentDateTo(LocalDateTime.now(ZoneId.systemDefault()))
        .build();
    try (
        MockedStatic<MessageEntitySpecification> specification = mockStatic(
            MessageEntitySpecification.class)
    ) {
      specification.when(
              () -> MessageEntitySpecification.sentEqualsAndLesserThan(
                  messagesRequest.sentDateTo())
          )
          .thenReturn(mock(Specification.class));

      assertNotNull(specificationFactory.create(messagesRequest));

      specification.verify(
          () -> MessageEntitySpecification.sentEqualsAndLesserThan(
              messagesRequest.sentDateTo())
      );
    }
  }

  @Test
  void shallNotIncludeModifiedTo() {
    final var messagesRequest = MessagesRequest.builder()
        .build();
    try (
        MockedStatic<MessageEntitySpecification> specification = mockStatic(
            MessageEntitySpecification.class)
    ) {
      assertNotNull(specificationFactory.create(messagesRequest));

      specification.verifyNoInteractions();
    }
  }

  @Test
  void shallIncludeForwarded() {
    final var messagesRequest = MessagesRequest.builder()
        .forwarded(new Forwarded(true))
        .build();
    try (
        MockedStatic<MessageEntitySpecification> specification = mockStatic(
            MessageEntitySpecification.class)
    ) {
      specification.when(
              () -> MessageEntitySpecification.equalsForwarded(messagesRequest.forwarded()))
          .thenReturn(mock(Specification.class));

      assertNotNull(specificationFactory.create(messagesRequest));

      specification.verify(
          () -> MessageEntitySpecification.equalsForwarded(messagesRequest.forwarded())
      );
    }
  }

  @Test
  void shallNotIncludeForwardedIfNull() {
    final var messagesRequest = MessagesRequest.builder()
        .build();
    try (
        MockedStatic<MessageEntitySpecification> specification = mockStatic(
            MessageEntitySpecification.class)
    ) {
      assertNotNull(specificationFactory.create(messagesRequest));
      specification.verifyNoInteractions();
    }
  }

  @Test
  void shallIncludeAuthor() {
    final var messagesRequest = MessagesRequest.builder()
        .author(new Author("WC"))
        .build();
    try (
        MockedStatic<MessageEntitySpecification> specification = mockStatic(
            MessageEntitySpecification.class)
    ) {
      specification.when(
              () -> MessageEntitySpecification.equalsAuthor(messagesRequest.author()))
          .thenReturn(mock(Specification.class));

      assertNotNull(specificationFactory.create(messagesRequest));

      specification.verify(
          () -> MessageEntitySpecification.equalsAuthor(messagesRequest.author())
      );
    }
  }

  @Test
  void shallNotIncludeAuthorIfNull() {
    final var messagesRequest = MessagesRequest.builder()
        .build();
    try (
        MockedStatic<MessageEntitySpecification> specification = mockStatic(
            MessageEntitySpecification.class)
    ) {
      assertNotNull(specificationFactory.create(messagesRequest));
      specification.verifyNoInteractions();
    }
  }
}