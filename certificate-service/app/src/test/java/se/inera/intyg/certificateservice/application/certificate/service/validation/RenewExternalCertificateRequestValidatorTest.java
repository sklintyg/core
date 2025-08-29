package se.inera.intyg.certificateservice.application.certificate.service.validation;

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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REF;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PrefillXmlDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewExternalCertificateRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;

class RenewExternalCertificateRequestValidatorTest {

  private static final String CERTIFICATE_ID = "certificateId";
  private static final String TYPE = "TYPE";
  private static final String VERSION = "VERSION";
  private RenewExternalCertificateRequestValidator validator;
  private RenewExternalCertificateRequest.RenewExternalCertificateRequestBuilder requestBuilder;

  @BeforeEach
  void setUp() {
    validator = new RenewExternalCertificateRequestValidator();
    requestBuilder = RenewExternalCertificateRequest.builder()
        .user(AJLA_DOCTOR_DTO)
        .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .patient(ATHENA_REACT_ANDERSSON_DTO)
        .externalReference(EXTERNAL_REF)
        .status(CertificateStatusTypeDTO.SIGNED)
        .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
        .prefillXml(new PrefillXmlDTO("XML"))
        .certificateModelId(
            CertificateModelIdDTO.builder()
                .type("type")
                .version("version")
                .build()
        );
  }

  @Test
  void validRequest() {
    validator.validate(requestBuilder.build(), CERTIFICATE_ID);
  }

  @Nested
  class UserValidation {

