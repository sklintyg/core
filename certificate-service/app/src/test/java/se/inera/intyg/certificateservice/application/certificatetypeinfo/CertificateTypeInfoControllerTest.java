package se.inera.intyg.certificateservice.application.certificatetypeinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.CertificateTypeInfoService;

@ExtendWith(MockitoExtension.class)
class CertificateTypeInfoControllerTest {

  @Mock
  private CertificateTypeInfoService certificateTypeInfoService;
  @InjectMocks
  private CertificateTypeInfoController certificateTypeInfoController;

  @Test
  void shallReturnListOfCertificateTypeInfo() {
    final var certificateTypeInfoRequest = GetCertificateTypeInfoRequest.builder()
        .build();
    final var expectedResult = GetCertificateTypeInfoResponse.builder()
        .list(
            List.of(
                CertificateTypeInfoDTO.builder().build(),
                CertificateTypeInfoDTO.builder().build()
            )
        )
        .build();

    when(certificateTypeInfoService.getActiveCertificateTypeInfos(
        certificateTypeInfoRequest)).thenReturn(expectedResult);

    final var result = certificateTypeInfoController.findActiveCertificateTypeInfos(
        certificateTypeInfoRequest);

    assertEquals(expectedResult, result);
  }
}
