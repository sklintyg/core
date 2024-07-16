package se.inera.intyg.certificateservice.application.patient.service.validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesWithQARequest;

class GetPatientCertificatesWithQARequestValidatorTest {

  private static final String CARE_PROVIDER_ID = "careProviderId";
  private static final String PERSON_ID = "19121212121212";
  private static final List<String> UNIT_IDS = List.of("unitId1", "unitId2", "unitId3");
  private GetPatientCertificatesWithQARequestValidator validator;
  private GetPatientCertificatesWithQARequest.GetPatientCertificatesWithQARequestBuilder requestBuilder;

  @BeforeEach
  void setUp() {
    validator = new GetPatientCertificatesWithQARequestValidator();
    requestBuilder = GetPatientCertificatesWithQARequest.builder()
        .careProviderId(CARE_PROVIDER_ID)
        .personId(
            PersonIdDTO.builder()
                .id(PERSON_ID)
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .build()
        )
        .unitIds(UNIT_IDS);
  }

  @Nested
  class PersonIdTests {

    @Test
    void shallThrowIfPersonIdIdIsNull() {
      final var personId = PersonIdDTO.builder()
          .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
          .build();

      final var request = requestBuilder.personId(personId).build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request));
      assertTrue(illegalArgumentException.getMessage().contains("PersonId"));
    }

    @Test
    void shallThrowIfPersonIdIdIsEmpty() {
      final var personId = PersonIdDTO.builder()
          .id("")
          .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
          .build();

      final var request = requestBuilder.personId(personId).build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request));
      assertTrue(illegalArgumentException.getMessage().contains("PersonId.id"));
    }

    @Test
    void shallThrowIfPersonIdTypeIsNull() {
      final var personId = PersonIdDTO.builder()
          .id(PERSON_ID)
          .build();

      final var request = requestBuilder.personId(personId).build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request));
      assertTrue(illegalArgumentException.getMessage().contains("PersonId.type"));
    }
  }

  @Nested
  class CareProviderIdOrUnitIdsTests {

    @Test
    void shallThrowIfCareProviderIdAndUnitIdsIsNull() {
      final var request = requestBuilder
          .careProviderId(null)
          .unitIds(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request));
      assertTrue(illegalArgumentException.getMessage().contains("unitIds or careProviderId"));
    }

    @Test
    void shallThrowIfCareProviderIdIsEmptyAndUnitIdsIsNull() {
      final var request = requestBuilder
          .careProviderId("")
          .unitIds(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request));
      assertTrue(illegalArgumentException.getMessage().contains("unitIds or careProviderId"));
    }

    @Test
    void shallThrowIfCareProviderIdIsNullAndUnitIdsIsEmpty() {
      final var request = requestBuilder
          .careProviderId(null)
          .unitIds(Collections.emptyList())
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> validator.validate(request));
      assertTrue(illegalArgumentException.getMessage().contains("unitIds or careProviderId"));
    }
  }
}
