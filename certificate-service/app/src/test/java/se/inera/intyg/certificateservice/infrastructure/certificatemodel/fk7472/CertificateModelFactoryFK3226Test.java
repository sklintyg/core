package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.PDF_FK_3226_PDF;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.SCHEMATRON_PATH;

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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessageLevel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateList;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationUnitContactInformation;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CodeSystemKvFkmu0010;

@ExtendWith(MockitoExtension.class)
class CertificateModelFactoryFK3226Test {

  private static final String TYPE = "fk3226";
  private static final String VERSION = "1.0";
  private CertificateModelFactoryFK3226 certificateModelFactoryFK3226;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK3226 = new CertificateModelFactoryFK3226();
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
  void shallIncludePdfTemplatePath() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(PDF_FK_3226_PDF, certificateModel.pdfTemplatePath());
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

    assertFalse(certificateModel.availableForCitizen());
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
    final var expectedType = CertificateActionType.SIGN;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSend() {
    final var expectedType = CertificateActionType.SEND;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
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
    final var expectedType = CertificateActionType.REVOKE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
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
  void shallIncludeCertificateActionReceiveComplement() {
    final var expectedType = CertificateActionType.RECEIVE_COMPLEMENT;

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
  void shallIncludeSchematronPath() {
    final var certificateModel = certificateModelFactoryFK3226.create();

    assertEquals(SCHEMATRON_PATH, certificateModel.schematronPath());
  }

  @Nested
  class CertificateSpecifications {

    @Nested
    class CategoryGrund {

      private static final ElementId ELEMENT_ID = new ElementId("KAT_1");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK3226.create();

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

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }
    }

    @Nested
    class QuestionUtlatandeBaseratPa {

      private static final ElementId ELEMENT_ID = new ElementId("1");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK3226.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationCheckboxMultipleDate.builder()
            .name("Utlåtandet är baserat på")
            .id(new FieldId("1.1"))
            .dates(
                List.of(
                    new CheckboxDate(
                        new FieldId("undersokningAvPatienten"),
                        "min undersökning av patienten",
                        new Code(
                            "UNDERSOKNING",
                            "KV_FKMU_0001",
                            "min undersökning av patienten"
                        )
                    ),
                    new CheckboxDate(
                        new FieldId("journaluppgifter"),
                        "journaluppgifter från den",
                        new Code(
                            "JOURNALUPPGIFTER",
                            "KV_FKMU_0001",
                            "journaluppgifter från den"
                        )
                    ),
                    new CheckboxDate(
                        new FieldId("annat"),
                        "annat",
                        new Code(
                            "ANNAT",
                            "KV_FKMU_0001",
                            "annat"
                        )
                    )
                )
            )
            .build();

        final var certificateModel = certificateModelFactoryFK3226.create();

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
                        "$undersokningAvPatienten || $journaluppgifter || $annat"
                    )
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationDateList.builder()
                .mandatory(true)
                .max(Period.ofDays(0))
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Nested
    class QuestionUtlatandeBaseratPaAnnat {

      private static final ElementId ELEMENT_ID = new ElementId("1.3");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK3226.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationTextArea.builder()
            .name(
                "Ange vad annat är och motivera vid behov varför du inte baserar utlåtandet på en undersökning eller på journaluppgifter")
            .id(new FieldId("1.3"))
            .build();

        final var certificateModel = certificateModelFactoryFK3226.create();

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
                        "$1.3"
                    )
                )
                .build(),
            ElementRuleLimit.builder()
                .id(ELEMENT_ID)
                .type(ElementRuleType.TEXT_LIMIT)
                .limit(new RuleLimit((short) 4000))
                .build(),
            ElementRuleExpression.builder()
                .id(new ElementId("1"))
                .type(ElementRuleType.SHOW)
                .expression(
                    new RuleExpression(
                        "$annat"
                    )
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeCustomMapping() {
        final var expectedConfiguration = new ElementMapping(
            new ElementId("1"),
            new Code(
                "ANNAT",
                "KV_FKMU_0001",
                "annat"
            )
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).mapping()
        );
      }

      @Test
      void shallIncludeShouldValidatePredicate() {
        final var certificateModel = certificateModelFactoryFK3226.create();
        assertNotNull(certificateModel.elementSpecification(ELEMENT_ID).shouldValidate());
      }
    }

