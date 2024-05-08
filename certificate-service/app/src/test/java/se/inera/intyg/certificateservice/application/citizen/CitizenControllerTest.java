package se.inera.intyg.certificateservice.application.citizen;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.citizen.dto.CitizenCertificateExistsResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.service.CitizenCertificateExistsService;
import se.inera.intyg.certificateservice.application.citizen.service.GetCitizenCertificateService;

@ExtendWith(MockitoExtension.class)
class CitizenControllerTest {

  private static final String CERTIFICATE_ID = "CERTIFICATE_ID";

  @Mock
  GetCitizenCertificateService getCitizenCertificateService;

  @Mock
  CitizenCertificateExistsService citizenCertificateExistsService;

  @InjectMocks
  CitizenController citizenController;

  @Test
  void shouldReturnGetCitizenCertificateResponse() {
    final var expected = GetCitizenCertificateResponse.builder().build();
    final var request = GetCitizenCertificateRequest.builder().build();
    when(getCitizenCertificateService.get(request, CERTIFICATE_ID)).thenReturn(expected);

    final var result = citizenController.getCertificate(request, CERTIFICATE_ID);

    assertEquals(expected, result);
  }

  @Test
  void shouldReturnExistCitizenCertificateResponse() {
    final var expected = CitizenCertificateExistsResponse.builder().build();
    when(citizenCertificateExistsService.exist(CERTIFICATE_ID)).thenReturn(expected);

    final var result = citizenController.findExistingCertificate(CERTIFICATE_ID);

    assertEquals(expected, result);
  }
}