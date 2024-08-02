package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.PDF_FK_3226_PDF;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.PDF_NO_ADDRESS_FK_3226_PDF;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.SCHEMATRON_PATH;

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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;

@ExtendWith(MockitoExtension.class)
class CertificateModelFactoryFK3226Test {

  private static final String TYPE = "fk3226";
  private static final String VERSION = "1.0";
  private CertificateModelFactoryFK3226 certificateModelFactoryFK3226;

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK3226 = new CertificateModelFactoryFK3226(diagnosisCodeRepository);
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Läkarutlåtande för närståendepenning";

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryFK3226,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.availableForCitizen());
  }

  @Test
  void shallIncludeCertificateSummaryProvider() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(FK3226CertificateSummaryProvider.class,
        certificateModel.summaryProvider().getClass());
  }

  @Test
  void shallIncludeCertificateText() {
    final var expectedText = CertificateText.builder()
        .text(
            "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. Har du frågor kontaktar du den som skrivit ditt intyg.")
        .type(CertificateTextType.PREAMBLE_TEXT)
        .links(Collections.emptyList())
        .build();

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(List.of(expectedText), certificateModel.texts());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan"
    );

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeCertificateActionCreate() {
    final var expectedType = CertificateActionType.CREATE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType())
        ), "Expected type: %s".formatted(expectedType)
    );
  }

  @Test
  void shallIncludeCertificateActionRead() {
    final var expectedType = CertificateActionType.READ;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType()
            )
        ), "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionUpdate() {
    final var expectedType = CertificateActionType.UPDATE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDelete() {
    final var expectedType = CertificateActionType.DELETE;

    final var certificateModel = certificateModelFactoryFK3226.create();

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

    final var certificateModel = certificateModelFactoryFK3226.create();

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

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionPrint() {
    final var expectedType = CertificateActionType.PRINT;

    final var certificateModel = certificateModelFactoryFK3226.create();

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

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionReplace() {
    final var expectedType = CertificateActionType.REPLACE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReplaceContinue() {
    final var expectedType = CertificateActionType.REPLACE_CONTINUE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRenew() {
    final var expectedType = CertificateActionType.RENEW;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionMessages() {
    final var expectedType = CertificateActionType.MESSAGES;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionMessagesAdministrative() {
    final var expectedType = CertificateActionType.MESSAGES_ADMINISTRATIVE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionMessagesAdministrativeWithEnabledTrue() {
    final var expectedType = CertificateActionType.MESSAGES_ADMINISTRATIVE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream()
            .filter(
                actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
            )
            .findFirst()
            .orElseThrow()
            .enabled(),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReceiveComplement() {
    final var expectedType = CertificateActionType.RECEIVE_COMPLEMENT;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReceiveQuestion() {
    final var expectedType = CertificateActionType.RECEIVE_QUESTION;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReceiveAnswer() {
    final var expectedType = CertificateActionType.RECEIVE_ANSWER;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionAnswerMessages() {
    final var expectedType = CertificateActionType.ANSWER_MESSAGE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSaveMessage() {
    final var expectedType = CertificateActionType.SAVE_MESSAGE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDeleteMessage() {
    final var expectedType = CertificateActionType.DELETE_MESSAGE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSendMessage() {
    final var expectedType = CertificateActionType.SEND_MESSAGE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionHandleMessage() {
    final var expectedType = CertificateActionType.HANDLE_MESSAGE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSaveAnswer() {
    final var expectedType = CertificateActionType.SAVE_ANSWER;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDeleteAnswer() {
    final var expectedType = CertificateActionType.DELETE_ANSWER;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSendAnswer() {
    final var expectedType = CertificateActionType.SEND_ANSWER;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionComplement() {
    final var expectedType = CertificateActionType.COMPLEMENT;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionCannotComplement() {
    final var expectedType = CertificateActionType.CANNOT_COMPLEMENT;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionForwardMessage() {
    final var expectedType = CertificateActionType.FORWARD_MESSAGE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionHandleComplement() {
    final var expectedType = CertificateActionType.HANDLE_COMPLEMENT;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionCreateMessages() {
    final var expectedType = CertificateActionType.CREATE_MESSAGE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRecieveReminder() {
    final var expectedType = CertificateActionType.RECEIVE_REMINDER;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionForwardCertificate() {
    final var expectedType = CertificateActionType.FORWARD_CERTIFICATE;

    final var certificateModel = certificateModelFactoryFK3226.create();

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

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeSchematronPath() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(SCHEMATRON_PATH, certificateModel.schematronPath());
  }

  @Nested
  class MessageActionSpecificationsTests {

    @Test
    void shallIncludeMessageActionAnswer() {
      final var expectedType = MessageActionType.ANSWER;

      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeMessageActionHandleComplement() {
      final var expectedType = MessageActionType.HANDLE_COMPLEMENT;

      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeMessageActionCannotComplement() {
      final var expectedType = MessageActionType.CANNOT_COMPLEMENT;

      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeMessageActionComplement() {
      final var expectedType = MessageActionType.COMPLEMENT;

      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeMessageActionForward() {
      final var expectedType = MessageActionType.FORWARD;

      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeMessageActionHandleMessage() {
      final var expectedType = MessageActionType.HANDLE_MESSAGE;

      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }
  }

  @Test
  void shallIncludeActiveForRoles() {
    final var expected = List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
        Role.CARE_ADMIN);

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(expected, certificateModel.rolesWithAccess());
  }

  @Nested
  class CertificateSpecifications {

    @Test
    void shallIncludeCategoryGrund() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_1")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_1"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeUtlatandeBaseratPa() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("1")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("1"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeUtlatandeBaseratPaAnnat() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("1.3")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("1.3"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryHot() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_2")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_2"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeDiagnos() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("58")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("58"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludePatientBehandlingOchVardsituation() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionNarAktivaBehandlingenAvslutades() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52.2")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52.2"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionNarTillstandetBlevAkutLivshotande() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52.3")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52.3"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionPatagligtHotMotPatientensLivAkutLivshotande() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52.4")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52.4"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionUppskattaHurLangeTillstandetKommerVaraLivshotande() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52.5")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52.5"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionTillstandetUppskattasLivshotandeTillOchMed() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52.6")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52.6"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionPatagligtHotMotPatientensLivAnnat() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("52.7")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("52.7"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategorySamtycke() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_3")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_3"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionForutsattningarForAttLamnaSkriftligtSamtycke() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("53")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("53"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeMessageForutsattningarForAttLamnaSkriftligtSamtycke() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("forutsattningar")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("forutsattningar"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeIssuingUnitContactInfo() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(
          certificateModel.elementSpecificationExists(new ElementId("UNIT_CONTACT_INFORMATION")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'"
              .formatted(new ElementId("UNIT_CONTACT_INFORMATION"),
                  certificateModel.elementSpecifications())
      );
    }
  }

  @Nested
  class
  PdfSpecificationTest {

    @Test
    void shallIncludePdfTemplatePathWithAddress() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertEquals(PDF_FK_3226_PDF, certificateModel.pdfSpecification().pdfTemplatePath());
    }

    @Test
    void shallIncludePdfTemplatePathNoAddress() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertEquals(PDF_NO_ADDRESS_FK_3226_PDF,
          certificateModel.pdfSpecification().pdfNoAddressTemplatePath());
    }

    @Test
    void shallIncludePatientFieldId() {
      final var expected = new PdfFieldId("form1[0].#subform[0].flt_txtPnr[0]");

      final var certificateModel = certificateModelFactoryFK3226.create();

      assertEquals(expected, certificateModel.pdfSpecification().patientIdFieldId());
    }

    @Test
    void shallIncludeSignatureFields() {
      final var expected = PdfSignature.builder()
          .signaturePageIndex(1)
          .signatureWithAddressTagIndex(new PdfTagIndex(50))
          .signatureWithoutAddressTagIndex(new PdfTagIndex(42))
          .signedDateFieldId(new PdfFieldId("form1[0].#subform[1].flt_datUnderskrift[0]"))
          .signedByNameFieldId(new PdfFieldId("form1[0].#subform[1].flt_txtNamnfortydligande[0]"))
          .paTitleFieldId(new PdfFieldId("form1[0].#subform[1].flt_txtBefattning[0]"))
          .specialtyFieldId(
              new PdfFieldId("form1[0].#subform[1].flt_txtEventuellSpecialistkompetens[0]"))
          .hsaIdFieldId(new PdfFieldId("form1[0].#subform[1].flt_txtLakarensHSA-ID[0]"))
          .workplaceCodeFieldId(new PdfFieldId("form1[0].#subform[1].flt_txtArbetsplatskod[0]"))
          .contactInformation(
              new PdfFieldId("form1[0].#subform[1].flt_txtVardgivarensNamnAdressTelefon[0]"))
          .build();

      final var certificateModel = certificateModelFactoryFK3226.create();

      assertEquals(expected, certificateModel.pdfSpecification().signature());
    }

    @Test
    void shallIncludeMcid() {
      final var expected = 100;
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertEquals(expected, certificateModel.pdfSpecification().pdfMcid().value());
    }
  }
}