    @Nested
    class CategoryHot {

      private static final ElementId ELEMENT_ID = new ElementId("KAT_2");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK3226.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationCategory.builder()
            .name("Påtagligt hot mot patientens liv")
            .description("""
                Ange på vilket sätt hälsotillståndet utgör ett påtagligt hot mot patientens liv i nuläget eller på någon tids sikt. Hälsotillstånd som på flera års sikt kan utvecklas till livshotande tillstånd ger däremot inte rätt till närståendepenning.
                                    \s
                Hälsotillståndet kan utgöra ett påtagligt hot även om det finns hopp om att det förbättras.
                                    \s
                <b>Särskilda regler för vissa HIV-smittade </b>
                Ange om patienten blivit hiv-smittad på något av följande sätt.
                                    \s
                1.   Patienten har blivit smittad när hen fick blod- eller blodprodukter, och smittades när hen behandlades av den svenska hälso- och sjukvården.
                2.   Patienten har blivit smittad av nuvarande eller före detta make, maka, sambo eller registrerade partner, och den personen smittades när hen behandlades av den svenska hälso- och sjukvården.
                """)
            .build();

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }
    }


    @Nested
    class QuestionPatientBehandlingOchVardsituation {

      private static final ElementId ELEMENT_ID = new ElementId("52");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK3226.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationRadioMultipleCode.builder()
            .id(new FieldId("52.1"))
            .name("Patientens behandling och vårdsituation")
            .elementLayout(ElementLayout.ROWS)
            .list(
                List.of(
                    new ElementConfigurationCode(
                        new FieldId("ENDAST_PALLIATIV"),
                        "Endast palliativ vård ges och all aktiv behandling mot sjukdomstillståndet har avslutats",
                        CodeSystemKvFkmu0010.ENDAST_PALLIATIV
                    ),
                    new ElementConfigurationCode(
                        new FieldId("AKUT_LIVSHOTANDE"),
                        "Akut livshotande tillstånd (till exempel vård på intensivvårdsavdelning)",
                        CodeSystemKvFkmu0010.AKUT_LIVSHOTANDE
                    ),
                    new ElementConfigurationCode(
                        new FieldId("ANNAT"),
                        "Annat",
                        CodeSystemKvFkmu0010.ANNAT
                    )
                )
            )
            .build();

        final var certificateModel = certificateModelFactoryFK3226.create();

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
                        "exists($ENDAST_PALLIATIV) || exists($AKUT_LIVSHOTANDE) || exists($ANNAT)"
                    )
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationCode.builder()
                .mandatory(true)
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Nested
    class QuestionNarAktivaBehandlingenAvslutades {

      private static final ElementId ELEMENT_ID = new ElementId("52.2");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK3226.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationDate.builder()
            .id(new FieldId("52.2"))
            .name("Ange när den aktiva behandlingen avslutades")
            .max(Period.ofDays(0))
            .build();

        final var certificateModel = certificateModelFactoryFK3226.create();

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
                        "$52.2"
                    )
                )
                .build(),
            ElementRuleExpression.builder()
                .id(new ElementId("52"))
                .type(ElementRuleType.SHOW)
                .expression(
                    new RuleExpression(
                        "$ENDAST_PALLIATIV"
                    )
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationDate.builder()
                .mandatory(true)
                .max(Period.ofDays(0))
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }

      @Test
      void shallIncludeShouldValidatePredicate() {
        final var certificateModel = certificateModelFactoryFK3226.create();
        assertNotNull(certificateModel.elementSpecification(ELEMENT_ID).shouldValidate());
      }
    }

    @Nested
    class QuestionNarTillstandetBlevAkutLivshotande {

      private static final ElementId ELEMENT_ID = new ElementId("52.3");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK3226.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationDate.builder()
            .id(new FieldId("52.3"))
            .name("Ange när tillståndet blev akut livshotande")
            .max(Period.ofDays(0))
            .build();

        final var certificateModel = certificateModelFactoryFK3226.create();

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
                        "$52.3"
                    )
                )
                .build(),
            ElementRuleExpression.builder()
                .id(new ElementId("52"))
                .type(ElementRuleType.SHOW)
                .expression(
                    new RuleExpression(
                        "$AKUT_LIVSHOTANDE"
                    )
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationDate.builder()
                .mandatory(true)
                .max(Period.ofDays(0))
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }

      @Test
      void shallIncludeShouldValidatePredicate() {
        final var certificateModel = certificateModelFactoryFK3226.create();
        assertNotNull(certificateModel.elementSpecification(ELEMENT_ID).shouldValidate());
      }
    }

    @Nested
    class QuestionPatagligtHotMotPatientensLivAkutLivshotande {

      private static final ElementId ELEMENT_ID = new ElementId("52.4");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK3226.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationTextArea.builder()
            .id(new FieldId("52.4"))
            .name(
                "Beskriv på vilket sätt  sjukdomstillståndet utgör ett påtagligt hot mot patientens liv")
            .description(
                "Ange om möjligt hur länge hotet mot livet kvarstår när patienten får vård enligt den vårdplan som gäller.")
            .build();

        final var certificateModel = certificateModelFactoryFK3226.create();

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
                        "$52.4"
                    )
                )
                .build(),
            ElementRuleExpression.builder()
                .id(new ElementId("52"))
                .type(ElementRuleType.SHOW)
                .expression(
                    new RuleExpression(
                        "$AKUT_LIVSHOTANDE"
                    )
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationText.builder()
                .mandatory(true)
                .limit(4000)
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }

      @Test
      void shallIncludeShouldValidatePredicate() {
        final var certificateModel = certificateModelFactoryFK3226.create();
        assertNotNull(certificateModel.elementSpecification(ELEMENT_ID).shouldValidate());
      }
    }

    @Nested
    class QuestionUppskattaHurLangeTillstandetKommerVaraLivshotande {

      private static final ElementId ELEMENT_ID = new ElementId("52.5");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK3226.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
            .id(new FieldId("52.5"))
            .name(
                "Kan du uppskatta hur länge tillståndet kommer vara livshotande?")
            .selectedText("Ja")
            .unselectedText("Nej")
            .build();

        final var certificateModel = certificateModelFactoryFK3226.create();

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
                        "exists($52.5)"
                    )
                )
                .build(),
            ElementRuleExpression.builder()
                .id(new ElementId("52"))
                .type(ElementRuleType.SHOW)
                .expression(
                    new RuleExpression(
                        "$AKUT_LIVSHOTANDE"
                    )
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationBoolean.builder()
                .mandatory(true)
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }

      @Test
      void shallIncludeShouldValidatePredicate() {
        final var certificateModel = certificateModelFactoryFK3226.create();
        assertNotNull(certificateModel.elementSpecification(ELEMENT_ID).shouldValidate());
      }
    }

    @Nested
    class QuestionTillstandetUppskattasLivshotandeTillOchMed {

      private static final ElementId ELEMENT_ID = new ElementId("52.6");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK3226.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationDate.builder()
            .id(new FieldId("52.6"))
            .name("Till och med")
            .min(Period.ofDays(0))
            .build();

        final var certificateModel = certificateModelFactoryFK3226.create();

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
                        "$52.6"
                    )
                )
                .build(),
            ElementRuleExpression.builder()
                .id(new ElementId("52.5"))
                .type(ElementRuleType.SHOW)
                .expression(
                    new RuleExpression(
                        "$52.5"
                    )
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationDate.builder()
                .mandatory(true)
                .min(Period.ofDays(0))
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }

      @Test
      void shallIncludeShouldValidatePredicate() {
        final var certificateModel = certificateModelFactoryFK3226.create();
        assertNotNull(certificateModel.elementSpecification(ELEMENT_ID).shouldValidate());
      }
    }


    @Nested
    class QuestionPatagligtHotMotPatientensLivAnnat {

      private static final ElementId ELEMENT_ID = new ElementId("52.7");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK3226.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationTextArea.builder()
            .id(new FieldId("52.7"))
            .name(
                "Beskriv på vilket sätt  sjukdomstillståndet utgör ett påtagligt hot mot patientens liv")
            .description(
                "Ange när tillståndet blev livshotande, och om det är möjligt hur länge hotet mot livet kvarstår när patienten får vård enligt den vårdplan som gäller.")
            .build();

        final var certificateModel = certificateModelFactoryFK3226.create();

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
                        "$52.7"
                    )
                )
                .build(),
            ElementRuleExpression.builder()
                .id(new ElementId("52"))
                .type(ElementRuleType.SHOW)
                .expression(
                    new RuleExpression(
                        "$ANNAT"
                    )
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationText.builder()
                .mandatory(true)
                .limit(4000)
                .build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }

      @Test
      void shallIncludeShouldValidatePredicate() {
        final var certificateModel = certificateModelFactoryFK3226.create();
        assertNotNull(certificateModel.elementSpecification(ELEMENT_ID).shouldValidate());
      }
    }
  }

  @Nested
  class CategorySamtycke {

    private static final ElementId ELEMENT_ID = new ElementId("KAT_3");

    @Test
    void shallIncludeId() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              ELEMENT_ID,
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeConfiguration() {
      final var expectedConfiguration = ElementConfigurationCategory.builder()
          .name("Samtycke för närståendes stöd")
          .build();

      final var certificateModel = certificateModelFactoryFK3226.create();

      assertEquals(expectedConfiguration,
          certificateModel.elementSpecification(ELEMENT_ID).configuration()
      );
    }


    @Nested
    class IssuingUnitContactInfo {

      private static final ElementId ELEMENT_ID = new ElementId("UNIT_CONTACT_INFORMATION");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK3226.create();

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

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }

      @Test
      void shallIncludeValidation() {
        final var expectedValidation = List.of(
            ElementValidationUnitContactInformation.builder().build()
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedValidation,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Test
    void shallIncludeActiveForRoles() {
      final var expected = List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
          Role.CARE_ADMIN);

      final var certificateModel = certificateModelFactoryFK3226.create();

      assertEquals(expected, certificateModel.rolesWithAccess());
    }
  }

  @Nested
  class QuestionForutsattningarForAttLamnaSkriftligtSamtycke {

    private static final ElementId ELEMENT_ID = new ElementId("53");

    @Test
    void shallIncludeId() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              ELEMENT_ID,
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeConfiguration() {
      final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
          .id(new FieldId("53.1"))
          .name(
              "Har patienten de medicinska förutsättningarna för att kunna lämna sitt samtycke?")
          .selectedText("Ja")
          .unselectedText("Nej")
          .build();

      final var certificateModel = certificateModelFactoryFK3226.create();

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
                      "exists($53.1)"
                  )
              )
              .build()
      );

      final var certificateModel = certificateModelFactoryFK3226.create();

      assertEquals(expectedRules,
          certificateModel.elementSpecification(ELEMENT_ID).rules()
      );
    }

    @Test
    void shallIncludeValidations() {
      final var expectedValidations = List.of(
          ElementValidationBoolean.builder()
              .mandatory(true)
              .build()
      );

      final var certificateModel = certificateModelFactoryFK3226.create();

      assertEquals(expectedValidations,
          certificateModel.elementSpecification(ELEMENT_ID).validations()
      );
    }
  }

  @Nested
  class MessageForutsattningarForAttLamnaSkriftligtSamtycke {

    private static final ElementId ELEMENT_ID = new ElementId("forutsattningar");

    @Test
    void shallIncludeId() {
      final var certificateModel = certificateModelFactoryFK3226.create();

      assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              ELEMENT_ID,
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeConfiguration() {
      final var expectedConfiguration = ElementConfigurationMessage.builder()
          .message("""
               Om patienten har medicinska förutsättningar att samtycka till att den vill ha stöd av en närstående, så ska patienten göra det.
                                  \s
               Därför ska du fylla i om patienten kan lämna ett skriftligt samtycke eller inte.
              """)
          .level(ElementMessageLevel.INFO)
          .build();

      final var certificateModel = certificateModelFactoryFK3226.create();

      assertEquals(expectedConfiguration,
          certificateModel.elementSpecification(ELEMENT_ID).configuration()
      );
    }
  }
}