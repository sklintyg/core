package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customRenewExternalCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetPatientCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificates;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.relation;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.renewCertificateResponse;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationTypeDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class RenewExternalCertificateIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Skall skapa ett förnyat intyg med en relation till det tidigare intyget")
  void shallSuccessfullyRenewCertificateAndAddParentRelation() {
    final var certificateId = UUID.randomUUID().toString();
    final var response = api().renewExternalCertificate(
        customRenewExternalCertificateRequest()
            .certificateModelId(
                CertificateModelIdDTO.builder()
                    .type(type())
                    .version(typeVersion())
                    .build()
            )
            .build(),
        certificateId
    );

    assertAll(
        () -> assertEquals(CertificateRelationTypeDTO.EXTENDED,
            relation(renewCertificateResponse(response)).getParent().getType()),
        () -> assertEquals(certificateId,
            relation(renewCertificateResponse(response)).getParent().getCertificateId())
    );
  }

  @Test
  @DisplayName("Placeholder intyg skall inte vara tillgängliga vid efterfrågandet om ett särskilt intyg")
  void shallNotBeAbleToRetrievePlaceholderCertificateFromExternalApi() {
    final var certificateId = UUID.randomUUID().toString();
    api().renewExternalCertificate(
        customRenewExternalCertificateRequest()
            .certificateModelId(
                CertificateModelIdDTO.builder()
                    .type(type())
                    .version(typeVersion())
                    .build()
            )
            .build(),
        certificateId
    );

    final var certificate = api().getCertificate(
        defaultGetCertificateRequest(),
        certificateId
    );

    assertNull(certificate(certificate.getBody()));
  }

  @Test
  @DisplayName("Placeholder intyg skall inte vara tillgängliga under patientvyn där tidigare intyg presenteras")
  void shallNotBeAbleToRetrievePlaceholderCertificateFromPatientController() {
    final var certificateId = UUID.randomUUID().toString();
    final var renewExternalCertificate = api().renewExternalCertificate(
        customRenewExternalCertificateRequest()
            .certificateModelId(
                CertificateModelIdDTO.builder()
                    .type(type())
                    .version(typeVersion())
                    .build()
            )
            .build(),
        certificateId
    );

    final var certificate = api().getPatientCertificates(
        defaultGetPatientCertificateRequest()
    );

    final var certificates = certificates(certificate.getBody());
    assertAll(
        () -> assertTrue(
            certificates.stream().noneMatch(
                certificateDTO -> certificateDTO.getMetadata().getId().equals(certificateId))
        ),
        () -> assertTrue(
            certificates.stream().anyMatch(certificateDTO -> certificateDTO.getMetadata().getId()
                .equals(renewCertificateResponse(renewExternalCertificate).getCertificate()
                    .getMetadata().getId()))
        )
    );
  }

  @Test
  @DisplayName("Skall returnera false vid efterfrågan om intyget finns i tjänsten om intyget är ett placeholder intyg")
  void shallNotBeAbleToRetrievePlaceholderCertificateFromExistRequest() {
    final var certificateId = UUID.randomUUID().toString();
    api().renewExternalCertificate(
        customRenewExternalCertificateRequest()
            .certificateModelId(
                CertificateModelIdDTO.builder()
                    .type(type())
                    .version(typeVersion())
                    .build()
            )
            .build(),
        certificateId
    );

    final var certificate = api().certificateExists(
        certificateId
    );

    assertNotNull(certificate.getBody());
    assertFalse(certificate.getBody().isExists());
  }
}