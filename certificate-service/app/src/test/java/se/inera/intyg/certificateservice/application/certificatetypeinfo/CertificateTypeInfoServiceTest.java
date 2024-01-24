package se.inera.intyg.certificateservice.application.certificatetypeinfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.model.CertificateTypeInfo;
import se.inera.intyg.certificateservice.model.ResourceLink;
import se.inera.intyg.certificateservice.model.ResourceLinkType;
import se.inera.intyg.certificateservice.service.GetActiveCertificateTypeInfoService;

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
  CertificateTypeInfoConverter certificateTypeInfoConverter;
  @Mock
  GetActiveCertificateTypeInfoService getActiveCertificateTypeInfoService;

  @InjectMocks
  CertificateTypeInfoService certificateTypeInfoService;

  @Test
  void shallReturnListOfCertificateTypeInfoDTO() {
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

    when(getActiveCertificateTypeInfoService.get()).thenReturn(certificateTypeInfos);
    when(certificateTypeInfoConverter.convert(certificateTypeInfos.get(0))).thenReturn(
        certificateTypeInfoDTO1);
    when(certificateTypeInfoConverter.convert(certificateTypeInfos.get(1))).thenReturn(
        certificateTypeInfoDTO2);

    final var result = certificateTypeInfoService.getActiveCertificateTypeInfos();

    assertEquals(expectedResult, result);
  }

  @NotNull
  private static CertificateTypeInfo getCertificateTypeInfo(String id) {
    return new CertificateTypeInfo(id, LABEL, ISSUER_TYPE_ID, DESCRIPTION, DETAILED_DESCRIPTION,
        List.of(new ResourceLink(
            ResourceLinkType.SIGN_CERTIFICATE, NAME, DESCRIPTION, BODY, true, TITLE)), MESSAGE);
  }
}