package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;

@ExtendWith(MockitoExtension.class)
class XmlValidationServiceTest {

  private static final SchematronPath SCHEMATRON_PATH = new SchematronPath("schematronPath");
  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final Xml XML = new Xml("xml");
  @Mock
  private XmlSchematronValidator xmlSchematronValidator;
  @Mock
  private XmlSchemaValidator xmlSchemaValidator;
  @InjectMocks
  private XmlValidationService xmlValidationService;

  @Nested
  class ValidateParameters {

    @Test
    void shallThrowIfXmlIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> xmlValidationService.validate(
              null,
              SCHEMATRON_PATH,
              CERTIFICATE_ID)
      );
      assertTrue(illegalArgumentException.getMessage().contains("Missing required parameter xml"));
    }

    @Test
    void shallThrowIfXmlContentIsNull() {
      final var xml = new Xml(null);
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> xmlValidationService.validate(
              xml,
              SCHEMATRON_PATH,
              CERTIFICATE_ID)
      );
      assertTrue(illegalArgumentException.getMessage().contains("Missing required parameter xml"));
    }

    @Test
    void shallThrowIfXmlContentIsEmpty() {
      final var xml = new Xml("");
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> xmlValidationService.validate(
              xml,
              SCHEMATRON_PATH,
              CERTIFICATE_ID)
      );
      assertTrue(illegalArgumentException.getMessage().contains("Missing required parameter xml"));
    }

    @Test
    void shallNotThrowIfSchematronPathIsNull() {
      doReturn(true).when(xmlSchemaValidator).validate(CERTIFICATE_ID, XML);
      assertDoesNotThrow(() -> xmlValidationService.validate(
          XML,
          null,
          CERTIFICATE_ID)
      );
    }

    @Test
    void shallThrowIfCertificateIdIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> xmlValidationService.validate(
              XML,
              SCHEMATRON_PATH,
              null)
      );
      assertTrue(illegalArgumentException.getMessage()
          .contains("Missing required parameter certificateId")
      );
    }

    @Test
    void shallThrowIfCertificateIdIsEmpty() {
      final var certificateId = new CertificateId("");
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> xmlValidationService.validate(
              XML,
              SCHEMATRON_PATH,
              certificateId)
      );
      assertTrue(illegalArgumentException.getMessage()
          .contains("Missing required parameter certificateId")
      );
    }
  }

  @Test
  void shallThrowIfSchemaValidationIsFalse() {
    doReturn(false).when(xmlSchemaValidator).validate(CERTIFICATE_ID, XML);
    doReturn(true).when(xmlSchematronValidator).validate(CERTIFICATE_ID, XML, SCHEMATRON_PATH);
    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> xmlValidationService.validate(XML, SCHEMATRON_PATH, CERTIFICATE_ID)
    );

    assertEquals(
        getExpectedMessage(true, false),
        illegalStateException.getMessage()
    );
  }

  @Test
  void shallThrowIfSchematronValidationIsFalse() {
    doReturn(true).when(xmlSchemaValidator).validate(CERTIFICATE_ID, XML);
    doReturn(false).when(xmlSchematronValidator).validate(CERTIFICATE_ID, XML, SCHEMATRON_PATH);
    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> xmlValidationService.validate(XML, SCHEMATRON_PATH, CERTIFICATE_ID)
    );

    assertEquals(
        getExpectedMessage(false, true),
        illegalStateException.getMessage()
    );
  }

  @Test
  void shallNotThrowIfXmlIsValid() {
    doReturn(true).when(xmlSchemaValidator).validate(CERTIFICATE_ID, XML);
    doReturn(true).when(xmlSchematronValidator).validate(CERTIFICATE_ID, XML, SCHEMATRON_PATH);

    assertDoesNotThrow(() -> xmlValidationService.validate(XML, SCHEMATRON_PATH, CERTIFICATE_ID));
  }

  private static String getExpectedMessage(boolean expectedSchematronResult,
      boolean expectedSchemaResult) {
    return "Certificate did not pass schematron validation."
        + " Schematron validation result: '%s'. Schema validation result: '%s'".formatted(
        expectedSchematronResult,
        expectedSchemaResult
    );
  }
}
