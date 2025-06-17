package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;

class CertificateModelFactoryFK3221Test {


  private static final String TYPE = "fk3221";
  private static final String VERSION = "1.0";
  private static final String LOGICAL_ADDRESS = "L-A";

  @Mock
  private CertificateActionFactory certificateActionFactory;
  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  private CertificateModelFactoryFK3221 certificateModelFactoryFK3221;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK3221 = new CertificateModelFactoryFK3221(certificateActionFactory,
        diagnosisCodeRepository);

    ReflectionTestUtils.setField(certificateModelFactoryFK3221, "fkLogicalAddress",
        LOGICAL_ADDRESS);
  }

  @Test
  void shallIncludeId() {
    final var expectedId =
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build();

    final var certificateModel = certificateModelFactoryFK3221.create();

    assertEquals(expectedId, certificateModel.id());
  }

  @Test
  @Disabled
  void shallIncludePdfSpecifications() {
    final var certificateModel = certificateModelFactoryFK3221.create();

    assertNotNull(certificateModel.pdfSpecification());
  }

  @Test
  void shallIncludeName() {
    final var expectedName = "Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning";

    final var certificateModel = certificateModelFactoryFK3221.create();

    assertEquals(expectedName, certificateModel.name());
  }

  @Test
  void shallIncludeDescription() {
    final var certificateModel = certificateModelFactoryFK3221.create();

    assertFalse(certificateModel.description().isBlank());
  }

  @Test
  void shallIncludeDetailedDescription() {
    final var certificateModel = certificateModelFactoryFK3221.create();

    assertFalse(certificateModel.detailedDescription().isBlank());
  }

  @Test
  void shallIncludeActiveFrom() {
    final var expectedActiveFrom = LocalDateTime.now(ZoneId.systemDefault());
    ReflectionTestUtils.setField(
        certificateModelFactoryFK3221,
        "activeFrom",
        expectedActiveFrom
    );

    final var certificateModel = certificateModelFactoryFK3221.create();

    assertEquals(expectedActiveFrom, certificateModel.activeFrom());
  }

  @Test
  void shallIncludeAvailableForCitizen() {
    final var certificateModel = certificateModelFactoryFK3221.create();

    assertFalse(certificateModel.availableForCitizen());
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

    final var certificateModel = certificateModelFactoryFK3221.create();

    assertEquals(expectedMessageTypes, certificateModel.messageTypes());
  }

  @Test
  void shallIncludeRecipient() {
    final var expectedRecipient = new Recipient(
        new RecipientId("FKASSA"),
        "Försäkringskassan",
        LOGICAL_ADDRESS
    );

    final var certificateModel = certificateModelFactoryFK3221.create();

    assertEquals(expectedRecipient, certificateModel.recipient());
  }

  @Test
  void shallIncludeSchematronPath() {
    final var expectedSchematronPath = "fk3221/schematron/lu_omv_mek.v1.sch";
    final var certificateModel = certificateModelFactoryFK3221.create();

    assertAll(
        () -> assertNotNull(certificateModel.schematronPath()),
        () -> assertEquals(expectedSchematronPath, certificateModel.schematronPath().value())
    );
  }

  @Test
  void shallIncludeCertificateActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK3221.create();

    assertAll(
        () -> assertNotNull(certificateModel.certificateActionSpecifications()),
        () -> assertFalse(certificateModel.certificateActionSpecifications().isEmpty())
    );
  }

  @Test
  void shallIncludeMessageActionSpecifications() {
    final var certificateModel = certificateModelFactoryFK3221.create();

    assertAll(
        () -> assertNotNull(certificateModel.messageActionSpecifications()),
        () -> assertFalse(certificateModel.messageActionSpecifications().isEmpty())
    );
  }
}