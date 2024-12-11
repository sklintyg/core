package se.inera.intyg.certificateprintservice.application.print;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateRequest;
import se.inera.intyg.certificateprintservice.application.print.dto.PrintCertificateResponse;

@ExtendWith(MockitoExtension.class)
class PrintControllerTest {

  @Mock
  GetPrintService getPrintService;
  @InjectMocks
  PrintController printController;

  @Test
  void shallReturnPrintCertificateResponse() {
    final var request = PrintCertificateRequest.builder().build();
    final var expectedResponse = PrintCertificateResponse.builder()
        .build();

    doReturn(expectedResponse).when(getPrintService).get(request);

    final var actualResponse = printController.get(request);
    assertEquals(expectedResponse, actualResponse);
  }
}