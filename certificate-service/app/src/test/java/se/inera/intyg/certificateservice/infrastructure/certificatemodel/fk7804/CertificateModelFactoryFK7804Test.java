package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;

class CertificateModelFactoryFK7804Test {

  private static final String TYPE = "fk7804";
  private static final String VERSION = "2.0";
  private static final String LOGICAL_ADDRESS = "L-A";

  @Mock
  private CertificateActionFactory certificateActionFactory;

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  private CertificateModelFactoryFK7804 certificateModelFactoryFK7804;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7804 = new CertificateModelFactoryFK7804(
        certificateActionFactory,
        diagnosisCodeRepository
    );
    ReflectionTestUtils.setField(certificateModelFactoryFK7804, "activeFrom",
        java.time.LocalDateTime.now(java.time.ZoneId.systemDefault()));
    ReflectionTestUtils.setField(certificateModelFactoryFK7804, "fkLogicalAddress", "L-A");
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Läkarintyg för sjukpenning";

    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryFK7804,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertTrue(certificateModel.availableForCitizen());
  }

  @Test
  void shallIncludeMessageTypes() {
    final var expectedMessageTypes = List.of(
        CertificateMessageType.builder()
            .type(MessageType.MISSING)
            .subject(new Subject(MessageType.MISSING.displayName()))
            .build(),
        CertificateMessageType.builder()
            .type(MessageType.CONTACT)
            .subject(new Subject(MessageType.CONTACT.displayName()))
            .build(),
        CertificateMessageType.builder()
            .type(MessageType.OTHER)
            .subject(new Subject(MessageType.OTHER.displayName()))
            .build()
    );

    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals(expectedMessageTypes, certificateModel.messageTypes());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan",
        LOGICAL_ADDRESS
    );

    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeCertificateActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertAll(
        () -> assertNotNull(certificateModel.certificateActionSpecifications()),
        () -> assertFalse(certificateModel.certificateActionSpecifications().isEmpty())
    );
  }

  @Test
  void shallIncludeMessageActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertAll(
        () -> assertNotNull(certificateModel.messageActionSpecifications()),
        () -> assertFalse(certificateModel.messageActionSpecifications().isEmpty())
    );
  }

  @Test
  void shallIncludeCertificateActionFactory() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals(certificateActionFactory, certificateModel.certificateActionFactory());
  }

  @Nested
  class CertificateSpecifications {

    @ParameterizedTest
    @ValueSource(strings = {
        "KAT_1", "KAT_2", "KAT_3", "KAT_4", "KAT_5", "KAT_6", "KAT_7", "KAT_8", "KAT_9", "KAT_10",
        "KAT_11", "KAT_12", "UNIT_CONTACT_INFORMATION", "1", "1.3", "28", "29", "19", "32"
    })
    void shallIncludeCategories(String id) {
      final var elementId = new ElementId(id);
      final var certificateModel = certificateModelFactoryFK7804.create();

      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exist in elementSpecifications".formatted(elementId));
    }

    @Test
    void shallIncludeQuestionOvrigt() {
      final var certificateModel = certificateModelFactoryFK7804.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("25")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("25"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionAktivitetsbegransning() {
      final var certificateModel = certificateModelFactoryFK7804.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("44")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("44"),
              certificateModel.elementSpecifications())
      );
    }
  }
}