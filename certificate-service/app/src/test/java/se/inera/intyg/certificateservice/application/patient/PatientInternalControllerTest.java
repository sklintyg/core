package se.inera.intyg.certificateservice.application.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SickLeaveCertificateItemDTO;
import se.inera.intyg.certificateservice.application.certificate.service.GetSickLeaveCertificatesInternalService;

@ExtendWith(MockitoExtension.class)
class PatientInternalControllerTest {

  @Mock
  private GetSickLeaveCertificatesInternalService getSickLeaveCertificatesInternalService;
  @InjectMocks
  private PatientInternalController patientInternalController;

  @Test
  void shallReturnGetSickLeaveCertificatesInternalResponse() {
    final var certificates = List.of(SickLeaveCertificateItemDTO.builder().build());
    final var expectedResult = GetSickLeaveCertificatesInternalResponse.builder()
        .certificates(certificates)
        .build();

    final var request = GetSickLeaveCertificatesInternalRequest.builder().build();
    doReturn(expectedResult).when(getSickLeaveCertificatesInternalService).get(request);

    final var actualResult = patientInternalController.getSickLeaveCertificates(request);
    assertEquals(expectedResult, actualResult);
  }
}