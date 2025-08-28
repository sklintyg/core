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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REF;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PrefillXmlDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewExternalCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.RenewExternalCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.service.RenewExternalCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PlaceholderCertificateRequest;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.model.Inactive;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.domain.unit.model.UnitAddress;
import se.inera.intyg.certificateservice.domain.unit.model.UnitContactInfo;
import se.inera.intyg.certificateservice.domain.unit.model.UnitName;
import se.inera.intyg.certificateservice.domain.unit.model.WorkplaceCode;

@ExtendWith(MockitoExtension.class)
class RenewExternalCertificateServiceTest {

  @Mock
  private RenewExternalCertificateRequestValidator renewExternalCertificateRequestValidator;
  @Mock
  private ActionEvaluationFactory actionEvaluationFactory;
  @Mock
  private RenewExternalCertificateDomainService renewExternalCertificateDomainService;
  @Mock
  private CertificateConverter certificateConverter;
  @Mock
  private ResourceLinkConverter resourceLinkConverter;
  @InjectMocks
  private RenewExternalCertificateService renewExternalCertificateService;

  private static final String TYPE = "type";
  private static final String CERTIFICATE_ID = "certificateId";
  private static final String VERSION = "version";
  private static final RenewExternalCertificateRequest RENEW_EXTERNAL_CERTIFICATE_REQUEST = RenewExternalCertificateRequest.builder()
      .certificateModelId(
          CertificateModelIdDTO.builder()
              .type(TYPE)
              .version(VERSION)
              .build()
      )
      .user(AJLA_DOCTOR_DTO)
      .careProvider(ALFA_REGIONEN_DTO)
      .careUnit(ALFA_MEDICINCENTRUM_DTO)
      .unit(ALFA_ALLERGIMOTTAGNINGEN_DTO)
      .patient(ATHENA_REACT_ANDERSSON_DTO)
      .externalReference(EXTERNAL_REF)
      .status(CertificateStatusTypeDTO.SIGNED)
      .issuingUnit(ALFA_MEDICINCENTRUM_DTO)
      .prefillXml(new PrefillXmlDTO("xml"))
      .build();

  @Test
  void shallThrowIfRequestIsInvalid() {
    final var request = RenewExternalCertificateRequest.builder().build();

    doThrow(IllegalArgumentException.class).when(renewExternalCertificateRequestValidator)
        .validate(request, CERTIFICATE_ID);

    assertThrows(IllegalArgumentException.class,
        () -> renewExternalCertificateService.renew(request, CERTIFICATE_ID)
    );
  }

  @Test
  void shallReturnResponseWithRenewedCertificate() {
    final var resourceLinkDTO = ResourceLinkDTO.builder().build();
    final var expectedReponse = RenewCertificateResponse.builder()
        .certificate(
            CertificateDTO.builder()
                .links(List.of(resourceLinkDTO))
                .build()
        )
        .build();

    final var actionEvaluation = ActionEvaluation.builder().build();
    doReturn(actionEvaluation).when(actionEvaluationFactory).create(
        RENEW_EXTERNAL_CERTIFICATE_REQUEST.getPatient(),
        RENEW_EXTERNAL_CERTIFICATE_REQUEST.getUser(),
        RENEW_EXTERNAL_CERTIFICATE_REQUEST.getUnit(),
        RENEW_EXTERNAL_CERTIFICATE_REQUEST.getCareUnit(),
        RENEW_EXTERNAL_CERTIFICATE_REQUEST.getCareProvider()
    );
    final var placeHolderRequest = PlaceholderCertificateRequest.builder()
        .certificateId(new CertificateId(CERTIFICATE_ID))
        .status(Status.SIGNED)
        .certificateModelId(
            CertificateModelId.builder()
                .type(new CertificateType(
                    RENEW_EXTERNAL_CERTIFICATE_REQUEST.getCertificateModelId().getType()))
                .version(new CertificateVersion(
                    RENEW_EXTERNAL_CERTIFICATE_REQUEST.getCertificateModelId().getVersion()))
                .build()
        )
        .prefillXml(new Xml("xml"))
        .issuingUnit(
            SubUnit.builder()
                .hsaId(new HsaId(ALFA_MEDICINCENTRUM_DTO.getId()))
                .name(new UnitName(ALFA_MEDICINCENTRUM_DTO.getName()))
                .address(
                    UnitAddress.builder()
                        .address(ALFA_MEDICINCENTRUM_DTO.getAddress())
                        .zipCode(ALFA_MEDICINCENTRUM_DTO.getZipCode())
                        .city(ALFA_MEDICINCENTRUM_DTO.getCity())
                        .build()
                )
                .contactInfo(
                    UnitContactInfo.builder()
                        .phoneNumber(ALFA_MEDICINCENTRUM_DTO.getPhoneNumber())
                        .email(ALFA_MEDICINCENTRUM_DTO.getEmail())
                        .build()
                )
                .inactive(new Inactive(ALFA_MEDICINCENTRUM_DTO.getInactive()))
                .workplaceCode(new WorkplaceCode(ALFA_MEDICINCENTRUM_DTO.getWorkplaceCode()))
                .build()
        )
        .build();

    final var certificate = mock(MedicalCertificate.class);
    doReturn(certificate).when(renewExternalCertificateDomainService).renew(
        actionEvaluation,
        new ExternalReference(EXTERNAL_REF),
        placeHolderRequest
    );

    final var certificateAction = mock(CertificateAction.class);
    final List<CertificateAction> certificateActions = List.of(certificateAction);
    doReturn(certificateActions).when(certificate).actionsInclude(Optional.of(actionEvaluation));

    doReturn(resourceLinkDTO).when(resourceLinkConverter).convert(certificateAction,
        Optional.of(certificate), actionEvaluation);

    doReturn(expectedReponse.getCertificate()).when(certificateConverter)
        .convert(certificate, List.of(resourceLinkDTO), actionEvaluation);

    final var actualResponse = renewExternalCertificateService.renew(
        RENEW_EXTERNAL_CERTIFICATE_REQUEST, CERTIFICATE_ID);
    assertEquals(expectedReponse, actualResponse);
  }
}