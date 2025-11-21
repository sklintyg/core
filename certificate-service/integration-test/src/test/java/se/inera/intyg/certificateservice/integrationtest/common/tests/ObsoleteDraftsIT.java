package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultRevokeCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.exists;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.DisposeObsoleteDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ListObsoleteDraftsRequest;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class ObsoleteDraftsIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Om utkast är äldre än cutoff datum ska de listas")
  void shouldListObsoleteDrafts() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificateId = certificateId(testCertificates);
    final var cutoffDate = LocalDateTime.now(ZoneId.systemDefault()).plusDays(1);

    final var request = ListObsoleteDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    final var response = internalApi().listObsoleteDrafts(request);

    assertAll(
        () -> assertEquals(200, response.getStatusCode().value()),
        () -> assertNotNull(response.getBody()),
        () -> assertTrue(response.getBody().getCertificateIds().contains(certificateId))
    );
  }

  @Test
  @DisplayName("Om utkast är nyare än cutoff datum ska de inte listas")
  void shouldNotListNewerDrafts() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var cutoffDate = LocalDateTime.now(ZoneId.systemDefault()).minusDays(1);

    final var request = ListObsoleteDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    final var response = internalApi().listObsoleteDrafts(request);

    assertAll(
        () -> assertEquals(200, response.getStatusCode().value()),
        () -> assertNotNull(response.getBody()),
        () -> assertTrue(response.getBody().getCertificateIds().isEmpty())
    );
  }

  @Test
  @DisplayName("Om gammalt utkast raderas via internt API ska det tas bort")
  void shouldDisposeObsoleteDraft() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificateId = certificateId(testCertificates);

    final var request = DisposeObsoleteDraftsRequest.builder()
        .certificateId(certificateId)
        .build();

    final var response = internalApi().disposeObsoleteDrafts(request);

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
  @DisplayName("Om inga gamla utkast finns ska tom lista returneras")
  void shouldReturnEmptyListWhenNoObsoleteDrafts() {
    final var cutoffDate = LocalDateTime.now(ZoneId.systemDefault()).minusYears(10);

    final var request = ListObsoleteDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    final var response = internalApi().listObsoleteDrafts(request);

    assertAll(
        () -> assertEquals(200, response.getStatusCode().value()),
        () -> assertNotNull(response.getBody()),
        () -> assertTrue(response.getBody().getCertificateIds().isEmpty())
    );
  }

  @Test
  @DisplayName("Om låst utkast är äldre än cutoff datum ska det listas")
  void shouldListLockedDrafts() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.LOCKED)
    );

    final var certificateId = certificateId(testCertificates);
    final var cutoffDate = LocalDateTime.now(ZoneId.systemDefault()).plusDays(1);

    final var request = ListObsoleteDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    final var response = internalApi().listObsoleteDrafts(request);

    assertAll(
        () -> assertEquals(200, response.getStatusCode().value()),
        () -> assertNotNull(response.getBody()),
        () -> assertTrue(response.getBody().getCertificateIds().contains(certificateId))
    );
  }

  @Test
  @DisplayName("Om intyg är signerat ska det inte listas som ett gammalt utkast")
  void shouldNotListSignedCertificates() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    final var cutoffDate = LocalDateTime.now(ZoneId.systemDefault()).plusDays(1);

    final var request = ListObsoleteDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    final var response = internalApi().listObsoleteDrafts(request);

    assertAll(
        () -> assertEquals(200, response.getStatusCode().value()),
        () -> assertNotNull(response.getBody()),
        () -> assertTrue(response.getBody().getCertificateIds().isEmpty())
    );
  }

  @Test
  @DisplayName("Om intyg är makulerat ska det inte listas som ett gammalt utkast")
  void shouldNotListRevokedCertificates() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    api().revokeCertificate(
        defaultRevokeCertificateRequest(),
        certificateId(testCertificates)
    );

    final var cutoffDate = LocalDateTime.now(ZoneId.systemDefault()).plusDays(1);

    final var request = ListObsoleteDraftsRequest.builder()
        .cutoffDate(cutoffDate)
        .build();

    final var response = internalApi().listObsoleteDrafts(request);

    assertAll(
        () -> assertEquals(200, response.getStatusCode().value()),
        () -> assertNotNull(response.getBody()),
        () -> assertTrue(response.getBody().getCertificateIds().isEmpty())
    );
  }

  @Test
  @DisplayName("Om låst utkast skickas till radering ska det tas bort")
  void shouldDeleteLockedDraft() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.LOCKED)
    );

    final var certificateId = certificateId(testCertificates);

    final var request = DisposeObsoleteDraftsRequest.builder()
        .certificateId(certificateId)
        .build();

    final var response = internalApi().disposeObsoleteDrafts(request);

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
  @DisplayName("Om signerat intyg skickas till radering ska ett fel returneras")
  void shouldNotDeleteSignedCertificate() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    final var certificateId = certificateId(testCertificates);

    final var request = DisposeObsoleteDraftsRequest.builder()
        .certificateId(certificateId)
        .build();

    final var response = internalApi().disposeObsoleteDrafts(request);

    assertAll(
        () -> assertEquals(500, response.getStatusCode().value()),
        () -> assertNotNull(response.getBody()),
        () -> assertTrue(
            exists(internalApi().certificateExists(certificateId).getBody())
        )
    );
  }

  @Test
  @DisplayName("Om makulerat intyg skickas till radering ska ett fel returneras")
  void shouldNotDeleteRevokedCertificate() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
    );

    api().revokeCertificate(
        defaultRevokeCertificateRequest(),
        certificateId(testCertificates)
    );

    final var certificateId = certificateId(testCertificates);

    final var request = DisposeObsoleteDraftsRequest.builder()
        .certificateId(certificateId)
        .build();

    final var response = internalApi().disposeObsoleteDrafts(request);

    assertAll(
        () -> assertEquals(500, response.getStatusCode().value()),
        () -> assertNotNull(response.getBody()),
        () -> assertTrue(
            exists(internalApi().certificateExists(certificateId).getBody())
        )
    );
  }
}

