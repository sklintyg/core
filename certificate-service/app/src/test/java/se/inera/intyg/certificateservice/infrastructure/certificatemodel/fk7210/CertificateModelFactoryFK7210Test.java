package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.FK_NAME;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.LINK_FK_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.PREAMBLE_TEXT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.URL_FK;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
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
import se.inera.intyg.certificateservice.domain.common.model.CertificateLink;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;

@ExtendWith(MockitoExtension.class)
class CertificateModelFactoryFK7210Test {

  @Mock
  private CertificateActionFactory certificateActionFactory;
  private static final String FK_7210 = "fk7210";
  private static final String VERSION = "1.0";
  private CertificateModelFactoryFK7210 certificateModelFactoryFK7210;

  private static final String LOGICAL_ADDRESS = "L-A";

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7210 = new CertificateModelFactoryFK7210(certificateActionFactory);

    ReflectionTestUtils.setField(certificateModelFactoryFK7210, "fkLogicalAddress",
        LOGICAL_ADDRESS);
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(FK_7210))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shouldIncludeTypeName() {
    final var expected = new CertificateTypeName("FK7210");

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertEquals(expected, certificateModel.typeName());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Intyg om graviditet";

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK7210.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryFK7210.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(certificateModelFactoryFK7210, "activeFrom",
        expectedActiveFrom);

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryFK7210.create();

    assertTrue(certificateModel.availableForCitizen());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan",
        LOGICAL_ADDRESS
    );

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeSchematronPath() {
    final var certificateModel = certificateModelFactoryFK7210.create();

    assertEquals(SCHEMATRON_PATH, certificateModel.schematronPath());
  }

  @Test
  void shallIncludeCertificateSummaryProvider() {
    final var certificateModel = certificateModelFactoryFK7210.create();

    assertEquals(FK7210CertificateSummaryProvider.class,
        certificateModel.summaryProvider().getClass());
  }

  @Test
  void shallIncludeCertificateText() {
    final var expectedText = CertificateText.builder()
        .text(PREAMBLE_TEXT)
        .type(CertificateTextType.PREAMBLE_TEXT)
        .links(List.of(CertificateLink.builder()
            .url(URL_FK)
            .id(LINK_FK_ID)
            .name(FK_NAME)
            .build()
        ))
        .build();

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertEquals(List.of(expectedText), certificateModel.texts());
  }

  @Test
  void shallIncludeActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK7210.create();

    assertAll(
        () -> assertNotNull(certificateModel.certificateActionSpecifications()),
        () -> assertFalse(certificateModel.certificateActionSpecifications().isEmpty())
    );
  }

  @Test
  void shallIncludePdfSpecifications() {
    final var certificateModel = certificateModelFactoryFK7210.create();

    assertNotNull(certificateModel.pdfSpecification());
  }

  @Nested
  class CertificateSpecifications {

    @Test
    void shallIncludeCategoryBeraknatFodelsedatum() {
      final var certificateModel = certificateModelFactoryFK7210.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_1")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'"
              .formatted(new ElementId("KAT_1"), certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionBeraknatFodelsedatum() {
      final var certificateModel = certificateModelFactoryFK7210.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("54")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'"
              .formatted(new ElementId("54"), certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeIssuingUnitContactInfo() {
      final var certificateModel = certificateModelFactoryFK7210.create();

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
    final var certificateModel = certificateModelFactoryFK7210.create();

    assertEquals(certificateActionFactory, certificateModel.certificateActionFactory());
  }
}
