package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.FK_NAME;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.LINK_FK_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.PDF_FK_7210_PDF;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.PDF_NO_ADDRESS_FK_7210_PDF;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.PREAMBLE_TEXT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.SCHEMATRON_PATH;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.URL_FK;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;
import se.inera.intyg.certificateservice.domain.common.model.CertificateLink;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.common.model.Role;

class CertificateModelFactoryFK7210Test {

  private static final String FK_7210 = "fk7210";
  private static final String VERSION = "1.0";
  private CertificateModelFactoryFK7210 certificateModelFactoryFK7210;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7210 = new CertificateModelFactoryFK7210();
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
        "Försäkringskassan"
    );

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeCertificateActionCreate() {
    final var expectedType = CertificateActionType.CREATE;

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRead() {
    final var expectedType = CertificateActionType.READ;

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionUpdate() {
    final var expectedType = CertificateActionType.UPDATE;

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDelete() {
    final var expectedType = CertificateActionType.DELETE;

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionSign() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SIGN)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
        .build();

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionSend() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
        .build();

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionPrint() {
    final var expectedType = CertificateActionType.PRINT;

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRevoke() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.REVOKE)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
        .build();

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionReplace() {
    final var expectedType = CertificateActionType.REPLACE;

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReplaceContinue() {
    final var expectedType = CertificateActionType.REPLACE_CONTINUE;

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionForwardCertificate() {
    final var expectedType = CertificateActionType.FORWARD_CERTIFICATE;

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionResponsibleIssuer() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.RESPONSIBLE_ISSUER)
        .allowedRoles(List.of(Role.CARE_ADMIN))
        .build();

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
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
  void shallIncludeActiveForRoles() {
    final var expected = List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
        Role.CARE_ADMIN);

    final var certificateModel = certificateModelFactoryFK7210.create();

    assertEquals(expected, certificateModel.rolesWithAccess());
  }

  @Nested
  class PdfSpecificationTest {

    @Test
    void shallIncludeCertificateType() {
      final var expected = new CertificateType("fk7210");

      final var certificateModel = certificateModelFactoryFK7210.create();

      assertEquals(expected, certificateModel.pdfSpecification().certificateType());
    }

    @Test
    void shallIncludePdfTemplatePathWithAddress() {
      final var certificateModel = certificateModelFactoryFK7210.create();

      assertEquals(PDF_FK_7210_PDF, certificateModel.pdfSpecification().pdfTemplatePath());
    }

    @Test
    void shallIncludePdfTemplatePathNoAddress() {
      final var certificateModel = certificateModelFactoryFK7210.create();

      assertEquals(PDF_NO_ADDRESS_FK_7210_PDF,
          certificateModel.pdfSpecification().pdfNoAddressTemplatePath());
    }

    @Test
    void shallIncludePatientFieldId() {
      final var expected = new PdfFieldId("form1[0].#subform[0].flt_txtPersonNr[0]");

      final var certificateModel = certificateModelFactoryFK7210.create();

      assertEquals(expected, certificateModel.pdfSpecification().patientIdFieldId());
    }

    @Test
    void shallIncludeSignatureFields() {
      final var expected = PdfSignature.builder()
          .signaturePageIndex(0)
          .signatureWithAddressTagIndex(new PdfTagIndex(15))
          .signatureWithoutAddressTagIndex(new PdfTagIndex(7))
          .signedDateFieldId(new PdfFieldId("form1[0].#subform[0].flt_datUnderskrift[0]"))
          .signedByNameFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtNamnfortydligande[0]"))
          .paTitleFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtBefattning[0]"))
          .specialtyFieldId(
              new PdfFieldId("form1[0].#subform[0].flt_txtEventuellSpecialistkompetens[0]"))
          .hsaIdFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtLakarensHSA-ID[0]"))
          .workplaceCodeFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtArbetsplatskod[0]"))
          .contactInformation(
              new PdfFieldId("form1[0].#subform[0].flt_txtVardgivarensNamnAdressTelefon[0]"))
          .build();

      final var certificateModel = certificateModelFactoryFK7210.create();

      assertEquals(expected, certificateModel.pdfSpecification().signature());
    }

    @Test
    void shallIncludeMcid() {
      final var expected = 100;
      final var certificateModel = certificateModelFactoryFK7210.create();

      assertEquals(expected, certificateModel.pdfSpecification().pdfMcid().value());
    }
  }
}
