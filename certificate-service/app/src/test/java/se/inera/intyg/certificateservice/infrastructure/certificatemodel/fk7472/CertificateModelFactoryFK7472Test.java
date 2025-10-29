package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.CertificateModelFactoryFK7472.SCHEMATRON_PATH;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateTypeName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;

@ExtendWith(MockitoExtension.class)
class CertificateModelFactoryFK7472Test {

  @Mock
  private CertificateActionFactory certificateActionFactory;
  private static final String TYPE = "fk7472";
  private static final String VERSION = "1.0";
  private CertificateModelFactoryFK7472 certificateModelFactoryFK7472;

  private static final String LOGICAL_ADDRESS = "L-A";

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7472 = new CertificateModelFactoryFK7472(certificateActionFactory);

    ReflectionTestUtils.setField(certificateModelFactoryFK7472, "fkLogicalAddress",
        LOGICAL_ADDRESS);
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
  void shouldIncludeTypeName() {
    final var expected = new CertificateTypeName("FK7472");

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(expected, certificateModel.typeName());
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
        "Försäkringskassan",
        LOGICAL_ADDRESS
    );

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeSchematronPath() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(SCHEMATRON_PATH, certificateModel.schematronPath());
  }

  @Test
  void shallIncludePdfSpecifications() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertNotNull(certificateModel.pdfSpecification());
  }

  @Test
  void shallIncludeCertificateActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertAll(
        () -> assertNotNull(certificateModel.certificateActionSpecifications()),
        () -> assertFalse(certificateModel.certificateActionSpecifications().isEmpty())
    );
  }

  @Test
  void shallIncludeMessageActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertAll(
        () -> assertNotNull(certificateModel.messageActionSpecifications()),
        () -> assertFalse(certificateModel.messageActionSpecifications().isEmpty())
    );
  }

  @Nested
  class CertificateSpecifications {

    @Test
    void shallIncludeCategorySymptom() {
      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_1")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_1"), certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionSymptom() {
      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("55")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("55"), certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryPrognos() {
      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_2")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_2"), certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionPrognos() {
      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("56")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("56"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeIssuingUnitContactInfo() {
      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(
          certificateModel.elementSpecificationExists(new ElementId("UNIT_CONTACT_INFORMATION")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'"
              .formatted(new ElementId("UNIT_CONTACT_INFORMATION"),
                  certificateModel.elementSpecifications())
      );
    }
  }

  @Test
  void shallIncludeCertificateActionFactory() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(certificateActionFactory, certificateModel.certificateActionFactory());
  }
}
