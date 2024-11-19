package se.inera.intyg.certificateservice.application.citizen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.citizen.dto.CitizenCertificateExistsResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateListRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateListResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.PrintCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.PrintCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.service.CitizenCertificateExistsService;
import se.inera.intyg.certificateservice.application.citizen.service.GetCitizenCertificateListService;
import se.inera.intyg.certificateservice.application.citizen.service.GetCitizenCertificateService;
import se.inera.intyg.certificateservice.application.citizen.service.PrintCitizenCertificateService;
import se.inera.intyg.certificateservice.application.citizen.service.SendCitizenCertificateService;

@ExtendWith(MockitoExtension.class)
class CitizenControllerTest {

  private static final String CERTIFICATE_ID = "CERTIFICATE_ID";
  private static final CertificateDTO CERTIFICATE = CertificateDTO.builder().build();
  @Mock
  SendCitizenCertificateService sendCitizenCertificateService;
  @Mock
  GetCitizenCertificateService getCitizenCertificateService;

  @Mock
  CitizenCertificateExistsService citizenCertificateExistsService;

  @Mock
  GetCitizenCertificateListService citizenCertificateListService;

  @Mock
  PrintCitizenCertificateService printCitizenCertificateService;

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

  @Test
  void shouldReturnCitizenCertificateListResponse() {
    final var request = GetCitizenCertificateListRequest.builder().build();
    final var expected = GetCitizenCertificateListResponse.builder()
        .citizenCertificates(List.of(CERTIFICATE))
        .build();

    when(citizenCertificateListService.get(request)).thenReturn(expected);

    final var result = citizenController.getCertificateList(request);

    assertEquals(expected, result);
  }

  @Test
  void shouldReturnPrintCitizenCertificateResponse() {
    final var request = PrintCitizenCertificateRequest.builder().build();
    final var expected = PrintCitizenCertificateResponse.builder().build();

    when(printCitizenCertificateService.get(request, CERTIFICATE_ID)).thenReturn(expected);

    final var result = citizenController.printCertificate(request, CERTIFICATE_ID);

    assertEquals(expected, result);
  }

  @Test
  void shouldReturnSendCitizenCertificateResponse() {
    final var request = SendCitizenCertificateRequest.builder().build();
    final var expected = SendCitizenCertificateResponse.builder().build();

    when(sendCitizenCertificateService.send(request, CERTIFICATE_ID)).thenReturn(expected);

    final var result = citizenController.sendCertificate(request, CERTIFICATE_ID);

    assertEquals(expected, result);
  }
}