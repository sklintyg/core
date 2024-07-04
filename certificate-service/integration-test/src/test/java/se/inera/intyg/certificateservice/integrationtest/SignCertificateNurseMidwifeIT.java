package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ANNA_SJUKSKOTERSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.BERTIL_BARNMORSKA_DTO;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.version;

import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public abstract class SignCertificateNurseMidwifeIT extends BaseIntegrationIT {

  protected abstract String type();

  protected abstract String typeVersion();

  @Test
  @DisplayName("Om användaren har rollen sjuksköterska ska intyget gå att signeras")
  void shallSuccessfullySignIfRoleIsNurse() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.signCertificate(
        customSignCertificateRequest()
            .user(ANNA_SJUKSKOTERSKA_DTO)
            .build(),
        certificateId(testCertificates),
        version(testCertificates)
    );

    assertAll(
        () -> assertEquals(SIGNED,
            Objects.requireNonNull(certificate(response)).getMetadata().getStatus()
        )
    );
  }

  @Test
  @DisplayName("Om användaren har rollen barnmorska ska intyget gå att signeras")
  void shallSuccessfullySignIfRoleIsMidwife() {
    final var testCertificates = testabilityApi.addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api.signCertificate(
        customSignCertificateRequest()
            .user(BERTIL_BARNMORSKA_DTO)
            .build(),
        certificateId(testCertificates),
        version(testCertificates)
    );

    assertAll(
        () -> assertEquals(SIGNED,
            Objects.requireNonNull(certificate(response)).getMetadata().getStatus()
        )
    );
  }
}
