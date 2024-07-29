package se.inera.intyg.certificateservice.application.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQARequest;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;

class CertificatesWithQARequestFactoryTest {

  private static final String UNIT_ID_1 = "unitId1";
  private static final String UNIT_ID_2 = "unitId2";
  private static final String CARE_PROVIDER_ID = "careProviderId";
  private static final String PERSON_ID = "personId";
  private PatientCertificatesWithQARequest.PatientCertificatesWithQARequestBuilder requestBuilder;
  private CertificatesWithQARequestFactory certificatesWithQARequestFactory;

  @BeforeEach
  void setUp() {
    certificatesWithQARequestFactory = new CertificatesWithQARequestFactory();
    requestBuilder = PatientCertificatesWithQARequest.builder()
        .unitIds(List.of(UNIT_ID_1, UNIT_ID_2))
        .careProviderId(CARE_PROVIDER_ID)
        .personId(
            PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .id(PERSON_ID)
                .build()
        );
  }

  @Test
  void shallIncludePersonId() {
    final var request = requestBuilder.build();
    assertEquals(PERSON_ID, certificatesWithQARequestFactory.create(request).personId().id());
    assertEquals(PersonIdType.PERSONAL_IDENTITY_NUMBER,
        certificatesWithQARequestFactory.create(request).personId().type());
  }

  @Test
  void shallIncludeCareProviderId() {
    final var expectedCareProviderId = new HsaId(CARE_PROVIDER_ID);
    final var request = requestBuilder.build();
    assertEquals(expectedCareProviderId,
        certificatesWithQARequestFactory.create(request).careProviderId());
  }

  @Test
  void shallExcludeCareProviderId() {
    final var request = requestBuilder.careProviderId(null).build();
    assertNull(certificatesWithQARequestFactory.create(request).careProviderId());
  }

  @Test
  void shallIncludeUnitIds() {
    final var expectedUnitIds = List.of(new HsaId(UNIT_ID_1), new HsaId(UNIT_ID_2));
    final var request = requestBuilder.build();
    assertEquals(expectedUnitIds,
        certificatesWithQARequestFactory.create(request).unitIds());
  }

  @Test
  void shallExcludeUnitIds() {
    final var request = requestBuilder.unitIds(null).build();
    assertNull(certificatesWithQARequestFactory.create(request).unitIds());
  }
}