    @Test
    void shouldThrowIfUserIsNull() {
      final var request = requestBuilder
          .user(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: User", illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfIdIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .id(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: User.id", illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfIdIsEmpty() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .id("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: User.id", illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfFirstNameIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .firstName(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: User.firstName",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfFirstNameIsEmpty() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .firstName("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: User.firstName",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfLastNameIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .lastName(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: User.lastName",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfLastNameIsEmpty() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .lastName("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: User.lastName",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfRoleIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .role(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: User.role", illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfBlockedIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .blocked(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: User.blocked",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfAgreementIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .agreement(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: User.agreement",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfAllowCopyIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .allowCopy(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: User.allowCopy",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfHealthCareProfessionalLicenceIsNull() {
      final var request = requestBuilder
          .user(
              ajlaDoktorDtoBuilder()
                  .healthCareProfessionalLicence(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: User.healthCareProfessionalLicence",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class UnitValidation {

    @Test
    void shouldThrowIfUnitIsNull() {
      final var request = requestBuilder
          .unit(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Unit",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfIdIsNull() {
      final var request = requestBuilder
          .unit(
              alfaAllergimottagningenDtoBuilder()
                  .id(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Unit.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfIdIsEmpty() {
      final var request = requestBuilder
          .unit(
              alfaAllergimottagningenDtoBuilder()
                  .id("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Unit.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfWorkplaceCodeIsNull() {
      final var request = requestBuilder
          .unit(
              alfaAllergimottagningenDtoBuilder()
                  .workplaceCode(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Unit.workplaceCode",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfIsInactiveIsNull() {
      final var request = requestBuilder
          .unit(
              alfaAllergimottagningenDtoBuilder()
                  .inactive(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Unit.isInactive",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class CareUnitValidation {

    @Test
    void shouldThrowIfCareUnitIsNull() {
      final var request = requestBuilder
          .careUnit(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: CareUnit",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfIdIsNull() {
      final var request = requestBuilder
          .careUnit(
              alfaMedicincentrumDtoBuilder()
                  .id(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: CareUnit.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfIdIsEmpty() {
      final var request = requestBuilder
          .careUnit(
              alfaMedicincentrumDtoBuilder()
                  .id("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: CareUnit.id",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class CareProviderValidation {

    @Test
    void shouldThrowIfCareProviderIsNull() {
      final var request = requestBuilder
          .careProvider(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: CareProvider",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfIdIsNull() {
      final var request = requestBuilder
          .careProvider(
              alfaRegionenDtoBuilder()
                  .id(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: CareProvider.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfIdIsEmpty() {
      final var request = requestBuilder
          .careProvider(
              alfaRegionenDtoBuilder()
                  .id("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: CareProvider.id",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class PatientValidation {

    @Test
    void shouldThrowIfPatientIsNull() {
      final var request = requestBuilder
          .patient(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Patient",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfIdIsNull() {
      final var request = requestBuilder
          .patient(
              athenaReactAnderssonDtoBuilder()
                  .id(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Patient.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfPatientIdIsNull() {
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
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Patient.id.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfPatientIdIsEmpty() {
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
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Patient.id.id",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfPatientIdTypeIsNull() {
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
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Patient.id.type",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfTestIndicatedIsNull() {
      final var request = requestBuilder
          .patient(
              athenaReactAnderssonDtoBuilder()
                  .testIndicated(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Patient.testIndicated",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfDeceasedIsNull() {
      final var request = requestBuilder
          .patient(
              athenaReactAnderssonDtoBuilder()
                  .deceased(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Patient.deceased",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfProtectedPersonIsNull() {
      final var request = requestBuilder
          .patient(
              athenaReactAnderssonDtoBuilder()
                  .protectedPerson(null)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: Patient.protectedPerson",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class CertificateIdValidation {

    @Test
    void shouldThrowIfCertificateIdIsNull() {
      final var request = requestBuilder.build();
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, null));

      assertEquals("Required parameter missing: certificateId",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfCertificateIdIsEmpty() {
      final var request = requestBuilder.build();
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, ""));

      assertEquals("Required parameter missing: certificateId",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class CertificateModelIdValidaiton {

    @Test
    void shouldThrowIfCertificateModelIdIsNull() {
      final var request = requestBuilder
          .certificateModelId(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: CertificateModelId",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfCertificateModelIdVersionIsNull() {
      final var request = requestBuilder
          .certificateModelId(
              CertificateModelIdDTO.builder()
                  .type(TYPE)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: CertificateModelId.version",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfCertificateModelIdVersionIsEmpty() {
      final var request = requestBuilder
          .certificateModelId(
              CertificateModelIdDTO.builder()
                  .type(TYPE)
                  .version("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: CertificateModelId.version",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfCertificateModelIdTypeIsNull() {
      final var request = requestBuilder
          .certificateModelId(
              CertificateModelIdDTO.builder()
                  .version(VERSION)
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: CertificateModelId.type",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfCertificateModelIdTypeIsEmpty() {
      final var request = requestBuilder
          .certificateModelId(
              CertificateModelIdDTO.builder()
                  .version(VERSION)
                  .type("")
                  .build()
          )
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: CertificateModelId.type",
          illegalArgumentException.getMessage());
    }
  }

  @Test
  void shouldThrowIfStatusIsMissing() {
    final var request = requestBuilder
        .status(null)
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request, CERTIFICATE_ID));

    assertEquals("Required parameter missing: status",
        illegalArgumentException.getMessage());
  }

  @Test
  void shouldThrowIfMissingIssuingUnit() {
    final var request = requestBuilder
        .issuingUnit(null)
        .build();

    final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
        () -> validator.validate(request, CERTIFICATE_ID));

    assertEquals("Required parameter missing: IssuingUnit",
        illegalArgumentException.getMessage());
  }

  @Nested
  class PrefillXml {

    @Test
    void shouldThrowIfPrefillXmlIsNull() {
      final var request = requestBuilder
          .prefillXml(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: PrefillXml",
          illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfPrefillXmlIsEmpty() {
      final var request = requestBuilder
          .prefillXml(new PrefillXmlDTO(""))
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request, CERTIFICATE_ID));

      assertEquals("Required parameter missing: PrefillXml",
          illegalArgumentException.getMessage());
    }
  }
}