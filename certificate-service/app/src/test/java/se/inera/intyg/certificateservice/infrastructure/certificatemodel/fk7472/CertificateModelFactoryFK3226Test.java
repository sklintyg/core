package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.PDF_FK_3226_PDF;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.SCHEMATRON_PATH;

import java.time.LocalDateTime;
import java.time.Period;
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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
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
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
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
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.FK3226CertificateSummaryProvider;

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
        .text("Här skall det stå en text om intyget!")
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
  void shallIncludeCertificateActionMessagesAdministrativeWithEnabledTrue() {
    final var expectedType = CertificateActionType.MESSAGES_ADMINISTRATIVE;

    final var certificateModel = certificateModelFactoryFK3226.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream()
            .filter(
                actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
            )
            .findFirst()
            .orElseThrow()
            .isEnabled(),
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
    final var expectedType = CertificateActionType.CREATE_MESSAGES;

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
                    CheckboxDate.builder()
                        .id(new FieldId("undersokningAvPatienten"))
                        .label("min undersökning av patienten")
                        .code(
                            new Code(
                                "UNDERSOKNING",
                                "KV_FKMU_0001",
                                "min undersökning av patienten"
                            )
                        )
                        .max(Period.ZERO)
                        .build(),
                    CheckboxDate.builder()
                        .id(new FieldId("journaluppgifter"))
                        .label("journaluppgifter från den")
                        .code(
                            new Code(
                                "JOURNALUPPGIFTER",
                                "KV_FKMU_0001",
                                "journaluppgifter från den"
                            )
                        )
                        .max(Period.ZERO)
                        .build(),
                    CheckboxDate.builder()
                        .id(new FieldId("annat"))
                        .label("annat")
                        .code(
                            new Code(
                                "ANNAT",
                                "KV_FKMU_0001",
                                "annat"
                            )
                        )
                        .max(Period.ZERO)
                        .build()
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

      @Nested
      class ShouldValidate {

        @Test
        void shallReturnTrueIfElementPresent() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("1"))
                  .value(
                      ElementValueDateList.builder()
                          .dateList(
                              List.of(
                                  ElementValueDate.builder()
                                      .dateId(new FieldId("annat"))
                                      .build()
                              )
                          )
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertTrue(shouldValidate.test(elementData));
        }

        @Test
        void shallReturnFalseIfElementMissing() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("2"))
                  .value(
                      ElementValueDateList.builder()
                          .dateList(
                              List.of(
                                  ElementValueDate.builder()
                                      .dateId(new FieldId("annat"))
                                      .build()
                              )
                          )
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertFalse(shouldValidate.test(elementData));
        }
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
                Ange på vilket sätt hälsotillståndet utgör ett påtagligt hot mot patientens liv i nuläget eller på någon tids sikt.
                                    \s
                Hälsotillståndet kan utgöra ett påtagligt hot även om det finns hopp om att det förbättras.
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

      @Nested
      class ShouldValidate {

        @Test
        void shallReturnTrueIfElementPresent() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("52"))
                  .value(
                      ElementValueCode.builder()
                          .codeId(new FieldId("ENDAST_PALLIATIV"))
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertTrue(shouldValidate.test(elementData));
        }

        @Test
        void shallReturnFalseIfElementMissing() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("52"))
                  .value(
                      ElementValueCode.builder()
                          .codeId(new FieldId("annat"))
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertFalse(shouldValidate.test(elementData));
        }
      }

      @Test
      void shallIncludeCustomMapping() {
        final var expectedConfiguration = new ElementMapping(
            new ElementId("52"), null
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).mapping()
        );
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

      @Nested
      class ShouldValidate {

        @Test
        void shallReturnTrueIfElementPresent() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("52"))
                  .value(
                      ElementValueCode.builder()
                          .codeId(new FieldId("AKUT_LIVSHOTANDE"))
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertTrue(shouldValidate.test(elementData));
        }

        @Test
        void shallReturnFalseIfElementMissing() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("5"))
                  .value(
                      ElementValueCode.builder()
                          .codeId(new FieldId("annat"))
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertFalse(shouldValidate.test(elementData));
        }
      }


      @Test
      void shallIncludeCustomMapping() {
        final var expectedConfiguration = new ElementMapping(
            new ElementId("52"), null
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).mapping()
        );
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
                "Beskriv på vilket sätt  sjukdomstillståndet utgör ett påtagligt hot mot patientens liv"
            )
            .label(
                "Ange om möjligt hur länge hotet mot livet kvarstår när patienten får vård enligt den vårdplan som gäller."
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

      @Nested
      class ShouldValidate {

        @Test
        void shallReturnTrueIfElementPresent() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("52"))
                  .value(
                      ElementValueCode.builder()
                          .codeId(new FieldId("AKUT_LIVSHOTANDE"))
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertTrue(shouldValidate.test(elementData));
        }

        @Test
        void shallReturnFalseIfElementMissing() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("5"))
                  .value(
                      ElementValueCode.builder()
                          .codeId(new FieldId("annat"))
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertFalse(shouldValidate.test(elementData));
        }
      }

      @Test
      void shallIncludeCustomMapping() {
        final var expectedConfiguration = new ElementMapping(
            new ElementId("52"), null
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).mapping()
        );
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

      @Nested
      class ShouldValidate {

        @Test
        void shallReturnTrueIfElementPresent() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("52"))
                  .value(
                      ElementValueCode.builder()
                          .codeId(new FieldId("AKUT_LIVSHOTANDE"))
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertTrue(shouldValidate.test(elementData));
        }

        @Test
        void shallReturnFalseIfElementMissing() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("5"))
                  .value(
                      ElementValueCode.builder()
                          .codeId(new FieldId("annat"))
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertFalse(shouldValidate.test(elementData));
        }
      }

      @Test
      void shallIncludeCustomMapping() {
        final var expectedConfiguration = new ElementMapping(
            new ElementId("52"), null
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).mapping()
        );
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

      @Nested
      class ShouldValidate {

        @Test
        void shallReturnTrueIfElementPresent() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("52.5"))
                  .value(
                      ElementValueBoolean.builder()
                          .value(true)
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertTrue(shouldValidate.test(elementData));
        }

        @Test
        void shallReturnFalseIfElementMissing() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("5"))
                  .value(
                      ElementValueBoolean.builder()
                          .value(false)
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertFalse(shouldValidate.test(elementData));
        }
      }

      @Test
      void shallIncludeCustomMapping() {
        final var expectedConfiguration = new ElementMapping(
            new ElementId("52"), null
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).mapping()
        );
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
                "Beskriv på vilket sätt sjukdomstillståndet utgör ett påtagligt hot mot patientens liv"
            )
            .label(
                "Ange när tillståndet blev livshotande, och om det är möjligt hur länge hotet mot livet kvarstår när patienten får vård enligt den vårdplan som gäller."
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

      @Nested
      class ShouldValidate {

        @Test
        void shallReturnTrueIfElementPresent() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("52"))
                  .value(
                      ElementValueCode.builder()
                          .codeId(new FieldId("ANNAT"))
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertTrue(shouldValidate.test(elementData));
        }

        @Test
        void shallReturnFalseIfElementMissing() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("5"))
                  .value(
                      ElementValueCode.builder()
                          .codeId(new FieldId("annat"))
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK3226.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertFalse(shouldValidate.test(elementData));
        }
      }

      @Test
      void shallIncludeCustomMapping() {
        final var expectedConfiguration = new ElementMapping(
            new ElementId("52"), null
        );

        final var certificateModel = certificateModelFactoryFK3226.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).mapping()
        );
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
