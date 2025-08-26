package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateWithoutSignatureRequest;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.SignCertificateWithoutSignatureRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.service.SignCertificateWithoutSignatureDomainService;

@ExtendWith(MockitoExtension.class)
class SignCertificateWithoutSignatureServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  private static final long VERSION = 99;
  private static final Certificate CERTIFICATE = mock(MedicalCertificate.class);
  private static final ResourceLinkDTO RESOURCE_LINK_DTO = ResourceLinkDTO.builder()
      .type(ResourceLinkTypeDTO.CREATE_CERTIFICATE)
      .build();
  private static final CertificateDTO CERTIFICATE_DTO = CertificateDTO.builder()
      .links(List.of(RESOURCE_LINK_DTO))
      .build();

  public static final SignCertificateWithoutSignatureRequest REQUEST = SignCertificateWithoutSignatureRequest.builder()
      .user(AJLA_DOCTOR_DTO)
      .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
      .careUnit(ALFA_MEDICINCENTRUM_DTO)
      .careProvider(ALFA_REGIONEN_DTO)
      .build();

  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private SignCertificateWithoutSignatureRequestValidator signCertificateWithoutSignatureRequestValidator;
  @Mock
  private SignCertificateWithoutSignatureDomainService signCertificateWithoutSignatureDomainService;
  @Mock
  private CertificateConverter certificateConverter;
  @Mock
  private ResourceLinkConverter resourceLinkConverter;
  @InjectMocks
  private SignCertificateWithoutSignatureService signCertificateWithoutSignatureService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    doThrow(IllegalArgumentException.class).when(signCertificateWithoutSignatureRequestValidator)
        .validate(REQUEST, CERTIFICATE_ID, VERSION);

    assertThrows(IllegalArgumentException.class,
        () -> signCertificateWithoutSignatureService.sign(REQUEST, CERTIFICATE_ID, VERSION)
    );
  }

  @Nested
  class ValidRequest {

    @BeforeEach
    void setup() {
      final var actionEvaluation = ActionEvaluation.builder().build();

      doReturn(actionEvaluation).when(actionEvaluationFactory).create(
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      doReturn(CERTIFICATE).when(signCertificateWithoutSignatureDomainService).sign(
          new CertificateId(CERTIFICATE_ID), new Revision(VERSION), actionEvaluation
      );

      final var certificateAction = mock(CertificateAction.class);
      final List<CertificateAction> certificateActions = List.of(certificateAction);
      doReturn(certificateActions).when(CERTIFICATE).actionsInclude(Optional.of(actionEvaluation));

      final var resourceLinkDTO = ResourceLinkDTO.builder()
          .type(ResourceLinkTypeDTO.CREATE_CERTIFICATE)
          .build();

      doReturn(resourceLinkDTO).when(resourceLinkConverter).convert(certificateAction,
          Optional.of(CERTIFICATE), actionEvaluation);
      doReturn(CERTIFICATE_DTO).when(certificateConverter)
          .convert(CERTIFICATE, List.of(resourceLinkDTO), actionEvaluation);
    }

    @Test
    void shallReturnResponseWithXml() {
      final var expectedResponse = SignCertificateResponse.builder()
          .certificate(CERTIFICATE_DTO)
          .build();

      final var actualResult = signCertificateWithoutSignatureService.sign(
          REQUEST,
          CERTIFICATE_ID,
          VERSION
      );

      assertEquals(expectedResponse, actualResult);
    }
  }
}