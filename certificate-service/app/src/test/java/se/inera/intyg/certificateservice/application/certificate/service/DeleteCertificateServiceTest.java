package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.service.validation.DeleteCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.service.DeleteCertificateDomainService;

@ExtendWith(MockitoExtension.class)
class DeleteCertificateServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  private static final long VERSION = 0L;
  @Mock
  private DeleteCertificateRequestValidator deleteCertificateRequestValidator;
  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private DeleteCertificateDomainService deleteCertificateDomainService;
  @InjectMocks
  private DeleteCertificateService deleteCertificateService;

  private static final DeleteCertificateRequest DELETE_CERTIFICATE_REQUEST = DeleteCertificateRequest.builder()
      .user(AJLA_DOCTOR_DTO)
      .careProvider(ALFA_REGIONEN_DTO)
      .careUnit(ALFA_MEDICINCENTRUM_DTO)
      .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
      .patient(ATHENA_REACT_ANDERSSON_DTO)
      .build();

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = DeleteCertificateRequest.builder().build();

    doThrow(IllegalArgumentException.class).when(deleteCertificateRequestValidator)
        .validate(request, CERTIFICATE_ID, VERSION);

    assertThrows(IllegalArgumentException.class,
        () -> deleteCertificateService.delete(request, CERTIFICATE_ID, VERSION)
    );
  }

  @Test
  void shallCallDeleteCertificateDomainService() {
    final var actionEvaluation = ActionEvaluation.builder().build();
    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        DELETE_CERTIFICATE_REQUEST.getPatient(),
        DELETE_CERTIFICATE_REQUEST.getUser(),
        DELETE_CERTIFICATE_REQUEST.getUnit(),
        DELETE_CERTIFICATE_REQUEST.getCareUnit(),
        DELETE_CERTIFICATE_REQUEST.getCareProvider()
    );

    deleteCertificateService.delete(DELETE_CERTIFICATE_REQUEST, CERTIFICATE_ID, VERSION);

    verify(deleteCertificateDomainService).delete(
        new CertificateId(CERTIFICATE_ID),
        new Revision(VERSION),
        actionEvaluation
    );
  }
}