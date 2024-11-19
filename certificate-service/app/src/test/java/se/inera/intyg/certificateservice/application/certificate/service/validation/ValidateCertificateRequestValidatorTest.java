package se.inera.intyg.certificateservice.application.certificate.service.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonCertificateDTO.certificateMetadata;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.athenaReactAnderssonDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.alfaAllergimottagningenDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.alfaMedicincentrumDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.alfaRegionenDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.config.ValidateCertificateRequest;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;

class ValidateCertificateRequestValidatorTest {


  private static final String CERTIFICATE_ID = "certificateId";
  private ValidateCertificateRequestValidator validateCertificateRequestValidator;
  private ValidateCertificateRequest.ValidateCertificateRequestBuilder requestBuilder;

  @BeforeEach
  void setUp() {
    validateCertificateRequestValidator = new ValidateCertificateRequestValidator();
    requestBuilder = ValidateCertificateRequest.builder()
        .patient(ATHENA_REACT_ANDERSSON_DTO)
        .user(AJLA_DOCTOR_DTO)
        .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .certificate(
            CertificateDTO.builder()
                .metadata(certificateMetadata().build())
                .data(Collections.emptyMap())
                .build()
        );
  }

  @Test
  void validRequest() {
    validateCertificateRequestValidator.validate(requestBuilder.build(), CERTIFICATE_ID);
  }

  @Nested
  class UserValidation {

    @Test
    void shallThrowIfUserIsNull() {
      final var request = requestBuilder
          .user(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Unit.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfWorkplaceCodeIsNull() {
      final var request = requestBuilder
          .unit(
              alfaAllergimottagningenDtoBuilder()
                  .workplaceCode(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Unit.workplaceCode",
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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

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
          () -> validateCertificateRequestValidator.validate(request, null));

      assertEquals("Required parameter missing: certificateId",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfCertificateIdIsEmpty() {
      final var request = requestBuilder.build();
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validateCertificateRequestValidator.validate(request, ""));

      assertEquals("Required parameter missing: certificateId",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class CertificateValidation {

    @Test
    void shallThrowIfCertificateIsNull() {
      final var request = requestBuilder
          .certificate(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Certificate",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfCertificateMetadataIsNull() {
      final var request = requestBuilder
          .certificate(
              CertificateDTO.builder()
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Certificate.metadata",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfCertificateDataIsNull() {
      final var request = requestBuilder
          .certificate(
              CertificateDTO.builder()
                  .data(null)
                  .metadata(
                      CertificateMetadataDTO.builder().build()
                  )
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Certificate.data",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class PatientValidation {

    @Test
    void shallThrowIfPatientIsNull() {
      final var request = requestBuilder
          .patient(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Patient",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsNull() {
      final var request = requestBuilder
          .patient(
              athenaReactAnderssonDtoBuilder()
                  .id(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Patient.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfPatientIdIsNull() {
      final var request = requestBuilder
          .patient(
              athenaReactAnderssonDtoBuilder()
                  .id(
                      PersonIdDTO.builder()
                          .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                          .build()
                  )
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Patient.id.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfPatientIdIsEmpty() {
      final var request = requestBuilder
          .patient(
              athenaReactAnderssonDtoBuilder()
                  .id(
                      PersonIdDTO.builder()
                          .id("")
                          .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                          .build()
                  )
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Patient.id.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfPatientIdTypeIsNull() {
      final var request = requestBuilder
          .patient(
              athenaReactAnderssonDtoBuilder()
                  .id(
                      PersonIdDTO.builder()
                          .id(ATHENA_REACT_ANDERSSON_DTO.getId().getId())
                          .build()
                  )
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Patient.id.type",
          illegalArgumentException.getMessage());
    }
  }

  @Test
  void shallThrowIfTestIndicatedIsNull() {
    final var request = requestBuilder
        .patient(
            athenaReactAnderssonDtoBuilder()
                .testIndicated(null)
                .build()
        )
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

    assertEquals("Required parameter missing: Patient.testIndicated",
        illegalArgumentException.getMessage());
  }

  @Test
  void shallThrowIfDeceasedIsNull() {
    final var request = requestBuilder
        .patient(
            athenaReactAnderssonDtoBuilder()
                .deceased(null)
                .build()
        )
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

    assertEquals("Required parameter missing: Patient.deceased",
        illegalArgumentException.getMessage());
  }

  @Test
  void shallThrowIfProtectedPersonIsNull() {
    final var request = requestBuilder
        .patient(
            athenaReactAnderssonDtoBuilder()
                .protectedPerson(null)
                .build()
        )
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validateCertificateRequestValidator.validate(request, CERTIFICATE_ID));

    assertEquals("Required parameter missing: Patient.protectedPerson",
        illegalArgumentException.getMessage());
  }
}