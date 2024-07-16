package se.inera.intyg.certificateservice.application.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesWithQARequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesWithQAResponse;
import se.inera.intyg.certificateservice.application.patient.service.GetPatientCertificatesWithQAService;

@ExtendWith(MockitoExtension.class)
class PatientCertificateInternalApiControllerTest {

  @Mock
  GetPatientCertificatesWithQAService getPatientCertificatesWithQAService;
  @InjectMocks
  PatientCertificateInternalApiController patientCertificateInternalApiController;

  @Test
  void shallReturnGetPatientCertificatesWithQAResponse() {
    final var expectedResponse = GetPatientCertificatesWithQAResponse.builder().build();
    final var request = GetPatientCertificatesWithQARequest.builder().build();
    doReturn(expectedResponse).when(getPatientCertificatesWithQAService).get(request);

    final var actualResponse = patientCertificateInternalApiController.getPatientCertificatesWithQA(
        request);
    assertEquals(expectedResponse, actualResponse);
  }
}