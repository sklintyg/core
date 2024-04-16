package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlSchemaValidator;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlSchematronValidator;

@ExtendWith(MockitoExtension.class)
class XmlValidationServiceTest {

  private static final String SCHEMATRON_PATH = "schematronPath";
  private static final String CERTIFICATE_ID = "certificateId";
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
    void shallThrowIfSchematronPathIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> xmlValidationService.validate(
              XML,
              null,
              CERTIFICATE_ID)
      );
      assertTrue(illegalArgumentException.getMessage()
          .contains("Missing required parameter schematronPath")
      );
    }

    @Test
    void shallThrowIfSchematronPathIsEmpty() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> xmlValidationService.validate(
              XML,
              "",
              CERTIFICATE_ID)
      );
      assertTrue(illegalArgumentException.getMessage()
          .contains("Missing required parameter schematronPath")
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
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> xmlValidationService.validate(
              XML,
              SCHEMATRON_PATH,
              "")
      );
      assertTrue(illegalArgumentException.getMessage()
          .contains("Missing required parameter certificateId")
      );
    }
  }
}
