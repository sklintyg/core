package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateTypeName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;

@ExtendWith(MockitoExtension.class)
class CertificateModelFactoryFK7809Test {

  private static final String TYPE = "fk7809";
  private static final String VERSION = "1.0";
  private static final String LOGICAL_ADDRESS = "L-A";

  @Mock
  private CertificateActionFactory certificateActionFactory;
  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  private CertificateModelFactoryFK7809 certificateModelFactoryFK7809;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7809 = new CertificateModelFactoryFK7809(certificateActionFactory,
        diagnosisCodeRepository);

    ReflectionTestUtils.setField(certificateModelFactoryFK7809, "fkLogicalAddress",
        LOGICAL_ADDRESS);
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  void shouldIncludeTypeName() {
    final var expected = new CertificateTypeName("FK7809");

    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(expected, certificateModel.typeName());
  }

  @Test
  void shallIncludePdfSpecifications() {
    final var certificateModel = certificateModelFactoryFK7809.create();

    assertNotNull(certificateModel.pdfSpecification());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Läkarutlåtande för merkostnadsersättning";

    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK7809.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryFK7809.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryFK7809,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryFK7809.create();

    assertTrue(certificateModel.availableForCitizen());
  }

  @Test
  void shallIncludeCertificateSummaryProvider() {
    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(FK7809CertificateSummaryProvider.class,
        certificateModel.summaryProvider().getClass());
  }

  @Test
  void shallIncludeCertificateConfirmationModalProvider() {
    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(FK7809CertificateConfirmationModalProvider.class,
        certificateModel.confirmationModalProvider().getClass());
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

    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(expectedMessageTypes, certificateModel.messageTypes());
  }

  @Test
  void shallIncludeCertificateText() {
    final var expectedText = CertificateText.builder()
        .text(
            "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. Har du frågor kontaktar du den som skrivit ditt intyg.")
        .type(CertificateTextType.PREAMBLE_TEXT)
        .links(Collections.emptyList())
        .build();

    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(List.of(expectedText), certificateModel.texts());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan",
        LOGICAL_ADDRESS
    );

    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeCertificateActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK7809.create();

    assertAll(
        () -> assertNotNull(certificateModel.certificateActionSpecifications()),
        () -> assertFalse(certificateModel.certificateActionSpecifications().isEmpty())
    );
  }

  @Test
  void shallIncludeMessageActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK7809.create();

    assertAll(
        () -> assertNotNull(certificateModel.messageActionSpecifications()),
        () -> assertFalse(certificateModel.messageActionSpecifications().isEmpty())
    );
  }

  @Nested
  class CertificateSpecifications {

    @Test
    void shallIncludeCategoryGrundForMedicinsktUnderlag() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_1")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_1"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionGrundForMedicinsktUnderlag() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("1")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("1"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionRelationTillPatienten() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("1.4")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("1.4"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionAnnanGrundForMedicinsktUnderlag() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("1.3")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("1.3"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionBaseratPaAnnatMedicinsktUnderlag() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("3")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("3"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionUtredningEllerUnderlag() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("4")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("4"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryAktivitetsBegransningar() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_6")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_6"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionAktivitetsbegransningar() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("17")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("17"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryMedicinskBehandling() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_7")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_7"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionPagaendeOchPlaneradeBehandlingar() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("50")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("50"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionVardenhetOchTidplan() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("50.2")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("50.2"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryPrognas() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_8")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_8"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionPrognos() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("51")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("51"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryOvrigt() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_9")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_9"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionOvrigt() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("25")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("25"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryDiagnos() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_4")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_4"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionDiagnos() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("58")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("58"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionDiagnosMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("5")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("5"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeCategoryFunktionsnedsattning() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("KAT_5")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("KAT_5"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionFunktionsnedsattning() {
      final var certificateModel = certificateModelFactoryFK7809.create();

      assertTrue(certificateModel.elementSpecificationExists(new ElementId("funktionsnedsattning")),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              new ElementId("funktionsnedsattning"),
              certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionIntellektuellFunktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("8");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionKommunikationSocialInteraktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("9");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionUppmarksamhetMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("10");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionPsykiskFunktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("11");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionHorselFunktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("48");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionSynFunktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("49");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionSinnesFunktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("12");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionKoordinationMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("13");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }

    @Test
    void shallIncludeQuestionAnnanKroppsligFunktionMotivering() {
      final var certificateModel = certificateModelFactoryFK7809.create();
      final var elementId = new ElementId("14");
      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exists in elementSpecifications '%s'".formatted(
              elementId, certificateModel.elementSpecifications())
      );
    }
  }

  @Test
  void shallIncludeCertificateActionFactory() {
    final var certificateModel = certificateModelFactoryFK7809.create();

    assertEquals(certificateActionFactory, certificateModel.certificateActionFactory());
  }
}
