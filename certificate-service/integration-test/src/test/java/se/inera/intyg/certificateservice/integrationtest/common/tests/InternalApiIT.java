package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultRevokeCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateInternalXmlResponse;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.decodeXml;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.exists;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.metadata;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.version;

import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class InternalApiIT extends BaseIntegrationIT {


  @Test
  @DisplayName("Signerat intyg skall gå att hämta från intern api:et")
  void shallReturnSignedCertificate() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    api().signCertificate(
        defaultSignCertificateRequest(),
        certificateId(testCertificates),
        version(testCertificates)
    );

    final var response = internalApi().getCertificateXml(certificateId(testCertificates));

    assertAll(
        () -> assertEquals(certificateId(testCertificates),
            certificateInternalXmlResponse(response).getCertificateId()),
        () -> assertEquals(type(), certificateInternalXmlResponse(response).getCertificateType()),
        () -> assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ID,
            certificateInternalXmlResponse(response).getUnit().getUnitId()),
        () -> assertNull(certificateInternalXmlResponse(response).getRevoked()),
        () -> assertTrue(decodeXml(certificateInternalXmlResponse(response).getXml()).contains(
                Objects.requireNonNull(certificateId(testCertificates))),
            () -> "Expected 'Läkare' to be part of xml: '%s'"
                .formatted(decodeXml(certificateInternalXmlResponse(response).getXml())))
    );
  }

  @Test
  @DisplayName("Makulerat intyg skall gå att hämta från intern api:et")
  void shallReturnRevokedCertificate() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().revokeCertificate(
        defaultRevokeCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = internalApi().getCertificateXml(certificateId(testCertificates));

    assertAll(
        () -> assertEquals(certificateId(testCertificates),
            certificateInternalXmlResponse(response).getCertificateId()),
        () -> assertEquals(type(), certificateInternalXmlResponse(response).getCertificateType()),
        () -> assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ID,
            certificateInternalXmlResponse(response).getUnit().getUnitId()),
        () -> assertNotNull(certificateInternalXmlResponse(response).getRevoked()),
        () -> assertTrue(decodeXml(certificateInternalXmlResponse(response).getXml()).contains(
                Objects.requireNonNull(certificateId(testCertificates))),
            () -> "Expected 'Läkare' to be part of xml: '%s'"
                .formatted(decodeXml(certificateInternalXmlResponse(response).getXml())))
    );
  }

  @Test
  @DisplayName("Skickat intyg skall gå att hämta från intern api:et")
  void shallReturnSentCertificate() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(testCertificates)
    );

    final var response = internalApi().getCertificateXml(certificateId(testCertificates));

    assertAll(
        () -> assertEquals(certificateId(testCertificates),
            certificateInternalXmlResponse(response).getCertificateId()),
        () -> assertEquals(type(), certificateInternalXmlResponse(response).getCertificateType()),
        () -> assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ID,
            certificateInternalXmlResponse(response).getUnit().getUnitId()),
        () -> assertNotNull(certificateInternalXmlResponse(response).getRecipient()),
        () -> assertNull(certificateInternalXmlResponse(response).getRevoked()),
        () -> assertTrue(decodeXml(certificateInternalXmlResponse(response).getXml()).contains(
                Objects.requireNonNull(certificateId(testCertificates))),
            () -> "Expected 'Läkare' to be part of xml: '%s'"
                .formatted(decodeXml(certificateInternalXmlResponse(response).getXml())))
    );
  }

  @Test
  @DisplayName("Metadata för intyget skall gå att hämta")
  void shallReturnCertificateMetadata() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = internalApi().getCertificateMetadata(
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(certificateId(testCertificates),
            metadata(response).getId()),
        () -> assertEquals(type(), metadata(response).getType()),
        () -> assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ID,
            metadata(response).getUnit().getUnitId())
    );
  }

  @Test
  @DisplayName("Om intyget finns så returneras true")
  void shallReturnTrueIfCertificateExists() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = internalApi().certificateExists(
        certificateId(testCertificates)
    );

    assertTrue(
        exists(response.getBody()),
        "Should return true when certificate exists!"
    );
  }

  @Test
  @DisplayName("Om intyget inte finns lagrat så returneras false")
  void shallReturnFalseIfCertificateDoesnt() {
    final var response = internalApi().certificateExists("certificate-not-exists");

    assertFalse(
        exists(response.getBody()),
        "Should return false when certificate doesnt exists!"
    );
  }

  @Test
  @DisplayName("Intyget skall gå att hämta")
  void shallReturnCertificate() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = internalApi().getCertificate(
        certificateId(testCertificates)
    );

    final var certificate = certificate(response.getBody());
    assertAll(
        () -> assertEquals(certificateId(testCertificates), certificate.getMetadata().getId()),
        () -> assertEquals(type(), certificate.getMetadata().getType())
    );
  }
}
