package se.inera.intyg.certificateservice.domain.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_MEDICINSKT_CENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest.CertificatesRequestBuilder;

@ExtendWith(MockitoExtension.class)
class GetUnitCertificatesDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @InjectMocks
  private GetUnitCertificatesDomainService getUnitCertificatesDomainService;

  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private CertificatesRequestBuilder certificatesRequestBuilder;

  @BeforeEach
  void setUp() {
    actionEvaluationBuilder = ActionEvaluation.builder()
        .patient(ATHENA_REACT_ANDERSSON)
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .careUnit(ALFA_MEDICINCENTRUM)
        .careProvider(ALFA_REGIONEN);

    certificatesRequestBuilder = CertificatesRequest.builder()
        .statuses(Status.all())
        .issuedUnitId(ALFA_ALLERGIMOTTAGNINGEN.hsaId())
        .personId(ATHENA_REACT_ANDERSSON.id());
  }

  @Test
  void shallReturnListOfCertificates() {
    final var actionEvaluation = actionEvaluationBuilder.build();
    final var certificatesRequest = certificatesRequestBuilder.build();

    final var certificate = mock(Certificate.class);
    doReturn(true).when(certificate).allowTo(CertificateActionType.READ, actionEvaluation);
    doReturn(List.of(certificate)).when(certificateRepository)
        .findByCertificatesRequest(certificatesRequest);

    final var actualResult = getUnitCertificatesDomainService.get(
        certificatesRequest,
        actionEvaluation
    );

    assertEquals(List.of(certificate), actualResult);
  }

  @Test
  void shallReturnFilteredListOfCertificates() {
    final var actionEvaluation = actionEvaluationBuilder.build();
    final var certificatesRequest = certificatesRequestBuilder.build();

    final var certificate1 = mock(Certificate.class);
    final var certificate2 = mock(Certificate.class);
    doReturn(true).when(certificate1).allowTo(CertificateActionType.READ, actionEvaluation);
    doReturn(false).when(certificate2).allowTo(CertificateActionType.READ, actionEvaluation);
    doReturn(List.of(certificate1, certificate2)).when(certificateRepository)
        .findByCertificatesRequest(certificatesRequest);

    final var actualResult = getUnitCertificatesDomainService.get(
        certificatesRequest,
        actionEvaluation
    );

    assertEquals(List.of(certificate1), actualResult);
  }

  @Test
  void shallAddIssuedUnitIfNoIssuedUnitProvidedAndIsOnSubUnit() {
    final var actionEvaluation = actionEvaluationBuilder.build();
    final var certificatesRequest = certificatesRequestBuilder
        .issuedUnitId(null)
        .build();

    final var certificateRequestCaptor = ArgumentCaptor.forClass(CertificatesRequest.class);

    getUnitCertificatesDomainService.get(certificatesRequest, actionEvaluation);

    verify(certificateRepository).findByCertificatesRequest(certificateRequestCaptor.capture());

    assertEquals(ALFA_ALLERGIMOTTAGNINGEN.hsaId(),
        certificateRequestCaptor.getValue().issuedUnitId()
    );
  }

  @Test
  void shallAddCareUnitIfNoIssuedUnitProvidedAndIsOnCareUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(ALFA_MEDICINSKT_CENTRUM)
        .build();

    final var certificatesRequest = certificatesRequestBuilder
        .issuedUnitId(null)
        .build();

    final var certificateRequestCaptor = ArgumentCaptor.forClass(CertificatesRequest.class);

    getUnitCertificatesDomainService.get(certificatesRequest, actionEvaluation);

    verify(certificateRepository).findByCertificatesRequest(certificateRequestCaptor.capture());

    assertEquals(ALFA_MEDICINCENTRUM.hsaId(),
        certificateRequestCaptor.getValue().careUnitId()
    );
  }
}