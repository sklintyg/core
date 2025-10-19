package se.inera.intyg.certificateservice.domain.patient;

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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest.CertificatesRequestBuilder;
import se.inera.intyg.certificateservice.domain.patient.service.GetPatientCertificatesDomainService;

@ExtendWith(MockitoExtension.class)
class GetPatientCertificatesDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;

  @InjectMocks
  private GetPatientCertificatesDomainService getPatientCertificatesDomainService;

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
        .issuedUnitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN.hsaId()))
        .personId(ATHENA_REACT_ANDERSSON.id());
  }

  @Test
  void shallGetCertificatesBySubUnit() {
    final var actionEvaluation = actionEvaluationBuilder.build();
    final var certificatesRequest = certificatesRequestBuilder.build();

    getPatientCertificatesDomainService.get(actionEvaluation);

    verify(certificateRepository).findByCertificatesRequest(certificatesRequest);
  }

  @Test
  void shallGetCertificatesByCareUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(ALFA_MEDICINSKT_CENTRUM)
        .build();
    final var certificatesRequest = certificatesRequestBuilder
        .careUnitId(ALFA_MEDICINSKT_CENTRUM.hsaId())
        .issuedUnitIds(null)
        .build();

    getPatientCertificatesDomainService.get(actionEvaluation);

    verify(certificateRepository).findByCertificatesRequest(certificatesRequest);
  }

  @Test
  void shallReturnListOfCertificatesForPatient() {
    final var actionEvaluation = actionEvaluationBuilder.build();
    final var certificatesRequest = certificatesRequestBuilder.build();

    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.READ, Optional.of(actionEvaluation));
    doReturn(List.of(certificate)).when(certificateRepository)
        .findByCertificatesRequest(certificatesRequest);

    final var actualResult = getPatientCertificatesDomainService.get(actionEvaluation);

    assertEquals(List.of(certificate), actualResult);
  }

  @Test
  void shallReturnFilteredListOfCertificatesForPatient() {
    final var actionEvaluation = actionEvaluationBuilder.build();
    final var certificatesRequest = certificatesRequestBuilder.build();

    final var certificate1 = mock(MedicalCertificate.class);
    final var certificate2 = mock(MedicalCertificate.class);
    doReturn(true).when(certificate1)
        .allowTo(CertificateActionType.READ, Optional.of(actionEvaluation));
    doReturn(false).when(certificate2)
        .allowTo(CertificateActionType.READ, Optional.of(actionEvaluation));
    doReturn(List.of(certificate1, certificate2)).when(certificateRepository)
        .findByCertificatesRequest(certificatesRequest);

    final var actualResult = getPatientCertificatesDomainService.get(actionEvaluation);

    assertEquals(List.of(certificate1), actualResult);
  }
}