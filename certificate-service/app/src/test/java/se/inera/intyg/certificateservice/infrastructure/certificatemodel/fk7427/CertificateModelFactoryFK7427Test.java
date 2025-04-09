package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;

class CertificateModelFactoryFK7427Test {

  @Mock
  private CertificateActionFactory certificateActionFactory;
  private static final String TYPE = "fk7427";
  private static final String VERSION = "1.0";
  private CertificateModelFactoryFK7427 certificateModelFactoryFK7427;

  private static final String LOGICAL_ADDRESS = "L-A";

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7427 = new CertificateModelFactoryFK7427(certificateActionFactory,
        diagnosisCodeRepository);

    ReflectionTestUtils.setField(certificateModelFactoryFK7427, "fkLogicalAddress",
        LOGICAL_ADDRESS);
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK7427.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Läkarutlåtande tillfällig föräldrapenning barn 12–16 år";

    final var certificateModel = certificateModelFactoryFK7427.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK7427.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryFK7427.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryFK7427,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryFK7427.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryFK7427.create();

    assertFalse(certificateModel.availableForCitizen());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan",
        LOGICAL_ADDRESS
    );

    final var certificateModel = certificateModelFactoryFK7427.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeCertificateActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK7427.create();

    assertAll(
        () -> assertNotNull(certificateModel.certificateActionSpecifications()),
        () -> assertFalse(certificateModel.certificateActionSpecifications().isEmpty())
    );
  }

  @Test
  void shallIncludeMessageActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK7427.create();

    assertAll(
        () -> assertNotNull(certificateModel.messageActionSpecifications()),
        () -> assertFalse(certificateModel.messageActionSpecifications().isEmpty())
    );
  }


  @Test
  void shallIncludeCertificateActionFactory() {
    final var certificateModel = certificateModelFactoryFK7427.create();

    assertEquals(certificateActionFactory, certificateModel.certificateActionFactory());
  }

  @Nested
  class ElementSpecificationTests {

    @Test
    void shallIncludeCategoryGrundForMedicinsktUnderlag() {
      final var certificateModel = certificateModelFactoryFK7427.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_1")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_1"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionGrundForMedicinsktUnderlag() {
      final var certificateModel = certificateModelFactoryFK7427.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("1")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("1"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionAnnanGrundForMedicinsktUnderlag() {
      final var certificateModel = certificateModelFactoryFK7427.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("1.3")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("1.3"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeIssuingUnitContactInfo() {
      final var certificateModel = certificateModelFactoryFK7427.create();

      assertTrue(
          certificateModel.elementSpecificationExists(new ElementId("UNIT_CONTACT_INFORMATION")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'"
              .formatted(new ElementId("UNIT_CONTACT_INFORMATION"),
                  certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryDiagnos() {
      final var certificateModel = certificateModelFactoryFK7427.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_2")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_2"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionDiagnos() {
      final var certificateModel = certificateModelFactoryFK7427.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("58")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("58"),
              certificateModel.elementSpecifications())
      );
    }

  }
}