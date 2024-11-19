package se.inera.intyg.certificateservice.application.certificatetypeinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateExternalTypeVersionResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateTypeVersionResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.GetCertificateTypeInfoService;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.GetLatestCertificateExternalTypeVersionService;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.GetLatestCertificateTypeVersionService;

@ExtendWith(MockitoExtension.class)
class CertificateTypeInfoControllerTest {

  private static final String FK_7210 = "fk7210";
  private static final String VERSION = "1.0";
  private static final String CODE_SYSTEM = "codeSystem";
  private static final String CODE = "code";
  @Mock
  private GetCertificateTypeInfoService getCertificateTypeInfoService;
  @Mock
  private GetLatestCertificateExternalTypeVersionService getLatestCertificateExternalTypeVersionService;
  @Mock
  private GetLatestCertificateTypeVersionService getLatestCertificateTypeVersionService;
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

    when(getCertificateTypeInfoService.getActiveCertificateTypeInfos(
        certificateTypeInfoRequest)).thenReturn(expectedResult);

    final var result = certificateTypeInfoController.findActiveCertificateTypeInfos(
        certificateTypeInfoRequest);

    assertEquals(expectedResult, result);
  }

  @Test
  void shallReturnCertificateTypeAndVersion() {
    final var expectedResult = GetLatestCertificateTypeVersionResponse.builder()
        .certificateModelId(
            CertificateModelIdDTO.builder()
                .type(FK_7210)
                .version(VERSION)
                .build()
        )
        .build();

    when(getLatestCertificateTypeVersionService.get(FK_7210)).thenReturn(expectedResult);

    final var result = certificateTypeInfoController.findLatestCertificateTypeVersion(FK_7210);

    assertEquals(expectedResult, result);
  }

  @Test
  void shallReturnCertificateExternalTypeAndVersion() {
    final var expectedResult = GetLatestCertificateExternalTypeVersionResponse.builder()
        .certificateModelId(
            CertificateModelIdDTO.builder()
                .type(FK_7210)
                .version(VERSION)
                .build()
        )
        .build();

    when(getLatestCertificateExternalTypeVersionService.get(CODE_SYSTEM, CODE)).thenReturn(
        expectedResult);

    final var result = certificateTypeInfoController.findLatestCertificateExternalTypeVersion(
        CODE_SYSTEM, CODE
    );

    assertEquals(expectedResult, result);
  }
}
