package se.inera.intyg.certificateservice.application.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.AnswerComplementRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.AnswerComplementResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateExistsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateReadyForSignRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateReadyForSignResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ComplementCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ComplementCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateDraftFromCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateDraftFromCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateCandidateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateCandidateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateEventsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificatePdfResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateXmlResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewExternalCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SendCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SendCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateWithoutSignatureRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ValidateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.config.ValidateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.service.AnswerComplementService;
import se.inera.intyg.certificateservice.application.certificate.service.CertificateExistsService;
import se.inera.intyg.certificateservice.application.certificate.service.ComplementCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.CreateCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.CreateDraftFromCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.DeleteCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.ForwardCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateCandidateService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateEventsService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificatePdfService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.GetCertificateXmlService;
import se.inera.intyg.certificateservice.application.certificate.service.RenewCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.RenewExternalCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.ReplaceCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.RevokeCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.SendCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.SetCertificateReadyForSignService;
import se.inera.intyg.certificateservice.application.certificate.service.SignCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.SignCertificateWithoutSignatureService;
import se.inera.intyg.certificateservice.application.certificate.service.UpdateCertificateService;
import se.inera.intyg.certificateservice.application.certificate.service.UpdateWithCertificateCandidateService;
import se.inera.intyg.certificateservice.application.certificate.service.ValidateCertificateService;

@ExtendWith(MockitoExtension.class)
class CertificateControllerTest {

  private static final String CERTIFICATE_ID = "certificateId";
  private static final String CANDICATE_CERTIFICATE_ID = "candidateCertificateId";
  private static final Long VERSION = 0L;
  @Mock
  private CreateDraftFromCertificateService createDraftFromCertificateService;
  @Mock
  private AnswerComplementService answerComplementService;
  @Mock
  private ValidateCertificateService validateCertificateService;
  @Mock
  private UpdateCertificateService updateCertificateService;
  @Mock
  private GetCertificateService getCertificateService;
  @Mock
  private CertificateExistsService certificateExistsService;
  @Mock
  private CreateCertificateService createCertificateService;
  @Mock
  private DeleteCertificateService deleteCertificateService;
  @Mock
  private GetCertificateXmlService getCertificateXmlService;
  @Mock
  private SignCertificateService signCertificateService;
  @Mock
  private SendCertificateService sendCertificateService;
  @Mock
  private SignCertificateWithoutSignatureService signCertificateWithoutSignatureService;
  @Mock
  private GetCertificatePdfService getCertificatePdfService;
  @Mock
  private RevokeCertificateService revokeCertificateService;
  @Mock
  private ReplaceCertificateService replaceCertificateService;
  @Mock
  private RenewCertificateService renewCertificateService;
  @Mock
  private RenewExternalCertificateService renewExternalCertificateService;
  @Mock
  private ComplementCertificateService complementCertificateService;
  @Mock
  private ForwardCertificateService forwardCertificateService;
  @Mock
  private GetCertificateEventsService getCertificateEventsService;
  @Mock
  private SetCertificateReadyForSignService setCertificateReadyForSignService;
  @Mock
  private GetCertificateCandidateService getCertificateCandidateService;
  @Mock
  private UpdateWithCertificateCandidateService updateWithCertificateCandidateService;
  @InjectMocks
  private CertificateController certificateController;

