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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@ExtendWith(MockitoExtension.class)
class GetPatientCertificatesDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;

  @InjectMocks
  private GetPatientCertificatesDomainService getPatientCertificatesDomainService;

  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;

  @BeforeEach
  void setUp() {
    actionEvaluationBuilder = ActionEvaluation.builder()
        .patient(ATHENA_REACT_ANDERSSON)
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .careUnit(ALFA_MEDICINCENTRUM)
        .careProvider(ALFA_REGIONEN);
  }

  @Test
  void shallGetCertificatesBySubUnit() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    getPatientCertificatesDomainService.get(actionEvaluation);

    verify(certificateRepository).findByPatientBySubUnit(actionEvaluation.patient(),
        actionEvaluation.subUnit());
  }

  @Test
  void shallGetCertificatesByCareUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(ALFA_MEDICINSKT_CENTRUM)
        .build();

    getPatientCertificatesDomainService.get(actionEvaluation);

    verify(certificateRepository).findByPatientByCareUnit(actionEvaluation.patient(),
        actionEvaluation.careUnit());
  }

  @Test
  void shallReturnListOfCertificatesForPatient() {
    final var actionEvaluation = actionEvaluationBuilder
        .build();

    final var certificate = mock(Certificate.class);
    doReturn(true).when(certificate).allowTo(CertificateActionType.READ, actionEvaluation);
    doReturn(List.of(certificate)).when(certificateRepository)
        .findByPatientBySubUnit(actionEvaluation.patient(), actionEvaluation.subUnit());

    final var actualResult = getPatientCertificatesDomainService.get(actionEvaluation);

    assertEquals(List.of(certificate), actualResult);
  }

  @Test
  void shallReturnFilteredListOfCertificatesForPatient() {
    final var actionEvaluation = actionEvaluationBuilder
        .build();

    final var certificate1 = mock(Certificate.class);
    final var certificate2 = mock(Certificate.class);
    doReturn(true).when(certificate1).allowTo(CertificateActionType.READ, actionEvaluation);
    doReturn(false).when(certificate2).allowTo(CertificateActionType.READ, actionEvaluation);
    doReturn(List.of(certificate1, certificate2)).when(certificateRepository)
        .findByPatientBySubUnit(actionEvaluation.patient(), actionEvaluation.subUnit());

    final var actualResult = getPatientCertificatesDomainService.get(actionEvaluation);

    assertEquals(List.of(certificate1), actualResult);
  }
}