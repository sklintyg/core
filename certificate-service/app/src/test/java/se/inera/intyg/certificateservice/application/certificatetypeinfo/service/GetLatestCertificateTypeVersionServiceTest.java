package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetLatestCertificateTypeVersionResponse;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;

@ExtendWith(MockitoExtension.class)
class GetLatestCertificateTypeVersionServiceTest {

  private static final String TYPE = "type";
  private static final String VERSION = "version";

  @Mock
  private CertificateModelRepository certificateModelRepository;
  @InjectMocks
  private GetLatestCertificateTypeVersionService getLatestCertificateTypeVersionService;

  @Test
  void shallThrowIfTypeIsNull() {
    assertThrows(IllegalArgumentException.class, () ->
        getLatestCertificateTypeVersionService.get(null));
  }

  @Test
  void shallThrowIfTypeIsEmpty() {
    assertThrows(IllegalArgumentException.class, () ->
        getLatestCertificateTypeVersionService.get(""));
  }

  @Test
  void shallReturnCertificateTypeVersionIfExists() {
    final var expectedResult = GetLatestCertificateTypeVersionResponse.builder()
        .certificateModelId(
            CertificateModelIdDTO.builder()
                .type(TYPE)
                .version(VERSION)
                .build()
        )
        .build();

    final var certificateModel = mock(CertificateModel.class);
    when(certificateModelRepository.findLatestActiveByType(new CertificateType(TYPE)))
        .thenReturn(Optional.of(certificateModel));
    when(certificateModel.id()).thenReturn(
        CertificateModelId.builder()
            .type(new CertificateType(TYPE))
            .version(new CertificateVersion(VERSION))
            .build());

    final var actualResult = getLatestCertificateTypeVersionService.get(TYPE);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnNullCertificateTypeVersionIfNotExists() {
    final var expectedResult = GetLatestCertificateTypeVersionResponse.builder()
        .build();

    when(certificateModelRepository.findLatestActiveByType(new CertificateType(TYPE)))
        .thenReturn(Optional.empty());

    final var actualResult = getLatestCertificateTypeVersionService.get(TYPE);

    assertEquals(expectedResult, actualResult);
  }
}
