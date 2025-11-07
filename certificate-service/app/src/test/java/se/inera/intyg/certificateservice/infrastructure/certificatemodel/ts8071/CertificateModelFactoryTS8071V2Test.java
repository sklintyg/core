package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.GeneralPdfSpecification;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;

class CertificateModelFactoryTS8071V2Test {

  private static final String TYPE = "ts8071";
  private static final String VERSION = "2.0";
  private static final String LOGICAL_ADDRESS = "L-A";

  @Mock
  private CertificateActionFactory certificateActionFactory;

  private CertificateModelFactoryTS8071V2 certificateModelFactoryTS8071V2;

  @BeforeEach
  void setUp() {
    certificateModelFactoryTS8071V2 = new CertificateModelFactoryTS8071V2(
        certificateActionFactory);

    ReflectionTestUtils.setField(certificateModelFactoryTS8071V2, "tsLogicalAddress",
        LOGICAL_ADDRESS);
  }

  @Test
  void shouldIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryTS8071V2.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shouldIncludeName() {
    final var expectedName = "Läkarintyg för högre körkortsbehörigheter, taxiförarlegitimation och på begäran av Transportstyrelsen";

    final var certificateModel = certificateModelFactoryTS8071V2.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shouldIncludeDescription() {
    final var certificateModel = certificateModelFactoryTS8071V2.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludePdfSpecificationWithDescription() {
    final var expected = """
        Transportstyrelsens läkarintyg ska användas vid förlängd giltighet av högre behörighet från 45 år, ansökan om körkortstillstånd för grupp II och III och vid ansökan om taxiförarlegitimation. Transportstyrelsens läkarintyg kan även användas när Transportstyrelsen i annat fall begärt ett allmänt läkarintyg avseende lämplighet att inneha körkort.
        
        Specialistintyg finns bl.a. för alkohol, läkemedel, synfunktion, Alkolås m.m. Se Transportstyrelsens hemsida.""";
    final var certificateModel = certificateModelFactoryTS8071V2.create();

    final var pdfSpecification = (GeneralPdfSpecification) certificateModel.pdfSpecification();

    assertEquals(
        expected,
        pdfSpecification.description()
    );
  }

  @Test
  void shouldIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryTS8071V2.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shouldIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryTS8071V2,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryTS8071V2.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shouldIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryTS8071V2.create();

    assertTrue(certificateModel.availableForCitizen());
  }

  @Test
  void shouldIncludeCertificateSummaryProvider() {
    final var certificateModel = certificateModelFactoryTS8071V2.create();

    assertEquals(TS8071CertificateSummaryProvider.class,
        certificateModel.summaryProvider().getClass());
  }

  @Test
  void shouldNotIncludeCertificateConfirmationModalProvider() {
    final var certificateModel = certificateModelFactoryTS8071V2.create();

    assertNull(certificateModel.confirmationModalProvider());
  }

  @Test
  void shouldIncludeCertificateText() {
    final var expectedText = CertificateText.builder()
        .text(
            "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. Har du frågor kontaktar du den som skrivit ditt intyg.")
        .type(CertificateTextType.PREAMBLE_TEXT)
        .links(Collections.emptyList())
        .build();

    final var certificateModel = certificateModelFactoryTS8071V2.create();

    assertEquals(List.of(expectedText), certificateModel.texts());
  }

  @Test
  void shouldIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("TRANSP"),
        "Transportstyrelsen",
        LOGICAL_ADDRESS,
        "ts/transportstyrelsen-logo.png",
        "Läkarintyg Transportstyrelsen"
    );

    final var certificateModel = certificateModelFactoryTS8071V2.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shouldIncludeCertificateActionSpecifications() {
    final var certificateModel = certificateModelFactoryTS8071V2.create();

    assertAll(
        () -> assertNotNull(certificateModel.certificateActionSpecifications()),
        () -> assertFalse(certificateModel.certificateActionSpecifications().isEmpty())
    );
  }

  @Test
  void shouldNotIncludeMessageActionSpecifications() {
    final var certificateModel = certificateModelFactoryTS8071V2.create();

    assertTrue(certificateModel.messageActionSpecifications().isEmpty());
  }

  @Nested
  class CertificateSpecifications {

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "2.2", "3", "4", "5", "24", "25", "7", "7.2", "7.3", "7.4",
        "8", "8.2", "9", "9.2", "9.3", "10", "10.2", "10.3", "11", "11.2", "11.3", "11.4", "11.5",
        "11.6", "11.7", "11.8", "11.9", "11.10", "12", "13", "13.2", "14", "14.2", "14.3",
        "14.4", "14.5", "14.6", "14.7", "14.8", "14.9", "15", "15.2", "15.3", "16", "16.2", "16.3",
        "17", "17.2", "17.3", "18", "18.2", "18.3", "18.4", "18.5", "18.6", "18.7", "18.8", "18.9",
        "19", "19.2", "19.3", "26", "26.2", "21", "21.2", "22", "23", "23.2", "23.3", "diabetes",
        "UNIT_CONTACT_INFORMATION",})
    void shouldIncludeQuestions(String value) {
      final var certificateModel = certificateModelFactoryTS8071V2.create();

      final var id = new ElementId(value);
      assertTrue(certificateModel.elementSpecificationExists(id),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              id,
              certificateModel.elementSpecifications())
      );
    }

    @ParameterizedTest
    @ValueSource(strings = {"KAT_0.0", "KAT_0.1", "KAT_0.2", "KAT_1.0", "KAT_1.1", "KAT_1.2",
        "KAT_2", "KAT_3", "KAT_4", "KAT_5", "KAT_6", "KAT_7", "KAT_8", "KAT_9", "KAT_10", "KAT_11",
        "KAT_12", "KAT_13", "KAT_15", "KAT_16", "KAT_17"})
    void shouldIncludeCategories(String id) {
      final var elementId = new ElementId(id);
      final var certificateModel = certificateModelFactoryTS8071V2.create();

      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId,
              certificateModel.elementSpecifications()));
    }

  }

  @Test
  void shouldIncludeCertificateActionFactory() {
    final var certificateModel = certificateModelFactoryTS8071V2.create();

    assertEquals(certificateActionFactory, certificateModel.certificateActionFactory());
  }
}

