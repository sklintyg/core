package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.model.CertificateModel;
import se.inera.intyg.certificateservice.model.CertificateModelId;
import se.inera.intyg.certificateservice.model.CertificateType;
import se.inera.intyg.certificateservice.model.CertificateVersion;
import se.inera.intyg.certificateservice.repository.CertificateModelRepository;

@ExtendWith(MockitoExtension.class)
class CertificateTypeInfoServiceTest {

  private static final String TYPE_1 = "type1";
  private static final String TYPE_2 = "type2";
  private static final String DESCRIPTION = "description";
  private static final String NAME = "name";
  @Mock
  CertificateTypeInfoValidator certificateTypeInfoValidator;
  @Mock
  CertificateTypeInfoConverter certificateTypeInfoConverter;
  @Mock
  CertificateModelRepository certificateModelRepository;
  @InjectMocks
  CertificateTypeInfoService certificateTypeInfoService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var certificateTypeInfoRequest = GetCertificateTypeInfoRequest.builder().build();

    doThrow(IllegalArgumentException.class).when(
        certificateTypeInfoValidator).validate(certificateTypeInfoRequest);
    assertThrows(IllegalArgumentException.class,

        () -> certificateTypeInfoService.getActiveCertificateTypeInfos(certificateTypeInfoRequest));
  }

  @Test
  void shallNotThrowIfRequestIsInvalid() {
    final var certificateTypeInfoRequest = GetCertificateTypeInfoRequest.builder().build();

    certificateTypeInfoService.getActiveCertificateTypeInfos(certificateTypeInfoRequest);

    verify(certificateTypeInfoValidator).validate(certificateTypeInfoRequest);
  }

  @Test
  void shallReturnListOfCertificateTypeInfoDTO() {
    final var certificateTypeInfoRequest = GetCertificateTypeInfoRequest.builder()
        .build();
    final var certificateTypeInfoDTO1 = CertificateTypeInfoDTO.builder().type(TYPE_1).build();
    final var certificateTypeInfoDTO2 = CertificateTypeInfoDTO.builder().type(TYPE_2).build();
    final var expectedResult = List.of(
        certificateTypeInfoDTO1,
        certificateTypeInfoDTO2
    );

    final var certificateTypeInfos = List.of(
        getCertificateTypeInfo(TYPE_1),
        getCertificateTypeInfo(TYPE_2)
    );

    when(certificateModelRepository.findAllActive()).thenReturn(certificateTypeInfos);
    when(certificateTypeInfoConverter.convert(certificateTypeInfos.get(0))).thenReturn(
        certificateTypeInfoDTO1);
    when(certificateTypeInfoConverter.convert(certificateTypeInfos.get(1))).thenReturn(
        certificateTypeInfoDTO2);

    final var result = certificateTypeInfoService.getActiveCertificateTypeInfos(
        certificateTypeInfoRequest);

    assertEquals(expectedResult, result);
  }

  @NotNull
  private static CertificateModel getCertificateTypeInfo(String type) {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(new CertificateType(type))
                .version(new CertificateVersion("1.0"))
                .build()
        )
        .name(NAME)
        .description(DESCRIPTION)
        .activeFrom(LocalDateTime.now(ZoneId.systemDefault()))
        .build();
  }
}