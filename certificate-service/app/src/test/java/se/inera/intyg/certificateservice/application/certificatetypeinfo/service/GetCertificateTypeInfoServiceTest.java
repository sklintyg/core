package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoResponse;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.converter.CertificateTypeInfoConverter;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.validator.CertificateTypeInfoValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.service.ListAvailableCertificateModelsDomainService;

@ExtendWith(MockitoExtension.class)
class GetCertificateTypeInfoServiceTest {

  private static final String TYPE_1 = "type1";
  private static final String TYPE_2 = "type2";
  private static final List<CertificateAction> CERTIFICATE_ACTIONS = List.of(
      mock(CertificateAction.class)
  );
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();

  @Mock
  ListAvailableCertificateModelsDomainService availableCertificateModelsDomainService;
  @Mock
  CertificateTypeInfoValidator certificateTypeInfoValidator;

  @Mock
  CertificateTypeInfoConverter certificateTypeInfoConverter;

  @Mock
  ActionEvaluationFactory actionEvaluationFactory;

  @InjectMocks
  GetCertificateTypeInfoService getCertificateTypeInfoService;

  final GetCertificateTypeInfoRequest certificateTypeInfoRequest = GetCertificateTypeInfoRequest.builder()
      .user(AJLA_DOCTOR_DTO)
      .careProvider(ALFA_REGIONEN_DTO)
      .careUnit(ALFA_MEDICINCENTRUM_DTO)
      .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
      .patient(ATHENA_REACT_ANDERSSON_DTO)
      .build();

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var certificateTypeInfoRequest = GetCertificateTypeInfoRequest.builder().build();

    doThrow(IllegalArgumentException.class).when(
        certificateTypeInfoValidator).validate(certificateTypeInfoRequest);
    assertThrows(IllegalArgumentException.class,

        () -> getCertificateTypeInfoService.getActiveCertificateTypeInfos(
            certificateTypeInfoRequest));
  }

  @Test
  void shallNotThrowIfRequestIsInvalid() {
    final var certificateTypeInfoRequest = GetCertificateTypeInfoRequest.builder().build();

    getCertificateTypeInfoService.getActiveCertificateTypeInfos(certificateTypeInfoRequest);

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

    final var certificateModels = List.of(getCertificateModel(), getCertificateModel());

    when(actionEvaluationFactory.create(
        certificateTypeInfoRequest.getPatient(),
        certificateTypeInfoRequest.getUser(),
        certificateTypeInfoRequest.getUnit(),
        certificateTypeInfoRequest.getCareUnit(),
        certificateTypeInfoRequest.getCareProvider()
    )).thenReturn(ACTION_EVALUATION);
    when(availableCertificateModelsDomainService.getLatestVersions(ACTION_EVALUATION)).thenReturn(
        certificateModels);
    when(certificateTypeInfoConverter.convert(certificateModels.get(0),
        CERTIFICATE_ACTIONS, ACTION_EVALUATION)).thenReturn(
        certificateTypeInfoDTO1);
    when(certificateTypeInfoConverter.convert(certificateModels.get(1),
        CERTIFICATE_ACTIONS, ACTION_EVALUATION)).thenReturn(
        certificateTypeInfoDTO2);

    final var result = getCertificateTypeInfoService.getActiveCertificateTypeInfos(
        certificateTypeInfoRequest);

    assertEquals(expectedResult, result);
  }

  @NotNull
  private static CertificateModel getCertificateModel() {
    final var certificateModel = mock(CertificateModel.class);
    doReturn(CERTIFICATE_ACTIONS).when(certificateModel)
        .actionsInclude(Optional.of(ACTION_EVALUATION));
    return certificateModel;
  }
}
