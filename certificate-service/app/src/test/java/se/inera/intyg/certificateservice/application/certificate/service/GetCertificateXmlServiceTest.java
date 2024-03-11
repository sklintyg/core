package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.service.validation.GetCertificateXmlRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateXmlDomainService;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.user.model.User;

@ExtendWith(MockitoExtension.class)
class GetCertificateXmlServiceTest {

  private static final String CERTIFICATE_ID = "certificateId";
  private static final String XML = "XML";
  public static final GetCertificateXmlRequest REQUEST = GetCertificateXmlRequest.builder()
      .user(AJLA_DOCTOR_DTO)
      .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
      .careUnit(ALFA_MEDICINCENTRUM_DTO)
      .careProvider(ALFA_REGIONEN_DTO)
      .build();

  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private GetCertificateXmlRequestValidator getCertificateXmlRequestValidator;
  @Mock
  private GetCertificateXmlDomainService getCertificateXmlDomainService;
  @InjectMocks
  private GetCertificateXmlService getCertificateXmlService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = GetCertificateXmlRequest.builder().build();

    doThrow(IllegalArgumentException.class).when(getCertificateXmlRequestValidator)
        .validate(request, CERTIFICATE_ID);

    assertThrows(IllegalArgumentException.class,
        () -> getCertificateXmlService.get(request, CERTIFICATE_ID)
    );
  }

  @Nested
  class ValidRequest {

    @BeforeEach
    void setup() {
      final var actionEvaluation = ActionEvaluation.builder()
          .user(User.builder().role(Role.DOCTOR).build())
          .build();

      doReturn(actionEvaluation).when(actionEvaluationFactory).create(
          AJLA_DOCTOR_DTO,
          ALFA_ALLERGIMOTTAGNINGEN_DTO,
          ALFA_MEDICINCENTRUM_DTO,
          ALFA_REGIONEN_DTO
      );

      doReturn(XML).when(getCertificateXmlDomainService).get(
          new CertificateId(CERTIFICATE_ID),
          actionEvaluation
      );
    }

    @Test
    void shallReturnResponseWithXml() {
      final var expectedResponse = GetCertificateXmlResponse.builder()
          .xml(XML)
          .build();

      final var actualResult = getCertificateXmlService.get(
          REQUEST,
          CERTIFICATE_ID
      );

      assertEquals(expectedResponse, actualResult);
    }
  }
}