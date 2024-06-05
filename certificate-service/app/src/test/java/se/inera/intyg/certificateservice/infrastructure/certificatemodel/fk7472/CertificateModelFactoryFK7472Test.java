package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.CertificateModelFactoryFK7472.PDF_FK_7472_PDF;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.CertificateModelFactoryFK7472.SCHEMATRON_PATH;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRangeList;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationUnitContactInformation;

@ExtendWith(MockitoExtension.class)
class CertificateModelFactoryFK7472Test {

  private static final String TYPE = "fk7472";
  private static final String VERSION = "1.0";
  private CertificateModelFactoryFK7472 certificateModelFactoryFK7472;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7472 = new CertificateModelFactoryFK7472();
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Intyg om tillfällig föräldrapenning";

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shallIncludePdfTemplatePath() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(PDF_FK_7472_PDF, certificateModel.pdfTemplatePath());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryFK7472,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertFalse(certificateModel.availableForCitizen());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan"
    );

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeCertificateActionCreate() {
    final var expectedType = CertificateActionType.CREATE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType())
        ), "Expected type: %s".formatted(expectedType)
    );
  }

  @Test
  void shallIncludeCertificateActionRead() {
    final var expectedType = CertificateActionType.READ;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType()
            )
        ), "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionUpdate() {
    final var expectedType = CertificateActionType.UPDATE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDelete() {
    final var expectedType = CertificateActionType.DELETE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSign() {
    final var expectedType = CertificateActionType.SIGN;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSend() {
    final var expectedType = CertificateActionType.SEND;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionPrint() {
    final var expectedType = CertificateActionType.PRINT;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRevoke() {
    final var expectedType = CertificateActionType.REVOKE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReplace() {
    final var expectedType = CertificateActionType.REPLACE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReplaceContinue() {
    final var expectedType = CertificateActionType.REPLACE_CONTINUE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRenew() {
    final var expectedType = CertificateActionType.RENEW;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionMessages() {
    final var expectedType = CertificateActionType.MESSAGES;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionMessagesAdministrative() {
    final var expectedType = CertificateActionType.MESSAGES_ADMINISTRATIVE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReceiveComplement() {
    final var expectedType = CertificateActionType.RECEIVE_COMPLEMENT;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionComplement() {
    final var expectedType = CertificateActionType.COMPLEMENT;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionCannotComplement() {
    final var expectedType = CertificateActionType.CANNOT_COMPLEMENT;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionForwardMessage() {
    final var expectedType = CertificateActionType.FORWARD_MESSAGE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionHandleComplement() {
    final var expectedType = CertificateActionType.HANDLE_COMPLEMENT;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeSchematronPath() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(SCHEMATRON_PATH, certificateModel.schematronPath());
  }

  @Nested
  class CertificateSpecifications {

    @Nested
    class CategorySymptom {

      private static final ElementId ELEMENT_ID = new ElementId("KAT_1");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK7472.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationCategory.builder()
            .name("Barnets diagnos eller symtom")
            .build();

        final var certificateModel = certificateModelFactoryFK7472.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }
    }

    @Nested
    class QuestionSymptom {

      private static final ElementId ELEMENT_ID = new ElementId("55");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK7472.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationTextArea.builder()
            .name("Ange diagnos eller symtom")
            .id(new FieldId("55.1"))
            .build();

        final var certificateModel = certificateModelFactoryFK7472.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }

      @Test
      void shallIncludeRules() {
        final var expectedRules = List.of(
            ElementRuleExpression.builder()
                .id(new ElementId("55"))
                .type(ElementRuleType.MANDATORY)
                .expression(
                    new RuleExpression("$55.1")
                )
                .build(),
            ElementRuleLimit.builder()
                .id(new ElementId("55"))
                .type(ElementRuleType.TEXT_LIMIT)
                .limit(new RuleLimit((short) 318))
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7472.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationText.builder()
                .mandatory(true)
                .limit(318)
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7472.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }

      @Test
      void shallIncludeWhenRenewingTrue() {
        final var certificateModel = certificateModelFactoryFK7472.create();

        assertEquals(Boolean.TRUE,
            certificateModel.elementSpecification(ELEMENT_ID).includeWhenRenewing()
        );
      }
    }

    @Nested
    class CategoryPeriod {

      private static final ElementId ELEMENT_ID = new ElementId("KAT_2");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK7472.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationCategory.builder()
            .name("Period som barnet inte bör vårdas i ordinarie tillsynsform")
            .build();

        final var certificateModel = certificateModelFactoryFK7472.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }
    }

    @Nested
    class QuestionPrognos {

      private static final ElementId ELEMENT_ID = new ElementId("56");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK7472.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationCheckboxDateRangeList.builder()
            .name("Jag bedömer att barnet inte ska vårdas i ordinarie tillsynsform")
            .label("Andel av ordinarie tid:")
            .id(new FieldId("56.1"))
            .hideWorkingHours(true)
            .min(Period.ofMonths(-1))
            .dateRanges(
                List.of(
                    new ElementConfigurationCode(new FieldId("EN_ATTONDEL"), "12,5 procent"),
                    new ElementConfigurationCode(new FieldId("EN_FJARDEDEL"), "25 procent"),
                    new ElementConfigurationCode(new FieldId("HALVA"), "50 procent"),
                    new ElementConfigurationCode(new FieldId("TRE_FJARDEDELAR"), "75 procent"),
                    new ElementConfigurationCode(new FieldId("HELA"), "100 procent")
                )
            )
            .build();

        final var certificateModel = certificateModelFactoryFK7472.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }

      @Test
      void shallIncludeRules() {
        final var expectedRules = List.of(
            ElementRuleExpression.builder()
                .id(ELEMENT_ID)
                .type(ElementRuleType.MANDATORY)
                .expression(
                    new RuleExpression(
                        "$EN_ATTONDEL || $EN_FJARDEDEL || $HALVA || $TRE_FJARDEDELAR || $HELA"
                    )
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7472.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationDateRangeList.builder()
                .min(Period.ofMonths(-1))
                .mandatory(true)
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7472.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }

      @Test
      void shallIncludeWhenRenewingFalse() {
        final var certificateModel = certificateModelFactoryFK7472.create();

        assertEquals(Boolean.FALSE,
            certificateModel.elementSpecification(ELEMENT_ID).includeWhenRenewing()
        );
      }
    }

    @Nested
    class IssuingUnitContactInfo {

      private static final ElementId ELEMENT_ID = new ElementId("UNIT_CONTACT_INFORMATION");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK7472.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationUnitContactInformation.builder()
            .build();

        final var certificateModel = certificateModelFactoryFK7472.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }

      @Test
      void shallIncludeValidation() {
        final var expectedValidation = List.of(
            ElementValidationUnitContactInformation.builder().build()
        );

        final var certificateModel = certificateModelFactoryFK7472.create();

        assertEquals(expectedValidation,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Test
    void shallIncludeActiveForRoles() {
      final var expected = List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
          Role.CARE_ADMIN);

      final var certificateModel = certificateModelFactoryFK7472.create();

      assertEquals(expected, certificateModel.rolesWithAccess());
    }
  }
}