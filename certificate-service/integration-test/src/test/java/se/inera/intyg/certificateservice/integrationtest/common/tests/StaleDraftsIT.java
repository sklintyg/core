package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.exists;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteStaleDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ListStaleDraftsRequest;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class StaleDraftsIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Ska lista gamla utkast som är äldre än cutoff datum")
  void shallListStaleDraftsOlderThanCutoffDate() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificateId = certificateId(testCertificates);
    final var cutoffDate = LocalDateTime.now(ZoneId.systemDefault()).plusDays(1);

    final var request = ListStaleDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    final var response = internalApi().listStaleDrafts(request);

    assertAll(
        () -> assertEquals(200, response.getStatusCode().value()),
        () -> assertNotNull(response.getBody()),
        () -> assertTrue(response.getBody().getCertificateIds().contains(certificateId))
    );
  }

  @Test
  @DisplayName("Ska inte lista utkast som är nyare än cutoff datum")
  void shallNotListDraftsNewerThanCutoffDate() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var cutoffDate = LocalDateTime.now(ZoneId.systemDefault()).minusDays(1);

    final var request = ListStaleDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    final var response = internalApi().listStaleDrafts(request);

    assertAll(
        () -> assertEquals(200, response.getStatusCode().value()),
        () -> assertNotNull(response.getBody()),
        () -> assertTrue(response.getBody().getCertificateIds().isEmpty())
    );
  }

  @Test
  @DisplayName("Ska ta bort gammalt utkast via internt API")
  void shallDeleteStaleDraft() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificateId = certificateId(testCertificates);

    final var request = DeleteStaleDraftsRequest.builder()
        .certificateId(certificateId)
        .build();

    final var response = internalApi().deleteStaleDrafts(request);

    assertAll(
        () -> assertEquals(200, response.getStatusCode().value()),
        () -> assertNotNull(response.getBody()),
        () -> assertNotNull(response.getBody().getCertificate()),
        () -> assertEquals(certificateId,
            response.getBody().getCertificate().getMetadata().getId()),
        () -> assertFalse(
            exists(internalApi().certificateExists(certificateId).getBody())
        )
    );
  }

  @Test
  @DisplayName("Ska returnera tom lista om inga gamla utkast finns")
  void shallReturnEmptyListWhenNoStaleDrafts() {
    final var cutoffDate = LocalDateTime.now(ZoneId.systemDefault()).minusYears(10);

    final var request = ListStaleDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    final var response = internalApi().listStaleDrafts(request);

    assertAll(
        () -> assertEquals(200, response.getStatusCode().value()),
        () -> assertNotNull(response.getBody()),
        () -> assertTrue(response.getBody().getCertificateIds().isEmpty())
    );
  }
}

