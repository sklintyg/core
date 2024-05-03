package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7211;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7211.CertificateModelFactoryFK7211.PDF_FK_7211_PDF;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7211.CertificateModelFactoryFK7211.SCHEMATRON_PATH;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationUnitContactInformation;

class CertificateModelFactoryFK7211Test {

  private static final String FK_7211 = "fk7211";
  private static final String VERSION = "1.0";
  private CertificateModelFactoryFK7211 certificateModelFactoryFK7211;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7211 = new CertificateModelFactoryFK7211();
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(FK_7211))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Intyg om graviditet";

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK7211.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(certificateModelFactoryFK7211, "activeFrom",
        expectedActiveFrom);

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryFK7211.create();

    assertTrue(certificateModel.availableForCitizen());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan"
    );

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeCertificateActionCreate() {
    final var expectedType = CertificateActionType.CREATE;

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRead() {
    final var expectedType = CertificateActionType.READ;

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionUpdate() {
    final var expectedType = CertificateActionType.UPDATE;

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDelete() {
    final var expectedType = CertificateActionType.DELETE;

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSign() {
    final var expectedType = CertificateActionType.SIGN;

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSend() {
    final var expectedType = CertificateActionType.SEND;

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionPrint() {
    final var expectedType = CertificateActionType.PRINT;

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRevoke() {
    final var expectedType = CertificateActionType.REVOKE;

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReplace() {
    final var expectedType = CertificateActionType.REPLACE;

    final var certificateModel = certificateModelFactoryFK7211.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludePdfTemplatePath() {
    final var certificateModel = certificateModelFactoryFK7211.create();

    assertEquals(PDF_FK_7211_PDF, certificateModel.pdfTemplatePath());
  }

  @Test
  void shallIncludeSchematronPath() {
    final var certificateModel = certificateModelFactoryFK7211.create();

    assertEquals(SCHEMATRON_PATH, certificateModel.schematronPath());
  }

  @Nested
  class CertificateSpecifications {

    @Nested
    class CategoryBeraknatNedkomstdatum {

      private static final ElementId ELEMENT_ID = new ElementId("KAT_1");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK7211.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationCategory.builder()
            .name("Beräknat nedkomstdatum")
            .build();

        final var certificateModel = certificateModelFactoryFK7211.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }
    }

    @Nested
    class QuestionBeraknatNedkomstdatum {

      private static final ElementId ELEMENT_ID = new ElementId("1");

      @Test
      void shallIncludeId() {
        final var certificateModel = certificateModelFactoryFK7211.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationDate.builder()
            .name("Beräknat nedkomstdatum")
            .id(new FieldId("1.1"))
            .min(Period.ofDays(0))
            .max(Period.ofYears(1))
            .build();

        final var certificateModel = certificateModelFactoryFK7211.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }

      @Test
      void shallIncludeRules() {
        final var expectedRules = List.of(
            ElementRuleExpression.builder()
                .id(new ElementId("1"))
                .type(ElementRuleType.MANDATORY)
                .expression(
                    new RuleExpression("$1.1")
                )
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7211.create();

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
                .max(Period.ofYears(1))
                .build()
        );

        final var certificateModel = certificateModelFactoryFK7211.create();

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
        final var certificateModel = certificateModelFactoryFK7211.create();

        assertTrue(certificateModel.elementSpecificationExists(ELEMENT_ID),
            "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(ELEMENT_ID,
                certificateModel.elementSpecifications())
        );
      }

      @Test
      void shallIncludeConfiguration() {
        final var expectedConfiguration = ElementConfigurationUnitContactInformation.builder()
            .build();

        final var certificateModel = certificateModelFactoryFK7211.create();

        assertEquals(expectedConfiguration,
            certificateModel.elementSpecification(ELEMENT_ID).configuration()
        );
      }

      @Test
      void shallIncludeValidation() {
        final var expectedValidation = List.of(
            ElementValidationUnitContactInformation.builder().build()
        );

        final var certificateModel = certificateModelFactoryFK7211.create();

        assertEquals(expectedValidation,
            certificateModel.elementSpecification(ELEMENT_ID).validations()
        );
      }
    }
  }
}
