package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class GetCitizenCertificateIT extends BaseIntegrationIT {


  @Test
  @DisplayName("Om intyget är utfärdat på invånaren ska intyget returneras")
  void shallReturnCertificateIfIssuedOnCitizen() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().getCitizenCertificate(
        GetCitizenCertificateRequest.builder()
            .personId(PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .id(ATHENA_REACT_ANDERSSON_ID)
                .build())
            .build(), certificateId(testCertificates)
    );

    assertNotNull(response.getBody().getCertificate(),
        "Should return certificate of patient.");
  }

  @Test
  @DisplayName("Om intyget är utfärdat på en annan invånare ska felkod 403 (FORBIDDEN) returneras")
  void shallReturn403IfNotIssuedOnCitizen() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    final var response = api().getCitizenCertificate(
        GetCitizenCertificateRequest.builder()
            .personId(PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .id(ALVE_REACT_ALFREDSSON_ID)
                .build())
            .build(), certificateId(testCertificates)
    );

    assertEquals(403, response.getStatusCode().value());
  }
}
