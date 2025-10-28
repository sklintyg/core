package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateTypeName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;

class CertificateModelFactoryAG7804Test {

  private static final String TYPE = "ag7804";
  private static final String VERSION = "2.0";

  @Mock
  private CertificateActionFactory certificateActionFactory;

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  private CertificateModelFactoryAG7804 certificateModelFactoryAG7804;

  @BeforeEach
  void setUp() {
    certificateModelFactoryAG7804 = new CertificateModelFactoryAG7804(
        certificateActionFactory,
        diagnosisCodeRepository
    );
    ReflectionTestUtils.setField(certificateModelFactoryAG7804, "activeFrom",
        java.time.LocalDateTime.now(java.time.ZoneId.systemDefault()));
  }

  @Test
  void shouldIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryAG7804.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shouldIncludeTypeName() {
    final var expected = new CertificateTypeName("AG7804");

    final var certificateModel = certificateModelFactoryAG7804.create();

    assertEquals(expected, certificateModel.typeName());
  }

  @Test
  void shouldIncludeName() {
    final var expectedName = "Läkarintyg om arbetsförmåga – arbetsgivaren";

    final var certificateModel = certificateModelFactoryAG7804.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shouldIncludeDescription() {
    final var certificateModel = certificateModelFactoryAG7804.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shouldIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryAG7804.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shouldIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryAG7804,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryAG7804.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shouldIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryAG7804.create();

    assertTrue(certificateModel.availableForCitizen());
  }

  @Test
  void shouldIncludeSummaryProvider() {
    final var certificateModel = certificateModelFactoryAG7804.create();

    assertEquals(AG7804CertificateSummaryProvider.class,
        certificateModel.summaryProvider().getClass());
  }

  @Test
  void shouldNotIncludeMessageTypes() {
    final var certificateModel = certificateModelFactoryAG7804.create();

    assertNull(certificateModel.messageTypes());
  }

  @Test
  void shouldIncludeCertificateActionSpecifications() {
    final var certificateModel = certificateModelFactoryAG7804.create();

    assertAll(
        () -> assertNotNull(certificateModel.certificateActionSpecifications()),
        () -> assertFalse(certificateModel.certificateActionSpecifications().isEmpty())
    );
  }

  @Test
  void shouldIncludeCertificateActionFactory() {
    final var certificateModel = certificateModelFactoryAG7804.create();

    assertEquals(certificateActionFactory, certificateModel.certificateActionFactory());
  }

  @Nested
  class CertificateSpecifications {

    @ParameterizedTest
    @ValueSource(strings = {
        "KAT_1", "KAT_2", "KAT_3", "KAT_4", "KAT_5", "KAT_6", "KAT_7", "KAT_8", "KAT_9", "KAT_10",
        "KAT_11", "KAT_12", "UNIT_CONTACT_INFORMATION", "27", "1", "1.3", "28", "29", "19", "32",
        "37", "34", "33", "33.2", "6", "44", "25", "39", "39.2", "39.4", "103", "103.2", "100"
    })
    void shouldIncludeCategories(String id) {
      final var elementId = new ElementId(id);
      final var certificateModel = certificateModelFactoryAG7804.create();

      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exist in elementSpecifications".formatted(elementId));
    }
  }

  @Test
  void shouldHaveAvailableFunctionsProvider() {
    final var certificateModel = certificateModelFactoryAG7804.create();

    assertNotNull(certificateModel.citizenAvailableFunctionsProvider());
  }

  @Test
  void shouldIncludeGeneralPrintProvider() {
    final var certificateModel = certificateModelFactoryAG7804.create();

    assertNotNull(certificateModel.generalPrintProvider());
  }
}