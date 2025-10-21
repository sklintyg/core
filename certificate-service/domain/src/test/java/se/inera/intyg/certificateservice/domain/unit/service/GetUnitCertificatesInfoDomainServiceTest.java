package se.inera.intyg.certificateservice.domain.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_MEDICINSKT_CENTRUM;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest.CertificatesRequestBuilder;
import se.inera.intyg.certificateservice.domain.testdata.TestDataUser;

@ExtendWith(MockitoExtension.class)
class GetUnitCertificatesInfoDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @InjectMocks
  private GetUnitCertificatesInfoDomainService getUnitCertificatesInfoDomainService;

  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private CertificatesRequestBuilder certificatesRequestBuilder;

  @BeforeEach
  void setUp() {
    actionEvaluationBuilder = ActionEvaluation.builder()
        .patient(ATHENA_REACT_ANDERSSON)
        .user(TestDataUser.AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .careUnit(ALFA_MEDICINCENTRUM)
        .careProvider(ALFA_REGIONEN);

    certificatesRequestBuilder = CertificatesRequest.builder()
        .statuses(Status.unsigned())
        .issuedUnitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN.hsaId()))
        .personId(ATHENA_REACT_ANDERSSON.id());
  }

  @Test
  void shallReturnListOfStaff() {
    final var actionEvaluation = actionEvaluationBuilder.build();
    final var certificatesRequest = certificatesRequestBuilder.build();
    final var certificateMetaData = CertificateMetaData.builder()
        .issuer(AJLA_DOKTOR)
        .build();
    final var certificateMetaDataTwo = CertificateMetaData.builder()
        .issuer(ALVA_VARDADMINISTRATOR)
        .build();

    final var certificate = mock(MedicalCertificate.class);
    final var certificateTwo = mock(MedicalCertificate.class);
    doReturn(certificateMetaData).when(certificate).certificateMetaData();
    doReturn(certificateMetaDataTwo).when(certificateTwo).certificateMetaData();
    doReturn(List.of(certificate, certificateTwo)).when(certificateRepository)
        .findByCertificatesRequest(certificatesRequest);

    final var actualResult = getUnitCertificatesInfoDomainService.get(certificatesRequest,
        actionEvaluation);

    assertEquals(List.of(AJLA_DOKTOR, ALVA_VARDADMINISTRATOR), actualResult);
  }

  @Test
  void shallReturnListOfUniqueStaff() {
    final var actionEvaluation = actionEvaluationBuilder.build();
    final var certificatesRequest = certificatesRequestBuilder.build();
    final var certificateMetaData = CertificateMetaData.builder()
        .issuer(AJLA_DOKTOR)
        .build();

    final var certificate = mock(MedicalCertificate.class);
    final var certificateTwo = mock(MedicalCertificate.class);
    doReturn(certificateMetaData).when(certificate).certificateMetaData();
    doReturn(certificateMetaData).when(certificateTwo).certificateMetaData();
    doReturn(List.of(certificate, certificateTwo)).when(certificateRepository)
        .findByCertificatesRequest(certificatesRequest);

    final var actualResult = getUnitCertificatesInfoDomainService.get(certificatesRequest,
        actionEvaluation);

    assertEquals(List.of(AJLA_DOKTOR), actualResult);
  }

  @Test
  void shallAddIssuedUnitIfNoIssuedUnitProvidedAndIsOnSubUnit() {
    final var actionEvaluation = actionEvaluationBuilder.build();
    final var certificatesRequest = certificatesRequestBuilder
        .issuedUnitIds(null)
        .build();

    final var certificateRequestCaptor = ArgumentCaptor.forClass(CertificatesRequest.class);

    getUnitCertificatesInfoDomainService.get(certificatesRequest, actionEvaluation);

    verify(certificateRepository).findByCertificatesRequest(certificateRequestCaptor.capture());

    assertEquals(List.of(ALFA_ALLERGIMOTTAGNINGEN.hsaId()),
        certificateRequestCaptor.getValue().issuedUnitIds()
    );
  }

  @Test
  void shallAddCareUnitIfNoIssuedUnitProvidedAndIsOnCareUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(ALFA_MEDICINSKT_CENTRUM)
        .build();

    final var certificatesRequest = certificatesRequestBuilder
        .issuedUnitIds(null)
        .build();

    final var certificateRequestCaptor = ArgumentCaptor.forClass(CertificatesRequest.class);

    getUnitCertificatesInfoDomainService.get(certificatesRequest, actionEvaluation);

    verify(certificateRepository).findByCertificatesRequest(certificateRequestCaptor.capture());

    assertEquals(ALFA_MEDICINCENTRUM.hsaId(),
        certificateRequestCaptor.getValue().careUnitId()
    );
  }
}