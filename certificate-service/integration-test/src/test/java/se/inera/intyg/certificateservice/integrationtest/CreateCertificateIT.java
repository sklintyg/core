package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATLAS_REACT_ABRAHAMSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customValidateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.validationErrors;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import se.inera.intyg.certificateservice.application.certificate.dto.PrefillXmlDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;

public abstract class CreateCertificateIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();

  protected abstract String wrongVersion();


  private String loadResourceAsString() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader()
        .getResourceAsStream("prefill/%s.xml".formatted(type().toUpperCase()))) {
      if (inputStream == null) {
        throw new IllegalArgumentException(
            "Resource not found: " + "prefill/%s.xml".formatted(type()));
      }
      return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }
  }

  @Test
  @DisplayName("Om utkastet framgångsrikt skapats skall utkastet returneras")
  void shallReturnCertificateWhenActive() {
    final var response = api.createCertificate(
        defaultCreateCertificateRequest(type(), typeVersion())
    );

    assertNotNull(
        certificate(response.getBody()),
        "Should return certificate as it is active!"
    );
  }

  @Test
  @DisplayName("Om ett utkast förifylls med komplett intygsinformation ska inga valideringsfel visas")
  void shallReturnCertificateWithPrefilledAnswers() throws IOException {
    final var today = LocalDate.now().toString(); // e.g., "2025-07-17"
    final var xmlString = loadResourceAsString().replace("TODAY", today);
    final var xml = new Xml(xmlString);
    final var createCertificateRequest =
        customCreateCertificateRequest(
            type(),
            typeVersion())
            .prefillXml(new PrefillXmlDTO(xml.base64()))
            .build();

    final var response = api.createCertificate(
        createCertificateRequest
    );

    final var validateCertificate = api.validateCertificate(
        customValidateCertificateRequest()
            .certificate(certificate(response.getBody()))
            .build(),
        certificateId(response.getBody())
    );

    assertEquals(0, validationErrors(validateCertificate).size(),
        () -> "Should not return validation errors, got '%s' errors".formatted(
            validationErrors(validateCertificate)));
  }

  @Test
  @DisplayName("Om patienten är avliden skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403PatientIsDeceased() {
    final var response = api.createCertificate(
        customCreateCertificateRequest(type(), typeVersion())
            .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om användaren är blockerad skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403UserIsBlocked() {
    final var response = api.createCertificate(
        customCreateCertificateRequest(type(), typeVersion())
            .user(
                ajlaDoktorDtoBuilder()
                    .blocked(Boolean.TRUE)
                    .build()
            )
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om användaren saknar avtal skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403UserMissingAgreement() {
    final var response = api.createCertificate(
        customCreateCertificateRequest(type(), typeVersion())
            .user(
                ajlaDoktorDtoBuilder()
                    .agreement(Boolean.FALSE)
                    .build()
            )
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om patient är avliden och användaren är blockerad skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403PatientIsDeceasedAndUserIsBlocked() {
    final var response = api.createCertificate(
        customCreateCertificateRequest(type(), typeVersion())
            .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
            .user(
                ajlaDoktorDtoBuilder()
                    .blocked(Boolean.TRUE)
                    .build()
            )
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @ParameterizedTest
  @DisplayName("Om utkastet är utfärdat på en patient som har skyddade personuppgifter skall det returneras")
  @MethodSource("rolesAccessToProtectedPerson")
  void shallReturnCertificateIfPatientIsProtectedPerson(UserDTO userDTO) {
    final var response = api.createCertificate(
        customCreateCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .user(userDTO)
            .build()
    );

    assertNotNull(
        certificate(response.getBody()),
        "Should return certificate because the user is a doctor!"
    );
  }

  @Test
  @DisplayName("Vårdadministratör - Om patienten har skyddade personuppgifter skall felkod 403 (FORBIDDEN) returneras")
  void shallReturn403PatientIsProtectedPersonAndUserDoctor() {
    final var response = api.createCertificate(
        customCreateCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build()
    );

    assertEquals(403, response.getStatusCode().value());
  }

  @Test
  @DisplayName("Om den efterfrågade versionen inte stöds skall felkod 400 (BAD_REQUEST) returneras")
  void shallReturn400IfVersionNotSupported() {
    final var response = api.createCertificate(
        defaultCreateCertificateRequest(type(), wrongVersion())
    );

    assertEquals(400, response.getStatusCode().value());
  }
}