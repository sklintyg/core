package se.inera.intyg.certificateservice.application.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQARequest;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQAResponse;
import se.inera.intyg.certificateservice.application.patient.service.GetPatientCertificatesWithQAInternalService;

@ExtendWith(MockitoExtension.class)
class PatientCertificateInternalApiControllerTest {

  @Mock
  GetPatientCertificatesWithQAInternalService getPatientCertificatesWithQAInternalService;
  @InjectMocks
  PatientCertificateInternalApiController patientCertificateInternalApiController;

  @Test
  void shallReturnGetPatientCertificatesWithQAResponse() {
    final var expectedResponse = PatientCertificatesWithQAResponse.builder().build();
    final var request = PatientCertificatesWithQARequest.builder().build();
    doReturn(expectedResponse).when(getPatientCertificatesWithQAInternalService).get(request);

    final var actualResponse = patientCertificateInternalApiController.getPatientCertificatesWithQA(
        request);
    assertEquals(expectedResponse, actualResponse);
  }
}