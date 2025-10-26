package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.CertificateModelFactoryAG7804.AG7804_V2_0;

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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateTypeName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.common.model.CertificateLink;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
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
  void shouldIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shouldIncludeTypeName() {
    final var expected = new CertificateTypeName("FK7804");

    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals(expected, certificateModel.typeName());
  }

  @Test
  void shouldIncludeName() {
    final var expectedName = "Läkarintyg för sjukpenning";

    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shouldIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shouldIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shouldIncludeActiveFrom() {
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
  void shouldIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertTrue(certificateModel.availableForCitizen());
  }

  @Test
  void shouldIncludeTexts() {
    final var expected = List.of(
        CertificateText.builder()
            .text(
                "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. Har du frågor kontaktar du den som skrivit ditt intyg. Om du vill ansöka om sjukpenning, gör du det på {LINK_FK}.\n")
            .type(CertificateTextType.PREAMBLE_TEXT)
            .links(List.of(
                CertificateLink.builder()
                    .id("LINK_FK")
                    .name("Försäkringskassan")
                    .url("https://www.forsakringskassan.se/")
                    .build()
            ))
            .build()
    );

    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals(expected, certificateModel.texts());
  }

  @Test
  void shouldIncludeSummaryProvider() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals(FK7804CertificateSummaryProvider.class,
        certificateModel.summaryProvider().getClass());
  }

  @Test
  void shouldIncludeMessageTypes() {
    final var expectedMessageTypes = List.of(
        CertificateMessageType.builder()
            .type(MessageType.MISSING)
            .subject(new Subject(MessageType.MISSING.displayName()))
            .build(),
        CertificateMessageType.builder()
            .type(MessageType.COORDINATION)
            .subject(new Subject(MessageType.COORDINATION.displayName()))
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
  void shouldIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan",
        LOGICAL_ADDRESS
    );

    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shouldIncludeCertificateActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertAll(
        () -> assertNotNull(certificateModel.certificateActionSpecifications()),
        () -> assertFalse(certificateModel.certificateActionSpecifications().isEmpty())
    );
  }

  @Test
  void shouldIncludeMessageActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertAll(
        () -> assertNotNull(certificateModel.messageActionSpecifications()),
        () -> assertFalse(certificateModel.messageActionSpecifications().isEmpty())
    );
  }

  @Test
  void shouldIncludeCertificateActionFactory() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals(certificateActionFactory, certificateModel.certificateActionFactory());
  }

  @Test
  void shallIncludeSchematronPath() {
    final var certificateModel = certificateModelFactoryFK7804.create();

    assertEquals("fk7804/schematron/lisjp.v2.sch", certificateModel.schematronPath().value());
  }

  @Test
  void shouldIncludePdfSpecification() {
    final var model = certificateModelFactoryFK7804.create();
    assertNotNull(model.pdfSpecification(), "PdfSpecification in FK7804 model should not be null");
  }

  @Test
  void allElementSpecificationsShouldHavePdfConfiguration() {
    final var model = certificateModelFactoryFK7804.create();
    model.elementSpecifications().forEach(this::checkPdfConfigRecursive);
  }

  private void checkPdfConfigRecursive(ElementSpecification element) {
    if (!(element.configuration() instanceof ElementConfigurationCategory)
        && !(element.configuration() instanceof ElementConfigurationMessage)
        && !(element.configuration() instanceof ElementConfigurationUnitContactInformation)) {
      assertNotNull(
          element.pdfConfiguration(),
          () -> "Missing pdfConfiguration for element: " + element.id()
      );
    }
    element.children().forEach(this::checkPdfConfigRecursive);
  }

  @Nested
  class CertificateSpecifications {

    @ParameterizedTest
    @ValueSource(strings = {
        "KAT_1", "KAT_2", "KAT_3", "KAT_4", "KAT_5", "KAT_6", "KAT_7", "KAT_8", "KAT_9", "KAT_10",
        "KAT_11", "KAT_12", "UNIT_CONTACT_INFORMATION", "27", "1", "1.3", "28", "29", "19", "32",
        "37", "34", "33", "33.2", "6", "44", "25", "39", "39.2", "39.4", "26", "26.2"
    })
    void shouldIncludeCategories(String id) {
      final var elementId = new ElementId(id);
      final var certificateModel = certificateModelFactoryFK7804.create();

      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exist in elementSpecifications".formatted(elementId));
    }
  }

  @Test
  void shouldIncludeAbleToCreateDraftForModel() {
    final var certificateModel = certificateModelFactoryFK7804.create();
    assertEquals(AG7804_V2_0, certificateModel.getAbleToCreateDraftForModel().orElseThrow());
  }
}