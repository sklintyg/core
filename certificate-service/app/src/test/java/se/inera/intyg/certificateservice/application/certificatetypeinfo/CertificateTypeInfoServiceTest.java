package se.inera.intyg.certificateservice.application.certificatetypeinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.CertificateTypeInfoConverter;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.CertificateTypeInfoService;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.CertificateTypeInfoValidator;
import se.inera.intyg.certificateservice.model.CertificateModel;
import se.inera.intyg.certificateservice.model.ResourceLink;
import se.inera.intyg.certificateservice.model.ResourceLinkType;
import se.inera.intyg.certificateservice.repository.CertificateModelRepository;

@ExtendWith(MockitoExtension.class)
class CertificateTypeInfoServiceTest {

  private static final String ID_1 = "id1";
  private static final String ID_2 = "id2";
  private static final String LABEL = "label";
  private static final String ISSUER_TYPE_ID = "issuerTypeId";
  private static final String DESCRIPTION = "description";
  private static final String DETAILED_DESCRIPTION = "detailedDescription";
  private static final String NAME = "name";
  private static final String BODY = "body";
  private static final String TITLE = "title";
  private static final String MESSAGE = "message";
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
    final var certificateTypeInfoDTO1 = CertificateTypeInfoDTO.builder().id(ID_1).build();
    final var certificateTypeInfoDTO2 = CertificateTypeInfoDTO.builder().id(ID_2).build();
    final var expectedResult = List.of(
        certificateTypeInfoDTO1,
        certificateTypeInfoDTO2
    );

    final var certificateTypeInfos = List.of(
        getCertificateTypeInfo(ID_1),
        getCertificateTypeInfo(ID_2)
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
  private static CertificateModel getCertificateTypeInfo(String id) {
    return new CertificateModel(id, LABEL, ISSUER_TYPE_ID, DESCRIPTION, DETAILED_DESCRIPTION,
        List.of(new ResourceLink(
            ResourceLinkType.SIGN_CERTIFICATE, NAME, DESCRIPTION, BODY, true, TITLE)), MESSAGE);
  }
}