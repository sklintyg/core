package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.CertificateModelFactoryFK7472.PDF_FK_7472_PDF;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.CertificateModelFactoryFK7472.PDF_NO_ADDRESS_FK_7472_PDF;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.CertificateModelFactoryFK7472.SCHEMATRON_PATH;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;

@ExtendWith(MockitoExtension.class)
class CertificateModelFactoryFK7472Test {

  private static final String TYPE = "fk7472";
  private static final String VERSION = "1.0";
  private CertificateModelFactoryFK7472 certificateModelFactoryFK7472;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7472 = new CertificateModelFactoryFK7472();
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Intyg om tillfällig föräldrapenning";

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryFK7472,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertFalse(certificateModel.availableForCitizen());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan"
    );

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeCertificateActionCreate() {
    final var expectedType = CertificateActionType.CREATE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType())
        ), "Expected type: %s".formatted(expectedType)
    );
  }

  @Test
  void shallIncludeCertificateActionRead() {
    final var expectedType = CertificateActionType.READ;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream()
        .anyMatch(actionSpecification ->
            expectedType.equals(actionSpecification.certificateActionType()
            )
        ), "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionUpdate() {
    final var expectedType = CertificateActionType.UPDATE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionDelete() {
    final var expectedType = CertificateActionType.DELETE;

    final var certificateModel = certificateModelFactoryFK7472.create();

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

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            expectedSpecification::equals),
        "Expected specification: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionSend() {
    final var expectedSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SEND)
        .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE))
        .build();

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionPrint() {
    final var expectedType = CertificateActionType.PRINT;

    final var certificateModel = certificateModelFactoryFK7472.create();

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

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeCertificateActionReplace() {
    final var expectedType = CertificateActionType.REPLACE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReplaceContinue() {
    final var expectedType = CertificateActionType.REPLACE_CONTINUE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRenew() {
    final var expectedType = CertificateActionType.RENEW;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionMessages() {
    final var expectedType = CertificateActionType.MESSAGES;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionMessagesAdministrative() {
    final var expectedType = CertificateActionType.MESSAGES_ADMINISTRATIVE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionReceiveComplement() {
    final var expectedType = CertificateActionType.RECEIVE_COMPLEMENT;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionRecieveReminder() {
    final var expectedType = CertificateActionType.RECEIVE_REMINDER;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionComplement() {
    final var expectedType = CertificateActionType.COMPLEMENT;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionCannotComplement() {
    final var expectedType = CertificateActionType.CANNOT_COMPLEMENT;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionForwardMessage() {
    final var expectedType = CertificateActionType.FORWARD_MESSAGE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionForwardCertificate() {
    final var expectedType = CertificateActionType.FORWARD_CERTIFICATE;

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            actionSpecification -> expectedType.equals(actionSpecification.certificateActionType())
        ),
        "Expected type: %s".formatted(expectedType));
  }

  @Test
  void shallIncludeCertificateActionHandleComplement() {
    final var expectedType = CertificateActionType.HANDLE_COMPLEMENT;

    final var certificateModel = certificateModelFactoryFK7472.create();

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

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertTrue(certificateModel.certificateActionSpecifications().stream().anyMatch(
            expectedSpecification::equals),
        "Expected type: %s".formatted(expectedSpecification));
  }

  @Test
  void shallIncludeSchematronPath() {
    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(SCHEMATRON_PATH, certificateModel.schematronPath());
  }

  @Nested
  class MessageActionSpecificationsTests {


    @Test
    void shallIncludeMessageActionHandleComplement() {
      final var expectedType = MessageActionType.HANDLE_COMPLEMENT;

      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeMessageActionCannotComplement() {
      final var expectedType = MessageActionType.CANNOT_COMPLEMENT;

      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeMessageActionComplement() {
      final var expectedType = MessageActionType.COMPLEMENT;

      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }

    @Test
    void shallIncludeMessageActionForward() {
      final var expectedType = MessageActionType.FORWARD;

      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(certificateModel.messageActionSpecifications().stream().anyMatch(
              actionSpecification -> expectedType.equals(actionSpecification.messageActionType())
          ),
          "Expected type: %s".formatted(expectedType));
    }
  }

  @Test
  void shallIncludeActiveForRoles() {
    final var expected = List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
        Role.CARE_ADMIN);

    final var certificateModel = certificateModelFactoryFK7472.create();

    assertEquals(expected, certificateModel.rolesWithAccess());
  }

  @Nested
  class CertificateSpecifications {

    @Test
    void shallIncludeCategorySymptom() {
      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_1")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_1"), certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionSymptom() {
      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("55")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("55"), certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryPrognos() {
      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_2")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_2"), certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionPrognos() {
      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("56")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("56"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeIssuingUnitContactInfo() {
      final var certificateModel = certificateModelFactoryFK7472.create();

      assertTrue(
          certificateModel.elementSpecificationExists(new ElementId("UNIT_CONTACT_INFORMATION")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'"
              .formatted(new ElementId("UNIT_CONTACT_INFORMATION"),
                  certificateModel.elementSpecifications())
      );
    }
  }

  @Nested
  class PdfSpecificationTest {

    @Test
    void shallIncludePdfTemplatePathWithAddress() {
      final var certificateModel = certificateModelFactoryFK7472.create();

      assertEquals(PDF_FK_7472_PDF, certificateModel.pdfSpecification().pdfTemplatePath());
    }

    @Test
    void shallIncludePdfTemplatePathNoAddress() {
      final var certificateModel = certificateModelFactoryFK7472.create();

      assertEquals(PDF_NO_ADDRESS_FK_7472_PDF,
          certificateModel.pdfSpecification().pdfNoAddressTemplatePath());
    }

    @Test
    void shallIncludePatientFieldId() {
      final var expected = new PdfFieldId("form1[0].#subform[0].flt_txtPersonNrBarn[0]");

      final var certificateModel = certificateModelFactoryFK7472.create();

      assertEquals(expected, certificateModel.pdfSpecification().patientIdFieldId());
    }

    @Test
    void shallIncludeSignatureFields() {
      final var expected = PdfSignature.builder()
          .signaturePageIndex(0)
          .signatureWithAddressTagIndex(new PdfTagIndex(50))
          .signatureWithoutAddressTagIndex(new PdfTagIndex(42))
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

      final var certificateModel = certificateModelFactoryFK7472.create();

      assertEquals(expected, certificateModel.pdfSpecification().signature());
    }

    @Test
    void shallIncludeMcid() {
      final var expected = 120;
      final var certificateModel = certificateModelFactoryFK7472.create();

      assertEquals(expected, certificateModel.pdfSpecification().pdfMcid().value());
    }
  }
}
