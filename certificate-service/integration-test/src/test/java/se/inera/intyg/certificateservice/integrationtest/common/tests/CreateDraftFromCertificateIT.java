package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementDTOBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionMedicinskBehandling.QUESTION_MEDICINSK_BEHANDLING_ID;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customCreateDraftFromCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultComplementCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultCreateDraftFromCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultReplaceCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.version;

import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;

public abstract class CreateDraftFromCertificateIT extends BaseIntegrationIT {

  private static final String QUESTION_KONTAKT_ID = "26";
  private static final String FK7804 = "fk7804";
  private static final String VERSION = "2.0";

  @Test
  @DisplayName("Skall skapa ett AG7804 med intygsinnehåll från ett signerat FK7804")
  void shallCreateAG7804FromSignedFK7804() {
    final var createCertificateResponse = testabilityApi().addCertificate(
        customTestabilityCertificateRequest(
            FK7804,
            VERSION,
            CertificateStatusTypeDTO.SIGNED
        )
            .fillType(TestabilityFillTypeDTO.MAXIMAL)
            .build()
    );

    final var response = api().createDraftFromCertificate(
        defaultCreateDraftFromCertificateRequest(),
        certificateId(createCertificateResponse.getBody())
    );

    final var templateCertificate = certificate(createCertificateResponse.getBody());
    final var certificateData = Objects.requireNonNull(certificate(response.getBody())).getData();

    assertNotNull(templateCertificate);

    templateCertificate.getData().entrySet().forEach(data -> {
      if (excludeIcfAndQuestionKontakt(data)) {
        return;
      }

      final var certificateDataElement = certificateData.get(data.getKey());

      assertEquals(data.getValue().getValue(), certificateDataElement.getValue());
    });
  }

  private static boolean excludeIcfAndQuestionKontakt(
      Entry<String, CertificateDataElement> data) {
    return data.getValue().getValue() == null || data.getValue().getValue().getType()
        .equals(CertificateDataValueType.ICF) || data.getValue().getId().contains(
        QUESTION_KONTAKT_ID);
  }

  @Test
  @DisplayName("Skall returnera felkod 403 om man försöker skapa ett AG7804 från ett osignerat FK7804")
  void shallReturn403IfCreateAG7804FromUnsignedFK7804() {
    final var createCertificateResponse = testabilityApi().addCertificate(
        customTestabilityCertificateRequest(
            FK7804,
            VERSION,
            CertificateStatusTypeDTO.UNSIGNED
        )
            .fillType(TestabilityFillTypeDTO.MAXIMAL)
            .build()
    );

    final var response = api().createDraftFromCertificate(
        defaultCreateDraftFromCertificateRequest(),
        certificateId(createCertificateResponse.getBody())
    );

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  @DisplayName("Skall returnera felkod 403 om man försöker skapa ett AG7804 från ett makulerat FK7804")
  void shallReturn403IfCreateAG7804FromRevokedFK7804() {
    final var createCertificateResponse = testabilityApi().addCertificate(
        customTestabilityCertificateRequest(
            FK7804,
            VERSION,
            CertificateStatusTypeDTO.REVOKED
        )
            .fillType(TestabilityFillTypeDTO.MAXIMAL)
            .build()
    );

    final var response = api().createDraftFromCertificate(
        defaultCreateDraftFromCertificateRequest(),
        certificateId(createCertificateResponse.getBody())
    );

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  @DisplayName("Skall returnera felkod 403 om man försöker skapa ett AG7804 från ett ersatt FK7804")
  void shallReturn403IfCreateAG7804FromReplacedAndSignedFK7804() {
    final var createCertificateResponse = testabilityApi().addCertificate(
        customTestabilityCertificateRequest(
            FK7804,
            VERSION,
            CertificateStatusTypeDTO.SIGNED
        )
            .fillType(TestabilityFillTypeDTO.MAXIMAL)
            .build()
    );

    final var replaceCertificateResponse = api().replaceCertificate(
        defaultReplaceCertificateRequest(),
        certificateId(createCertificateResponse.getBody())
    );

    api().signCertificate(
        defaultSignCertificateRequest(),
        certificateId(replaceCertificateResponse.getBody()),
        version(replaceCertificateResponse.getBody())
    );

    final var response = api().createDraftFromCertificate(
        defaultCreateDraftFromCertificateRequest(),
        certificateId(createCertificateResponse.getBody())
    );

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }


  @Test
  @DisplayName("Skall returnera felkod 403 om man försöker skapa ett AG7804 från ett kompletterat FK7804")
  void shallReturn403IfCreateAG7804FromComplementedAndSignedFK7804() {
    final var createCertificateResponse = testabilityApi().addCertificate(
        customTestabilityCertificateRequest(
            FK7804,
            VERSION,
            CertificateStatusTypeDTO.SIGNED
        )
            .fillType(TestabilityFillTypeDTO.MAXIMAL)
            .build()
    );

    api().sendCertificate(
        defaultSendCertificateRequest(),
        certificateId(createCertificateResponse.getBody())
    );

    api().receiveMessage(
        incomingComplementMessageBuilder()
            .certificateId(certificateId(createCertificateResponse.getBody()))
            .complements(List.of(incomingComplementDTOBuilder()
                .questionId(QUESTION_MEDICINSK_BEHANDLING_ID.id())
                .build()))
            .build()
    );

    final var complementCertificateResponse = api().complementCertificate(
        defaultComplementCertificateRequest(),
        certificateId(createCertificateResponse.getBody())
    );

    api().signCertificate(
        defaultSignCertificateRequest(),
        certificateId(complementCertificateResponse.getBody()),
        version(complementCertificateResponse.getBody())
    );

    final var response = api().createDraftFromCertificate(
        defaultCreateDraftFromCertificateRequest(),
        certificateId(createCertificateResponse.getBody())
    );

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }


  @Test
  @DisplayName("Skall returnera felkod 403 om man försöker skapa ett AG7804 från ett FK7804 som är utfärdat på en patient med skyddade personuppgifter som vårdadmin")
  void shallReturn403IfCreateAG7804FromFK7804OnCertificateWithProtectedPersonAsCareAdmin() {
    final var createCertificateResponse = testabilityApi().addCertificate(
        customTestabilityCertificateRequest(
            FK7804,
            VERSION,
            CertificateStatusTypeDTO.SIGNED
        )
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .fillType(TestabilityFillTypeDTO.MAXIMAL)
            .build()
    );

    final var response = api().createDraftFromCertificate(
        customCreateDraftFromCertificateRequest()
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .build(),
        certificateId(createCertificateResponse.getBody())
    );

    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }
}
