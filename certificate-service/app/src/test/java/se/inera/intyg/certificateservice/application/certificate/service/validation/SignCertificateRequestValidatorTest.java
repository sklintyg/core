package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.alfaAllergimottagningenDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.alfaMedicincentrumDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.alfaRegionenDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateRequest;

class SignCertificateRequestValidatorTest {

  private static final Long VERSION = 0L;
  private static final String CERTIFICATE_ID = "certificateId";
  private static final String SIGNATURE_XML = Base64.getEncoder().encodeToString(
      "SIGNATURE_XML".getBytes(StandardCharsets.UTF_8)
  );
  private SignCertificateRequestValidator signCertificateRequestValidator;
  private SignCertificateRequest.SignCertificateRequestBuilder requestBuilder;

  @BeforeEach
  void setUp() {
    signCertificateRequestValidator = new SignCertificateRequestValidator();
    requestBuilder = SignCertificateRequest.builder()
        .user(AJLA_DOCTOR_DTO)
        .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .signatureXml(SIGNATURE_XML);
  }

  @Test
  void validRequest() {
    signCertificateRequestValidator.validate(requestBuilder.build(), CERTIFICATE_ID, VERSION);
  }

  @Nested
  class UserValidation {

    @Test
    void shallThrowIfUserIsNull() {
      final var request = requestBuilder
          .user(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: User", illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .id(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: User.id", illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsEmpty() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .id("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: User.id", illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfFirstNameIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .firstName(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: User.firstName",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfFirstNameIsEmpty() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .firstName("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: User.firstName",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfLastNameIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .lastName(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: User.lastName",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfLastNameIsEmpty() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .lastName("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: User.lastName",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfRoleIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .role(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: User.role", illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfBlockedIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .blocked(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: User.blocked",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfAgreementIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .agreement(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: User.agreement",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfAllowCopyIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .allowCopy(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: User.allowCopy",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfHealthCareProfessionalLicenceIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .healthCareProfessionalLicence(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: User.healthCareProfessionalLicence",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class UnitValidation {

    @Test
    void shallThrowIfUnitIsNull() {
      final var request = requestBuilder
          .unit(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: Unit",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsNull() {
      final var request = requestBuilder
          .unit(
              alfaAllergimottagningenDtoBuilder()
                  .id(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: Unit.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsEmpty() {
      final var request = requestBuilder
          .unit(
              alfaAllergimottagningenDtoBuilder()
                  .id("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: Unit.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIsInactiveIsNull() {
      final var request = requestBuilder
          .unit(
              alfaAllergimottagningenDtoBuilder()
                  .inactive(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: Unit.isInactive",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class CareUnitValidation {

    @Test
    void shallThrowIfCareUnitIsNull() {
      final var request = requestBuilder
          .careUnit(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: CareUnit",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsNull() {
      final var request = requestBuilder
          .careUnit(
              alfaMedicincentrumDtoBuilder()
                  .id(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: CareUnit.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsEmpty() {
      final var request = requestBuilder
          .careUnit(
              alfaMedicincentrumDtoBuilder()
                  .id("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: CareUnit.id",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class CareProviderValidation {

    @Test
    void shallThrowIfCareProviderIsNull() {
      final var request = requestBuilder
          .careProvider(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: CareProvider",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsNull() {
      final var request = requestBuilder
          .careProvider(
              alfaRegionenDtoBuilder()
                  .id(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: CareProvider.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsEmpty() {
      final var request = requestBuilder
          .careProvider(
              alfaRegionenDtoBuilder()
                  .id("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: CareProvider.id",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class CertificateIdValidation {

    @Test
    void shallThrowIfCertificateIdIsNull() {
      final var request = requestBuilder.build();
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, null, VERSION));

      assertEquals("Required parameter missing: certificateId",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfCertificateIdIsEmpty() {
      final var request = requestBuilder.build();
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, "", VERSION));

      assertEquals("Required parameter missing: certificateId",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class VersionValidation {

    @Test
    void shallThrowIfVersionIsNull() {
      final var request = requestBuilder.build();
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
          signCertificateRequestValidator.validate(request, CERTIFICATE_ID, null));

      assertEquals("Required parameter missing: version",
          illegalArgumentException.getMessage()
      );
    }
  }

  @Nested
  class SignatureXmlValidation {

    @Test
    void shallThrowIfNull() {
      final var request = requestBuilder
          .signatureXml(null)
          .build();
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: signatureXml",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfEmpty() {
      final var request = requestBuilder
          .signatureXml("")
          .build();
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> signCertificateRequestValidator.validate(request, CERTIFICATE_ID, VERSION));

      assertEquals("Required parameter missing: signatureXml",
          illegalArgumentException.getMessage());
    }
  }
}