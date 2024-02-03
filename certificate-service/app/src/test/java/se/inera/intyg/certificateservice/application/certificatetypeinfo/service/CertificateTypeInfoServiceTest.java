package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoResponse;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;

@ExtendWith(MockitoExtension.class)
class CertificateTypeInfoServiceTest {

  private static final String TYPE_1 = "type1";
  private static final String TYPE_2 = "type2";
  private static final List<CertificateAction> CERTIFICATE_ACTIONS = List.of(
      mock(CertificateAction.class)
  );
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  @Mock
  CertificateTypeInfoValidator certificateTypeInfoValidator;
  @Mock
  CertificateTypeInfoConverter certificateTypeInfoConverter;
  @Mock
  CertificateModelRepository certificateModelRepository;
  @Mock
  ActionEvaluationFactory actionEvaluationFactory;
  @InjectMocks
  CertificateTypeInfoService certificateTypeInfoService;

  final GetCertificateTypeInfoRequest certificateTypeInfoRequest = GetCertificateTypeInfoRequest.builder()
      .user(AJLA_DOCTOR_DTO)
      .careProvider(UnitDTO.builder().build())
      .careUnit(ALFA_MEDICINCENTRUM_DTO)
      .unit(UnitDTO.builder().build())
      .patient(ATHENA_REACT_ANDERSSON_DTO)
      .build();

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
    final var certificateTypeInfoDTO1 = CertificateTypeInfoDTO.builder().type(TYPE_1).build();
    final var certificateTypeInfoDTO2 = CertificateTypeInfoDTO.builder().type(TYPE_2).build();
    final var expectedResult = GetCertificateTypeInfoResponse.builder()
        .list(
            List.of(
                certificateTypeInfoDTO1,
                certificateTypeInfoDTO2
            )
        )
        .build();

    final var certificateModels = List.of(getCertifiateModel(), getCertifiateModel());

    when(actionEvaluationFactory.create(
        certificateTypeInfoRequest.getPatient(),
        certificateTypeInfoRequest.getUser(),
        certificateTypeInfoRequest.getUnit(),
        certificateTypeInfoRequest.getCareUnit(),
        certificateTypeInfoRequest.getCareProvider()
    )).thenReturn(ACTION_EVALUATION);
    when(certificateModelRepository.findAllActive()).thenReturn(certificateModels);
    when(certificateTypeInfoConverter.convert(certificateModels.get(0),
        CERTIFICATE_ACTIONS)).thenReturn(
        certificateTypeInfoDTO1);
    when(certificateTypeInfoConverter.convert(certificateModels.get(1),
        CERTIFICATE_ACTIONS)).thenReturn(
        certificateTypeInfoDTO2);

    final var result = certificateTypeInfoService.getActiveCertificateTypeInfos(
        certificateTypeInfoRequest);

    assertEquals(expectedResult, result);
  }

  @NotNull
  private static CertificateModel getCertifiateModel() {
    final var certificateModel = mock(CertificateModel.class);
    doReturn(CERTIFICATE_ACTIONS).when(certificateModel).actions(ACTION_EVALUATION);
    return certificateModel;
  }
}
