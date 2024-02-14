package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCategory;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDate;
import se.inera.intyg.certificateservice.application.certificate.service.validation.UpdateCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.service.UpdateCertificateDomainService;

@ExtendWith(MockitoExtension.class)
class UpdateCertificateServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  private static final String QUESTION_ID = "questionId";
  @Mock
  private UpdateCertificateRequestValidator updateCertificateRequestValidator;

  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private ElementDataConverter elementDataConverter;

  @Mock
  private ElementMetaDataConverter elementMetaDataConverter;

  @Mock
  private UpdateCertificateDomainService updateCertificateDomainService;

  @Mock
  private CertificateConverter certificateConverter;

  @Mock
  private ResourceLinkConverter resourceLinkConverter;

  @InjectMocks
  private UpdateCertificateService updateCertificateService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = UpdateCertificateRequest.builder().build();
    doThrow(IllegalStateException.class).when(updateCertificateRequestValidator).validate(
        request,
        CERTIFICATE_ID
    );
    assertThrows(IllegalStateException.class,
        () -> updateCertificateService.update(request, CERTIFICATE_ID)
    );
  }

  @Test
  void shallReturnUpdateCertificateResponse() {
    final var resourceLinkDTO = ResourceLinkDTO.builder().build();
    final var unitDTO = UnitDTO.builder().build();
    final var expectedCertificate = CertificateDTO.builder()
        .data(
            Map.of(QUESTION_ID, CertificateDataElement.builder()
                .config(CertificateDataConfigDate.builder()
                    .build())
                .build())
        )
        .links(
            List.of(resourceLinkDTO)
        )
        .metadata(
            CertificateMetadataDTO.builder()
                .unit(unitDTO)
                .build()
        )
        .build();

    final var expectedResult = UpdateCertificateResponse.builder()
        .certificate(expectedCertificate)
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();
    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        ATHENA_REACT_ANDERSSON_DTO,
        AJLA_DOCTOR_DTO,
        ALFA_ALLERGIMOTTAGNINGEN_DTO,
        ALFA_MEDICINCENTRUM_DTO,
        ALFA_REGIONEN_DTO
    );
    final var elementData = ElementData.builder().build();

    doReturn(elementData).when(elementDataConverter)
        .convert(QUESTION_ID, expectedCertificate.getData().get(QUESTION_ID));
    doReturn(elementData).when(elementMetaDataConverter).convert(unitDTO);

    final var elementDataList = List.of(elementData, elementData);

    final var certificate = mock(Certificate.class);

    doReturn(certificate).when(updateCertificateDomainService).update(
        new CertificateId(CERTIFICATE_ID), elementDataList, actionEvaluation
    );

    final var certificateAction = mock(CertificateAction.class);
    final List<CertificateAction> certificateActions = List.of(certificateAction);
    doReturn(certificateActions).when(certificate).actions(actionEvaluation);

    doReturn(resourceLinkDTO).when(resourceLinkConverter).convert(certificateAction);
    doReturn(expectedCertificate).when(certificateConverter)
        .convert(certificate, List.of(resourceLinkDTO));

    final var actualResult = updateCertificateService.update(
        UpdateCertificateRequest.builder()
            .user(AJLA_DOCTOR_DTO)
            .patient(ATHENA_REACT_ANDERSSON_DTO)
            .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
            .careUnit(ALFA_MEDICINCENTRUM_DTO)
            .careProvider(ALFA_REGIONEN_DTO)
            .certificate(expectedCertificate)
            .build(),
        CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallNotGenerateElementDataForCategories() {
    final var unitDTO = UnitDTO.builder().build();
    final var expectedCertificate = CertificateDTO.builder()
        .data(
            Map.of(QUESTION_ID, CertificateDataElement.builder()
                .config(
                    CertificateDataConfigCategory.builder().build()
                )
                .build())
        )
        .metadata(
            CertificateMetadataDTO.builder()
                .unit(unitDTO)
                .build()
        )
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();
    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        ATHENA_REACT_ANDERSSON_DTO,
        AJLA_DOCTOR_DTO,
        ALFA_ALLERGIMOTTAGNINGEN_DTO,
        ALFA_MEDICINCENTRUM_DTO,
        ALFA_REGIONEN_DTO
    );

    final var certificate = mock(Certificate.class);
    final var elementData = ElementData.builder().build();
    final var elementDataList = List.of(elementData);

    doReturn(elementData).when(elementMetaDataConverter).convert(unitDTO);
    doReturn(certificate).when(updateCertificateDomainService).update(
        new CertificateId(CERTIFICATE_ID), elementDataList, actionEvaluation
    );

    final var certificateAction = mock(CertificateAction.class);
    final List<CertificateAction> certificateActions = List.of(certificateAction);
    doReturn(certificateActions).when(certificate).actions(actionEvaluation);

    updateCertificateService.update(
        UpdateCertificateRequest.builder()
            .user(AJLA_DOCTOR_DTO)
            .patient(ATHENA_REACT_ANDERSSON_DTO)
            .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
            .careUnit(ALFA_MEDICINCENTRUM_DTO)
            .careProvider(ALFA_REGIONEN_DTO)
            .certificate(expectedCertificate)
            .build(),
        CERTIFICATE_ID);

    verifyNoInteractions(elementDataConverter);
  }
}
