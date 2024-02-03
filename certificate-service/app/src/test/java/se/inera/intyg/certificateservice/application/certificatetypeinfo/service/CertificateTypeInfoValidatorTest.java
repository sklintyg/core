package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.athenaReactAnderssonDtoBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.service.CreateCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.RoleTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

class CertificateTypeInfoValidatorTest {

  private static final String ID = "id";
  private CreateCertificateRequestValidator certificateTypeInfoValidator;
  private CreateCertificateRequest.CreateCertificateRequestBuilder requestBuilder;

  @BeforeEach
  void setUp() {
    certificateTypeInfoValidator = new CreateCertificateRequestValidator();
    requestBuilder = CreateCertificateRequest.builder()
        .user(
            UserDTO.builder()
                .id(ID)
                .role(RoleTypeDTO.DOCTOR)
                .blocked(false)
                .build()
        )
        .unit(
            UnitDTO.builder()
                .id(ID)
                .inactive(false)
                .build()
        )
        .careUnit(
            UnitDTO.builder()
                .id(ID)
                .build()
        )
        .careProvider(
            UnitDTO.builder()
                .id(ID)
                .build()
        )
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
          () -> certificateTypeInfoValidator.validate(request));

      assertEquals("Required parameter missing: User", illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsNull() {
      final var request = requestBuilder
          .user(
              UserDTO.builder()
                  .build())
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificateTypeInfoValidator.validate(request));

      assertEquals("Required parameter missing: User.id", illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsEmpty() {
      final var request = requestBuilder
          .user(
              UserDTO.builder()
                  .id("")
                  .build())
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificateTypeInfoValidator.validate(request));

      assertEquals("Required parameter missing: User.id", illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfRoleIsNull() {
      final var request = requestBuilder
          .user(
              UserDTO.builder()
                  .id(ID)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificateTypeInfoValidator.validate(request));

      assertEquals("Required parameter missing: User.role", illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfBlockedIsNull() {
      final var request = requestBuilder
          .user(
              UserDTO.builder()
                  .id(ID)
                  .role(RoleTypeDTO.DOCTOR)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificateTypeInfoValidator.validate(request));

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
          () -> certificateTypeInfoValidator.validate(request));

      assertEquals("Required parameter missing: Unit",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsNull() {
      final var request = requestBuilder
          .unit(
              UnitDTO.builder().build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificateTypeInfoValidator.validate(request));

      assertEquals("Required parameter missing: Unit.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsEmpty() {
      final var request = requestBuilder
          .unit(
              UnitDTO.builder()
                  .id("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificateTypeInfoValidator.validate(request));

      assertEquals("Required parameter missing: Unit.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIsInactiveIsNull() {
      final var request = requestBuilder
          .unit(
              UnitDTO.builder()
                  .id(ID)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificateTypeInfoValidator.validate(request));

      assertEquals("Required parameter missing: Unit.isInactive",
          illegalArgumentException.getMessage());
    }

    @Nested
    class CareUnitValidation {

      @Test
      void shallThrowIfCareUnitIsNull() {
        final var request = requestBuilder
            .careUnit(null)
            .build();

        final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
            () -> certificateTypeInfoValidator.validate(request));

        assertEquals("Required parameter missing: CareUnit",
            illegalArgumentException.getMessage());
      }

      @Test
      void shallThrowIfIdIsNull() {
        final var request = requestBuilder
            .careUnit(
                UnitDTO.builder().build()
            )
            .build();

        final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
            () -> certificateTypeInfoValidator.validate(request));

        assertEquals("Required parameter missing: CareUnit.id",
            illegalArgumentException.getMessage());
      }

      @Test
      void shallThrowIfIdIsEmpty() {
        final var request = requestBuilder
            .careUnit(
                UnitDTO.builder()
                    .id("")
                    .build()
            )
            .build();

        final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
            () -> certificateTypeInfoValidator.validate(request));

        assertEquals("Required parameter missing: CareUnit.id",
            illegalArgumentException.getMessage());
      }
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
          () -> certificateTypeInfoValidator.validate(request));

      assertEquals("Required parameter missing: CareProvider",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsNull() {
      final var request = requestBuilder
          .careProvider(
              UnitDTO.builder().build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificateTypeInfoValidator.validate(request));

      assertEquals("Required parameter missing: CareProvider.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfIdIsEmpty() {
      final var request = requestBuilder
          .careProvider(
              UnitDTO.builder()
                  .id("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> certificateTypeInfoValidator.validate(request));

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
          () -> certificateTypeInfoValidator.validate(request));

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
          () -> certificateTypeInfoValidator.validate(request));

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
          () -> certificateTypeInfoValidator.validate(request));

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
          () -> certificateTypeInfoValidator.validate(request));

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
          () -> certificateTypeInfoValidator.validate(request));

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
        () -> certificateTypeInfoValidator.validate(request));

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
        () -> certificateTypeInfoValidator.validate(request));

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
        () -> certificateTypeInfoValidator.validate(request));

    assertEquals("Required parameter missing: Patient.protectedPerson",
        illegalArgumentException.getMessage());
  }
}