  @Test
  void shallReturnCreateCertificateResponse() {
    final var expectedResult = CreateCertificateResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();

    final var request = CreateCertificateRequest.builder().build();

    doReturn(expectedResult).when(createCertificateService).create(
        request
    );

    final var actualResult = certificateController.createCertificate(request);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnTrueIfCertificateExists() {
    final var expectedResult = CertificateExistsResponse.builder()
        .exists(true)
        .build();

    doReturn(expectedResult).when(certificateExistsService).exist(CERTIFICATE_ID);

    final var actualResult = certificateController.findExistingCertificate(CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnFalseIfCertificateDontExists() {
    final var expectedResult = CertificateExistsResponse.builder()
        .exists(false)
        .build();

    doReturn(expectedResult).when(certificateExistsService).exist(CERTIFICATE_ID);

    final var actualResult = certificateController.findExistingCertificate(CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetCertificateResponse() {
    final var expectedResult = GetCertificateResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();

    final var request = GetCertificateRequest.builder().build();

    doReturn(expectedResult).when(getCertificateService).get(
        request,
        CERTIFICATE_ID);

    final var actualResult = certificateController.getCertificate(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnUpdateCertificateResponse() {
    final var expectedResult = UpdateCertificateResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();

    final var request = UpdateCertificateRequest.builder().build();

    doReturn(expectedResult).when(updateCertificateService).update(request, CERTIFICATE_ID);

    final var actualResult = certificateController.updateCertificate(request,
        CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnDeleteCertificateResponse() {
    final var request = DeleteCertificateRequest.builder().build();
    final var expectedResult = DeleteCertificateResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();
    doReturn(expectedResult).when(deleteCertificateService)
        .delete(request, CERTIFICATE_ID, VERSION);

    final var actualResult = certificateController.deleteCertificate(request, CERTIFICATE_ID,
        VERSION);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnValidateCertificateResponse() {
    final var request = ValidateCertificateRequest.builder().build();
    final var expectedResult = ValidateCertificateResponse.builder()
        .validationErrors(Collections.emptyList())
        .build();

    doReturn(expectedResult).when(validateCertificateService).validate(request, CERTIFICATE_ID);

    final var actualResult = certificateController.validateCertificate(request,
        CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetCertificateXmlResponse() {
    final var request = GetCertificateXmlRequest.builder().build();
    final var expectedResult = GetCertificateXmlResponse.builder()
        .xml("XML")
        .build();
    doReturn(expectedResult).when(getCertificateXmlService).get(request, CERTIFICATE_ID);

    final var actualResult = certificateController.getCertificateXml(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnSignCertificateResponse() {
    final var request = SignCertificateRequest.builder().build();
    final var expectedResult = SignCertificateResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();
    doReturn(expectedResult).when(signCertificateService).sign(request, CERTIFICATE_ID, VERSION);

    final var actualResult = certificateController.signCertificate(request, CERTIFICATE_ID,
        VERSION);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnSignCertificateResponseWhenSigningWithoutSignature() {
    final var request = SignCertificateWithoutSignatureRequest.builder().build();
    final var expectedResult = SignCertificateResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();
    doReturn(expectedResult).when(signCertificateWithoutSignatureService)
        .sign(request, CERTIFICATE_ID, VERSION);

    final var actualResult = certificateController.signCertificateWithoutSignature(request,
        CERTIFICATE_ID, VERSION);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnSendCertificateResponse() {
    final var request = SendCertificateRequest.builder().build();
    final var expectedResult = SendCertificateResponse.builder()
        .certificate(CertificateDTO.builder().build())
        .build();

    doReturn(expectedResult).when(sendCertificateService).send(request, CERTIFICATE_ID);
    final var actualResult = certificateController.sendCertificate(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetCertificatePdfResponse() {
    final var request = GetCertificatePdfRequest.builder().build();
    final var expectedResult = GetCertificatePdfResponse.builder()
        .pdfData("pdf".getBytes())
        .fileName("fileName")
        .build();
    doReturn(expectedResult).when(getCertificatePdfService).get(request, CERTIFICATE_ID);

    final var actualResult = certificateController.getCertificatePdf(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnRevokeCertificateResponse() {
    final var request = RevokeCertificateRequest.builder().build();
    final var expectedResult = RevokeCertificateResponse.builder().build();
    doReturn(expectedResult).when(revokeCertificateService).revoke(request, CERTIFICATE_ID);

    final var actualResult = certificateController.revokeCertificate(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnReplaceCertificateResponse() {
    final var request = ReplaceCertificateRequest.builder().build();
    final var expectedResult = ReplaceCertificateResponse.builder().build();
    doReturn(expectedResult).when(replaceCertificateService).replace(request, CERTIFICATE_ID);

    final var actualResult = certificateController.replaceCertificate(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnRenewCertificateResponse() {
    final var request = RenewCertificateRequest.builder().build();
    final var expectedResult = RenewCertificateResponse.builder().build();
    doReturn(expectedResult).when(renewCertificateService).renew(request, CERTIFICATE_ID);

    final var actualResult = certificateController.renewCertificate(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnRenewExternalCertificateResponse() {
    final var request = RenewExternalCertificateRequest.builder().build();
    final var expectedResult = RenewCertificateResponse.builder().build();
    doReturn(expectedResult).when(renewExternalCertificateService).renew(request, CERTIFICATE_ID);

    final var actualResult = certificateController.renewExternalCertificate(request,
        CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnComplementCertificateResponse() {
    final var request = ComplementCertificateRequest.builder().build();
    final var expectedResult = ComplementCertificateResponse.builder().build();
    doReturn(expectedResult).when(complementCertificateService).complement(request, CERTIFICATE_ID);

    final var actualResult = certificateController.complementCertificate(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnAnswerComplementResponse() {
    final var request = AnswerComplementRequest.builder().build();
    final var expectedResult = AnswerComplementResponse.builder().build();
    doReturn(expectedResult).when(answerComplementService).answer(request, CERTIFICATE_ID);

    final var actualResult = certificateController.answerComplement(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnForwardCertificateResponse() {
    final var request = ForwardCertificateRequest.builder().build();
    final var expectedResult = ForwardCertificateResponse.builder().build();
    doReturn(expectedResult).when(forwardCertificateService).forward(request, CERTIFICATE_ID);

    final var actualResult = certificateController.forwardCertificate(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetCertificateEventsResponse() {
    final var request = GetCertificateEventsRequest.builder().build();
    final var expectedResult = GetCertificateEventsResponse.builder().build();
    doReturn(expectedResult).when(getCertificateEventsService).get(request, CERTIFICATE_ID);

    final var actualResult = certificateController.getCertificateEvents(request, CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnSetReadyForSignResponse() {
    final var request = CertificateReadyForSignRequest.builder().build();
    final var expectedResult = CertificateReadyForSignResponse.builder().build();
    doReturn(expectedResult).when(setCertificateReadyForSignService).set(request, CERTIFICATE_ID);

    final var actualResult = certificateController.setCertificateReadyForSign(request,
        CERTIFICATE_ID);

    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnCreateDraftFromCertificateResponse() {
    final var request = CreateDraftFromCertificateRequest.builder().build();
    final var expectedResult = CreateDraftFromCertificateResponse.builder().build();

    doReturn(expectedResult).when(createDraftFromCertificateService)
        .create(request, CERTIFICATE_ID);

    final var actualResult = certificateController.createDraftFromCertificate(request,
        CERTIFICATE_ID);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnGetCertificateCandidateResponse() {
    final var request = GetCertificateCandidateRequest.builder().build();
    final var expectedResult = GetCertificateCandidateResponse.builder().build();

    doReturn(expectedResult).when(getCertificateCandidateService)
        .get(request, CERTIFICATE_ID);

    final var actualResult = certificateController.getCertificateCandidate(request, CERTIFICATE_ID);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shallReturnUpdateWithCertificateCandidateResponse() {
    final var request = UpdateWithCertificateCandidateRequest.builder().build();
    final var expectedResult = UpdateWithCertificateCandidateResponse.builder().build();

    doReturn(expectedResult).when(updateWithCertificateCandidateService)
        .update(request, CERTIFICATE_ID, CANDICATE_CERTIFICATE_ID);

    final var actualResult = certificateController.updateWithCertificateCandidate(request,
        CERTIFICATE_ID, CANDICATE_CERTIFICATE_ID);
    assertEquals(expectedResult, actualResult);
  }
}