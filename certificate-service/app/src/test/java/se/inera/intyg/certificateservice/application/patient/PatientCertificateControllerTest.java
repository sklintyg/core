package se.inera.intyg.certificateservice.application.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesRequest;
import se.inera.intyg.certificateservice.application.patient.dto.GetPatientCertificatesResponse;
import se.inera.intyg.certificateservice.application.patient.service.GetPatientCertificateService;

@ExtendWith(MockitoExtension.class)
class PatientCertificateControllerTest {

  @Mock
  private GetPatientCertificateService getPatientCertificateService;
  @InjectMocks
  private PatientCertificateController patientCertificateController;

  @Test
  void shallReturnGetPatientCertificatesResponse() {
    final var certificates = List.of(CertificateDTO.builder().build());
    final var expectedResult = GetPatientCertificatesResponse.builder()
        .certificates(certificates)
        .build();

    final var request = GetPatientCertificatesRequest.builder().build();
    doReturn(expectedResult).when(getPatientCertificateService).get(request);

    final var actualResult = patientCertificateController.getPatientCertificates(request);
    assertEquals(expectedResult, actualResult);
  }
}
