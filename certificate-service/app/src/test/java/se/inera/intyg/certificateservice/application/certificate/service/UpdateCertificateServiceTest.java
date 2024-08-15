package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDate;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.converter.ElementCertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.UpdateCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.service.UpdateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;

@ExtendWith(MockitoExtension.class)
class UpdateCertificateServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  private static final String QUESTION_ID = "questionId";
  private static final ExternalReference EXTERNAL_REFERENCE = new ExternalReference(
      "externalReference");
  @Mock
  private UpdateCertificateRequestValidator updateCertificateRequestValidator;

  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private ElementCertificateConverter elementCertificateConverter;
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

  @Nested
  class ValidRequest {

    private CertificateDTO expectedCertificateDTO;
    private CertificateDTO certificateDTO;
    private ActionEvaluation actionEvaluation;
    private List<ElementData> elementDataList;
    @Mock
    private Certificate certificate;


    @BeforeEach
    void setUp() {
      actionEvaluation = ActionEvaluation.builder().build();

      final var elementData = ElementData.builder().build();
      elementDataList = List.of(elementData, elementData);

      final var resourceLinkDTO = ResourceLinkDTO.builder().build();
      final var unitDTO = UnitDTO.builder().build();
      certificateDTO = CertificateDTO.builder()
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

      expectedCertificateDTO = CertificateDTO.builder().build();

      doReturn(actionEvaluation).when(actionEvaluationFactory).create(
          ATHENA_REACT_ANDERSSON_DTO,
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      doReturn(elementDataList).when(elementCertificateConverter)
          .convert(certificateDTO);

      final var certificateAction = mock(CertificateAction.class);
      final List<CertificateAction> certificateActions = List.of(certificateAction);
      doReturn(certificateActions).when(certificate).actionsInclude(Optional.of(actionEvaluation));

      doReturn(resourceLinkDTO).when(resourceLinkConverter)
          .convert(certificateAction, Optional.of(certificate), actionEvaluation);
      doReturn(expectedCertificateDTO).when(certificateConverter)
          .convert(certificate, List.of(resourceLinkDTO), actionEvaluation);
    }

    @Test
    void shallReturnUpdateCertificateResponseWithoutExternalReference() {
      doReturn(certificate).when(updateCertificateDomainService).update(
          new CertificateId(CERTIFICATE_ID), elementDataList, actionEvaluation,
          new Revision(0), null);
      final var expectedResult = UpdateCertificateResponse.builder()
          .certificate(expectedCertificateDTO)
          .build();

      final var actualResult = updateCertificateService.update(
          UpdateCertificateRequest.builder()
              .user(AJLA_DOCTOR_DTO)
              .patient(ATHENA_REACT_ANDERSSON_DTO)
              .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
              .careUnit(ALFA_MEDICINCENTRUM_DTO)
              .careProvider(ALFA_REGIONEN_DTO)
              .certificate(certificateDTO)
              .build(),
          CERTIFICATE_ID);

      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shallReturnUpdateCertificateResponseWithExternalReference() {
      doReturn(certificate).when(updateCertificateDomainService).update(
          new CertificateId(CERTIFICATE_ID), elementDataList, actionEvaluation,
          new Revision(0), EXTERNAL_REFERENCE);
      final var expectedResult = UpdateCertificateResponse.builder()
          .certificate(expectedCertificateDTO)
          .build();

      final var actualResult = updateCertificateService.update(
          UpdateCertificateRequest.builder()
              .user(AJLA_DOCTOR_DTO)
              .patient(ATHENA_REACT_ANDERSSON_DTO)
              .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
              .careUnit(ALFA_MEDICINCENTRUM_DTO)
              .careProvider(ALFA_REGIONEN_DTO)
              .certificate(certificateDTO)
              .externalReference(EXTERNAL_REFERENCE.value())
              .build(),
          CERTIFICATE_ID);

      assertEquals(expectedResult, actualResult);
    }
  }
}
