package se.inera.intyg.certificateservice.application.unit.service.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsRequest;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsRequest.UnitStatisticsRequestBuilder;

class UnitStatisticsRequestValidatorTest {

  private static final String UNIT_ID = "unitId";
  private UnitStatisticsRequestValidator getUnitCertificatesInfoRequestValidator;
  private UnitStatisticsRequestBuilder requestBuilder;

  @BeforeEach
  void setUp() {
    getUnitCertificatesInfoRequestValidator = new UnitStatisticsRequestValidator();
    requestBuilder = UnitStatisticsRequest.builder()
        .user(AJLA_DOCTOR_DTO)
        .issuedByUnitIds(List.of(UNIT_ID));
  }

  @Test
  void validRequest() {
    getUnitCertificatesInfoRequestValidator.validate(requestBuilder.build());
  }

  @Nested
  class UserValidation {

    @Test
    void shallThrowIfUserIsNull() {
      final var request = requestBuilder
          .user(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> getUnitCertificatesInfoRequestValidator.validate(request));

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
          () -> getUnitCertificatesInfoRequestValidator.validate(request));

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
          () -> getUnitCertificatesInfoRequestValidator.validate(request));

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
          () -> getUnitCertificatesInfoRequestValidator.validate(request));

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
          () -> getUnitCertificatesInfoRequestValidator.validate(request));

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
          () -> getUnitCertificatesInfoRequestValidator.validate(request));

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
          () -> getUnitCertificatesInfoRequestValidator.validate(request));

      assertEquals("Required parameter missing: User.allowCopy",
          illegalArgumentException.getMessage());
    }
  }

  @Nested
  class UnitIdsValidation {

    @Test
    void shallThrowIfListIsNull() {
      final var request = requestBuilder
          .issuedByUnitIds(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> getUnitCertificatesInfoRequestValidator.validate(request));

      assertEquals("Required parameter missing: IssuedByUnitIds",
          illegalArgumentException.getMessage());
    }

    @Test
    void shallThrowIfListIsEmpty() {
      final var request = requestBuilder
          .issuedByUnitIds(Collections.emptyList())
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> getUnitCertificatesInfoRequestValidator.validate(request));

      assertEquals("Required parameter missing: IssuedByUnitIds",
          illegalArgumentException.getMessage());
    }
  }
}
