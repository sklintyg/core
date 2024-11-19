package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_VERSION;

import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateExternalTypeVersionResponse;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.model.Code;

@ExtendWith(MockitoExtension.class)
class GetLatestCertificateExternalTypeVersionServiceTest {

  private static final String CODE = "code";
  private static final String CODE_SYSTEM = "codeSystem";
  private static final CertificateModel CERTIFICATE_MODEL = CertificateModel.builder()
      .id(
          CertificateModelId.builder()
              .type(FK7210_TYPE)
              .version(FK7210_VERSION)
              .build()
      )
      .build();
  @Mock
  private CertificateModelRepository certificateModelRepository;

  @InjectMocks
  private GetLatestCertificateExternalTypeVersionService getLatestCertificateExternalTypeVersionService;

  @Nested
  class ValidateRequestTests {

    @Test
    void shallThrowIfCodeSystemIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> getLatestCertificateExternalTypeVersionService.get(null, CODE));
      assertTrue(
          illegalArgumentException.getMessage().contains("Required parameter missing: 'codeSystem'")
      );
    }

    @Test
    void shallThrowIfCodeSystemIsBlank() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> getLatestCertificateExternalTypeVersionService.get("", CODE));
      assertTrue(
          illegalArgumentException.getMessage().contains("Required parameter missing: 'codeSystem'")
      );
    }

    @Test
    void shallThrowIfCodeIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> getLatestCertificateExternalTypeVersionService.get(CODE_SYSTEM, null));
      assertTrue(
          illegalArgumentException.getMessage().contains("Required parameter missing: 'code'")
      );
    }

    @Test
    void shallThrowIfCodeIsBlank() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> getLatestCertificateExternalTypeVersionService.get(CODE_SYSTEM, ""));
      assertTrue(
          illegalArgumentException.getMessage().contains("Required parameter missing: 'code'")
      );
    }
  }

  @Test
  void shallReturnGetLatestCertificateExternalTypeVersionResponseWithType() {
    doReturn(Optional.of(CERTIFICATE_MODEL)).when(certificateModelRepository)
        .findLatestActiveByExternalType(new Code(CODE, CODE_SYSTEM, null));

    final var response = getLatestCertificateExternalTypeVersionService.get(CODE_SYSTEM, CODE);
    assertEquals(FK7210_TYPE.type(), response.getCertificateModelId().getType());
  }


  @Test
  void shallReturnEmptyGetLatestCertificateExternalTypeVersionResponse() {
    final var expectedResponse = GetLatestCertificateExternalTypeVersionResponse.builder().build();
    doReturn(Optional.empty()).when(certificateModelRepository)
        .findLatestActiveByExternalType(new Code(CODE, CODE_SYSTEM, null));

    final var response = getLatestCertificateExternalTypeVersionService.get(CODE_SYSTEM, CODE);
    assertEquals(expectedResponse, response);
  }
}
