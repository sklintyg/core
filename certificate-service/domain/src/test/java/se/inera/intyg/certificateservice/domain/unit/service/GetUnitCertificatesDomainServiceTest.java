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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest.CertificatesRequestBuilder;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationError;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationResult;

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
        .issuedUnitIds(List.of(ALFA_ALLERGIMOTTAGNINGEN.hsaId()))
        .personId(ATHENA_REACT_ANDERSSON.id());
  }

  @Test
  void shallReturnListOfCertificates() {
    final var actionEvaluation = actionEvaluationBuilder.build();
    final var certificatesRequest = certificatesRequestBuilder.build();

    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.READ, Optional.of(actionEvaluation));
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

    final var certificate1 = mock(MedicalCertificate.class);
    final var certificate2 = mock(MedicalCertificate.class);
    doReturn(true).when(certificate1)
        .allowTo(CertificateActionType.READ, Optional.of(actionEvaluation));
    doReturn(false).when(certificate2)
        .allowTo(CertificateActionType.READ, Optional.of(actionEvaluation));
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
        .issuedUnitIds(null)
        .build();

    final var certificateRequestCaptor = ArgumentCaptor.forClass(CertificatesRequest.class);

    getUnitCertificatesDomainService.get(certificatesRequest, actionEvaluation);

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

    getUnitCertificatesDomainService.get(certificatesRequest, actionEvaluation);

    verify(certificateRepository).findByCertificatesRequest(certificateRequestCaptor.capture());

    assertEquals(ALFA_MEDICINCENTRUM.hsaId(),
        certificateRequestCaptor.getValue().careUnitId()
    );
  }

  @Test
  void shallNotReturnValidCertificatesIfAskingForInValidCertificates() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificatesRequest = certificatesRequestBuilder
        .validCertificates(Boolean.FALSE)
        .build();

    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.READ, Optional.of(actionEvaluation));
    doReturn(ValidationResult.builder().build()).when(certificate).validate();
    doReturn(List.of(certificate)).when(certificateRepository)
        .findByCertificatesRequest(certificatesRequest);

    final var actualResult = getUnitCertificatesDomainService.get(
        certificatesRequest,
        actionEvaluation
    );

    assertEquals(Collections.emptyList(), actualResult);
  }

  @Test
  void shallNotReturnInValidCertificatesIfAskingForValidCertificates() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificatesRequest = certificatesRequestBuilder
        .validCertificates(Boolean.TRUE)
        .build();

    final var certificate = mock(MedicalCertificate.class);
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.READ, Optional.of(actionEvaluation));
    doReturn(
        ValidationResult.builder()
            .errors(List.of(ValidationError.builder().build()))
            .build()
    ).when(certificate).validate();
    doReturn(List.of(certificate)).when(certificateRepository)
        .findByCertificatesRequest(certificatesRequest);

    final var actualResult = getUnitCertificatesDomainService.get(
        certificatesRequest,
        actionEvaluation
    );

    assertEquals(Collections.emptyList(), actualResult);
  }
}