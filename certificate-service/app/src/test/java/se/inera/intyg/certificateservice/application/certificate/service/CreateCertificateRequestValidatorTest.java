package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;

class CreateCertificateRequestValidatorTest {

  private static final String TYPE = "type";
  private static final String VERSION = "version";
  private CreateCertificateRequestValidator createCertificateRequestValidator;
  private CreateCertificateRequest.CreateCertificateRequestBuilder requestBuilder;

  @BeforeEach
  void setUp() {
    createCertificateRequestValidator = new CreateCertificateRequestValidator();
    requestBuilder = CreateCertificateRequest.builder()
        .user(AJLA_DOCTOR_DTO)
        .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .patient(ATHENA_REACT_ANDERSSON_DTO)
        .certificateModelId(
            CertificateModelIdDTO.builder()
                .type("type")
                .version("version")
                .build()
        );
  }

  @Nested
  class UserValidation {

    @Test
    void shallThrowIfUserIsNull() {
      final var request = requestBuilder
          .user(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

      assertEquals("Required parameter missing: User.id", illegalArgumentException.getMessage());
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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

      assertEquals("Required parameter missing: User.blocked",
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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

      assertEquals("Required parameter missing: CareProvider.id",
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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

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
          () -> createCertificateRequestValidator.validate(request));

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
        () -> createCertificateRequestValidator.validate(request));

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
        () -> createCertificateRequestValidator.validate(request));

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
        () -> createCertificateRequestValidator.validate(request));

    assertEquals("Required parameter missing: Patient.protectedPerson",
        illegalArgumentException.getMessage());
  }

  @Nested
  class CertificateModelIdValidation {

    @Test
    void shallThrowIfCertificateModelIdIsNull() {
      final var request = requestBuilder
          .certificateModelId(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> createCertificateRequestValidator.validate(request));

      assertEquals("Required parameter missing: CertificateModelId",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfCertificateModelIdVersionIsNull() {
      final var request = requestBuilder
          .certificateModelId(
              CertificateModelIdDTO.builder()
                  .type(TYPE)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> createCertificateRequestValidator.validate(request));

      assertEquals("Required parameter missing: CertificateModelId.version",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfCertificateModelIdVersionIsEmpty() {
      final var request = requestBuilder
          .certificateModelId(
              CertificateModelIdDTO.builder()
                  .type(TYPE)
                  .version("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> createCertificateRequestValidator.validate(request));

      assertEquals("Required parameter missing: CertificateModelId.version",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfCertificateModelIdTypeIsNull() {
      final var request = requestBuilder
          .certificateModelId(
              CertificateModelIdDTO.builder()
                  .version(VERSION)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> createCertificateRequestValidator.validate(request));

      assertEquals("Required parameter missing: CertificateModelId.type",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfCertificateModelIdTypeIsEmpty() {
      final var request = requestBuilder
          .certificateModelId(
              CertificateModelIdDTO.builder()
                  .version(VERSION)
                  .type("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> createCertificateRequestValidator.validate(request));

      assertEquals("Required parameter missing: CertificateModelId.type",
          illegalArgumentException.getMessage());
    }
  }
}
