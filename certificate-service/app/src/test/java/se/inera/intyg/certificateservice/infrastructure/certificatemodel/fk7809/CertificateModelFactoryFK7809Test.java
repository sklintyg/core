package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;

@ExtendWith(MockitoExtension.class)
class CertificateModelFactoryFK7809Test {

  private static final String TYPE = "fk7809";
  private static final String VERSION = "1.0";

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  private CertificateModelFactoryFK7809 certificateModelFactoryFK7809;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7809 = new CertificateModelFactoryFK7809(diagnosisCodeRepository);
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Läkarutlåtande för merkostnadsersättning";

    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK7809.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryFK7809.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryFK7809,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryFK7809.create();

    assertTrue(certificateModel.availableForCitizen());
  }

  @Test
  void shallIncludeCertificateSummaryProvider() {
    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(FK7809CertificateSummaryProvider.class,
        certificateModel.summaryProvider().getClass());
  }

  @Test
  void shallIncludeMessageTypes() {
    final var expectedMessageTypes = List.of(
        CertificateMessageType.builder()
            .type(MessageType.MISSING)
            .subject(new Subject(MessageType.MISSING.displayName()))
            .build(),
        CertificateMessageType.builder()
            .type(MessageType.CONTACT)
            .subject(new Subject(MessageType.CONTACT.displayName()))
            .build(),
        CertificateMessageType.builder()
            .type(MessageType.OTHER)
            .subject(new Subject(MessageType.OTHER.displayName()))
            .build()
    );

    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(expectedMessageTypes, certificateModel.messageTypes());
  }

  @Test
  void shallIncludeCertificateText() {
    final var expectedText = CertificateText.builder()
        .text(
            "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. Har du frågor kontaktar du den som skrivit ditt intyg.")
        .type(CertificateTextType.PREAMBLE_TEXT)
        .links(Collections.emptyList())
        .build();

    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(List.of(expectedText), certificateModel.texts());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan"
    );

    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Nested
  class CertificateActions {

    @Test
    void shallIncludeCertificateActionCreate() {
      final var expectedType = CertificateActionType.CREATE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream()
          .anyMatch(actionSpecification ->
              expectedType.equals(actionSpecification.certificateActionType())
          ), "Expected type: %s".formatted(expectedType)
      );
    }

    @Test
    void shallIncludeCertificateActionRead() {
      final var expectedType = CertificateActionType.READ;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream()
          .anyMatch(actionSpecification ->
              expectedType.equals(actionSpecification.certificateActionType()
              )
          ), "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionUpdate() {
      final var expectedType = CertificateActionType.UPDATE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionDelete() {
      final var expectedType = CertificateActionType.DELETE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionSign() {
      final var expectedSpecification = CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.SIGN)
          .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
          .build();

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              expectedSpecification::equals),
          "Expected type: %s".formatted(expectedSpecification));
    }

    @Test
    void shallIncludeCertificateActionSend() {
      final var expectedSpecification = CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.SEND)
          .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
          .build();

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              expectedSpecification::equals),
          "Expected type: %s".formatted(expectedSpecification));
    }

    @Test
    void shallIncludeCertificateActionPrint() {
      final var expectedType = CertificateActionType.PRINT;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionRevoke() {
      final var expectedSpecification = CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.REVOKE)
          .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
          .build();

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              expectedSpecification::equals),
          "Expected type: %s".formatted(expectedSpecification));
    }

    @Test
    void shallIncludeCertificateActionReplace() {
      final var expectedType = CertificateActionType.REPLACE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionReplaceContinue() {
      final var expectedType = CertificateActionType.REPLACE_CONTINUE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionRenew() {
      final var expectedType = CertificateActionType.RENEW;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionMessages() {
      final var expectedType = CertificateActionType.MESSAGES;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionMessagesAdministrative() {
      final var expected = CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.MESSAGES_ADMINISTRATIVE)
          .enabled(true)
          .build();

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications()
              .stream().anyMatch(expected::equals),
          "Expected type: %s".formatted(expected));
    }

    @Test
    void shallIncludeCertificateActionReceiveComplement() {
      final var expectedType = CertificateActionType.RECEIVE_COMPLEMENT;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionReceiveQuestion() {
      final var expectedType = CertificateActionType.RECEIVE_QUESTION;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionReceiveAnswer() {
      final var expectedType = CertificateActionType.RECEIVE_ANSWER;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionComplement() {
      final var expectedType = CertificateActionType.COMPLEMENT;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionCannotComplement() {
      final var expectedType = CertificateActionType.CANNOT_COMPLEMENT;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionForwardMessage() {
      final var expectedType = CertificateActionType.FORWARD_MESSAGE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionForwardCertificate() {
      final var expectedType = CertificateActionType.FORWARD_CERTIFICATE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionHandleComplement() {
      final var expectedType = CertificateActionType.HANDLE_COMPLEMENT;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionReceiveReminder() {
      final var expectedType = CertificateActionType.RECEIVE_REMINDER;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionCreateMessages() {
      final var expectedType = CertificateActionType.CREATE_MESSAGE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionSaveAnswer() {
      final var expectedType = CertificateActionType.SAVE_ANSWER;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionDeleteAnswer() {
      final var expectedType = CertificateActionType.DELETE_ANSWER;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionSendAnswer() {
      final var expectedType = CertificateActionType.SEND_ANSWER;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionAnswerMessages() {
      final var expectedType = CertificateActionType.ANSWER_MESSAGE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionSaveMessage() {
      final var expectedType = CertificateActionType.SAVE_MESSAGE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionDeleteMessage() {
      final var expectedType = CertificateActionType.DELETE_MESSAGE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionSendMessage() {
      final var expectedType = CertificateActionType.SEND_MESSAGE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionHandleMessage() {
      final var expectedType = CertificateActionType.HANDLE_MESSAGE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeCertificateActionResponsibleIssuer() {
      final var expectedSpecification = CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.RESPONSIBLE_ISSUER)
          .allowedRoles(List.of(Role.NURSE, Role.MIDWIFE, Role.CARE_ADMIN))
          .build();

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              expectedSpecification::equals),
          "Expected type: %s".formatted(expectedSpecification));
    }
  }

  @Nested
  class MessageActionSpecificationsTests {

    @Test
    void shallIncludeMessageActionAnswer() {
      final var expectedType = MessageActionType.ANSWER;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeMessageActionHandleComplement() {
      final var expectedType = MessageActionType.HANDLE_COMPLEMENT;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeMessageActionCannotComplement() {
      final var expectedType = MessageActionType.CANNOT_COMPLEMENT;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeMessageActionComplement() {
      final var expectedType = MessageActionType.COMPLEMENT;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeMessageActionForward() {
      final var expectedType = MessageActionType.FORWARD;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeMessageActionHandleMessage() {
      final var expectedType = MessageActionType.HANDLE_MESSAGE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }
  }

  @Nested
  class CertificateSpecifications {

    @Test
    void shallIncludeCategoryGrundForMedicinsktUnderlag() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_1")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_1"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionGrundForMedicinsktUnderlag() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("1")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("1"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionRelationTillPatienten() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("1.4")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("1.4"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionAnnanGrundForMedicinsktUnderlag() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("1.3")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("1.3"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionBaseratPaAnnatMedicinsktUnderlag() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("3")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("3"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionUtredningEllerUnderlag() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("4")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("4"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryAktivitetsBegransningar() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_6")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_6"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionAktivitetsbegransningar() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("17")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("17"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryMedicinskBehandling() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_7")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_7"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionPagaendeOchPlaneradeBehandlingar() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("50")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("50"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionVardenhetOchTidplan() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("50.2")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("50.2"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryPrognas() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_8")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_8"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionPrognos() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("51")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("51"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryOvrigt() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_9")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_9"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionOvrigt() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("25")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("25"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryDiagnos() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_4")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_4"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionDiagnos() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("58")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("58"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionDiagnosMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("5")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("5"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryFunktionsnedsattning() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_5")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_5"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionFunktionsnedsattning() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("funktionsnedsattning")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("funktionsnedsattning"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionIntellektuellFunktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("8");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionKommunikationSocialInteraktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("9");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionUppmarksamhetMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("10");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionPsykiskFunktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("11");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionHorselFunktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("48");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionSynFunktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("49");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionSinnesFunktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("12");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionKoordinationMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("13");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionAnnanKroppsligFunktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("14");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }
  }
}



