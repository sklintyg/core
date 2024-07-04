package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.ANNAN_KROPPSILIG_FUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.HORSELFUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.INTELLEKTUELL_FUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.KOMMUNIKATION_SOCIAL_INTERAKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.KOORDINATION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.PSYKISK_FUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.SINNESFUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.SYNFUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.UPPMARKSAMHET;

import java.time.LocalDateTime;
import java.time.Period;
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
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisTerminology;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateList;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDiagnosis;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

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

    @Nested
    class CategoryGrundForMedicinsktUnderlag {

      private static final ElementId ELEMENT_ID = new ElementId("KAT_1");

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

    @Nested
    class QuestionGrundForMedicinsktUnderlag {

      private static final ElementId ELEMENT_ID = new ElementId("1");

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
                        .label("journaluppgifter från och med")
                        .code(
                            new Code(
                                "JOURNALUPPGIFTER",
                                "KV_FKMU_0001",
                                "journaluppgifter från och med"
                            )
                        )
                        .max(Period.ZERO)
                        .build(),
                    CheckboxDate.builder()
                        .id(new FieldId("anhorig"))
                        .label("anhörig eller annans beskrivning av patienten")
                        .code(
                            new Code(
                                "ANHORIG",
                                "KV_FKMU_0001",
                                "anhörig eller annans beskrivning av patienten"
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

        final var certificateModel = certificateModelFactoryFK7809.create();

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
                        "$undersokningAvPatienten || $journaluppgifter || $anhorig || $annat"
                    )
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

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

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Nested
    class QuestionRelationTillPatienten {

      private static final ElementId ELEMENT_ID = new ElementId("1.4");

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
        final var expectedConfiguration = ElementConfigurationTextField.builder()
            .name(
                "Ange anhörig eller annans relation till patienten")
            .id(new FieldId("1.4"))
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

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
                        "$1.4"
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
                        "$anhorig"
                    )
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

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

        final var certificateModel = certificateModelFactoryFK7809.create();

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
                  .id(new ElementId("1"))
                  .value(
                      ElementValueDateList.builder()
                          .dateList(
                              List.of(
                                  ElementValueDate.builder()
                                      .dateId(new FieldId("anhorig"))
                                      .build()
                              )
                          )
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK7809.create();

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
                                      .dateId(new FieldId("anhorig"))
                                      .build()
                              )
                          )
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK7809.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertFalse(shouldValidate.test(elementData));
        }
      }
    }

    @Nested
    class QuestionAnnanGrundForMedicinsktUnderlag {

      private static final ElementId ELEMENT_ID = new ElementId("1.3");

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
        final var expectedConfiguration = ElementConfigurationTextArea.builder()
            .name(
                "Ange vad annat är")
            .id(new FieldId("1.3"))
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

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

        final var certificateModel = certificateModelFactoryFK7809.create();

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

        final var certificateModel = certificateModelFactoryFK7809.create();

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

          final var certificateModel = certificateModelFactoryFK7809.create();

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

          final var certificateModel = certificateModelFactoryFK7809.create();

          final var shouldValidate = certificateModel.elementSpecification(ELEMENT_ID)
              .shouldValidate();

          assertFalse(shouldValidate.test(elementData));
        }
      }

    }

    @Nested
    class QuestionBaseratPaAnnatMedicinsktUnderlag {

      private static final ElementId ELEMENT_ID = new ElementId("3");

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
        final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
            .name(
                "Är utlåtandet även baserat på andra medicinska utredningar eller underlag?")
            .id(new FieldId("3.1"))
            .selectedText("Ja")
            .unselectedText("Nej")
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }

      @Test
      void shallIncludeRules() {
        final var expectedRule = List.of(
            ElementRuleExpression.builder()
                .id(ELEMENT_ID)
                .type(ElementRuleType.MANDATORY)
                .expression(
                    new RuleExpression(
                        "exists($3.1)"
                    )
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedRule,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidation() {
        final var expectedValidations = List.of(
            ElementValidationBoolean.builder()
                .mandatory(true)
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }

      @Nested
      class QuestionUtredningEllerUnderlag {

        private static final ElementId ELEMENT_ID = new ElementId("4");

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
          final var codes = List.of(
              CodeSystemKvFkmu0005.NEUROPSYKIATRISKT,
              CodeSystemKvFkmu0005.HABILITERING,
              CodeSystemKvFkmu0005.ARBETSTERAPEUT,
              CodeSystemKvFkmu0005.FYSIOTERAPEUT,
              CodeSystemKvFkmu0005.LOGOPED,
              CodeSystemKvFkmu0005.PSYKOLOG,
              CodeSystemKvFkmu0005.SPECIALISTKLINIK,
              CodeSystemKvFkmu0005.VARD_UTOMLANDS,
              CodeSystemKvFkmu0005.HORSELHABILITERING,
              CodeSystemKvFkmu0005.SYNHABILITERING,
              CodeSystemKvFkmu0005.AUDIONOM,
              CodeSystemKvFkmu0005.DIETIST,
              CodeSystemKvFkmu0005.ORTOPTIST,
              CodeSystemKvFkmu0005.ORTOPEDTEKNIKER,
              CodeSystemKvFkmu0005.OVRIGT
          );
          final var expectedConfiguration = ElementConfigurationMedicalInvestigationList.builder()
              .name(
                  "Ange utredning eller underlag")
              .id(new FieldId("4.1"))
              .informationSourceDescription(
                  "Skriv exempelvis Neuropsykiatriska kliniken på X-stads sjukhus eller om patienten själv kommer att bifoga utredningen till sin ansökan.")
              .informationSourceText("Från vilken vårdgivare")
              .dateText("Datum")
              .typeText("Utredning eller underlag")
              .list(List.of(
                  MedicalInvestigationConfig.builder()
                      .id(new FieldId("medicalInvestigation1"))
                      .dateId(new FieldId("medicalInvestigation1_DATE"))
                      .investigationTypeId(new FieldId("medicalInvestigation1_INVESTIGATION_TYPE"))
                      .informationSourceId(new FieldId("medicalInvestigation1_INFORMATION_SOURCE"))
                      .typeOptions(codes)
                      .min(null)
                      .max(Period.ofDays(0))
                      .build(),
                  MedicalInvestigationConfig.builder()
                      .id(new FieldId("medicalInvestigation2"))
                      .dateId(new FieldId("medicalInvestigation2_DATE"))
                      .investigationTypeId(new FieldId("medicalInvestigation2_INVESTIGATION_TYPE"))
                      .informationSourceId(new FieldId("medicalInvestigation2_INFORMATION_SOURCE"))
                      .typeOptions(codes)
                      .min(null)
                      .max(Period.ofDays(0))
                      .build(),
                  MedicalInvestigationConfig.builder()
                      .id(new FieldId("medicalInvestigation3"))
                      .dateId(new FieldId("medicalInvestigation3_DATE"))
                      .investigationTypeId(new FieldId("medicalInvestigation3_INVESTIGATION_TYPE"))
                      .informationSourceId(new FieldId("medicalInvestigation3_INFORMATION_SOURCE"))
                      .typeOptions(codes)
                      .min(null)
                      .max(Period.ofDays(0))
                      .build()
              ))
              .build();

          final var certificateModel = certificateModelFactoryFK7809.create();

          assertEquals(expectedConfiguration,
              certificateModel.elementSpecification(ELEMENT_ID).configuration()
          );
        }

        @Test
        void shallIncludeRules() {
          final var expectedRule = List.of(
              ElementRuleExpression.builder()
                  .id(ELEMENT_ID)
                  .type(ElementRuleType.MANDATORY)
                  .expression(
                      new RuleExpression(
                          "!empty($medicalInvestigation1_DATE) "
                              + "|| !empty($medicalInvestigation1_INVESTIGATION_TYPE) "
                              + "|| !empty($medicalInvestigation1_INFORMATION_SOURCE)"
                      )
                  )
                  .build(),
              ElementRuleExpression.builder()
                  .id(new ElementId("3"))
                  .type(ElementRuleType.SHOW)
                  .expression(
                      new RuleExpression(
                          "$3.1"
                      )
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK7809.create();

          assertEquals(expectedRule,
              certificateModel.elementSpecification(ELEMENT_ID).rules()
          );
        }

        @Test
        void shallIncludeValidation() {
          final var expectedValidations = List.of(
              ElementValidationMedicalInvestigationList.builder()
                  .mandatory(true)
                  .max(Period.ofDays(0))
                  .min(null)
                  .limit(4000)
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK7809.create();

          assertEquals(expectedValidations,
              certificateModel.elementSpecification(ELEMENT_ID).validations()
          );
        }
      }
    }

    @Nested
    class CategoryAktivitetsBegransningar {

      private static final ElementId ELEMENT_ID = new ElementId("KAT_6");

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
            .name("Aktivitetsbegränsningar")
            .description(
                """
                    Beskriv de aktivitetsbegränsningar som du bedömer att patienten har. Beskriv även om din bedömning är baserad på observationer, anamnes eller utredning gjord av någon annan. Någon annan kan till exempel vara psykolog, arbetsterapeut, audionom, syn- eller hörselpedagog.
                                            
                    I beskrivningen kan du utgå från aktiviteter inom områden som till exempel kommunikation, förflyttning, personlig vård och hemliv. Ange om möjligt svårighetsgraden på aktivitetsbegränsningarna.
                    """)
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }
    }

    @Nested
    class QuestionAktivitetsbegransningar {

      private static final ElementId ELEMENT_ID = new ElementId("17");

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
        final var expectedConfiguration = ElementConfigurationTextArea.builder()
            .name("Beskriv vad patienten har svårt att göra på grund av sin funktionsnedsättning")
            .id(new FieldId("17.1"))
            .label("Ge konkreta exempel på aktiviteter i vardagen där svårigheter uppstår")
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }

      @Test
      void shallIncludeRules() {
        final var expectedRules = List.of(
            ElementRuleLimit.builder()
                .id(ELEMENT_ID)
                .type(ElementRuleType.TEXT_LIMIT)
                .limit(new RuleLimit((short) 4000))
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationText.builder()
                .mandatory(false)
                .limit(4000)
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Nested
    class CategoryMedicinskBehandling {

      private static final ElementId ELEMENT_ID = new ElementId("KAT_7");

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
            .name("Medicinsk behandling")
            .description(
                "Ange pågående och planerade medicinska behandlingar eller åtgärder som är relevanta utifrån funktionsnedsättningen. Det kan vara ordinerade läkemedel, hjälpmedel, träningsinsatser eller särskild kost. Ange den medicinska indikationen och syftet med behandlingen eller åtgärden. Du kan även beskriva andra behandlingar och åtgärder som prövats utifrån funktionsnedsättningen. Om patienten använder läkemedel som inte är subventionerade: beskriv anledningen till det.")
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }
    }

    @Nested
    class QuestionPagaendeOchPlaneradeBehandlingar {

      private static final ElementId ELEMENT_ID = new ElementId("50.1");

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
        final var expectedConfiguration = ElementConfigurationTextArea.builder()
            .name("Ange pågående och planerade medicinska behandlingar")
            .id(new FieldId("50.1"))
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }

      @Test
      void shallIncludeRules() {
        final var expectedRules = List.of(
            ElementRuleLimit.builder()
                .id(new ElementId("50.1"))
                .type(ElementRuleType.TEXT_LIMIT)
                .limit(new RuleLimit((short) 4000))
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationText.builder()
                .mandatory(false)
                .limit(4000)
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Nested
    class QuestionVardenhetOchTidplan {

      private static final ElementId ELEMENT_ID = new ElementId("50.2");

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
        final var expectedConfiguration = ElementConfigurationTextArea.builder()
            .name("Ange ansvarig vårdenhet och tidplan")
            .label(null)
            .description(null)
            .id(new FieldId("50.2"))
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }

      @Test
      void shallIncludeRules() {
        final var expectedRules = List.of(
            ElementRuleExpression.builder()
                .id(new ElementId("50.2"))
                .type(ElementRuleType.MANDATORY)
                .expression(new RuleExpression("$50.2"))
                .build(),
            ElementRuleLimit.builder()
                .id(new ElementId("50.2"))
                .type(ElementRuleType.TEXT_LIMIT)
                .limit(new RuleLimit((short) 4000))
                .build(),
            ElementRuleExpression.builder()
                .id(new ElementId("50.1"))
                .type(ElementRuleType.SHOW)
                .expression(new RuleExpression("$50.1"))
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

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

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Nested
    class CategoryPrognas {

      private static final ElementId ELEMENT_ID = new ElementId("KAT_8");

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
            .name("Prognos")
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }
    }

    @Nested
    class QuestionPrognos {

      private static final ElementId ELEMENT_ID = new ElementId("51");

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
        final var expectedConfiguration = ElementConfigurationTextArea.builder()
            .name(
                "Hur förväntas patientens funktionsnedsättning och aktivitetsbegränsningar utvecklas över tid?")
            .id(new FieldId("51.1"))
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

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
                .expression(new RuleExpression("$51.1"))
                .build(),
            ElementRuleLimit.builder()
                .id(ELEMENT_ID)
                .type(ElementRuleType.TEXT_LIMIT)
                .limit(new RuleLimit((short) 4000))
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

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

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Nested
    class CategoryOvrigt {

      private static final ElementId ELEMENT_ID = new ElementId("KAT_9");

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
            .name("Övrigt")
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }
    }

    @Nested
    class QuestionOvrigt {

      private static final ElementId ELEMENT_ID = new ElementId("25");

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
        final var expectedConfiguration = ElementConfigurationTextArea.builder()
            .name("Övriga upplysningar")
            .id(new FieldId("25.1"))
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }

      @Test
      void shallIncludeRules() {
        final var expectedRules = List.of(
            ElementRuleLimit.builder()
                .id(ELEMENT_ID)
                .type(ElementRuleType.TEXT_LIMIT)
                .limit(new RuleLimit((short) 4000))
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationText.builder()
                .mandatory(false)
                .limit(4000)
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Nested
    class CategoryDiagnos {

      private static final ElementId ELEMENT_ID = new ElementId("KAT_4");

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
            .name("Diagnos")
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }
    }

    @Nested
    class QuestionDiagnos {

      private static final ElementId ELEMENT_ID = new ElementId("58");

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
        final var expectedConfiguration = ElementConfigurationDiagnosis.builder()
            .id(new FieldId("58.1"))
            .name(
                "Diagnos eller diagnoser")
            .description(
                "Ange diagnoskod med så många positioner som möjligt. Använd inga andra tecken än bokstäver och siffror.")
            .terminology(
                List.of(
                    new ElementDiagnosisTerminology("ICD_10_SE", "ICD-10-SE",
                        "1.2.752.116.1.1.1.1.8")
                )
            )
            .list(
                List.of(
                    new ElementDiagnosisListItem(new FieldId("huvuddiagnos")),
                    new ElementDiagnosisListItem(new FieldId("diagnos2")),
                    new ElementDiagnosisListItem(new FieldId("diagnos3")),
                    new ElementDiagnosisListItem(new FieldId("diagnos4")),
                    new ElementDiagnosisListItem(new FieldId("diagnos5"))
                )
            )
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

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
                        "exists($huvuddiagnos)"
                    )
                )
                .build(),
            ElementRuleLimit.builder()
                .id(ELEMENT_ID)
                .type(ElementRuleType.TEXT_LIMIT)
                .limit(
                    new RuleLimit((short) 81)
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationDiagnosis.builder()
                .mandatoryField(new FieldId("huvuddiagnos"))
                .order(
                    List.of(
                        new FieldId("huvuddiagnos"),
                        new FieldId("diagnos2"),
                        new FieldId("diagnos3"),
                        new FieldId("diagnos4"),
                        new FieldId("diagnos5")
                    )
                )
                .diagnosisCodeRepository(diagnosisCodeRepository)
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Nested
    class QuestionDiagnosMotivering {

      private static final ElementId ELEMENT_ID = new ElementId("5");

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
        final var expectedConfiguration = ElementConfigurationTextArea.builder()
            .name("Sammanfatta historiken för diagnoserna")
            .id(new FieldId("5"))
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

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
                .expression(new RuleExpression("$5"))
                .build(),
            ElementRuleLimit.builder()
                .id(ELEMENT_ID)
                .type(ElementRuleType.TEXT_LIMIT)
                .limit(new RuleLimit((short) 4000))
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

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

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Nested
    class CategoryFunktionsnedsattning {

      private static final ElementId ELEMENT_ID = new ElementId("KAT_5");

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
            .name("Funktionsnedsättning")
            .description(
                """
                    Beskriv de funktionsnedsättningar som patienten har. Ange om din bedömning är baserad på observationer, undersökningsfynd eller testresultat. Det kan till exempel vara:
                    <ul>
                    <li>avvikelser i somatiskt och psykiskt status</li><li>röntgen- och laboratoriefynd</li><li>resultat av kliniskt fysiologiska undersökningar</li><li>andra testresultat, exempelvis neuropsykologiska.</li></ul>
                    Ange även vilka uppgifter som är baserade på anamnes. Ange om möjligt grad av funktionsnedsättning (till exempel lätt, måttlig, stor eller total).
                                        
                    Funktionsområdenas hjälptexter följer väsentligen ICF men då kategorierna i läkarutlåtandena är färre har vissa förenklingar gjorts.""")
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }
    }

    @Nested
    class QuestionFunktionsnedsattning {

      private static final ElementId ELEMENT_ID = new ElementId("funktionsnedsattning");

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
        final var expectedConfiguration = ElementConfigurationCheckboxMultipleCode.builder()
            .id(new FieldId("funktionsnedsattning"))
            .name("Välj alternativ att fylla i för att visa fritextfält. Välj minst ett:")
            .elementLayout(ElementLayout.COLUMNS)
            .list(List.of(
                new ElementConfigurationCode(new FieldId("8.2"),
                    INTELLEKTUELL_FUNKTION.displayName(),
                    INTELLEKTUELL_FUNKTION),
                new ElementConfigurationCode(new FieldId("9.2"),
                    KOMMUNIKATION_SOCIAL_INTERAKTION.displayName(),
                    KOMMUNIKATION_SOCIAL_INTERAKTION),
                new ElementConfigurationCode(new FieldId("10.2"), UPPMARKSAMHET.displayName(),
                    UPPMARKSAMHET),
                new ElementConfigurationCode(new FieldId("11.2"), PSYKISK_FUNKTION.displayName(),
                    PSYKISK_FUNKTION),
                new ElementConfigurationCode(new FieldId("48.2"), HORSELFUNKTION.displayName(),
                    HORSELFUNKTION),
                new ElementConfigurationCode(new FieldId("49.2"), SYNFUNKTION.displayName(),
                    SYNFUNKTION),
                new ElementConfigurationCode(new FieldId("12.2"), SINNESFUNKTION.displayName(),
                    SINNESFUNKTION),
                new ElementConfigurationCode(new FieldId("13.2"), KOORDINATION.displayName(),
                    KOORDINATION),
                new ElementConfigurationCode(new FieldId("14.2"),
                    ANNAN_KROPPSILIG_FUNKTION.displayName(), ANNAN_KROPPSILIG_FUNKTION)
            ))
            .build();

        final var certificateModel = certificateModelFactoryFK7809.create();

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
                        "$8.2 || $9.2 || $10.2 || $11.2 || $48.2 || $49.2 || $12.2 || $13.2 || $14.2"))
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedRules,
            certificateModel.elementSpecification(ELEMENT_ID).rules()
        );
      }

      @Test
      void shallIncludeValidations() {
        final var expectedValidations = List.of(
            ElementValidationCodeList.builder()
                .mandatory(true)
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7809.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Nested
    class FunktionsnedsattningMotivering {

      @Test
      void shallIncludeIntellektuellFunktion() {
        final var certificateModel = certificateModelFactoryFK7809.create();
        final var elementId = new ElementId("8");
        assertTrue(certificateModel.elementSpecificationExists(elementId),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                elementId, certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeKommunikationSocialInteraktion() {
        final var certificateModel = certificateModelFactoryFK7809.create();
        final var elementId = new ElementId("9");
        assertTrue(certificateModel.elementSpecificationExists(elementId),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                elementId, certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeUppmarksamhet() {
        final var certificateModel = certificateModelFactoryFK7809.create();
        final var elementId = new ElementId("10");
        assertTrue(certificateModel.elementSpecificationExists(elementId),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                elementId, certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludePsykiskFunktion() {
        final var certificateModel = certificateModelFactoryFK7809.create();
        final var elementId = new ElementId("11");
        assertTrue(certificateModel.elementSpecificationExists(elementId),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                elementId, certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeHorselFunktion() {
        final var certificateModel = certificateModelFactoryFK7809.create();
        final var elementId = new ElementId("48");
        assertTrue(certificateModel.elementSpecificationExists(elementId),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                elementId, certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeSynFunktion() {
        final var certificateModel = certificateModelFactoryFK7809.create();
        final var elementId = new ElementId("49");
        assertTrue(certificateModel.elementSpecificationExists(elementId),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                elementId, certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeSinnesFunktion() {
        final var certificateModel = certificateModelFactoryFK7809.create();
        final var elementId = new ElementId("12");
        assertTrue(certificateModel.elementSpecificationExists(elementId),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                elementId, certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeKoordination() {
        final var certificateModel = certificateModelFactoryFK7809.create();
        final var elementId = new ElementId("13");
        assertTrue(certificateModel.elementSpecificationExists(elementId),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                elementId, certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeAnnanKroppsligFunktion() {
        final var certificateModel = certificateModelFactoryFK7809.create();
        final var elementId = new ElementId("14");
        assertTrue(certificateModel.elementSpecificationExists(elementId),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
                elementId, certificateModel.elementSpecifications())
        );
      }

      @Nested
      class ShouldValidate {

        @Test
        void shallReturnTrueIfElementPresent() {
          final var elementData = List.of(
              ElementData.builder()
                  .id(new ElementId("funktionsnedsattning"))
                  .value(
                      ElementValueCodeList.builder()
                          .list(
                              List.of(
                                  ElementValueCode.builder()
                                      .codeId(new FieldId("8.2"))
                                      .build()
                              )
                          )
                          .build()
                  )
                  .build()
          );

          final var certificateModel = certificateModelFactoryFK7809.create();

          final var shouldValidate = certificateModel.elementSpecification(
                  new ElementId("8"))
              .shouldValidate();

          assertTrue(shouldValidate.test(elementData));
        }
      }
    }
  }
}



