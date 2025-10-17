package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetSickLeaveCertificatesInternalRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class GetSickLeaveCertificatesIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Om intyget är utfärdat på rätt patient ska det returneras")
  void shallReturnSickLeaveCertificatesForPatient() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .status(CertificateStatusTypeDTO.SIGNED)
            .build()
    );

    final var response = internalApi().getSickLeaveCertificatesInternal(
        defaultGetSickLeaveCertificatesInternalRequest()
    );

    assertAll(
        () -> assertFalse(response.getBody().getCertificates().isEmpty()),
        () -> assertEquals(1, response.getBody().getCertificates().size()),
        () -> assertEquals(certificateId(testCertificates),
            response.getBody().getCertificates().get(0).getId())
    );
  }
}

