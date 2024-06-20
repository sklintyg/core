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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.common.model.Role;

@ExtendWith(MockitoExtension.class)
class CertificateModelFactoryFK7809Test {

  private static final String TYPE = "fk7809";
  private static final String VERSION = "1.0";

  private CertificateModelFactoryFK7809 certificateModelFactoryFK7809;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7809 = new CertificateModelFactoryFK7809();
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
      final var expectedType = CertificateActionType.MESSAGES_ADMINISTRATIVE;

      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
          ),
          "Expected type: %s".formatted(expectedType));
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
  }

  @Nested
  class CertificateSpecifications {

    private static final ElementId ELEMENT_ID = new ElementId("KAT_1");

    @Nested
    class CategoryGrundForMedicinsktUnderlag {

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK7809.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationCategory.builder()
            .name("Grund för medicinskt underlag")
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }
    }

  }

}
