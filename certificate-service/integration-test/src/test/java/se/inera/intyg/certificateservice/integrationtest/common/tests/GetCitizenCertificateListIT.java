package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateListRequest;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class GetCitizenCertificateListIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Om intyget är utfärdat på invånaren ska intyget returneras")
  void shallReturnListOfCertificatesIfAvailableForCitizen() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED),
        defaultTestablilityCertificateRequest("fk7472", "1.0", SIGNED),
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().getCitizenCertificateList(
        GetCitizenCertificateListRequest.builder()
            .personId(PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .id(ATHENA_REACT_ANDERSSON_ID)
                .build())
            .build()
    );

    assertEquals(2, response.getBody().getCitizenCertificates().size(),
        "Should return list of certificates available for citizen.");
  }

  @Test
  @DisplayName("Om invånaren inte har några intyg ska en tom lista returneras")
  void shallReturnEmptyListIfNoCertificatesIssuedOnCitizen() {
    final var response = api().getCitizenCertificateList(
        GetCitizenCertificateListRequest.builder()
            .personId(PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .id(ATHENA_REACT_ANDERSSON_ID)
                .build())
            .build()
    );

    assertEquals(0, response.getBody().getCitizenCertificates().size(),
        "Should return empty list of certificates.");
  }
}
