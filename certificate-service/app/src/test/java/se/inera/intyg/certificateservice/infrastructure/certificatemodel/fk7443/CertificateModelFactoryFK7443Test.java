package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7443;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationUnitContactInformation;

@ExtendWith(MockitoExtension.class)
class CertificateModelFactoryFK7443Test {

  private static final String TYPE = "fk7443";
  private static final String VERSION = "1.0";
  private CertificateModelFactoryFK7443 certificateModelFactoryFK7443;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7443 = new CertificateModelFactoryFK7443();
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK7443.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Intyg om tillfällig föräldrapenning";

    final var certificateModel = certificateModelFactoryFK7443.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK7443.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryFK7443,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryFK7443.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan"
    );

    final var certificateModel = certificateModelFactoryFK7443.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeCertificateActionCreate() {
    final var expectedType = CertificateActionType.CREATE;

    final var certificateModel = certificateModelFactoryFK7443.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType())
        ), "Expected type: %s".formatted(expectedType)
    );
  }

  @Test
  void shallIncludeCertificateActionRead() {
    final var expectedType = CertificateActionType.READ;

    final var certificateModel = certificateModelFactoryFK7443.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType()
            )
        ), "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionUpdate() {
    final var expectedType = CertificateActionType.UPDATE;

    final var certificateModel = certificateModelFactoryFK7443.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDelete() {
    final var expectedType = CertificateActionType.DELETE;

    final var certificateModel = certificateModelFactoryFK7443.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSign() {
    final var expectedType = CertificateActionType.SIGN;

    final var certificateModel = certificateModelFactoryFK7443.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSend() {
    final var expectedType = CertificateActionType.SEND;

    final var certificateModel = certificateModelFactoryFK7443.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionPrint() {
    final var expectedType = CertificateActionType.PRINT;

    final var certificateModel = certificateModelFactoryFK7443.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRevoke() {
    final var expectedType = CertificateActionType.REVOKE;

    final var certificateModel = certificateModelFactoryFK7443.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Nested
  class CertificateSpecifications {

    @Nested
    class CategorySymptom {

      private static final ElementId ELEMENT_ID = new ElementId("KAT_1");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK7443.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationCategory.builder()
            .name("Barnets diagnos eller symtom")
            .build();

        final var certificateModel = certificateModelFactoryFK7443.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }
    }

    @Nested
    class QuestionSymptom {

      private static final ElementId ELEMENT_ID = new ElementId("1");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK7443.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationTextArea.builder()
            .name("Symtom")
            .id(new FieldId("1.1"))
            .limit(318)
            .build();

        final var certificateModel = certificateModelFactoryFK7443.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }

      @Test
      void shallIncludeRules() {
        final var expectedRules = List.of(
            ElementRule.builder()
                .id(new ElementId("1"))
                .type(ElementRuleType.MANDATORY)
                .expression(
                    new RuleExpression("$1.1")
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7443.create();

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

        final var certificateModel = certificateModelFactoryFK7443.create();

        assertEquals(expectedValidations,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }

    @Nested
    class IssuingUnitContactInfo {

      private static final ElementId ELEMENT_ID = new ElementId("UNIT_CONTACT_INFORMATION");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK7443.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationUnitContactInformation.builder()
            .build();

        final var certificateModel = certificateModelFactoryFK7443.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }

      @Test
      void shallIncludeValidation() {
        final var expectedValidation = List.of(
            ElementValidationUnitContactInformation.builder().build()
        );

        final var certificateModel = certificateModelFactoryFK7443.create();

        assertEquals(expectedValidation,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }
  }
}