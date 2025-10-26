package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426;

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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateTypeName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;

class CertificateModelFactoryFK7426Test {

  @Mock
  private CertificateActionFactory certificateActionFactory;
  private static final String TYPE = "fk7426";
  private static final String VERSION = "1.0";
  private CertificateModelFactoryFK7426 certificateModelFactoryFK7426;

  private static final String LOGICAL_ADDRESS = "L-A";

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7426 = new CertificateModelFactoryFK7426(certificateActionFactory,
        diagnosisCodeRepository);

    ReflectionTestUtils.setField(certificateModelFactoryFK7426, "fkLogicalAddress",
        LOGICAL_ADDRESS);
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK7426.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shouldIncludeTypeName() {
    final var expected = new CertificateTypeName("FK7426");

    final var certificateModel = certificateModelFactoryFK7426.create();

    assertEquals(expected, certificateModel.typeName());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har fyllt 18 år";

    final var certificateModel = certificateModelFactoryFK7426.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK7426.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryFK7426.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryFK7426,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryFK7426.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryFK7426.create();

    assertFalse(certificateModel.availableForCitizen());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan",
        LOGICAL_ADDRESS
    );

    final var certificateModel = certificateModelFactoryFK7426.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeCertificateActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK7426.create();

    assertAll(
        () -> assertNotNull(certificateModel.certificateActionSpecifications()),
        () -> assertFalse(certificateModel.certificateActionSpecifications().isEmpty())
    );
  }

  @Test
  void shallIncludeMessageActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK7426.create();

    assertAll(
        () -> assertNotNull(certificateModel.messageActionSpecifications()),
        () -> assertFalse(certificateModel.messageActionSpecifications().isEmpty())
    );
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

    final var certificateModel = certificateModelFactoryFK7426.create();
    assertEquals(expectedMessageTypes, certificateModel.messageTypes());
  }

  @Test
  void shallIncludeCertificateActionFactory() {
    final var certificateModel = certificateModelFactoryFK7426.create();

    assertEquals(certificateActionFactory, certificateModel.certificateActionFactory());
  }

  @Test
  void shallIncludeSchematronPath() {
    final var certificateModel = certificateModelFactoryFK7426.create();

    assertEquals("fk7426/schematron/lu_tfp_asb_18.v1.sch",
        certificateModel.schematronPath().value());
  }

  @Nested
  class ElementSpecificationTests {

    @ParameterizedTest
    @MethodSource("provideElementSpecifications")
    void shallIncludeElementSpecification(String elementId) {
      final var certificateModel = certificateModelFactoryFK7426.create();

      assertTrue(
          certificateModel.elementSpecificationExists(new ElementId(elementId)),
          "Expected elementId: '%s' to exist in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications()
          )
      );
    }

    private static List<Arguments> provideElementSpecifications() {
      return List.of(
          Arguments.of("KAT_1"),
          Arguments.of("1"),
          Arguments.of("1.3"),
          Arguments.of("UNIT_CONTACT_INFORMATION"),
          Arguments.of("KAT_2"),
          Arguments.of("58"),
          Arguments.of("55"),
          Arguments.of("KAT_3"),
          Arguments.of("71"),
          Arguments.of("72"),
          Arguments.of("KAT_4"),
          Arguments.of("60"),
          Arguments.of("KAT_5"),
          Arguments.of("19"),
          Arguments.of("20"),
          Arguments.of("KAT_6"),
          Arguments.of("61"),
          Arguments.of("61.2"),
          Arguments.of("KAT_7"),
          Arguments.of("62"),
          Arguments.of("62.2"),
          Arguments.of("62.3"),
          Arguments.of("62.4")
      );
    }
  }
}