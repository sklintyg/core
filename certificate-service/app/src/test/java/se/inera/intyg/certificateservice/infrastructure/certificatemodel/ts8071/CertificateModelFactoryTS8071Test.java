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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;

class CertificateModelFactoryTS8071Test {

  private static final String TYPE = "ts8071";
  private static final String VERSION = "1.0";
  private static final String LOGICAL_ADDRESS = "L-A";

  @Mock
  private CertificateActionFactory certificateActionFactory;

  private CertificateModelFactoryTS8071 certificateModelFactoryTS8071;

  @BeforeEach
  void setUp() {
    certificateModelFactoryTS8071 = new CertificateModelFactoryTS8071(certificateActionFactory);

    ReflectionTestUtils.setField(certificateModelFactoryTS8071, "tsLogicalAddress",
        LOGICAL_ADDRESS);
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryTS8071.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Läkarintyg avseende högre körkortsbehörigheter eller taxiförarlegitimation";

    final var certificateModel = certificateModelFactoryTS8071.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryTS8071.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryTS8071.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryTS8071,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryTS8071.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryTS8071.create();

    assertTrue(certificateModel.availableForCitizen());
  }

  @Test
  void shallIncludeCertificateSummaryProvider() {
    final var certificateModel = certificateModelFactoryTS8071.create();

    assertEquals(TS8071CertificateSummaryProvider.class,
        certificateModel.summaryProvider().getClass());
  }

  @Test
  void shallNotIncludeCertificateConfirmationModalProvider() {
    final var certificateModel = certificateModelFactoryTS8071.create();

    assertNull(certificateModel.confirmationModalProvider());
  }

  @Test
  void shallIncludeCertificateText() {
    final var expectedText = CertificateText.builder()
        .text(
            "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. Har du frågor kontaktar du den som skrivit ditt intyg.")
        .type(CertificateTextType.PREAMBLE_TEXT)
        .links(Collections.emptyList())
        .build();

    final var certificateModel = certificateModelFactoryTS8071.create();

    assertEquals(List.of(expectedText), certificateModel.texts());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("TRANSP"),
        "Transportstyrelsen",
        LOGICAL_ADDRESS
    );

    final var certificateModel = certificateModelFactoryTS8071.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeCertificateActionSpecifications() {
    final var certificateModel = certificateModelFactoryTS8071.create();

    assertAll(
        () -> assertNotNull(certificateModel.certificateActionSpecifications()),
        () -> assertFalse(certificateModel.certificateActionSpecifications().isEmpty())
    );
  }

  @Test
  void shallNotIncludeMessageActionSpecifications() {
    final var certificateModel = certificateModelFactoryTS8071.create();

    assertTrue(certificateModel.messageActionSpecifications().isEmpty());
  }

  @Nested
  class CertificateSpecifications {


    @ParameterizedTest
    @ValueSource(strings = {"3", "4"})
    void shallIncludeQuestions(String value) {
      final var certificateModel = certificateModelFactoryTS8071.create();

      final var id = new ElementId(value);
      assertTrue(certificateModel.elementSpecificationExists(id),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              id,
              certificateModel.elementSpecifications())
      );
    }

    @ParameterizedTest
    @ValueSource(strings = {"KAT_1.0", "KAT_1.1", "KAT_1.2", "KAT_2", "KAT_3", "KAT_4", "KAT_5"})
    void shallIncludeCategories(String id) {
      final var elementId = new ElementId(id);
      CertificateModel certificateModel = certificateModelFactoryTS8071.create();

      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId,
              certificateModel.elementSpecifications()));
    }

  }

  @Test
  void shallIncludeCertificateActionFactory() {
    final var certificateModel = certificateModelFactoryTS8071.create();

    assertEquals(certificateActionFactory, certificateModel.certificateActionFactory());
  }
}