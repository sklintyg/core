package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114;

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
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;

class CertificateModelFactoryAG114Test {

  private static final String TYPE = "ag114";
  private static final String VERSION = "2.0";

  @Mock
  private CertificateActionFactory certificateActionFactory;

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  private CertificateModelFactoryAG114 certificateModelFactoryAG114;

  @BeforeEach
  void setUp() {
    certificateModelFactoryAG114 = new CertificateModelFactoryAG114(
        certificateActionFactory,
        diagnosisCodeRepository
    );
    ReflectionTestUtils.setField(certificateModelFactoryAG114, "activeFrom",
        LocalDateTime.now(ZoneId.systemDefault()));
  }

  @Test
  void shouldIncludeId() {
    final var expectedId = CertificateModelId.builder()
        .type(new CertificateType(TYPE))
        .version(new CertificateVersion(VERSION))
        .build();

    final var certificateModel = certificateModelFactoryAG114.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shouldIncludeTypeName() {
    final var expected = new CertificateTypeName("AG1-14");

    final var certificateModel = certificateModelFactoryAG114.create();

    assertEquals(expected, certificateModel.typeName());
  }

  @Test
  void shouldIncludeName() {
    final var expectedName = "Läkarintyg om arbetsförmåga – sjuklöneperioden";

    final var certificateModel = certificateModelFactoryAG114.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shouldIncludeDescription() {
    final var certificateModel = certificateModelFactoryAG114.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shouldIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryAG114.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shouldIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryAG114,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryAG114.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shouldIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryAG114.create();

    assertTrue(certificateModel.availableForCitizen());
  }

  @Test
  void shouldIncludeSummaryProvider() {
    final var certificateModel = certificateModelFactoryAG114.create();

    assertEquals(AG114CertificateSummaryProvider.class,
        certificateModel.summaryProvider().getClass());
  }

  @Test
  void shouldNotIncludeMessageTypes() {
    final var certificateModel = certificateModelFactoryAG114.create();

    assertNull(certificateModel.messageTypes());
  }

  @Test
  void shouldIncludeCertificateActionSpecifications() {
    final var certificateModel = certificateModelFactoryAG114.create();

    assertAll(
        () -> assertNotNull(certificateModel.certificateActionSpecifications()),
        () -> assertFalse(certificateModel.certificateActionSpecifications().isEmpty())
    );
  }

  @Test
  void shouldIncludeCertificateActionFactory() {
    final var certificateModel = certificateModelFactoryAG114.create();

    assertEquals(certificateActionFactory, certificateModel.certificateActionFactory());
  }

  @Test
  void shouldIncludeCorrectType() {
    final var expected = new Code(
        "AG1-14",
        "b64ea353-e8f6-4832-b563-fc7d46f29548",
        "Läkarintyg om arbetsförmåga – sjuklöneperiod"
    );

    final var actual = certificateModelFactoryAG114.create().type();

    assertEquals(expected, actual);
  }

  @Nested
  class CertificateSpecifications {

    @ParameterizedTest
    @ValueSource(strings = {
        "KAT_1", "KAT_2", "KAT_3", "KAT_4", "KAT_5", "KAT_6", "KAT_7",
        "UNIT_CONTACT_INFORMATION", "10", "10.3", "1", "3", "4", "5", "6", "6.2", "7", "7.2", "8",
        "9", "9.2", "messageArbetsformaga"
    })
    void shouldIncludeElementSpecifications(String id) {
      final var elementId = new ElementId(id);
      final var certificateModel = certificateModelFactoryAG114.create();

      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exist in elementSpecifications".formatted(elementId));
    }
  }

  @Test
  void shouldHaveAvailableFunctionsProvider() {
    final var certificateModel = certificateModelFactoryAG114.create();

    assertNotNull(certificateModel.citizenAvailableFunctionsProvider());
  }

  @Test
  void shouldIncludeGeneralPrintProvider() {
    final var certificateModel = certificateModelFactoryAG114.create();

    assertNotNull(certificateModel.generalPrintProvider());
  }
}
